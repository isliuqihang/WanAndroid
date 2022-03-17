package com.lqh.fastlibrary.i

import android.os.Bundle
import androidx.annotation.LayoutRes

/**
 * <pre>
 * description: Basis Activity及Fragment通用属性
 * Created by: Lqh
 * date: 20210729
 * update: 0729
 * version:1.0
</pre> *
 */
interface IBasisView {
    /**
     * 是否注册EventBus
     *
     * @return
     */
    val isEventBusEnable: Boolean
        get() = true

    /**
     * 是否使用沉浸式状态栏
     *
     * @return
     */
    fun applySystemBarDrawable(): Boolean {
        return true
    }

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    @LayoutRes
    fun getContentLayout(): Int

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    fun initView(savedInstanceState: Bundle?)

    /**
     * 执行加载布局文件之前操作方法前调用
     */
    fun beforeSetContentView() {}

    /**
     * 显示加载动画
     */
    fun showLoadingView() {}

    /**
     * 显示加载动画
     *
     * @param loadingText 加载提示文字
     */
    fun showLoadingView(loadingText: String?) {}

    /**
     * 隐藏加载动画
     */
    fun hideLoadingView() {}

    /**
     * 在初始化控件前进行一些操作
     *
     * @param savedInstanceState
     */
    fun beforeInitView(savedInstanceState: Bundle?) {}

    /**
     * 需要加载数据时重写此方法
     */
    fun loadData() {}
}