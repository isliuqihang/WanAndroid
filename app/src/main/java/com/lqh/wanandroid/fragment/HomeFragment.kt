package com.lqh.wanandroid.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lqh.fastlibrary.FastManager
import com.lqh.fastlibrary.modle.fragment.FastTitleRefreshLoadFragment
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.IntentKey
import com.lqh.wanandroid.R
import com.lqh.wanandroid.activity.AuthorDetailsActivity
import com.lqh.wanandroid.activity.LoginActivity
import com.lqh.wanandroid.activity.WebViewActivity
import com.lqh.wanandroid.adapter.ImageTitleAdapter
import com.lqh.wanandroid.adapter.ReadArticleListAdapter
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.entity.BannerData
import com.lqh.wanandroid.network.ApiClient
import com.orhanobut.logger.Logger
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.listener.OnPageChangeListener


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211102
 *     update: 1102
 *     version:1.0
 * </pre>
 */

class HomeFragment : FastTitleRefreshLoadFragment<Article>() {

    private var bannerDataList: List<BannerData>? = null
    private var topArticleDataList: List<Article>? = null

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.fast_layout_title_refresh_recycler
    }


    override fun onItemClicked(
        adapter: BaseQuickAdapter<Article, BaseViewHolder>?,
        view: View?,
        position: Int,
    ) {
        super.onItemClicked(adapter, view, position)

        val article: Article = adapter?.data?.get(position) as Article
        val link = article.link
        WebViewActivity.start(mContext, link, true)
    }


    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {
        getBannerDatas()
        getTopArticleDatas()
    }


    /**
     * 获取置顶文章列表
     */
    private fun getTopArticleDatas() {
        val topArticleObservableLife = ApiClient.getTopArticleObservableLife()
        topArticleObservableLife.subscribe({


            for (bean in it) {
                bean.isTop = true
            }
            topArticleDataList = it
        }, {
            val message = it.message;
            LogUtils.i("请求错误信息: $message")
        })


    }


    private fun getBannerDatas() {

        val bannerObservableLife = ApiClient.getBannerObservableLife()
        bannerObservableLife
            .doOnSubscribe {
//                ToastUtil.show("显示加载动画....")
            }.doFinally {

            }.subscribe({

                bannerDataList = it



                setupBannerView(it)

            }, {
            })
    }

    private fun setupBannerView(it: List<BannerData>?) {

        val toMutableList = it!!.toMutableList()
        val bannerData1 = BannerData()

        bannerData1.apply {
            this.desc = "泥石流"
            this.id = 2232323
            this.url = "https://v-cdn.zjol.com.cn/276982.mp4"
        }


        toMutableList!!.add(bannerData1)

        for (index in toMutableList!!.indices) {
            toMutableList!![index].interval = 3 + index
            Logger.d("每个轮播图的停留时间: " + toMutableList!![index].interval)

        }

        bannerView?.setDatas(toMutableList)

        bannerView?.apply {
            this.addBannerLifecycleObserver(activity)
//            this.setIndicator(RoundLinesIndicator(activity))
                .setAdapter(ImageTitleAdapter(bannerDataList))
//                .isAutoLoop(false)
                .setBannerGalleryMZ(10)
                .setLoopTime((toMutableList!!.get(0).interval * 1000).toLong())
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                .setOnBannerListener { data, position ->
                    val bannerData: BannerData = data as BannerData
                    val desc = bannerData.desc
                    val interval = bannerData.interval
                    val url = bannerData.url
                    WebViewActivity.start(mContext, url, true)

                }
                .addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int,
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        if (position < toMutableList!!.size) {
                            val interval = toMutableList!!.get((position)).interval
                            bannerView!!.setLoopTime((interval!! * 1000).toLong())
                            Logger.d("当前pos为: ${position}时间间隔: $interval")

                        } else {
                            val bannerData = toMutableList?.get(0)
                            val interval = bannerData?.interval
                            bannerView!!.setLoopTime((interval!! * 1000).toLong())
                            Logger.d("当前pos为: ${position}时间间隔: $interval")
                        }
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                })

        }

    }

    lateinit var readArticleListAdapter: ReadArticleListAdapter

    /**
     * 使用BaseRecyclerViewAdapterHelper作为上拉加载的实现方式
     * 如果使用ListView或GridView等需要自己去实现上拉加载更多的逻辑
     *
     * @return BaseRecyclerViewAdapterHelper的实现类
     */
    override fun getAdapter(): BaseQuickAdapter<Article, BaseViewHolder> {

        readArticleListAdapter = ReadArticleListAdapter()
        readArticleListAdapter.addChildClickViewIds(R.id.tv_tag,
            R.id.tv_chapter_name,
            R.id.tv_author)


        readArticleListAdapter.addHeaderView(getHeadBannerView(), 0)
        readArticleListAdapter.setOnItemChildClickListener { adapter, view, position ->
            val articleItem = adapter.data[position] as Article


            when (view.id) {
                R.id.tv_tag -> {

                    ActivityUtils.startActivity(LoginActivity::class.java)
                }
                R.id.tv_chapter_name -> {


                }
                R.id.tv_author -> {
                    val userId = articleItem.userId
                    val intent = Intent(context, AuthorDetailsActivity::class.java)
                    intent.putExtra(IntentKey.AUTHOR_ID, userId)
                    ActivityUtils.startActivity(intent)

                }
            }
        }
        return readArticleListAdapter
    }


    private var bannerView: Banner<BannerData, BannerImageAdapter<BannerData>>? = null

    private fun getHeadBannerView(): View {
        val headView: View =
            LayoutInflater.from(mContext).inflate(R.layout.headview_banner, null)
        bannerView = headView.findViewById(R.id.banner_view)
        initHorizontalBanner()

        return headView
    }

    private fun initHorizontalBanner() {


    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.setTitleMainText("首页")

    }


    /**
     * 触发下拉或上拉刷新操作
     *
     * @param page
     */
    override fun loadData(page: Int) {
        mDefaultPageSize = 20
        val homeArticleObservableLife = ApiClient.getHomeArticleObservableLife(page)
        homeArticleObservableLife
            .doOnSubscribe {
                //                ToastUtil.show("显示加载动画....")
            }.doFinally {
                //                ToastUtil.show("加载完成咯")
            }.subscribe({
                val datas = it.datas
                if (page == 0) {
                    topArticleDataList?.let { it1 ->
                        datas.addAll(0, it1)
                    }
                }
                Logger.d("datas:" + datas.size)
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