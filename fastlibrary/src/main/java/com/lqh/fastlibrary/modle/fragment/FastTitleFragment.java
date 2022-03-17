package com.lqh.fastlibrary.modle.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.lqh.fastlibrary.FastLifecycleCallbacks;
import com.lqh.fastlibrary.base.BasisFragment;
import com.lqh.fastlibrary.i.IFastTitleView;
import com.lqh.fastlibrary.view.core.title.TitleBarView;
import com.lqh.fastlibrary.view.core.util.FindViewUtil;

/**
 * @Author: AriesHoo on 2018/7/23 10:34
 * @E-Mail: AriesHoo@126.com
 * Function: 设置有TitleBar的Fragment
 * Description:
 * 1、2019-3-25 17:03:43 推荐使用{@link IFastTitleView}通过接口方式由FastLib自动处理{@link FastLifecycleCallbacks#onFragmentStarted(FragmentManager, Fragment)}
 */
public abstract class FastTitleFragment extends BasisFragment implements IFastTitleView {

    protected TitleBarView mTitleBar;

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        mTitleBar = FindViewUtil.getTargetView(mContentView, TitleBarView.class);
    }
}
