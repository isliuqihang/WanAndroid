package com.lqh.fastlibrary.delegate;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lqh.fastlibrary.FastManager;
import com.lqh.fastlibrary.R;
import com.lqh.fastlibrary.i.IFastRefreshLoadView;
import com.lqh.fastlibrary.view.FastLoadMoreView;
import com.lqh.fastlibrary.view.core.util.FindViewUtil;
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * @Author: AriesHoo on 2018/7/13 17:52
 * @E-Mail: AriesHoo@126.com
 * Function: 快速实现下拉刷新及上拉加载更多代理类
 * Description:
 * 1、使用StatusLayoutManager重构多状态布局功能
 * 2、2018-7-20 17:00:16 新增StatusLayoutManager 设置目标View优先级
 * 3、2018-7-20 17:44:30 新增StatusLayoutManager 点击事件处理
 * 4、2021-03-01 08:56:55 删除设置多状态布局设置点击颜色相关控制避免color设置失效
 */
public class FastRefreshLoadDelegate<T> {

    public SmartRefreshLayout mRefreshLayout;
    public RecyclerView mRecyclerView;
    public BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    public MultiStateContainer mMultiStateContainer;
    private IFastRefreshLoadView<T> mIFastRefreshLoadView;
    private FastRefreshDelegate mRefreshDelegate;
    private Context mContext;
    private FastManager mManager;
    public View mRootView;
    private Class<?> mTargetClass;

    public FastRefreshLoadDelegate(View rootView, IFastRefreshLoadView<T> iFastRefreshLoadView, Class<?> cls) {
        this.mRootView = rootView;
        this.mIFastRefreshLoadView = iFastRefreshLoadView;
        this.mTargetClass = cls;
        this.mContext = rootView.getContext().getApplicationContext();
        this.mManager = FastManager.getInstance();
        if (mIFastRefreshLoadView == null) {
            return;
        }
        mRefreshDelegate = new FastRefreshDelegate(rootView, iFastRefreshLoadView);
        mRefreshLayout = mRefreshDelegate.mRefreshLayout;
        getRecyclerView(rootView);
        initRecyclerView();
        setStatusManager();
    }

    /**
     * 初始化RecyclerView配置
     */
    protected void initRecyclerView() {
        if (mRecyclerView == null) {
            return;
        }
        if (FastManager.getInstance().getFastRecyclerViewControl() != null) {
            FastManager.getInstance().getFastRecyclerViewControl().setRecyclerView(mRecyclerView, mTargetClass);
        }
        mAdapter = mIFastRefreshLoadView.getAdapter();
        mRecyclerView.setLayoutManager(mIFastRefreshLoadView.getLayoutManager() == null ? new LinearLayoutManager(mContext) : mIFastRefreshLoadView.getLayoutManager());
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setAdapter(mAdapter);
        if (mAdapter != null) {
            setLoadMore(mIFastRefreshLoadView.isLoadMoreEnable());
            //先判断是否Activity/Fragment设置过;再判断是否有全局设置;最后设置默认
            //是否实现加载更多接口
            if (mAdapter instanceof LoadMoreModule) {
                if (mIFastRefreshLoadView.getLoadMoreView() != null) {
                    mAdapter.getLoadMoreModule().setLoadMoreView(mIFastRefreshLoadView.getLoadMoreView());
                } else {
                    if (mManager.getLoadMoreFoot() != null) {
                        mAdapter.getLoadMoreModule().setLoadMoreView(mManager.getLoadMoreFoot().createDefaultLoadMoreView(mAdapter));
                    } else {
                        mAdapter.getLoadMoreModule().setLoadMoreView(new FastLoadMoreView(mContext).getBuilder().build());
                    }
                }
            }

            if (mIFastRefreshLoadView.isItemClickEnable()) {
                mAdapter.setOnItemClickListener((adapter, view, position) -> mIFastRefreshLoadView.onItemClicked((BaseQuickAdapter<T, BaseViewHolder>) adapter, view, position));
            }
        }
    }

    public void setLoadMore(boolean enable) {
        if (mAdapter != null && mAdapter instanceof LoadMoreModule) {
            mAdapter.getLoadMoreModule().setEnableLoadMore(enable);
            ///加载更多监听
            if (enable) {
                mAdapter.getLoadMoreModule().setOnLoadMoreListener(mIFastRefreshLoadView);
            } else {
                mAdapter.getLoadMoreModule().setOnLoadMoreListener(null);
            }
        }
    }

    private void setStatusManager() {

        mMultiStateContainer.setOnRetryEventListener(multiStateContainer -> {
            mMultiStateContainer.showLoadingState();
            mIFastRefreshLoadView.onRefresh(mRefreshLayout);
        });

        mMultiStateContainer.showLoadingState();

        mIFastRefreshLoadView.setMultiStatusView(mMultiStateContainer);
    }

    /**
     * 获取布局RecyclerView
     *
     * @param rootView
     */
    private void getRecyclerView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv_contentFastLib);
        mMultiStateContainer = rootView.findViewById(R.id.multi_state_container);

        if (mRecyclerView == null) {
            mRecyclerView = FindViewUtil.getTargetView(rootView, RecyclerView.class);
        }
    }

    /**
     * 与Activity 及Fragment onDestroy 及时解绑释放避免内存泄露
     */
    public void onDestroy() {
        if (mRefreshDelegate != null) {
            mRefreshDelegate.onDestroy();
            mRefreshDelegate = null;
        }
        mRefreshLayout = null;
        mRecyclerView = null;
        mAdapter = null;
        mMultiStateContainer = null;
        mIFastRefreshLoadView = null;
        mContext = null;
        mManager = null;
        mRootView = null;
        mTargetClass = null;
    }
}
