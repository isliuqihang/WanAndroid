package com.lqh.wanandroid.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.fastlibrary.FastManager
import com.lqh.fastlibrary.modle.fragment.FastTitleRefreshLoadFragment
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.R
import com.lqh.wanandroid.activity.WebViewActivity
import com.lqh.wanandroid.adapter.ReadArticleListAdapter
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.network.ApiClient


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211116
 *     update: 1116
 *     version:1.0
 * </pre>
 */

class QuestionFragment : FastTitleRefreshLoadFragment<Article>() {
    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.fast_layout_title_refresh_recycler
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

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
        titleBar?.setTitleMainText("问答")

    }

    /**
     * 使用BaseRecyclerViewAdapterHelper作为上拉加载的实现方式
     * 如果使用ListView或GridView等需要自己去实现上拉加载更多的逻辑
     *
     * @return BaseRecyclerViewAdapterHelper的实现类
     */
    override fun getAdapter(): BaseQuickAdapter<Article, BaseViewHolder> {
        return ReadArticleListAdapter()
    }

    /**
     * 触发下拉或上拉刷新操作
     *
     * @param page
     */
    override fun loadData(page: Int) {
        mDefaultPageSize = 20
        val questionObservableLife = ApiClient.getQuestionObservableLife(page)
        questionObservableLife
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