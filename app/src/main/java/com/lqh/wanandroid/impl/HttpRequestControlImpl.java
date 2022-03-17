package com.lqh.wanandroid.impl;

import android.accounts.AccountsException;
import android.accounts.NetworkErrorException;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.load.HttpException;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.lqh.fastlibrary.i.HttpRequestControl;
import com.lqh.fastlibrary.i.IHttpRequestControl;
import com.lqh.fastlibrary.i.OnHttpRequestListener;
import com.lqh.fastlibrary.utils.NetworkUtil;
import com.lqh.fastlibrary.utils.ToastUtil;
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer;
import com.lqh.wanandroid.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Author: AriesHoo on 2018/12/4 18:08
 * @E-Mail: AriesHoo@126.com
 * @Function: 网络请求成功/失败全局处理
 * @Description:
 */
public class HttpRequestControlImpl implements HttpRequestControl {

    private static String TAG = "HttpRequestControlImpl";
    private MultiStateContainer mMultiStateContainer;

    @Override

    public void httpRequestSuccess(IHttpRequestControl httpRequestControl, List<?> list, OnHttpRequestListener listener) {
        if (httpRequestControl == null) {
            return;
        }
        SmartRefreshLayout smartRefreshLayout = httpRequestControl.getRefreshLayout();
        BaseQuickAdapter adapter = httpRequestControl.getRecyclerAdapter();
        mMultiStateContainer = httpRequestControl.getMultiStateContainer();
        int size = httpRequestControl.getPageSize();
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
        }
        if (adapter == null) {
            return;
        }
        if (adapter instanceof LoadMoreModule) {
            adapter.getLoadMoreModule().loadMoreComplete();
        }

