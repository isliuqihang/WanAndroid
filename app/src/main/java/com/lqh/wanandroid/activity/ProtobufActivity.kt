package com.lqh.wanandroid.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ScreenUtils
import com.google.protobuf.ByteString
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.R
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import rxhttp.wrapper.param.RxHttp
import tianshu.ui.api.TsUiApiV20191205

class ProtobufActivity : FastTitleActivity() {
    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.activity_protobuf
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

        initProtobufData()
    }

    private fun initProtobufData() {
        val deviceBuilder = TsUiApiV20191205.Device.newBuilder()
        deviceBuilder.apply {
            this.vendor = ByteString.copyFromUtf8(DeviceUtils.getManufacturer())
            this.model = ByteString.copyFromUtf8(DeviceUtils.getModel())
            this.osType = TsUiApiV20191205.OsType.ANDROID
            this.setOsVersion(TsUiApiV20191205.Version.newBuilder()
                .setMajor(DeviceUtils.getSDKVersionCode()))
            this.setScreenSize(TsUiApiV20191205.Size.newBuilder()
                .setHeight(ScreenUtils.getAppScreenHeight())
                .setWidth(ScreenUtils.getAppScreenWidth()))
            this.setUdid(TsUiApiV20191205.UdId.newBuilder()
//                .setId(ByteString.copyFromUtf8(DeviceUtils.getMacAddress()))
                .setId(ByteString.copyFromUtf8("SMIT1D2021906000161"))
                .setIdType(TsUiApiV20191205.UdIdType.MEDIA_ID))
        }
        val device = deviceBuilder.build()
        val networkOperatorName = NetworkUtils.getNetworkOperatorName()
        val networkType = NetworkUtils.getNetworkType()
        val ipAddress = NetworkUtils.getIPAddress(true)

        var connectionType: TsUiApiV20191205.Network.ConnectionType?

        if (networkType == NetworkUtils.NetworkType.NETWORK_WIFI) {
            connectionType = TsUiApiV20191205.Network.ConnectionType.WIFI
        } else if (networkType == NetworkUtils.NetworkType.NETWORK_4G) {
            connectionType = TsUiApiV20191205.Network.ConnectionType.MOBILE_4G
        } else if (networkType == NetworkUtils.NetworkType.NETWORK_3G) {
            connectionType = TsUiApiV20191205.Network.ConnectionType.MOBILE_3G
        } else if (networkType == NetworkUtils.NetworkType.NETWORK_2G) {
            connectionType = TsUiApiV20191205.Network.ConnectionType.MOBILE_2G
        } else if (networkType == NetworkUtils.NetworkType.NETWORK_ETHERNET) {
            connectionType = TsUiApiV20191205.Network.ConnectionType.ETHERNET
        } else {
            connectionType = TsUiApiV20191205.Network.ConnectionType.NEW_TYPE
        }

        var operatorType: TsUiApiV20191205.Network.OperatorType? = null

        if (networkOperatorName.equals("中国移动")) {
            operatorType = TsUiApiV20191205.Network.OperatorType.ISP_CHINA_MOBILE
        } else if (networkOperatorName.equals("中国联通")) {
            operatorType = TsUiApiV20191205.Network.OperatorType.ISP_CHINA_UNICOM
        } else if (networkOperatorName.equals("中国电信")) {
            operatorType = TsUiApiV20191205.Network.OperatorType.ISP_CHINA_TELECOM

        } else if (networkOperatorName.equals("其它运营商")) {
            operatorType = TsUiApiV20191205.Network.OperatorType.ISP_FOREIGN

        } else if (networkOperatorName.equals("未知运营商")) {
            operatorType = TsUiApiV20191205.Network.OperatorType.ISP_UNKNOWN
        } else {
            operatorType = TsUiApiV20191205.Network.OperatorType.ISP_UNKNOWN

        }
        val networkBuilder = TsUiApiV20191205.Network.newBuilder()
        val network =
            networkBuilder
                .setConnectionType(connectionType)
                .setIpv4(ByteString.copyFromUtf8(ipAddress))//必填！用户设备的公网IPv4地址，服务器对接必填，格式要求：255.255.255.255
                .setOperatorType(operatorType)
                .build()

        val tsApiRequest = TsUiApiV20191205.TsApiRequest.newBuilder()
            .setRequestId(ByteString.copyFromUtf8("51244d5d88246936ccdf69a65e84b929"))//媒体方自定义,标识每次广告请求.仅英文字母和数字.32位.大小写不敏感
            .setApiVersion(TsUiApiV20191205.Version.newBuilder().setMajor(6))
            .setAppId(ByteString.copyFromUtf8("b45f78ee"))//标识资源方， MSSP 平台生成
            .setSlot(TsUiApiV20191205.SlotInfo.newBuilder()
                .setAdslotId(ByteString.copyFromUtf8("2022228")))//MSSP 平台生成， 广告位类型唯一（ 开机、屏保等），物料要求唯一（ 物料格式、 播放时长等）
            .setDevice(device)
            .setNetwork(network)
            .build()
        val result: ByteArray = tsApiRequest.toByteArray() //序列化
        val parseFrom = TsUiApiV20191205.TsApiRequest.parseFrom(result)
        Logger.d(parseFrom.toString())

        /*val testProtobufDataObservableLife = ApiClient.getTestProtobufDataObservableLife(tsApiRequest)
        testProtobufDataObservableLife.subscribe {
//            Logger.d(it.message)
        }

        var url = "http://jpad.baidu.com/api_6/"

        val parms: RequestBody =
            RequestBody.create("application/x-protobuf".toMediaTypeOrNull(),
                parseFrom.toByteArray())
        RxHttp.postBody(url)
            .setBody(parms)
            .setProConverter()
            .asOkResponse()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            }*/


        val url =
            "https://jpad.xiyunerp.com/juping.php?url=0600000cCfdfxKWlA_Hp6XznSjO-TEjIag1Dt0qhdGDn-eJRgr4u_NR60FotjUvw_aWZbLWwpjN3dPuMuEz5rddFJddKmlfGsDCoDL1SZP62xXffh3N-cLlkg8btUMj_JgjWjmAt0y_FuEa-G0obn0RMhoqp9hHI10PjwKIJUC1ii_KBhrAVDrqqIKaX6kkrvAe-u9pKwf-0R5DJjCtltdNffi6j.7Y_ifSMzEukmcljPKt2ccxHK6xKD45wWxnhKSMNKDM6qx-wIJIKsn3SyGh2e-zIZ-W1QDkOVgRgOzr1Kzs34-9h9mzXr1WYJ.pMFYXgK-5Hc0phwGujdHHR-RnRfznjckrHD4nj0sPWnd0AG9IjYvn0KJUgfqn6KVIjYz0AGVTZfqnHR0IAYqihw_pNCYIfKYIHd2fWImf1wHfLm0ULFbugFGujYkPjcdn6KJIy-xmyw1ThnqnH00IvbqnH00phqzuANz5HDYnWRz0AGBIgPGUhN1T1Ys0ZPbpdqv5Hfhni3sQW08n0K1pyfqnHckuyndujw-ujwbPhR1mfKJuAN_pgu-TMbqn0KJTAqG5yw9uj63PWnYPvfLPyFbPym4uHbduARdn0KJTA-b5HDv0ZKGujYkP6KJmv-b5HnvrfKWpyfqn1m40ZF-TgfqPjRd0ZNG5yF9pywd0A-V5HmkPsKM5y7bX0K-TMKWUvw-5N0zrjnv0APzm1YvnWn1n60"
//            "http://cpro.baidustatic.com/cpro/ui/noexpire/img/newBDlogo/wap_hand_2x.png"
//            "http://jpad.baidu.com/juping.php?url\u003d0600000cCfdfxKWlA8Hs3SI_WxMxORfPqXFrmNHTe7xuIX52VAzsOAGFbgpCmClKwrm2C8VeIhE-dEsKNzYYR4hW0_xMc8Dz9uzfgqpKUK6kvqi7M1QMMy6LsqFYb4LzwYinkd1AeHsBn0iEVIJ5LXKkgZK-0YJ4_OuAeIx8asftdrJ3kLXCO7XRQ3QRl4GyX1ZStIXCgZ46Y0Rk3ikhdrWQXYep.7Y_ifSMzEukmcljPKt2ccxHK6xKD45wWxnhKSMNKDM6qx-wIJIKsn3SyGh2e-zIZ-W1QDkOVgRgOzr1Kzs34-9h9mzXr1WYJ.pMFYXgK-5Hc0phwGujdHHR-RnRfznjckrHD4nj0sPWnd0AG9IjYvn0KJUgfqn6KVIjYz0AGVTZfqnHR0IAYqihw_pNCYIfKYIHd2fWImf1wHfLm0ULFbugFGujYkPjcdn6KJIy-xmyw1ThnqnH00IvbqnH00phqzuANz5HDYnWRz0AGBIgPGUhN1T1Ys0ZPbpdqv5Hfhni3sQW08n0K1pyfquHPBnjb3PW7hnyD1PAndmfKJuAN_pgu-TMbqn0KJTAqG5yw9uj63PWnYPvfLPyFbPym4uHbduARdn0KJTA-b5HDv0ZKGujYkP6KJmv-b5HnvrfKWpyfqn1m40ZF-TgfqPj0d0ZNG5yF9pywd0A-V5HmkPsKM5y7bX0K-TMKWUvw-5N0zrjnv0APzm1YvnjbvPs0"
        RxHttp.get(url)
            .asOkResponse()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
//                Logger.d(it)
                val stream = it.body!!.byteStream()
                val shareBitmap = BitmapFactory.decodeStream(stream)
                findViewById<ImageView>(R.id.iv).setImageBitmap(shareBitmap)

            }) { throwable: Throwable ->
            }
/*
        var url = "http://jpaccess.baidu.com/api_6"
        val parms: RequestBody = RequestBody.create("application/x-protobuf".toMediaTypeOrNull(),
            tsApiRequest.toByteArray())
        RxHttp.postBody(url)
            .setBody(parms)
            .asOkResponse()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val stream = response.body!!.byteStream()
                val responseResult = TsUiApiV20191205.TsApiResponse.parseFrom(stream)
                Logger.d(responseResult)
                val adsOrBuilderList = responseResult.adsOrBuilderList
//                httpSuccessResult(mHttpBaseBean, requestType)
            }) { throwable: Throwable ->
            }
*/
    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
        titleBar?.setTitleMainText("Protobuf演示")
    }

}
