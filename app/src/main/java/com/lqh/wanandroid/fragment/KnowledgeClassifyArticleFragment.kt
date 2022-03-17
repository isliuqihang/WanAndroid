package com.lqh.wanandroid.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.fastlibrary.FastManager
import com.lqh.fastlibrary.modle.fragment.FastRefreshLoadFragment
import com.lqh.wanandroid.IntentKey
import com.lqh.wanandroid.R
import com.lqh.wanandroid.adapter.ReadArticleListAdapter
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.network.ApiClient
import com.orhanobut.logger.Logger


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211117
 *     update: 1117
 *     version:1.0
 * </pre>
 */

class KnowledgeClassifyArticleFragment() : FastRefreshLoadFragment<Article>() {


    companion object {
        fun newInstance(chapterId: Int, currPos: Int): Fragment {
            val fragment = KnowledgeClassifyArticleFragment()
            val args = Bundle()
            args.putInt(IntentKey.CHAPTER_ID, chapterId)
            args.putInt(IntentKey.NAVI_CURR_POS, currPos)
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


    var chapterId: Int = 0


    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

        chapterId = getInt(IntentKey.CHAPTER_ID);

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
//        https://www.wanandroid.com/article/list/0/json?cid=60
        mDefaultPageSize = 20
        val homeArticleObservableLife =
            ApiClient.getKnowledgeArticleByCidObservableLife(page, chapterId)
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