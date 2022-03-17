package com.lqh.fastlibrary.modle.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lqh.fastlibrary.delegate.FastRefreshLoadDelegate;
import com.lqh.fastlibrary.delegate.FastTitleDelegate;
import com.lqh.fastlibrary.i.IFastRefreshLoadView;
import com.lqh.fastlibrary.i.IHttpRequestControl;
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;


/**
 * @Author: AriesHoo on 2018/7/20 16:54
 * @E-Mail: AriesHoo@126.com
 * Function:下拉刷新及上拉加载更多
 * Description:
 * 1、2018-7-9 09:50:59 修正Adapter 错误造成无法展示列表数据BUG
 * 2、2018-7-20 16:54:47 设置StatusLayoutManager 目标View
 * 3、2019-4-19 09:41:28 修改IFastTitleView 逻辑
 */
public abstract class FastRefreshLoadActivity<T>
        extends FastTitleActivity implements IFastRefreshLoadView<T> {
    protected SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    private BaseQuickAdapter mQuickAdapter;
    protected int mDefaultPage = 0;

    /**
     * 起始页
     */
    protected int mStartPage = 0;

    protected int mDefaultPageSize = 10;

    protected FastRefreshLoadDelegate<T> mFastRefreshLoadDelegate;
    private Class<?> mClass;
    private MultiStateContainer mMultiStateContainer;

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        mClass = getClass();
        new FastTitleDelegate(mContentView, this, getClass());
        mFastRefreshLoadDelegate = new FastRefreshLoadDelegate<>(mContentView, this, getClass());
        mRecyclerView = mFastRefreshLoadDelegate.mRecyclerView;
        mRefreshLayout = mFastRefreshLoadDelegate.mRefreshLayout;
        mMultiStateContainer = mFastRefreshLoadDelegate.mMultiStateContainer;
        mQuickAdapter = mFastRefreshLoadDelegate.mAdapter;
        mStartPage = mDefaultPage;

    }

    @Override
    public IHttpRequestControl getIHttpRequestControl() {
        IHttpRequestControl requestControl = new IHttpRequestControl() {
            @Override
            public SmartRefreshLayout getRefreshLayout() {
                return mRefreshLayout;
            }

            @Override
            public BaseQuickAdapter getRecyclerAdapter() {
                return mQuickAdapter;
            }

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
        return requestControl;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mDefaultPage = 0;
        mFastRefreshLoadDelegate.setLoadMore(isLoadMoreEnable());
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
    protected void onDestroy() {
        if (mFastRefreshLoadDelegate != null) {
            mFastRefreshLoadDelegate.onDestroy();
        }
        super.onDestroy();
    }
}
