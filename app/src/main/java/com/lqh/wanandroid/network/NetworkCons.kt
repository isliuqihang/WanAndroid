package com.lqh.wanandroid.network

/**
 * <pre>
 * description:
 * Created by: Lqh
 * date: 20210810
 * update: 0810
 * version:1.0
</pre> *
 */
object NetworkCons {
        var BASE_URL = "http://crouter.yunzongnet.com/mfoyou-agent-web/t4107/";
//    const val BASE_URL = "https://txld2019.com/api/"

    /**
     * 获取token
     */
    var getTokenUrl = BASE_URL + "token"

    //    val type = "8"
    //    val appId = "109"
    var loginByAccountAndPasswordUrl = BASE_URL + "user/login/pass/-2"

    var getShopInfoUrl: String = BASE_URL + "store/myinfo"

}