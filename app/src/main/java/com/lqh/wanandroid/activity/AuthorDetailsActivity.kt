package com.lqh.wanandroid.activity

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.fastlibrary.modle.activity.FastRefreshLoadActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.IntentKey
import com.lqh.wanandroid.R
import com.lqh.wanandroid.adapter.ReadArticleListAdapter
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.network.ApiClient


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211117
 *     update: 1117
 *     version:1.0
 * </pre>
 */

class AuthorDetailsActivity: FastRefreshLoadActivity<Article>() {

    var authorId: Int = 0

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return  R.layout.fast_layout_title_refresh_recycler

    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

        authorId = getInt(IntentKey.AUTHOR_ID)

    }

    /**
     * 触发下拉或上拉刷新操作
     *
     * @param page
     */
    override fun loadData(page: Int) {
        ApiClient.getAuthorDetailsDataObservableLife(page,authorId)
    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {

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
}