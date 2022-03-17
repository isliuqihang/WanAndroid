package com.lqh.wanandroid.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.allen.library.SuperTextView
import com.blankj.utilcode.util.ActivityUtils
import com.lqh.fastlibrary.modle.fragment.FastTitleFragment
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer
import com.lqh.wanandroid.R
import com.lqh.wanandroid.activity.*


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20220303
 *     update: 0303
 *     version:1.0
 * </pre>
 */

class FunctionFragment : FastTitleFragment() {
    private lateinit var multiStateContainer: MultiStateContainer
    private var mStvTipToast: SuperTextView? = null
    private var mStvSweetDialog: SuperTextView? = null
    private var mStvList: SuperTextView? = null
    private var mStvProtobuf: SuperTextView? = null
    lateinit var lyMainContent: LinearLayoutCompat

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.fragment_function
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
        findViewById<SuperTextView>(R.id.stv_ad).setOnClickListener(this)

        findViewById<SuperTextView>(R.id.stv_login).setOnClickListener(this)
        findViewById<SuperTextView>(R.id.stv_filter).setOnClickListener(this)
        findViewById<SuperTextView>(R.id.stv_protobuf).setOnClickListener(this)
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
            R.id.stv_ad -> {
                ActivityUtils.startActivity(AdActivity::class.java)
            }
            R.id.stv_protobuf -> {
                ActivityUtils.startActivity(ProtobufActivity::class.java)
            }

        }
    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.setTitleMainText("功能展示")
    }
}