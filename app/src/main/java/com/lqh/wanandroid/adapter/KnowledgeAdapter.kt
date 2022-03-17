package com.lqh.wanandroid.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.*
import com.lqh.fastlibrary.utils.ToastUtil
import com.lqh.wanandroid.IntentKey
import com.lqh.wanandroid.R
import com.lqh.wanandroid.activity.KnowledgeArticleActivity
import com.lqh.wanandroid.entity.ChapterBean
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

class KnowledgeAdapter(context: Activity) :
    BaseQuickAdapter<ChapterBean, BaseViewHolder>(R.layout.rv_item_knowledge),
    LoadMoreModule {

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
        val rlvItem = holder.getView<RecyclerView>(R.id.rlv_item)
        rlvItem.layoutManager = getFlexboxLayoutManager()

        val children = item.children
        val knowledgeFlexboxLayoutAdapter = KnowledgeFlexboxLayoutAdapter()
        rlvItem.adapter = knowledgeFlexboxLayoutAdapter
        knowledgeFlexboxLayoutAdapter.data = children
        knowledgeFlexboxLayoutAdapter.setOnItemClickListener { adapter, view, position ->

            item?.let {
                val intent = Intent(context, KnowledgeArticleActivity::class.java)
                intent.putExtra(IntentKey.NAVI_DATA, item)
                intent.putExtra(IntentKey.NAVI_NAME, item.name)
                intent.putExtra(IntentKey.NAVI_CURR_POS, position)
                if (context !is Activity) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
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