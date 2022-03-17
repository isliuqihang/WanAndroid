package com.lqh.fastlibrary

import android.app.Application
import android.util.Log
import androidx.core.content.FileProvider
import com.lqh.fastlibrary.utils.FastUtil

/**
 * @Author: AriesHoo on 2018/7/23 14:39
 * @E-Mail: AriesHoo@126.com
 * Function: FileProvider 配合[FastFileUtil]
 * Description:
 * 1、2019-9-16 14:34:51 增加FastManager初始化
 */
class FastFileProvider : FileProvider() {
    override fun onCreate(): Boolean {
        var context = context!!.applicationContext
        if (context == null) {
            context = FastUtil.getApplication()
        }
        Log.d("FastFileProvider", "context:$context")
        FastManager.init(context as Application)
        return super.onCreate()
    }
}