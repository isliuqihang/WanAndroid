package com.lqh.wanandroid.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.lqh.fastlibrary.manager.TabLayoutManager
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.utils.ToastUtil
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.IntentKey
import com.lqh.wanandroid.R
import com.lqh.wanandroid.entity.ChapterBean
import com.lqh.wanandroid.entity.NaviBean
import com.lqh.wanandroid.fragment.KnowledgeClassifyArticleFragment
import com.lqh.wanandroid.fragment.KnowledgeItemFragment
import com.lqh.wanandroid.fragment.NaviFragment
import java.util.*


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211117
 *     update: 1117
 *     version:1.0
 * </pre>
 */

class KnowledgeArticleActivity : FastTitleActivity() {
    lateinit var naviName: String
    private var classificationData: ChapterBean? = null
    private var currPos: Int = 0

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.activity_knowledge_classify
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

        classificationData = getSerializable(IntentKey.NAVI_DATA)
        currPos = getInt(IntentKey.NAVI_CURR_POS)
        naviName = getString(IntentKey.NAVI_NAME)!!

        initFragmentAbout();
    }

    private fun initFragmentAbout() {
        val fragmentList = ArrayList<Fragment>()
        val titleList = ArrayList<String>()
        classificationData?.children?.iterator()?.forEach {
            val title = it.name
            val chapterId = it.id
            titleList.add(title)
            fragmentList.add(KnowledgeClassifyArticleFragment.newInstance(chapterId, currPos))
        }


        TabLayoutManager.getInstance()?.setSlidingTabData(
            this ,
            findViewById(R.id.sld_tab_layout), findViewById(R.id.viewpager), titleList, fragmentList
        )
        findViewById<ViewPager>(R.id.viewpager).currentItem = currPos

    }


    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {

        titleBar?.setTitleMainText(naviName)
        ToastUtil.showSuccess("点击POS: " + naviName)

    }
}