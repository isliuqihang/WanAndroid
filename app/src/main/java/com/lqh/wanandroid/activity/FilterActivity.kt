package com.lqh.wanandroid.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.chad.library.adapter.base.module.LoadMoreModule
import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer
import com.lqh.fastlibrary.view.multistatepage.OnRetryEventListener
import com.lqh.wanandroid.R
import com.lqh.wanandroid.adapter.*
import com.lqh.wanandroid.entity.Article
import com.lqh.wanandroid.entity.BannerData
import com.lqh.wanandroid.network.ApiClient
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.RoundLinesIndicator
import com.yyydjk.library.DropDownMenu
import java.util.*
import kotlin.collections.ArrayList

class FilterActivity : FastTitleActivity() {
    private val headers = arrayOf("城市", "年龄", "性别", "星座")
    private val popupViews: ArrayList<View> = ArrayList()

    private var cityAdapter: GirdDropDownAdapter? = null
    private var ageAdapter: ListDropDownAdapter? = null
    private var sexAdapter: ListDropDownAdapter? = null
    private var constellationAdapter: ConstellationAdapter? = null
    var bannerView: Banner<BannerData, BannerImageAdapter<BannerData>>? = null
    var wss: List<BannerData>? = null

    private val citys =
        arrayOf("不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州")
    private val ages = arrayOf("不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上")
    private val sexs = arrayOf("不限", "男", "女")
    private val constellations = arrayOf(
        "不限",
        "白羊座",
        "金牛座",
        "双子座",

        "巨蟹座",
        "狮子座",
        "处女座",
        "天秤座",
        "天蝎座",
        "射手座",
        "摩羯座",
        "水瓶座",
        "双鱼座"
    )


    private var constellationPosition = 0

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
//        return R.layout.activity_filter
        return R.layout.activity_filter1
    }

    //    private var smartLayout: SmartRefreshLayout? = null
//    var scrollerLayout: ConsecutiveScrollerLayout? = null
    private lateinit var dropdownmenu: DropDownMenu

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

        dropdownmenu = findViewById(R.id.dropdownmenu)

//        smartLayout = findViewById<SmartRefreshLayout>(R.id.smartLayout_rootFastLib);
//        scrollerLayout = findViewById<ConsecutiveScrollerLayout>(R.id.scrollerLayout);

        bannerView = findViewById(R.id.banner_view)


        initHorizontalBanner()
        //init city menu

        //init city menu
        val cityView = ListView(this)
        cityAdapter = GirdDropDownAdapter(this, Arrays.asList(*citys))
        cityView.dividerHeight = 0
        cityView.adapter = cityAdapter

        //init age menu

        //init age menu
        val ageView = ListView(this)
        ageView.dividerHeight = 0
        ageAdapter = ListDropDownAdapter(this, Arrays.asList(*ages))
        ageView.adapter = ageAdapter

        //init sex menu

        //init sex menu
        val sexView = ListView(this)
        sexView.dividerHeight = 0
        sexAdapter = ListDropDownAdapter(this, Arrays.asList(*sexs))
        sexView.adapter = sexAdapter

        //init constellation

        //init constellation
        val constellationView: View = layoutInflater.inflate(R.layout.custom_layout, null)
        val constellation: GridView = constellationView.findViewById(R.id.constellation)
        constellationAdapter = ConstellationAdapter(this, Arrays.asList(*constellations))
        constellation.adapter = constellationAdapter
        val ok: TextView = constellationView.findViewById(R.id.ok)
        ok.setOnClickListener {
            dropdownmenu!!.setTabText(if (constellationPosition == 0) headers[3] else constellations[constellationPosition])
            dropdownmenu.closeMenu()
        }

        //init popupViews

        //init popupViews
        popupViews.add(cityView)
        popupViews.add(ageView)
        popupViews.add(sexView)
        popupViews.add(constellationView)

        //add item click event

        //add item click event
        cityView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                cityAdapter?.setCheckItem(position)
                dropdownmenu.setTabText(if (position == 0) headers[0] else citys[position])
                dropdownmenu.closeMenu()
            }

        ageView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                ageAdapter?.setCheckItem(position)
                dropdownmenu.setTabText(if (position == 0) headers[1] else ages[position])
                dropdownmenu.closeMenu()
            }

        sexView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                sexAdapter?.setCheckItem(position)
                dropdownmenu.setTabText(if (position == 0) headers[2] else sexs[position])
                dropdownmenu.closeMenu()
            }

        constellation.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                constellationAdapter?.setCheckItem(position)
                constellationPosition = position
            }

        //init context view

        //init context view

        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.fast_layout_refresh_recycler, null)

        //init dropdownview
        dropdownmenu.setDropDownMenu(Arrays.asList(*headers), popupViews, contentView)
        dropdownmenu.setOnClickListener { LogUtils.d("被店家啦") }
        initContextView(contentView);


