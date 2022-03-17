package com.lqh.fastlibrary.i;

import android.view.View;

import com.lqh.fastlibrary.view.multistatepage.MultiStateContainer;

/**
 * @Author: AriesHoo on 2018/7/20 17:08
 * @E-Mail: AriesHoo@126.com
 * Function: StatusLayoutManager 属性控制
 * Description:
 */
public interface IMultiStatusView {
    /**
     * 设置StatusLayoutManager 的目标View
     *
     * @return
     */
    default View getMultiStatusContentView() {
        return null;
    }

    /**
     * 设置StatusLayoutManager属性
     *
     * @param multiStatusView
     */
    default void setMultiStatusView(MultiStateContainer multiStatusView) {

    }


    /**
     * 获取空布局里点击View回调
     *
     * @return
     */
    default View.OnClickListener getEmptyClickListener() {
        return null;
    }

    /**
     * 获取错误布局里点击View回调
     *
     * @return
     */
    default View.OnClickListener getErrorClickListener() {
        return null;
    }

    /**
     * 获取自定义布局里点击View回调
     *
     * @return
     */
    default View.OnClickListener getCustomerClickListener() {
        return null;
    }
}
