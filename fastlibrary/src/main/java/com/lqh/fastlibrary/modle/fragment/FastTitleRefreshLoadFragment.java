package com.lqh.fastlibrary.modle.fragment;

import android.os.Bundle;

import com.lqh.fastlibrary.delegate.FastTitleDelegate;
import com.lqh.fastlibrary.i.IFastTitleView;
import com.lqh.fastlibrary.view.core.title.TitleBarView;

/**
 * @Author: AriesHoo on 2018/7/23 10:34
 * @E-Mail: AriesHoo@126.com
 * Function: 设置有TitleBar及下拉刷新Fragment
 * Description:
 */
public abstract class FastTitleRefreshLoadFragment<T> extends FastRefreshLoadFragment<T> implements IFastTitleView {

    protected TitleBarView mTitleBar;

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        mTitleBar = new FastTitleDelegate(mContentView, this, getClass()).mTitleBar;
        super.beforeInitView(savedInstanceState);
    }
}