//        smartLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
//            override fun onLoadMore(refreshLayout: RefreshLayout) {
//
//                mDefaultPage++
//                getListData()
//                LogUtils.d("加载更多")
//
//            }
//
//            override fun onRefresh(refreshLayout: RefreshLayout) {
//
//                mDefaultPage = 0
//                getListData()
//                loadData()
//            }
//        })

    }

    private var rvContent: RecyclerView? = null
    private var smartLayout: SmartRefreshLayout? = null
    private var multiStateContainer: MultiStateContainer? = null

    private fun initContextView(contextView: View?) {
        rvContent = contextView?.findViewById<RecyclerView>(R.id.rv_contentFastLib)
        smartLayout = contextView?.findViewById<SmartRefreshLayout>(R.id.smartLayout_rootFastLib)
        multiStateContainer =
            contextView?.findViewById<MultiStateContainer>(R.id.multi_state_container)

        initRecyclerView()

        getListData();

        initMultiStateContainer()


    }

    protected var mDefaultPage = 0

    /**
     * 起始页
     */
    protected var mStartPage = 0

    protected var mDefaultPageSize = 20

    private fun getListData() {
        val testObservableLife = ApiClient.getHomeArticleObservableLife(mDefaultPage)
        testObservableLife
            .doOnSubscribe {
//                ToastUtil.show("显示加载动画....")
            }.doFinally {
//                ToastUtil.show("加载完成咯")
            }.subscribe({
                val datas = it?.datas
                setupListView(datas)
            }, {

            })
    }

    private fun setupListView(datas: List<Article>?) {
        smartLayout?.finishRefresh()
        if (ObjectUtils.isEmpty(datas)) {
            if (mDefaultPage == 0) {
                readArticleListAdapter.setNewInstance(ArrayList())
                multiStateContainer?.showEmptyState("暂无文章数据")
            }
            return
        }

        multiStateContainer?.showSucceedState()
        readArticleListAdapter.addData(datas!!)
        readArticleListAdapter.notifyDataSetChanged()
        LogUtils.d("size: " + datas.size + "    " + mDefaultPageSize)
        if (datas.size < mDefaultPageSize) {
//            smartLayout?.finishLoadMoreWithNoMoreData()
            LogUtils.d("232323223")
        } else {
            LogUtils.d("6767767")

        }

    }

    private fun initMultiStateContainer() {
        multiStateContainer?.showLoadingState("正在获取数据....")
        multiStateContainer?.onRetryEventListener = OnRetryEventListener {
            mDefaultPage = 0
            getListData()
        }
    }

    lateinit var readArticleListAdapter: ReadArticleListAdapter

    private fun initRecyclerView() {
        rvContent?.layoutManager = LinearLayoutManager(this)
        rvContent?.overScrollMode = View.OVER_SCROLL_NEVER
        readArticleListAdapter = ReadArticleListAdapter()
        rvContent?.adapter = readArticleListAdapter
        if (readArticleListAdapter is LoadMoreModule) {
            readArticleListAdapter.loadMoreModule.setOnLoadMoreListener {
                LogUtils.d("上拉加载...")
                mDefaultPage++
                getListData()

            }
        } else {
            LogUtils.d("不可...")

        }

        smartLayout?.setEnableLoadMore(false)
        smartLayout?.setOnRefreshListener {
            mDefaultPage = 0
            getListData()
        }
//        smartLayout?.setOnMultiListener(object : SimpleMultiListener() {
//            override fun onFooterMoving(
//                footer: RefreshFooter,
//                isDragging: Boolean,
//                percent: Float,
//                offset: Int,
//                footerHeight: Int,
//                maxDragHeight: Int
//            ) {
//                scrollerLayout?.setStickyOffset(offset)
//            }
//        })
    }


    override fun loadData() {
        val bannerObservableLife = ApiClient.getBannerObservableLife()
        bannerObservableLife
            .doOnSubscribe {
//                ToastUtil.show("显示加载动画....")
            }.doFinally {

            }.subscribe({
                bannerView?.setDatas(it)
//                wss = it

            }, {

            })
    }

    private fun initHorizontalBanner() {

        bannerView?.apply {
            this?.addBannerLifecycleObserver(this@FilterActivity)
            this?.setIndicator(RoundLinesIndicator(this@FilterActivity))
                .setAdapter(ImageTitleAdapter(wss))
//                .setBannerGalleryEffect(50, 10)
                .setBannerGalleryMZ(20)
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
        }

    }


    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.setTitleMainText("筛选功能")
    }


}


