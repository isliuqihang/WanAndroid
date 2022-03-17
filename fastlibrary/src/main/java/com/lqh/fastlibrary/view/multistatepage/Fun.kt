package com.ruohuo.businessman.view.multistatepage

import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer
import com.lqh.fastlibrary.view.multistatepage.state.EmptyState
import com.lqh.fastlibrary.view.multistatepage.state.ErrorState
import com.lqh.fastlibrary.view.multistatepage.state.LoadingState
import com.lqh.fastlibrary.view.multistatepage.state.SuccessState


/**
 * @ProjectName: MultiStatePage
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2020/9/17 15:04
 */


fun MultiStateContainer.showSuccess(callBack: () -> Unit = {}) {
    show<SuccessState> {
        callBack.invoke()
    }
}

fun MultiStateContainer.showError(callBack: () -> Unit = {}) {
    show<ErrorState> {
        callBack.invoke()
    }
}

fun MultiStateContainer.showEmpty(callBack: () -> Unit = {}) {
    show<EmptyState> {
        callBack.invoke()
    }
}

fun MultiStateContainer.showLoading(callBack: () -> Unit = {}) {
    show<LoadingState> {
        callBack.invoke()
    }
}