        int page = httpRequestControl.getCurrentPage();
        int startPage = httpRequestControl.getStartPage();
        if (list == null || list.size() == 0) {
            //第一页没有
            if (page == 0) {
                adapter.setNewInstance(new ArrayList());
                int headerLayoutCount = adapter.getHeaderLayoutCount();
                LogUtils.d("Adapter添加的头部view数量: " + headerLayoutCount);
                //如果列表添加的有头部view,当list为空的时候也不展示空布局
                if (headerLayoutCount == 0) {
                    mMultiStateContainer.showEmptyState();
                } else {
                    mMultiStateContainer.showSucceedState();
                }

                if (listener != null) {
                    listener.onEmpty();
                }
            } else {
                if (adapter instanceof LoadMoreModule) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                }
                if (listener != null) {
                    listener.onNoMore();
                }
            }
            return;
        }
        mMultiStateContainer.showSucceedState();
        if (smartRefreshLayout.getState() == RefreshState.Refreshing || page == 0) {
            adapter.setNewInstance(new ArrayList());
        }
        adapter.addData(list);
        if (listener != null) {
            listener.onNext();
        }
        if (list.size() < size) {
            if (adapter instanceof LoadMoreModule) {
                adapter.getLoadMoreModule().loadMoreEnd();
            }
            if (listener != null) {
                listener.onNoMore();
            }
        }


    }


    @Override
    public void httpRequestSuccessWithCustomEmptyMsg(IHttpRequestControl httpRequestControl, List<?> list, String emptyMsg, OnHttpRequestListener listener) {
        if (httpRequestControl == null) {
            return;
        }
        SmartRefreshLayout smartRefreshLayout = httpRequestControl.getRefreshLayout();
        BaseQuickAdapter adapter = httpRequestControl.getRecyclerAdapter();
        mMultiStateContainer = httpRequestControl.getMultiStateContainer();
        int page = httpRequestControl.getCurrentPage();
        int size = httpRequestControl.getPageSize();

        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
        }
        if (adapter == null) {
            return;
        }
        if (adapter instanceof LoadMoreModule) {
            adapter.getLoadMoreModule().loadMoreComplete();
        }
        if (list == null || list.size() == 0) {
            //第一页没有
            if (page == 0) {
                adapter.setNewInstance(new ArrayList());
                int headerLayoutCount = adapter.getHeaderLayoutCount();
                //如果列表添加的有头部view,当list为空的时候也不展示空布局

                if (headerLayoutCount == 0) {
                    mMultiStateContainer.showEmptyState(emptyMsg);
                } else {
                    mMultiStateContainer.showSucceedState();
                }

                if (listener != null) {
                    listener.onEmpty();
                }
            } else {
                if (adapter instanceof LoadMoreModule) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                }
                if (listener != null) {
                    listener.onNoMore();
                }
            }
            return;
        }
        mMultiStateContainer.showSucceedState();
        if (smartRefreshLayout.getState() == RefreshState.Refreshing || page == 0) {
            adapter.setNewInstance(new ArrayList());
        }
        adapter.addData(list);
        if (listener != null) {
            listener.onNext();
        }
        if (list.size() < size) {
            if (adapter instanceof LoadMoreModule) {
                adapter.getLoadMoreModule().loadMoreEnd();
            }
            if (listener != null) {
                listener.onNoMore();
            }
        }
    }

    @Override
    public void httpRequestError(IHttpRequestControl httpRequestControl, Throwable e) {
        LogUtils.e("httpRequestError:" + e.getMessage() + "   \n" + e.toString() + e.getClass());
        int reason = R.string.fast_exception_other_error;

        if (!NetworkUtil.isConnected(Utils.getApp())) {
            reason = R.string.fast_exception_network_not_connected;
        } else {
            //网络异常--继承于AccountsException
            if (e instanceof NetworkErrorException) {
                reason = R.string.fast_exception_network_error;
                //账户异常
            } else if (e instanceof AccountsException) {
                reason = R.string.fast_exception_accounts;
                //连接异常--继承于SocketException
            } else if (e instanceof ConnectException) {
                reason = R.string.fast_exception_connect;
                //socket异常
            } else if (e instanceof SocketException) {
                reason = R.string.fast_exception_socket;
                // http异常
            } else if (e instanceof HttpException) {
                reason = R.string.fast_exception_http;
                //DNS错误
            } else if (e instanceof UnknownHostException) {
                reason = R.string.fast_exception_unknown_host;
            } else if (e instanceof JsonSyntaxException
                    || e instanceof JsonIOException
                    || e instanceof JsonParseException) {
                //数据格式化错误
                reason = R.string.fast_exception_json_syntax;
            } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
                reason = R.string.fast_exception_time_out;
            } else if (e instanceof ClassCastException) {
                reason = R.string.fast_exception_class_cast;
            }
        }
        if (httpRequestControl == null || httpRequestControl.getMultiStateContainer() == null) {
            ToastUtil.showWarning(reason);
            return;
        }
        SmartRefreshLayout smartRefreshLayout = httpRequestControl.getRefreshLayout();
        BaseQuickAdapter adapter = httpRequestControl.getRecyclerAdapter();
        MultiStateContainer multiStateContainer = httpRequestControl.getMultiStateContainer();

        int page = httpRequestControl.getCurrentPage();
        int startPage = httpRequestControl.getStartPage();
        LogUtils.d("列表起始页: " + startPage + "   \n列表当前页: " + page);
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh(false);
        }

        if (adapter != null) {
            if (adapter instanceof LoadMoreModule) {
                adapter.getLoadMoreModule().loadMoreFail();
            }
            if (multiStateContainer == null) {
                return;
            }
            //初始页
            if (page == 0) {
                multiStateContainer.showErrorState(StringUtils.getString(reason));
                return;
            }

        } else {
            LogUtils.d("啥事好好说和");
        }


    }

    /**
     * 网络成功后执行
     *
     * @param httpRequestControl 调用页面相关参数
     * @param errorMsg           错误信息
     */
    @Override
    public void httpRequestError(IHttpRequestControl httpRequestControl, @NotNull String errorMsg) {
        if (httpRequestControl == null || httpRequestControl.getMultiStateContainer() == null) {
            ToastUtil.showWarning(errorMsg);
            return;
        }
        SmartRefreshLayout smartRefreshLayout = httpRequestControl.getRefreshLayout();
        BaseQuickAdapter adapter = httpRequestControl.getRecyclerAdapter();
        MultiStateContainer multiStateContainer = httpRequestControl.getMultiStateContainer();

        int page = httpRequestControl.getCurrentPage();
        int startPage = httpRequestControl.getStartPage();

        LogUtils.d("列表起始页: " + startPage + "   \n列表当前页: " + page);

        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh(false);
        }

        if (adapter != null) {
            if (adapter instanceof LoadMoreModule) {
                adapter.getLoadMoreModule().loadMoreFail();
            }
            if (multiStateContainer == null) {
                return;
            }
            //初始页
            if (page == 0) {
                multiStateContainer.showErrorState(errorMsg);
                return;
            }
        }
    }
}
