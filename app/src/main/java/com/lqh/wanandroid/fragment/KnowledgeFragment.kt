package com.lqh.wanandroid.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ArrayUtils
import com.lqh.fastlibrary.manager.TabLayoutManager
import com.lqh.fastlibrary.modle.fragment.FastTitleFragment
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.R
import java.util.*


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211116
 *     update: 1116
 *     version:1.0
 * </pre>
 */

class KnowledgeFragment : FastTitleFragment() {
    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.fragement_knowledge
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

        val list = ArrayList<Fragment>()
        val titleList = ArrayList<String>()
        titleList.add("体系")
        titleList.add("导航")
        list.add(KnowledgeItemFragment())
        list.add(NaviFragment())
        TabLayoutManager.getInstance()?.setSlidingTabData(
            this as Fragment,
            findViewById(R.id.sld_tab_layout), findViewById(R.id.viewpager), titleList, list
        )

    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.visibility = View.GONE
    }
}