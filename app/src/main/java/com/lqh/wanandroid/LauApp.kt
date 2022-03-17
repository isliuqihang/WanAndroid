package com.lqh.wanandroid

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.lqh.fastlibrary.FastManager
import com.lqh.wanandroid.impl.ActivityControlImpl
import com.lqh.wanandroid.impl.AppImpl
import com.lqh.wanandroid.impl.HttpRequestControlImpl

/**
 * <pre>
 * description:
 * Created by: Lqh
 * date: 20210726
 * update: 0726
 * version:1.0
</pre> *
 */
class LauApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        context = this
        Utils.init(this)
        LogUtils.getConfig().globalTag = "WTF"

        initFastManager()
        RxHttpManager.init(this)
    }

    private fun initFastManager() {
        val impl = AppImpl(context)
        val activityControl = ActivityControlImpl()
        FastManager.getInstance() //设置Adapter加载更多视图--默认设置了FastLoadMoreView
            .setLoadMoreFoot(impl) //全局设置RecyclerView
            .setFastRecyclerViewControl(impl) //设置RecyclerView加载过程多布局属性
            //设置全局网络请求等待Loading提示框如登录等待loading--观察者必须为FastLoadingObserver及其子类
            .setLoadingDialog(impl) //设置SmartRefreshLayout刷新头-自定加载使用BaseRecyclerViewAdapterHelper
            .setDefaultRefreshHeader(impl) //设置全局TitleBarView相关配置
            .setTitleBarViewControl(impl) //设置Activity滑动返回控制-默认开启滑动返回功能不需要设置透明主题
            //设置Activity/Fragment相关配置(横竖屏+背景+虚拟导航栏+状态栏+生命周期)
            .setActivityFragmentControl(activityControl) //设置BasisActivity 子类按键监听
            .setActivityKeyEventControl(activityControl) //配置BasisActivity 子类事件派发相关
            .setActivityDispatchEventControl(activityControl) //设置http请求结果全局控制
            .setHttpRequestControl(HttpRequestControlImpl()) //配置{@link FastObserver#onError(Throwable)}全局处理
            .setFastObserverControl(impl) //设置主页返回键控制-默认效果为2000 毫秒时延退出程序
            .setQuitAppControl(impl).toastControl = impl
    }

    companion object {
        var context: Context? = null
            private set
    }
}