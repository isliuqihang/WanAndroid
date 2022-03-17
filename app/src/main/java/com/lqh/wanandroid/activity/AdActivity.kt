package com.lqh.wanandroid.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import com.blankj.utilcode.util.ViewUtils
import com.lake.banner.transformer.DefaultTransformer
import com.lake.banner.view.BannerViewPager
import com.lake.hbanner.HBanner
import com.lake.hbanner.ImageSubView
import com.lake.hbanner.SubView
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.R
import com.lqh.wanandroid.entity.ImageAdEntry
import com.lqh.wanandroid.view.banner.BannerModel
import com.lqh.wanandroid.view.banner.BannerMul
import com.lqh.wanandroid.view.banner.BannerPage


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20220303
 *     update: 0303
 *     version:1.0
 * </pre>
 */

class AdActivity : FastTitleActivity() {
    private var hBanner: HBanner? = null

    private val adImageView: ImageView? by lazy { findViewById<ImageView>(R.id.iv_ad) }
    private val bannerMul: BannerMul? by lazy { findViewById<BannerMul>(R.id.bannermul) }
//    private val bannerViewPager: BannerViewPager? by lazy { findViewById<BannerViewPager>(R.id.viewpager) }



    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.activity_ad
    }

    lateinit var bannerViewPager: BannerViewPager

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    private var playTime = 0

    override fun initView(savedInstanceState: Bundle?) {

        bannerViewPager = findViewById<BannerViewPager>(R.id.viewpager_ad)
        ViewUtils.runOnUiThreadDelayed({
            initBannerViewPager()

        }, 3000)

        val imageAdEntries = getImageAdEntries()
        val imageBannerAdEntries = getImageBannerAdEntries()
        val mutableListOf = mutableListOf<BannerModel>()
        val bannerModel = BannerModel()
        bannerModel.list = imageBannerAdEntries
        mutableListOf.add(bannerModel)
        bannerMul?.setDataList(mutableListOf)
//        GlideManager.loadImg(imgUrl, adImageView)

//        imageAdEntries.forEach {
//            playTime = playTime.plus(it.playTime)
//            val imgUrl = it.imgUrl
//            Observable.interval(playTime.toLong(), TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    GlideManager.loadImg(imgUrl, adImageView)
//                }
//
//        }


        /* Observable.range(1, 5).repeatWhen( Func1<Observable<? extends Void>, Observable<?>>() {
             @Override
             public Observable<?> call(Observable<? extends Void> observable) {
             return Observable.timer(6, TimeUnit.SECONDS);
         }
         }).subscribe(new Observer<Integer>() {
             @Override
             public void onCompleted() {
                 LogUtils.d("------------->onCompleted");
             }

             @Override
             public void onError(Throwable e) {
                 LogUtils.d("------------->onError:" + e);
             }

             @Override
             public void onNext(Integer integer) {
                 LogUtils.d("------------->onNext:" + integer);
             }
         });*/
    }

    private fun initBannerViewPager() {
        /**
         * 在create banner前需要确保viewpager控件已经被创建
         * 这里是双viewpager，为了方便所以直接对根布局进行视图创建
         * 进行回调
         */
        hBanner = HBanner.create(bannerViewPager)
        val mutableListOf = mutableListOf<SubView>()

        mutableListOf.add(ImageSubView.Builder(this)
            .url("http://m.qiyipic.com/common/lego/20171026/dd116655c96d4a249253167727ed37c8.jpg")
            .gravity(ImageView.ScaleType.FIT_XY)
            .duration(3000)
            .build())
        mutableListOf.add(ImageSubView.Builder(this)
            .url("http://m.qiyipic.com/common/lego/20171029/c9c3800f35f84f1398b89740f80d8aa6.jpg")
            .gravity(ImageView.ScaleType.FIT_XY)
            .duration(5000)
            .build())
        mutableListOf.add(ImageSubView.Builder(this)
            .url("http://m.qiyipic.com/common/lego/20171023/bd84e15d8dd44d7c9674218de30ac75c.jpg")
            .gravity(ImageView.ScaleType.FIT_XY)
            .duration(1000)
            .build())
        mutableListOf.add(ImageSubView.Builder(this)
            .url("http://m.qiyipic.com/common/lego/20171028/f1b872de43e649ddbf624b1451ebf95e.jpg")
            .gravity(ImageView.ScaleType.FIT_XY)
            .duration(3000)
            .build())
        mutableListOf.add(ImageSubView.Builder(this)
            .url("http://pic2.qiyipic.com/common/20171027/cdc6210c26e24f08940d36a5eb918c34.jpg")
            .gravity(ImageView.ScaleType.FIT_XY)
            .duration(8000)
            .build())

        hBanner!!.sources(mutableListOf)
        //设置viewpager切换方式
        bannerViewPager?.setPageTransformer(true, DefaultTransformer())
        //开始显示或者自动播放
        hBanner!!.play(true)

    }

    override fun onResume() {
        hBanner?.play(true)
        super.onResume()
    }

    override fun onPause() {
        hBanner?.pause(0)
        super.onPause()
    }

    override fun onStop() {
        hBanner = null
        super.onStop()
    }

    private fun getImageBannerAdEntries(): MutableList<BannerPage> {
        val mutableListOf = mutableListOf<BannerPage>()
        mutableListOf.add(BannerPage("http://m.qiyipic.com/common/lego/20171026/dd116655c96d4a249253167727ed37c8.jpg",
            2000))
        mutableListOf.add(BannerPage("http://m.qiyipic.com/common/lego/20171029/c9c3800f35f84f1398b89740f80d8aa6.jpg",
            5000))
        mutableListOf.add(BannerPage("http://m.qiyipic.com/common/lego/20171023/bd84e15d8dd44d7c9674218de30ac75c.jpg",
            3000))
        mutableListOf.add(BannerPage("http://m.qiyipic.com/common/lego/20171028/f1b872de43e649ddbf624b1451ebf95e.jpg",
            1000))
        mutableListOf.add(BannerPage("http://pic2.qiyipic.com/common/20171027/cdc6210c26e24f08940d36a5eb918c34.jpg",
            8000))

        return mutableListOf

    }

    private fun getImageAdEntries(): MutableList<ImageAdEntry> {
        val mutableList = mutableListOf<ImageAdEntry>()

        mutableList.add(ImageAdEntry(
            "大话西游：“炸毛韬”引诱老妖",
            "http://m.qiyipic.com/common/lego/20171026/dd116655c96d4a249253167727ed37c8.jpg",
            "BD", 5000
        ))
        mutableList.add(ImageAdEntry(
            "天使之路：藏风大片遇高反危机",
            "http://m.qiyipic.com/common/lego/20171029/c9c3800f35f84f1398b89740f80d8aa6.jpg",
            "JD", 3000
        ))
        mutableList.add(ImageAdEntry(
            "星空海2：陆漓设局害惨吴居蓝",
            "http://m.qiyipic.com/common/lego/20171023/bd84e15d8dd44d7c9674218de30ac75c.jpg",
            "XY", 1000
        ))
        mutableList.add(ImageAdEntry(
            "中国职业脱口秀大赛：狂笑首播",
            "http://m.qiyipic.com/common/lego/20171028/f1b872de43e649ddbf624b1451ebf95e.jpg",
            "BD", 5000
        ))
        mutableList.add(ImageAdEntry(
            "奇秀好音乐，你身边的音乐真人秀",
            "http://pic2.qiyipic.com/common/20171027/cdc6210c26e24f08940d36a5eb918c34.jpg",
            "XY", 1000
        ))


        return mutableList

    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.setTitleMainText("广告切换")
    }
}