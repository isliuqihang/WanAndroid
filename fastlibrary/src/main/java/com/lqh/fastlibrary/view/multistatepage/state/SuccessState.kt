package com.lqh.fastlibrary.view.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.lqh.fastlibrary.view.multistatepage.MultiState
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer

/**
 * @ProjectName: MultiStatePage
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2020/9/17 14:11
 */
class SuccessState : MultiState() {
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return View(context)
    }

    override fun onMultiStateViewCreate(view: View) = Unit

}