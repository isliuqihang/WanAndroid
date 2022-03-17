package com.lqh.fastlibrary.modle.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lqh.fastlibrary.FastLifecycleCallbacks;
import com.lqh.fastlibrary.base.BasisActivity;
import com.lqh.fastlibrary.i.IFastTitleView;
import com.lqh.fastlibrary.view.core.title.TitleBarView;
import com.lqh.fastlibrary.view.core.util.FindViewUtil;

/**
 * @Author: AriesHoo on 2018/7/23 10:35
 * @E-Mail: AriesHoo@126.com
 * Function: 包含TitleBarView的Activity
 * Description:
 * 1、2019-3-25 17:03:43 推荐使用{@link IFastTitleView}通过接口方式由FastLib自动处理{@link FastLifecycleCallbacks#onActivityStarted(Activity)}
 */
public abstract class FastTitleActivity extends BasisActivity implements IFastTitleView {

    protected TitleBarView mTitleBar;

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        mTitleBar = FindViewUtil.getTargetView(mContentView, TitleBarView.class);
    }

    @Override
    protected void onDestroy() {
        mTitleBar = null;
        super.onDestroy();
    }
}
