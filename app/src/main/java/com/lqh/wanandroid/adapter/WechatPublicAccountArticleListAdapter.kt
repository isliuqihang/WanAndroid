package com.lqh.wanandroid.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.wanandroid.R
import com.lqh.wanandroid.entity.Article


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20220222
 *     update: 0222
 *     version:1.0
 * </pre>
 */

class WechatPublicAccountArticleListAdapter :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_wechat_public_account_article_list),
    LoadMoreModule {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * 实现此方法，并使用 helper 完成 item 视图的操作
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.setText(R.id.tv_author, item.author)
        holder.setText(R.id.tv_time, item.niceDate)
        holder.setText(R.id.tv_title, Html.fromHtml(item.title))
    }
}