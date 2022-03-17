package com.lqh.fastlibrary.view.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lqh.fastlibrary.R
import com.lqh.fastlibrary.view.multistatepage.MultiState
import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer
import com.lqh.fastlibrary.view.multistatepage.MultiStatePage

/**
 * @ProjectName: MultiStatePage
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2020/9/17 14:15
 */
class LoadingState : MultiState() {
    private lateinit var tvLoadingMsg: TextView
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.layout_multi_state_loading, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvLoadingMsg = view.findViewById(R.id.tv_multi_state_loading_content)
        setLoadingMsg(MultiStatePage.config.loadingMsg)

    }

    fun setLoadingMsg(loadingMsg: String) {
        tvLoadingMsg.text = loadingMsg
    }
}