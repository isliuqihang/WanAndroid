package com.lqh.fastlibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.lqh.fastlibrary.i.ActivityDispatchEventControl;
import com.lqh.fastlibrary.i.ActivityFragmentControl;
import com.lqh.fastlibrary.i.ActivityKeyEventControl;
import com.lqh.fastlibrary.i.FastObserverControl;
import com.lqh.fastlibrary.i.FastRecyclerViewControl;
import com.lqh.fastlibrary.i.HttpRequestControl;
import com.lqh.fastlibrary.i.LoadMoreFoot;
import com.lqh.fastlibrary.i.LoadingDialog;
import com.lqh.fastlibrary.i.QuitAppControl;
import com.lqh.fastlibrary.i.TitleBarViewControl;
import com.lqh.fastlibrary.i.ToastControl;
import com.lqh.fastlibrary.manager.GlideManager;
import com.lqh.fastlibrary.retrofit.FastLoadingObserver;
import com.lqh.fastlibrary.retrofit.FastObserver;
import com.lqh.fastlibrary.utils.FastUtil;
import com.lqh.fastlibrary.utils.ToastUtil;
import com.lqh.fastlibrary.view.FastLoadDialog;
import com.lqh.fastlibrary.view.core.progress.UIProgressDialog;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;


/**
 * @Author: AriesHoo on 2018/7/30 13:49
 * @E-Mail: AriesHoo@126.com
 * Function: 各种UI相关配置属性
 * Description:
 * 1、2018-9-26 16:58:14 新增BasisActivity 子类前台监听按键事件
 */
public class FastManager {

    //原本在Provider中默认进行初始化,如果app出现多进程使用该模式可避免调用异常出现
    static {
        Application application = FastUtil.getApplication();
        if (application != null) {
            Log.i("FastManager", "initSuccess");
            init(application);
        }
    }

    private static volatile FastManager sInstance;

    private FastManager() {
    }

    public static FastManager getInstance() {
        if (sInstance == null) {
            throw new NullPointerException(FastConstant.EXCEPTION_NOT_INIT_FAST_MANAGER);
        }
        return sInstance;
    }

    private static Application mApplication;
    /**
     * Adapter加载更多View
     */
    private LoadMoreFoot mLoadMoreFoot;
    /**
     * 全局设置列表
     */
    private FastRecyclerViewControl mFastRecyclerViewControl;
    /**
     * SmartRefreshLayout默认刷新头
     */
    private DefaultRefreshHeaderCreator mDefaultRefreshHeader;
    /**
     * 配置全局通用加载等待Loading提示框
     */
    private LoadingDialog mLoadingDialog;
    /**
     * 配置TitleBarView相关属性
     */
    private TitleBarViewControl mTitleBarViewControl;
    /**
     * 配置Activity/Fragment(背景+Activity强制横竖屏+Activity 生命周期回调+Fragment生命周期回调)
     */
    private ActivityFragmentControl mActivityFragmentControl;

    /**
     * 配置BasisActivity 子类前台时监听按键相关
     */
    private ActivityKeyEventControl mActivityKeyEventControl;

    /**
     * 配置BasisActivity 子类事件派发相关
     */
    private ActivityDispatchEventControl mActivityDispatchEventControl;
    /**
     * 配置网络请求
     */
    private HttpRequestControl mHttpRequestControl;

    /**
     * 配置{@link FastObserver#onError(Throwable)}全局处理
     */
    private FastObserverControl mFastObserverControl;
    /**
     * Activity 主页点击返回键控制
     */
    private QuitAppControl mQuitAppControl;
    /**
     * ToastUtil相关配置
     */
    private ToastControl mToastControl;

    public Application getApplication() {
        return mApplication;
    }

    /**
     * 滑动返回基础配置查看{@link FastLifecycleCallbacks#onActivityCreated(Activity, Bundle)}
     * 不允许外部调用
     *
     * @param application Application 对象
     * @return
     */
    static FastManager init(Application application) {
        Log.i("FastManager", "init_mApplication:" + mApplication + ";application;" + application);
        //保证只执行一次初始化属性
        if (mApplication == null && application != null) {
            mApplication = application;
            sInstance = new FastManager();
            //预设置FastLoadDialog属性
            sInstance.setLoadingDialog(activity -> new FastLoadDialog(activity,
                    new UIProgressDialog.WeBoBuilder(activity)
                            .setMessage(R.string.fast_loading)
                            .create()));
            //注册activity生命周期
            mApplication.registerActivityLifecycleCallbacks(new FastLifecycleCallbacks());
            //初始化Toast工具
            ToastUtil.init(mApplication);
            //初始化Glide
            GlideManager.setPlaceholderColor(ContextCompat.getColor(mApplication, R.color.colorPlaceholder));
            GlideManager.setPlaceholderRoundRadius(mApplication.getResources().getDimension(R.dimen.dp_placeholder_radius));
        }
        return getInstance();
    }


