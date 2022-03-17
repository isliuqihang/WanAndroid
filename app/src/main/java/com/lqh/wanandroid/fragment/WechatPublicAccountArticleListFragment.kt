package com.lqh.wanandroid.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.fastlibrary.FastManager
import com.lqh.fastlibrary.modle.fragment.FastTitleRefreshLoadFragment
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.IntentKey
import com.lqh.wanandroid.R
import com.lqh.wanandroid.activity.WebViewActivity
import com.lqh.wanandroid.adapter.ReadArticleListAdapter
import com.lqh.wanandroid.adapter.WechatPublicAccountArticleListAdapter
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.network.ApiClient


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20220221
 *     update: 0221
 *     version:1.0
 * </pre>
 */

class WechatPublicAccountArticleListFragment : FastTitleRefreshLoadFragment<Article>() {

    private var parentChapterId: Int = 0


    companion object {
        fun newInstance(parentChapterId: Int): Fragment {
            val fragment = WechatPublicAccountArticleListFragment()
            val args = Bundle()
            args.putInt(IntentKey.PARENT_CHAPTER_ID, parentChapterId)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.fast_layout_refresh_recycler

    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {
        parentChapterId = getInt(IntentKey.PARENT_CHAPTER_ID);

    }

    /**
     * 使用BaseRecyclerViewAdapterHelper作为上拉加载的实现方式
     * 如果使用ListView或GridView等需要自己去实现上拉加载更多的逻辑
     *
     * @return BaseRecyclerViewAdapterHelper的实现类
     */
    override fun getAdapter(): BaseQuickAdapter<Article, BaseViewHolder> {
        return WechatPublicAccountArticleListAdapter()

    }

    override fun onItemClicked(
        adapter: BaseQuickAdapter<Article, BaseViewHolder>?,
        view: View?,
        position: Int,
    ) {
        super.onItemClicked(adapter, view, position)
        val article: Article = adapter?.data?.get(position) as Article
        val link = article.link
        WebViewActivity.start(mContext, link, true)
    }


    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
    }

    /**
     * 触发下拉或上拉刷新操作
     *
     * @param page
     */
    override fun loadData(page: Int) {
        mDefaultPageSize = 20
        val homeArticleObservableLife =
            ApiClient.getWechatPublicAccountArticleListObservableLife(page, parentChapterId)
        homeArticleObservableLife
            .subscribe({
                val datas = it.datas
                FastManager.getInstance().httpRequestControl
                    .httpRequestSuccess(iHttpRequestControl,
                        datas, null)

            }, {
                FastManager.getInstance().httpRequestControl
                    .httpRequestError(iHttpRequestControl,
                        it)
            })
    }
}