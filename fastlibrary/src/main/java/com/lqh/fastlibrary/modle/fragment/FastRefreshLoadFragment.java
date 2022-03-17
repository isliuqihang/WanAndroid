package com.lqh.fastlibrary.modle.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lqh.fastlibrary.base.BasisFragment;
import com.lqh.fastlibrary.delegate.FastRefreshLoadDelegate;
import com.lqh.fastlibrary.i.IFastRefreshLoadView;
import com.lqh.fastlibrary.i.IHttpRequestControl;
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;


/**
 * @Author: AriesHoo on 2018/7/20 16:55
 * @E-Mail: AriesHoo@126.com
 * Function:下拉刷新及上拉加载更多+多状态切换
 * Description:
 * 1、2018-7-20 16:55:45 设置StatusLayoutManager 目标View
 */
public abstract class FastRefreshLoadFragment<T>
        extends BasisFragment implements IFastRefreshLoadView<T> {
    protected SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected int mDefaultPage = 0;
    protected int mDefaultPageSize = 10;
    private BaseQuickAdapter mQuickAdapter;
    private Class<?> mClass;
    /**
     * 起始页
     */
    protected int mStartPage = 0;
    protected FastRefreshLoadDelegate<T> mFastRefreshLoadDelegate;
    private MultiStateContainer mMultiStateContainer;

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        mClass = this.getClass();
        mFastRefreshLoadDelegate = new FastRefreshLoadDelegate<>(mContentView, this, mClass);
        mRecyclerView = mFastRefreshLoadDelegate.mRecyclerView;
        mRefreshLayout = mFastRefreshLoadDelegate.mRefreshLayout;
        mMultiStateContainer = mFastRefreshLoadDelegate.mMultiStateContainer;
        mQuickAdapter = mFastRefreshLoadDelegate.mAdapter;
        mFastRefreshLoadDelegate.setLoadMore(isLoadMoreEnable());
        mStartPage = mDefaultPage;

    }

    @Override
    public IHttpRequestControl getIHttpRequestControl() {
        return new IHttpRequestControl() {
            @Override
            public SmartRefreshLayout getRefreshLayout() {
                return mRefreshLayout;
            }

            @Override
            public BaseQuickAdapter getRecyclerAdapter() {
                return mQuickAdapter;
            }

            /**
             * 获取多布局状态管理
             *
             * @return
             */
            @Override
            public MultiStateContainer getMultiStateContainer() {
                return mMultiStateContainer;
            }


            @Override
            public int getCurrentPage() {
                return mDefaultPage;
            }

            /**
             * 获取默认起始页
             *
             * @return
             */
            @Override
            public int getStartPage() {
                return mStartPage;
            }

            @Override
            public int getPageSize() {
                return mDefaultPageSize;
            }

            @Override
            public Class<?> getRequestClass() {
                return mClass;
            }
        };
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mDefaultPage = 0;
        loadData(mDefaultPage);
    }

    @Override
    public void onLoadMore() {
        loadData(++mDefaultPage);
    }

    @Override
    public void loadData() {
        loadData(mDefaultPage);
    }

    @Override
    public void onDestroy() {
        if (mFastRefreshLoadDelegate != null) {
            mFastRefreshLoadDelegate.onDestroy();
        }
        super.onDestroy();
    }
}
