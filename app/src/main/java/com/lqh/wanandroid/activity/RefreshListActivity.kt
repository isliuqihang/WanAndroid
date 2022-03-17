package com.lqh.wanandroid.activity

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.fastlibrary.FastManager
import com.lqh.fastlibrary.modle.activity.FastRefreshLoadActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.R
import com.lqh.wanandroid.adapter.ReadArticleListAdapter
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.network.ApiClient
import java.util.*


/**
 * <pre>
 * description:
 * Created by: Lqh
 * date: 20210726
 * update: 0726
 * version:1.0
</pre> *
 */
class RefreshListActivity : FastRefreshLoadActivity<Article>() {
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
        mDefaultPage = 0
        mDefaultPageSize = 20


    }

    /**
     * 使用BaseRecyclerViewAdapterHelper作为上拉加载的实现方式
     * 如果使用ListView或GridView等需要自己去实现上拉加载更多的逻辑
     *
     * @return BaseRecyclerViewAdapterHelper的实现类
     */
    override fun getAdapter(): BaseQuickAdapter<Article, BaseViewHolder> {
        val readArticleListAdapter = ReadArticleListAdapter()
        val inflate = View.inflate(mContext, R.layout.headview_test_add_head, null)
        readArticleListAdapter.addHeaderView(inflate)
        return readArticleListAdapter
    }


    /**
     * 触发下拉或上拉刷新操作
     *
     * @param page  页码
     */
    override fun loadData(page: Int) {

//        val pageListObservable = RxHttp.get("/service/...")
//            .asParser(ResponseParser<PageList<Article>>())
//            .observeOn(AndroidSchedulers.mainThread())
//
//        pageListObservable.doOnSubscribe { disposable: Disposable? -> }
//            .doFinally {}
//            .subscribe(
//                { s: PageList<Article>? -> }
//            ) { throwable: Throwable? -> }


        val testObservableLife = ApiClient.getHomeArticleObservableLife(page)
        testObservableLife
            .doOnSubscribe {
//                ToastUtil.show("显示加载动画....")
            }.doFinally {
//                ToastUtil.show("加载完成咯")
            }.subscribe({
                FastManager.getInstance().httpRequestControl.httpRequestSuccess(
                    iHttpRequestControl,
                    it?.datas,
                    null
                )
            }, {
                FastManager.getInstance().httpRequestControl.httpRequestError(
                    iHttpRequestControl,
                    it
                )

            })


//        RxHttp.get("https://www.wanandroid.com/wxarticle/list/408/$page/json")
//            .asParser(
//                ResponseParser<PageList<Article>>() {}) //返回Student类型
//            .to(RxLife.toMain(this)) //感知生命周期，并在主线程回调
//            .subscribe(
//                { pageList: PageList<Article> ->
//                    FastManager.getInstance().httpRequestControl.httpRequestSuccess(
//                        iHttpRequestControl,
//                        pageList.datas,
//                        null
//                    )
//                },
//                { throwable: Throwable ->
//                    FastManager.getInstance().httpRequestControl.httpRequestError(
//                        iHttpRequestControl,
//                        throwable
//                    )
//                })


    }


    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView) {
        titleBar.setTitleMainText("文章列表")
    }
}