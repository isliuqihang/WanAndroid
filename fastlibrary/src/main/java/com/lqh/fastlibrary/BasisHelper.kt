package com.lqh.fastlibrary

import android.app.Activity
import com.lqh.fastlibrary.manager.LauLogger

/**
 * @Author: AriesHoo on 2019/8/7 14:22
 * @E-Mail: AriesHoo@126.com
 * @Function: 绑定Activity Helper
 * @Description:
 */
open class BasisHelper(protected var mContext: Activity) {
    private var mTag = javaClass.simpleName

    /**
     * Activity 关闭onDestroy调用
     */
    fun onDestroy() {
        LauLogger.i(mTag, "onDestroy")
    }

    init {
        FastDelegateManager.getInstance().putBasisHelper(mContext, this)
    }
}