package com.lqh.wanandroid.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.SizeUtils
import com.gyf.immersionbar.ImmersionBar
import com.lqh.fastlibrary.manager.TabLayoutManager
import com.lqh.fastlibrary.modle.fragment.FastTitleFragment
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer
import com.lqh.fastlibrary.view.multistatepage.MultiStatePage
import com.lqh.fastlibrary.view.tablayout.SlidingTabLayout
import com.lqh.wanandroid.R
import com.lqh.wanandroid.entity.WxarticleChaptersEntity
import com.lqh.wanandroid.network.ApiClient
import java.util.*


/**
 * <pre>
 *     description:微信公众号列表Fragment
 *     date:2022/2/21 0021
 *     update:2022/2/21 0021-16:20
 *     version:1.0
 *     @author LQH
 * </pre>
 */


class WechatPublicAccountFragment : FastTitleFragment() {
    private lateinit var multiStateContainer: MultiStateContainer
    var sldTabLayout: SlidingTabLayout? = null

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
//        return R.layout.fragement_wechat_public_account
        return R.layout.fragement_wechat_public_account1
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

        multiStateContainer = MultiStatePage.bindMultiState(
            findViewById(R.id.viewpager),
            { multiStateContainer: MultiStateContainer ->
                multiStateContainer.showLoadingState()
                loadData()
            },
            "正在获取数据..."
        )

    }

    override fun loadData() {

        val wechatPublicAccountListObservableLife =
            ApiClient.getWechatPublicAccountListObservableLife()
        wechatPublicAccountListObservableLife
            .subscribe({
                setUpFragment(it)
            }, {

                multiStateContainer.showErrorState(it.message!!)
            })
    }

    private fun setUpFragment(list: List<WxarticleChaptersEntity>?) {
        val fragmentList = ArrayList<Fragment>()
        val titleList = ArrayList<String>()
        list?.forEach {
            val title = it.name
            val parentChapterId = it.id
            titleList.add(title)
            fragmentList.add(WechatPublicAccountArticleListFragment.newInstance(parentChapterId!!))
            TabLayoutManager.getInstance()?.setSlidingTabData(
                this,
                sldTabLayout,
                findViewById(R.id.viewpager),
                titleList,
                fragmentList
            )
            multiStateContainer.showSucceedState()

        }
    }


    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        val slidingTabLayoutView =
            View.inflate(mContext, R.layout.layout_title_slidingtablayout, null);
        sldTabLayout = slidingTabLayoutView.findViewById(R.id.sld_tab_layout)
        titleBar?.addCenterAction(
            titleBar.ViewAction(slidingTabLayoutView),
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT))
    }
}