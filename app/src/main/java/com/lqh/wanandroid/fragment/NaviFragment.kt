package com.lqh.wanandroid.fragment

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.fastlibrary.FastManager
import com.lqh.fastlibrary.modle.fragment.FastRefreshLoadFragment
import com.lqh.wanandroid.R
import com.lqh.wanandroid.adapter.NaviAdapter
import com.lqh.wanandroid.entity.NaviBean
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

class NaviFragment : FastRefreshLoadFragment<NaviBean>() {
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
    }

    /**
     * 使用BaseRecyclerViewAdapterHelper作为上拉加载的实现方式
     * 如果使用ListView或GridView等需要自己去实现上拉加载更多的逻辑
     *
     * @return BaseRecyclerViewAdapterHelper的实现类
     */
    override fun getAdapter(): BaseQuickAdapter<NaviBean, BaseViewHolder> {

        return NaviAdapter()
    }


    /**
     * 触发下拉或上拉刷新操作
     *
     * @param page
     */
    override fun loadData(page: Int) {
        val questionObservableLife = ApiClient.getNaviListObservableLife()
        questionObservableLife
            .subscribe({
                FastManager.getInstance().httpRequestControl
                    .httpRequestSuccess(iHttpRequestControl,
                        it, null)

            }, {
                FastManager.getInstance().httpRequestControl
                    .httpRequestError(iHttpRequestControl,
                        it)
            })

    }

}