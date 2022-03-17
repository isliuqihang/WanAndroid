package com.lqh.wanandroid.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.lqh.wanandroid.R
import com.lqh.wanandroid.activity.WebViewActivity
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.entity.NaviBean


/**
 * <pre>
 *     description:导航分类列表
 *     date:2021/11/17 0017
 *     update:2021/11/17 0017  14:41
 *     version:1.0
 *     @author LQH
 * </pre>
 */


class NaviAdapter :
    BaseQuickAdapter<NaviBean, BaseViewHolder>(R.layout.rv_item_knowledge),
    LoadMoreModule {

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * 实现此方法，并使用 helper 完成 item 视图的操作
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(holder: BaseViewHolder, item: NaviBean) {
        holder.setText(R.id.tv_name, item.name)
        val rlvItem = holder.getView<RecyclerView>(R.id.rlv_item)
        rlvItem.layoutManager = getFlexboxLayoutManager()

        val children = item.articles
        val knowledgeFlexboxLayoutAdapter = NaviFlexboxLayoutAdapter()
        rlvItem.adapter = knowledgeFlexboxLayoutAdapter
        knowledgeFlexboxLayoutAdapter.data = children
        knowledgeFlexboxLayoutAdapter.setOnItemClickListener { adapter, view, position ->
            val article = adapter.data[position] as Article
            article?.let {
                val link = article.link
                WebViewActivity.start(context, link)
            }
        }
    }


    private fun getFlexboxLayoutManager(): FlexboxLayoutManager? {
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START
        return flexboxLayoutManager
    }
}