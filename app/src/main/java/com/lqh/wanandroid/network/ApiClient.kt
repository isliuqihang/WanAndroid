package com.lqh.wanandroid.network

import com.blankj.utilcode.util.SPUtils
import com.lqh.wanandroid.ConstantValues
import com.lqh.wanandroid.entity.*
import com.lqh.wanandroid.entity.takeout.LoginInfoEntity
import com.lqh.wanandroid.entity.takeout.ShopInfoEntity
import com.lqh.wanandroid.network.parser.ResponseParser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.Response
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.asResponse
import tianshu.ui.api.TsUiApiV20191205
import java.util.logging.Logger


/**
 * <pre>
 * description:
 * Created by: Lqh
 * date: 20210728
 * update: 0728
 * version:1.0
</pre> *
 */
object ApiClient {
    /**
     * 首页文章
     */
    fun getHomeArticleObservableLife(
        page: Int,
    ): Observable<PageList<Article>> {
        return RxHttp.get("https://www.wanandroid.com/article/list/$page/json")
            .asParser(object : ResponseParser<PageList<Article>>() {})
            .observeOn(AndroidSchedulers.mainThread())

//
//        return RxHttp.get("https://www.wanandroid.com/wxarticle/list/408/$page/json")
//            .asParser(object :
//                ResponseParser<PageList<Article>>() {})
//            .to(RxLife.toMain(mContext))
////

    }

    @JvmStatic
    fun getTokenObservableLife(): @NonNull Observable<String> {
        return RxHttp.get(NetworkCons.getTokenUrl)
            .asResponse<String>()
    }

    @JvmStatic
    fun loginByPhoneObservableLife(
        phone: String,
        password: String,
        type: String,
        appId: String,
    ): @NonNull Observable<LoginInfoEntity> {
        return RxHttp.postJson(NetworkCons.loginByAccountAndPasswordUrl)
            .add("appId", appId)
            .add("phone", phone)
            .add("pass", password)
            .add("type", type)
            .asParser(object : ResponseParser<LoginInfoEntity>() {})
            .observeOn(AndroidSchedulers.mainThread())
    }

    @JvmStatic
    fun getShopInfoObservableLife(): @NonNull Observable<ShopInfoEntity> {


        return RxHttp.get(NetworkCons.getShopInfoUrl)
            .addHeader("token", SPUtils.getInstance().getString(ConstantValues.TOKEN))
            .asParser(object : ResponseParser<ShopInfoEntity>() {})
            .observeOn(AndroidSchedulers.mainThread())

    }

    /**
     * 首页banner
     */
    fun getBannerObservableLife(): @NonNull Observable<List<BannerData>> {
        return RxHttp.get("https://www.wanandroid.com/banner/json")
            .asParser(object : ResponseParser<List<BannerData>>() {})
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 置顶文章
     */
    fun getTopArticleObservableLife(): @NonNull Observable<List<Article>> {

        return RxHttp.get("https://www.wanandroid.com/article/top/json")
            .asParser(object : ResponseParser<List<Article>>() {})
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getQuestionObservableLife(
        page: Int,
    ): Observable<PageList<Article>> {
        return RxHttp.get("https://wanandroid.com/wenda/list/$page/json")
            .asParser(object : ResponseParser<PageList<Article>>() {})
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getKnowledgeObservableLife(): Observable<List<ChapterBean>> {
        return RxHttp.get("https://www.wanandroid.com/tree/json")
            .asParser(object : ResponseParser<List<ChapterBean>>() {})
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNaviListObservableLife(): Observable<List<NaviBean>> {
        return RxHttp.get("https://www.wanandroid.com/navi/json")
            .asParser(object : ResponseParser<List<NaviBean>>() {})
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getKnowledgeArticleByCidObservableLife(
        page: Int,
        chapterId: Int,
    ): Observable<PageList<Article>> {
        return RxHttp.get("https://www.wanandroid.com/article/list/$page/json?cid=$chapterId")
//        https://www.wanandroid.com/article/list/0/json?cid=60
            .asParser(object : ResponseParser<PageList<Article>>() {})
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAuthorDetailsDataObservableLife(page: Int, authorId: Int) {


    }

    /**
     * 获取微信公众号列表
     * @return Observable<PageList<WxarticleChaptersEntity>>
     */
    fun getWechatPublicAccountListObservableLife(): Observable<List<WxarticleChaptersEntity>> {
        return RxHttp.get("https://wanandroid.com/wxarticle/chapters/json")
            .asParser(object : ResponseParser<List<WxarticleChaptersEntity>>() {})
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getWechatPublicAccountArticleListObservableLife(
        page: Int,
        parentChapterId: Int,
    ): Observable<PageList<Article>> {
//        https://wanandroid.com/wxarticle/list/408/1/json
        return RxHttp.get("https://wanandroid.com/wxarticle/list/$parentChapterId/$page/json?")
            .asParser(object : ResponseParser<PageList<Article>>() {})
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTestProtobufDataObservableLife(parseFrom: TsUiApiV20191205.TsApiRequest): @NonNull Observable<Response> {
        var url = "http://jpaccess.baidu.com/api_6"

        val parms: RequestBody =
            RequestBody.create("application/x-protobuf".toMediaTypeOrNull(),
                parseFrom?.toByteArray())
        return RxHttp.postBody(url)
            .setBody(parms)
            .setProConverter()
            .asOkResponse()
            .observeOn(AndroidSchedulers.mainThread())

    }
}

