package com.lqh.wanandroid.activity

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ViewUtils
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.R


class TipActivity : FastTitleActivity() {

    override fun getContentLayout(): Int {
        return R.layout.activity_tip
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

        setOnClickListener(
            R.id.sbt_loading,
            R.id.sbt_warning,
            R.id.sbt_success,
            R.id.sbt_error,
            R.id.sbt_get_token
        )

    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.setTitleMainText("演示各种提示弹窗")
    }

    override fun onClick(view: View) {
        super.onClick(view)
        when (view?.id) {
            R.id.sbt_loading -> {

                showLoadingView("加载中,请稍等...")

                ViewUtils.runOnUiThreadDelayed({ hideLoadingView() }, 2000)
            }
            R.id.sbt_warning -> {
                showWarningDialog("请输入正确密码!")
            }
            R.id.sbt_success -> {
                showSuccessDialog("登录成功!")
            }
            R.id.sbt_error -> {
                showErrorDialog("账号已被封禁!")
            }
            R.id.sbt_get_token -> {


                MaterialDialog(this).show {
//                    title(text = "标题啊")
//                    message(text = "我是内容呀晕晕晕晕晕晕晕晕晕晕晕呀奥")
//                    positiveButton(text = "Hello")
//                    negativeButton(text = "How ?")
//                    neutralButton(text = "Testing ")
                    customView(    //自定义弹窗
                        viewRes = R.layout.layout_base_dialog_wait,//自定义文件
                        scrollable = true        //让自定义宽高生效
                    )
//                    setTheme(R.style.customDialogBg)
//                    onShow {
//                        val actionButton = it.getActionButton(WhichButton.POSITIVE);
//                        it.getActionButton(WhichButton.POSITIVE).apply {
//                            setTextColor(ColorUtils.getColor(R.color.purple_200))
//
//                        }
//                    }
                    lifecycleOwner(this@TipActivity)
                }
            }
        }
    }
}
