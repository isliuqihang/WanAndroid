package com.lqh.wanandroid.activity

import android.os.Bundle
import com.allen.library.SuperButton
import com.blankj.utilcode.util.LogUtils
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.fastlibrary.view.titanic.Titanic
import com.lqh.fastlibrary.view.titanic.TitanicTextView
import com.lqh.wanandroid.R


class DemonstrateActivity : FastTitleActivity() {
    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.activity_demonstrate
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {
        val my_text_view = findViewById<TitanicTextView>(R.id.my_text_view)
        var titanic = Titanic()
        findViewById<SuperButton>(R.id.sbt_start)?.setOnClickListener {
            LogUtils.d("开始动起来")
            titanic.start(my_text_view)
        }
        findViewById<SuperButton>(R.id.sbt_end)?.setOnClickListener {
            LogUtils.d("停止")
            titanic.cancel()
        }

    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
    }

}
