package com.lqh.wanandroid.activity

import android.content.DialogInterface.OnShowListener
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.fastlibrary.view.sweetalertdialog.SweetAlertDialog
import com.lqh.wanandroid.R


/**
 * <pre>
 * description:
 * Created by: Lqh
 * date: 20210729
 * update: 0729
 * version:1.0
</pre> *
 */
class SweetDialogActivity : FastTitleActivity(), View.OnClickListener {
    private var i: Int = 0

    override fun getContentLayout(): Int {
        return R.layout.activity_sweet_dialog
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {
        val btnIds = intArrayOf(
            R.id.basic_test,
            R.id.styled_text_and_stroke,
            R.id.basic_test_without_buttons,
            R.id.under_text_test,
            R.id.error_text_test,
            R.id.success_text_test,
            R.id.warning_confirm_test,
            R.id.warning_cancel_test,
            R.id.custom_img_test,
            R.id.progress_dialog,
            R.id.neutral_btn_test,
            R.id.disabled_btn_test,
            R.id.custom_view_test,
            R.id.custom_btn_colors_test
        )
        for (id in btnIds) {
            findViewById<View>(id).setOnClickListener(this)
        }


//        getMultiStatusContentView()


    }




    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.setTitleMainText("演示Sweet Dialog用法")
    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View) {
        when (v?.id) {
            R.id.basic_test -> {

                val sd = SweetAlertDialog(this)
                sd.setCancelable(true)
                sd.setCanceledOnTouchOutside(true)
                sd.confirmButtonBackgroundColor = ColorUtils.getColor(R.color.blue_btn_bg_color)
                sd.contentText = "Here's a message"
                sd.show()
            }
            R.id.basic_test_without_buttons -> {

                val sd2 = SweetAlertDialog(this)
                sd2.setCancelable(true)
                sd2.setCanceledOnTouchOutside(true)
                sd2.contentText = "Here's a message"
                sd2.hideConfirmButton()
                    .autoDismissWithAnimation(3000)
                sd2.show()
            }
            R.id.under_text_test -> SweetAlertDialog(this)
                .setTitleText("Title")
                .setContentText("It's pretty, isn't it?")
                .show()
            R.id.styled_text_and_stroke -> SweetAlertDialog(this)
                .setTitleText("<font color='red'>Red</font> title")
                .setContentText("Big <font color='green'>green </font><b><i> bold</i></b>")
                .setContentTextSize(21)
                .setStrokeWidth(2F)
                .show()
            R.id.error_text_test -> SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setContentText("抢单失败请重试!")

                .hideConfirmButton()
                .show()
            R.id.success_text_test -> SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("抢单成功!")
                .setContentText("You clicked the button!")
                .show()
            R.id.warning_confirm_test -> SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelButton("Yes, delete it!") {
                    it.setTitleText("Deleted!")
                        .setContentText("Your imaginary file has been deleted!")
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                }
                .show()
            R.id.warning_cancel_test ->
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this file!")
                    .setCancelText("No, cancel pls!")
                    .setConfirmText("Yes, delete it!")
                    .showCancelButton(true)
                    .setCancelClickListener {
                        it.setTitleText("Cancelled!")
                            .setContentText("Your imaginary file is safe :)")
                            .setConfirmText("OK")
                            .showCancelButton(false)
                            .setCancelClickListener(null)
                            .setConfirmClickListener(null)
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE)
                    }
                    .setConfirmClickListener {
                        it.setTitleText("Deleted!")
                            .setContentText("Your imaginary file has been deleted!")
                            .setConfirmText("OK")
                            .showCancelButton(false)
                            .setCancelClickListener(null)
                            .setConfirmClickListener(null)
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                    }
                    .show()
            R.id.custom_img_test -> SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Sweet!")
                .setContentText("Here's a custom image.")
                .setCustomImage(R.drawable.ic_multi_error)
                .show()
            R.id.progress_dialog -> {
                val pDialog: SweetAlertDialog =
                    SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("加载中,请稍等...")

                pDialog.show()
                pDialog.setLoadingIndicator("BallScaleRippleMultipleIndicator")
                pDialog.autoDismissWithAnimation(4000)
                pDialog.setCancelable(false)
            }
            R.id.neutral_btn_test -> SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Title")
                .setContentText("Three buttons dialog")
                .setConfirmText("Confirm")
                .setCancelText("Cancel")
                .setNeutralText("Neutral")
                .show()
            R.id.disabled_btn_test -> {
                val disabledBtnDialog: SweetAlertDialog =
                    SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Title")
                        .setContentText("Disabled button dialog")
                        .setConfirmText("OK")
                        .setCancelText("Cancel")
                        .setNeutralText("Neutral")
                disabledBtnDialog.setOnShowListener(OnShowListener {
                    disabledBtnDialog.getButton(
                        SweetAlertDialog.BUTTON_CONFIRM
                    ).setEnabled(false)
                })
                disabledBtnDialog.show()
            }
            R.id.custom_view_test -> {
                val inflate: View = View.inflate(mContext, R.layout.layout_customview, null)
                val dialog: SweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Custom view")
                dialog.setCustomView(inflate)
                dialog.show()
            }
            R.id.custom_btn_colors_test -> SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Custom view")
                .setContentText("瓦哈卡哈肯萨克萨克萨克是")
                .setCancelButton("red", null)
                .setCancelButtonBackgroundColor(Color.RED)
                .setNeutralButton("cyan", null)
                .setNeutralButtonBackgroundColor(Color.CYAN)
                .setConfirmButton("blue", null)
                .setConfirmButtonBackgroundColor(Color.BLUE)
                .show()
            else -> {
            }
        }
    }

}