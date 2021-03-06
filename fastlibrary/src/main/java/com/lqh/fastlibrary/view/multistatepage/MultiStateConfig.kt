package com.lqh.fastlibrary.view.multistatepage

import androidx.annotation.DrawableRes
import com.lqh.fastlibrary.R

/**
 * @ProjectName: MultiStatePage
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2020/9/19 12:30
 */

data class MultiStateConfig(
    val errorMsg: String = "哎呀,出错了",
    @DrawableRes
    val errorIcon: Int = R.drawable.ic_multi_error,
    val emptyMsg: String = "这里什么都没有",
    @DrawableRes
    val emptyIcon: Int = R.drawable.ic_multi_empty,
    val loadingMsg: String = "加载中,请稍等...",
    var alphaDuration: Long = 500
) {

    class Builder {
        private var errorMsg: String = "哎呀,出错了"

        @DrawableRes
        private var errorIcon: Int = R.drawable.ic_multi_error
        private var emptyMsg: String = "这里什么都没有"

        @DrawableRes
        private var emptyIcon: Int = R.drawable.ic_multi_empty
        private var loadingMsg: String = "loading..."
        private var alphaDuration: Long = 500

        fun errorMsg(msg: String): Builder {
            this.errorMsg = msg
            return this
        }

        fun errorIcon(@DrawableRes icon: Int): Builder {
            this.errorIcon = icon
            return this
        }

        fun emptyMsg(msg: String): Builder {
            this.emptyMsg = msg
            return this
        }

        fun emptyIcon(@DrawableRes icon: Int): Builder {
            this.emptyIcon = icon
            return this
        }

        fun loadingMsg(msg: String): Builder {
            this.loadingMsg = msg
            return this
        }

        fun alphaDuration(duration: Long): Builder {
            this.alphaDuration = duration
            return this
        }

        fun build() = MultiStateConfig(
            errorMsg = errorMsg,
            errorIcon = errorIcon,
            emptyMsg = emptyMsg,
            emptyIcon = emptyIcon,
            loadingMsg = loadingMsg,
            alphaDuration = alphaDuration
        )
    }
}