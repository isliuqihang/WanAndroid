package com.lqh.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.wanandroid.R
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.entity.ArticleEntity
import com.lqh.wanandroid.entity.ChapterBean


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211116
 *     update: 1116
 *     version:1.0
 * </pre>
 */

class NaviFlexboxLayoutAdapter :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.rv_item_knowledge_child) {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * 实现此方法，并使用 helper 完成 item 视图的操作
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.setText(R.id.tv_name, item.title)
    }

}