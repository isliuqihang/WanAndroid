package com.lqh.wanandroid.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.wanandroid.R
import com.lqh.wanandroid.entity.ChapterBean

class KnowledgeFlexboxLayoutAdapter() :
    BaseQuickAdapter<ChapterBean, BaseViewHolder>(R.layout.rv_item_knowledge_child) {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * 实现此方法，并使用 helper 完成 item 视图的操作
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(holder: BaseViewHolder, item: ChapterBean) {
        holder.setText(R.id.tv_name, item.name)
    }


}
