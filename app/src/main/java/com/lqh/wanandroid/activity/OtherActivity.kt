package com.lqh.wanandroid.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.allen.library.SuperTextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ViewUtils
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer
import com.lqh.fastlibrary.view.multistatepage.MultiStatePage
import com.lqh.wanandroid.R


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211102
 *     update: 1102
 *     version:1.0
 * </pre>
 */

class OtherActivity : FastTitleActivity() {
    private lateinit var multiStateContainer: MultiStateContainer
    private var mStvTipToast: SuperTextView? = null
    private var mStvSweetDialog: SuperTextView? = null
    private var mStvList: SuperTextView? = null
    lateinit var lyMainContent: LinearLayoutCompat


    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.activity_other

    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {
        lyMainContent = findViewById(R.id.ly_main_content)
        mStvTipToast = findViewById<View>(R.id.stv_tipToast) as SuperTextView
        mStvSweetDialog = findViewById<View>(R.id.stv_sweetDialog) as SuperTextView
        mStvList = findViewById<View>(R.id.stv_list) as SuperTextView
        mStvTipToast?.setOnClickListener(this)
        mStvSweetDialog?.setOnClickListener(this)
        mStvList?.setOnClickListener(this)

        findViewById<SuperTextView>(R.id.stv_login).setOnClickListener(this)
        findViewById<SuperTextView>(R.id.stv_filter).setOnClickListener(this)

    }

    fun getMultiStatusContentView(): View {

        multiStateContainer = MultiStatePage.bindMultiState(
            lyMainContent,
            {
                it.showLoadingState()
                loadData()
            },
            "正在加载中..."
        )


        ViewUtils.runOnUiThreadDelayed({ multiStateContainer.showSucceedState() }, 1500)
        return multiStateContainer


    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.setTitleMainText("功能展示")
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.stv_tipToast -> {
                ActivityUtils.startActivity(TipActivity::class.java)
            }
            R.id.stv_sweetDialog -> ActivityUtils.startActivity(SweetDialogActivity::class.java)
            R.id.stv_list -> ActivityUtils.startActivity(RefreshListActivity::class.java)
            R.id.stv_login -> {
                ActivityUtils.startActivity(LoginActivity::class.java)
            }
            R.id.stv_filter -> {
                ActivityUtils.startActivity(FilterActivity::class.java)


            }

        }
    }
}