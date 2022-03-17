package com.lqh.wanandroid.adapter

import android.text.Html
import android.text.TextUtils
import android.widget.TextView
import com.blankj.utilcode.util.ObjectUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.fastlibrary.manager.GlideManager
import com.lqh.wanandroid.R
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.utils.LauStringUtils

/**
 * <pre>
 * description:
 * Created by: Lqh
 * date: 20210726
 * update: 0726
 * version:1.0
</pre> *
 */
class ReadArticleListAdapter :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_read_article_list), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.setGone(R.id.tv_top, !item.isTop)
        holder.setGone(R.id.tv_new, !item.isFresh)
        holder.setText(R.id.tv_author, item.author)
        holder.setText(R.id.tv_time, item.niceDate)
        holder.setText(R.id.tv_title, Html.fromHtml(item.title))


        val tags = item.tags
        if (ObjectUtils.isNotEmpty(tags)) {
            holder.setGone(R.id.tv_tag, false)
            holder.setText(R.id.tv_time, tags[0].name)
        } else {
            holder.setGone(R.id.tv_tag, true)
        }


        val envelopePic = item.envelopePic
        if (ObjectUtils.isNotEmpty(envelopePic)) {
            holder.setGone(R.id.iv_img, false)
            GlideManager.loadRoundImg(envelopePic, holder.getView(R.id.iv_img), 6f)
        } else {
            holder.setGone(R.id.iv_img, true)
        }


        if (ObjectUtils.isEmpty(item.desc)) {
            holder.getView<TextView>(R.id.tv_title).isSingleLine = false
            holder.setGone(R.id.tv_desc, true)
        } else {
            holder.setGone(R.id.tv_desc, false)
            holder.getView<TextView>(R.id.tv_title).isSingleLine = true
            var desc = Html.fromHtml(item.desc).toString()
            desc = LauStringUtils.removeAllBank(desc, 2)
            holder.setText(R.id.tv_desc, desc)
        }
        holder.setText(R.id.tv_chapter_name,
            Html.fromHtml(formatChapterName(item.superChapterName, item.chapterName)))



    }


    private fun formatChapterName(vararg names: String): String? {
        val format = StringBuilder()
        for (name in names) {
            if (!TextUtils.isEmpty(name)) {
                if (format.isNotEmpty()) {
                    format.append("·")
                }
                format.append(name)
            }
        }
        return format.toString()
    }

}