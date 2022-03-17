package com.lqh.fastlibrary.modle.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.lqh.fastlibrary.R;
import com.lqh.fastlibrary.base.BasisFragment;
import com.lqh.fastlibrary.delegate.FastMainTabDelegate;
import com.lqh.fastlibrary.i.IFastMainView;
import com.lqh.fastlibrary.view.tablayout.listener.OnTabSelectListener;

/**
 * @Author: AriesHoo on 2018/7/23 11:27
 * @E-Mail: AriesHoo@126.com
 * Function: 快速创建主页布局
 * Description:
 */
public abstract class FastMainFragment extends BasisFragment implements IFastMainView, OnTabSelectListener {

    protected FastMainTabDelegate mFastMainTabDelegate;

    @Override
    public void setViewPager(ViewPager mViewPager) {
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    public int getContentLayout() {
        return isSwipeEnable() ? R.layout.fast_activity_main_view_pager : R.layout.fast_activity_main;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
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
    public void onTabReselect(int position) {

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public Bundle getSavedInstanceState() {
        return mSavedInstanceState;
    }
}
