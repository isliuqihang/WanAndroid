package com.lqh.fastlibrary.view.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
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
class ErrorState : MultiState() {

    private lateinit var tvErrorMsg: TextView
    private lateinit var imgError: ImageView
    private lateinit var tvRetry: TextView

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.layout_multi_state_error, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvErrorMsg = view.findViewById(R.id.tv_multi_state_error_content)
        imgError = view.findViewById(R.id.iv_multi_state_error_image)
        tvRetry = view.findViewById(R.id.tv_multi_state_retry)

        setErrorMsg(MultiStatePage.config.errorMsg)
        setErrorIcon(MultiStatePage.config.errorIcon)
    }

    override fun enableReload(): Boolean = true

    override fun bindRetryView(): View? {
        return tvRetry
    }

    fun setErrorMsg(errorMsg: String) {
        tvErrorMsg.text = errorMsg
    }

    fun setErrorIcon(@DrawableRes errorIcon: Int) {
        imgError.setImageResource(errorIcon)
    }
}