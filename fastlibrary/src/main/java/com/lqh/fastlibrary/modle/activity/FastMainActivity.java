package com.lqh.fastlibrary.modle.activity;

import android.os.Bundle;

import com.lqh.fastlibrary.R;
import com.lqh.fastlibrary.base.BasisActivity;
import com.lqh.fastlibrary.delegate.FastMainTabDelegate;
import com.lqh.fastlibrary.i.IFastMainView;

/**
 * @Author: AriesHoo on 2018/7/23 10:00
 * @E-Mail: AriesHoo@126.com
 * Function: 快速创建主页Activity布局
 * Description:
 */
public abstract class FastMainActivity extends BasisActivity implements IFastMainView {

    protected FastMainTabDelegate mFastMainTabDelegate;

    @Override
    public int getContentLayout() {
        return isSwipeEnable() ? R.layout.fast_activity_main_view_pager : R.layout.fast_activity_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mFastMainTabDelegate != null) {
            mFastMainTabDelegate.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        mFastMainTabDelegate = new FastMainTabDelegate(mContentView, this, this);
    }

    @Override
    public Bundle getSavedInstanceState() {
        return mSavedInstanceState;
    }

    @Override
    public void onBackPressed() {
        quitApp();
    }

    @Override
    protected void onDestroy() {
        if (mFastMainTabDelegate != null) {
            mFastMainTabDelegate.onDestroy();
        }
        super.onDestroy();
    }
}