    public LoadMoreFoot getLoadMoreFoot() {
        return mLoadMoreFoot;
    }

    /**
     * 设置Adapter统一加载更多相关脚布局
     *
     * @param mLoadMoreFoot
     * @return
     */
    public FastManager setLoadMoreFoot(LoadMoreFoot mLoadMoreFoot) {
        this.mLoadMoreFoot = mLoadMoreFoot;
        return this;
    }

    public FastRecyclerViewControl getFastRecyclerViewControl() {
        return mFastRecyclerViewControl;
    }

    /**
     * 全局设置列表
     *
     * @param control
     * @return
     */
    public FastManager setFastRecyclerViewControl(FastRecyclerViewControl control) {
        this.mFastRecyclerViewControl = control;
        return this;
    }

    public DefaultRefreshHeaderCreator getDefaultRefreshHeader() {
        return mDefaultRefreshHeader;
    }

    /**
     * 设置SmartRefreshLayout 下拉刷新头
     *
     * @param control
     * @return
     */
    public FastManager setDefaultRefreshHeader(DefaultRefreshHeaderCreator control) {
        this.mDefaultRefreshHeader = control;
        return sInstance;
    }

    public LoadingDialog getLoadingDialog() {
        return mLoadingDialog;
    }

    /**
     * 设置全局网络请求等待Loading提示框如登录等待loading
     * 最终调用{@link FastLoadingObserver#FastLoadingObserver(Activity)}
     *
     * @param control
     * @return
     */
    public FastManager setLoadingDialog(LoadingDialog control) {
        if (control != null) {
            this.mLoadingDialog = control;
        }
        return this;
    }

    public TitleBarViewControl getTitleBarViewControl() {
        return mTitleBarViewControl;
    }

    public FastManager setTitleBarViewControl(TitleBarViewControl control) {
        mTitleBarViewControl = control;
        return this;
    }


    public ActivityFragmentControl getActivityFragmentControl() {
        return mActivityFragmentControl;
    }

    /**
     * 配置Activity/Fragment(背景+Activity强制横竖屏+Activity 生命周期回调+Fragment生命周期回调)
     *
     * @param control
     * @return
     */
    public FastManager setActivityFragmentControl(ActivityFragmentControl control) {
        mActivityFragmentControl = control;
        return this;
    }

    public ActivityKeyEventControl getActivityKeyEventControl() {
        return mActivityKeyEventControl;
    }

    /**
     * 配置BasisActivity 子类前台时监听按键相关
     *
     * @param control
     * @return
     */
    public FastManager setActivityKeyEventControl(ActivityKeyEventControl control) {
        mActivityKeyEventControl = control;
        return this;
    }

    public ActivityDispatchEventControl getActivityDispatchEventControl() {
        return mActivityDispatchEventControl;
    }

    /**
     * 配置BasisActivity 子类事件派发相关
     *
     * @param control
     * @return
     */
    public FastManager setActivityDispatchEventControl(ActivityDispatchEventControl control) {
        mActivityDispatchEventControl = control;
        return this;
    }

    public HttpRequestControl getHttpRequestControl() {
        return mHttpRequestControl;
    }

    /**
     * 配置Http请求成功及失败相关回调-方便全局处理
     *
     * @param control
     * @return
     */
    public FastManager setHttpRequestControl(HttpRequestControl control) {
        mHttpRequestControl = control;
        return this;
    }

    public FastObserverControl getFastObserverControl() {
        return mFastObserverControl;
    }

    /**
     * 配置{@link FastObserver#onError(Throwable)}全局处理
     *
     * @param control FastObserverControl对象
     * @return
     */
    public FastManager setFastObserverControl(FastObserverControl control) {
        mFastObserverControl = control;
        return this;
    }

    public QuitAppControl getQuitAppControl() {
        return mQuitAppControl;
    }

    /**
     * 配置Http请求成功及失败相关回调-方便全局处理
     *
     * @param control
     * @return
     */
    public FastManager setQuitAppControl(QuitAppControl control) {
        mQuitAppControl = control;
        return this;
    }

    public ToastControl getToastControl() {
        return mToastControl;
    }

    /**
     * 配置ToastUtil
     *
     * @param control
     * @return
     */
    public FastManager setToastControl(ToastControl control) {
        mToastControl = control;
        return this;
    }
}
