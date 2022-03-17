package com.lqh.fastlibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.lqh.fastlibrary.delegate.FastRefreshDelegate;
import com.lqh.fastlibrary.delegate.FastTitleDelegate;
import com.lqh.fastlibrary.i.ActivityFragmentControl;
import com.lqh.fastlibrary.i.IFastRefreshLoadView;
import com.lqh.fastlibrary.i.IFastRefreshView;
import com.lqh.fastlibrary.i.IFastTitleView;
import com.lqh.fastlibrary.manager.LauLogger;
import com.lqh.fastlibrary.modle.activity.FastRefreshLoadActivity;
import com.lqh.fastlibrary.modle.fragment.FastRefreshLoadFragment;
import com.lqh.fastlibrary.utils.FastStackUtil;
import com.lqh.fastlibrary.utils.FastUtil;
import com.lqh.fastlibrary.view.core.navigation.KeyboardHelper;


/**
 * @Author: AriesHoo on 2018/7/30 13:48
 * @E-Mail: AriesHoo@126.com
 * Function: Activity/Fragment生命周期
 * Description:
 */
public class FastLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    private String TAG = getClass().getSimpleName();
    private ActivityFragmentControl mActivityFragmentControl;
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
    private FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycleCallbacks;


    @Override
    public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
        LauLogger.i(TAG, "onActivityCreated:" + activity.getClass().getSimpleName() + ";contentView:" + FastUtil.getRootView(activity));
        getControl();

        //统一Activity堆栈管理
        FastStackUtil.getInstance().push(activity);
        //统一横竖屏操作
        if (mActivityFragmentControl != null) {
            mActivityFragmentControl.setRequestedOrientation(activity);
        }
        //统一Fragment生命周期处理
        if (activity instanceof FragmentActivity) {
            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            fragmentManager.registerFragmentLifecycleCallbacks(this, true);
            if (mFragmentLifecycleCallbacks != null) {
                fragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks, true);
            }
        }
        //回调给开发者实现自己应用逻辑
        if (mActivityLifecycleCallbacks != null) {
            mActivityLifecycleCallbacks.onActivityCreated(activity, savedInstanceState);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        View contentView = FastUtil.getRootView(activity);
        LauLogger.i(TAG, "onActivityStarted:" + activity.getClass().getSimpleName() + ";contentView:" + contentView);
        boolean isSet = activity.getIntent().getBooleanExtra(FastConstant.IS_SET_CONTENT_VIEW_BACKGROUND, false);
        if (!isSet) {
            setContentViewBackground(FastUtil.getRootView(activity), activity.getClass());
        }
        //设置TitleBarView-先设置TitleBarView避免多状态将布局替换
        if (activity instanceof IFastTitleView
                && !(activity instanceof IFastRefreshLoadView)
                && !activity.getIntent().getBooleanExtra(FastConstant.IS_SET_TITLE_BAR_VIEW, false)
                && contentView != null) {
            FastDelegateManager.getInstance().putFastTitleDelegate(activity.getClass(),
                    new FastTitleDelegate(contentView, (IFastTitleView) activity, activity.getClass()));
            activity.getIntent().putExtra(FastConstant.IS_SET_TITLE_BAR_VIEW, true);
        }
        //配置下拉刷新
        if (activity instanceof IFastRefreshView
                && !(FastRefreshLoadActivity.class.isAssignableFrom(activity.getClass()))
                && !activity.getIntent().getBooleanExtra(FastConstant.IS_SET_REFRESH_VIEW, false)) {
            IFastRefreshView refreshView = (IFastRefreshView) activity;
            if (contentView != null
                    || refreshView.getContentView() != null) {
                FastDelegateManager.getInstance().putFastRefreshDelegate(activity.getClass(),
                        new FastRefreshDelegate(
                                refreshView.getContentView() != null ? refreshView.getContentView() : contentView,
                                (IFastRefreshView) activity));
                activity.getIntent().putExtra(FastConstant.IS_SET_REFRESH_VIEW, true);
            }
        }
        //回调给开发者实现自己应用逻辑
        if (mActivityLifecycleCallbacks != null) {
            mActivityLifecycleCallbacks.onActivityStarted(activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LauLogger.i(TAG, "onActivityResumed:" + activity.getClass().getSimpleName());
        if (mActivityLifecycleCallbacks != null) {
            mActivityLifecycleCallbacks.onActivityResumed(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LauLogger.i(TAG, "onActivityPaused:" + activity.getClass().getSimpleName() + ";isFinishing:" + activity.isFinishing());
        //Activity销毁前的时机需要关闭软键盘-在onActivityStopped及onActivityDestroyed生命周期内已无法关闭
        if (activity.isFinishing()) {
            KeyboardHelper.closeKeyboard(activity);
        }
        //回调给开发者实现自己应用逻辑
        if (mActivityLifecycleCallbacks != null) {
            mActivityLifecycleCallbacks.onActivityPaused(activity);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LauLogger.i(TAG, "onActivityStopped:" + activity.getClass().getSimpleName() + ";isFinishing:" + activity.isFinishing());
        //回调给开发者实现自己应用逻辑
        if (mActivityLifecycleCallbacks != null) {
            mActivityLifecycleCallbacks.onActivityStopped(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        LauLogger.i(TAG, "onActivitySaveInstanceState:" + activity.getClass().getSimpleName());
        //回调给开发者实现自己应用逻辑
        if (mActivityLifecycleCallbacks != null) {
            mActivityLifecycleCallbacks.onActivitySaveInstanceState(activity, outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //横竖屏会重绘将状态重置
        if (activity.getIntent() != null) {
            activity.getIntent().removeExtra(FastConstant.IS_SET_STATUS_VIEW_HELPER);
            activity.getIntent().removeExtra(FastConstant.IS_SET_NAVIGATION_VIEW_HELPER);
            activity.getIntent().removeExtra(FastConstant.IS_SET_CONTENT_VIEW_BACKGROUND);
            activity.getIntent().removeExtra(FastConstant.IS_SET_REFRESH_VIEW);
            activity.getIntent().removeExtra(FastConstant.IS_SET_TITLE_BAR_VIEW);
        }
        LauLogger.i(TAG, "onActivityDestroyed:" + activity.getClass().getSimpleName() + ";isFinishing:" + activity.isFinishing());
        FastStackUtil.getInstance().pop(activity, false);

        //清除下拉刷新代理FastRefreshDelegate
        FastDelegateManager.getInstance().removeFastRefreshDelegate(activity.getClass());
        //清除标题栏代理类FastTitleDelegate
        FastDelegateManager.getInstance().removeFastTitleDelegate(activity.getClass());
        //清除BasisHelper
        FastDelegateManager.getInstance().removeBasisHelper(activity);
        //回调给开发者实现自己应用逻辑
        if (mActivityLifecycleCallbacks != null) {
            mActivityLifecycleCallbacks.onActivityDestroyed(activity);
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        boolean isSet = f.getArguments() != null ? f.getArguments().getBoolean(FastConstant.IS_SET_CONTENT_VIEW_BACKGROUND, false) : false;
        if (!isSet) {
            setContentViewBackground(v, f.getClass());
        }
        //设置TitleBarView-先设置TitleBarView避免多状态将布局替换
        if (f instanceof IFastTitleView
                && !(f instanceof IFastRefreshLoadView)
                && v != null) {
            FastDelegateManager.getInstance().putFastTitleDelegate(f.getClass(),
                    new FastTitleDelegate(v, (IFastTitleView) f, f.getClass()));
        }
        //刷新功能处理
        if (f instanceof IFastRefreshView
                && !(FastRefreshLoadFragment.class.isAssignableFrom(f.getClass()))) {
            IFastRefreshView refreshView = (IFastRefreshView) f;
            FastDelegateManager.getInstance().putFastRefreshDelegate(f.getClass(),
                    new FastRefreshDelegate(
                            refreshView.getContentView() != null ? refreshView.getContentView() : f.getView(),
                            refreshView));
        }
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        if (f.getArguments() != null) {
            f.getArguments().putBoolean(FastConstant.IS_SET_CONTENT_VIEW_BACKGROUND, false);
        }
        FastDelegateManager.getInstance().removeFastRefreshDelegate(f.getClass());
        FastDelegateManager.getInstance().removeFastTitleDelegate(f.getClass());
    }

    /**
     * 实时获取回调
     */

    private void getControl() {
        mActivityFragmentControl = FastManager.getInstance().getActivityFragmentControl();
        if (mActivityFragmentControl == null) {
            return;
        }
        mActivityLifecycleCallbacks = mActivityFragmentControl.getActivityLifecycleCallbacks();
        mFragmentLifecycleCallbacks = mActivityFragmentControl.getFragmentLifecycleCallbacks();
    }

    /**
     * 回调设置Activity/Fragment背景
     *
     * @param v
     * @param cls
     */
    private void setContentViewBackground(View v, Class<?> cls) {
        if (mActivityFragmentControl != null && v != null) {
            mActivityFragmentControl.setContentViewBackground(v, cls);
        }
    }

    /**
     * 设置滑动返回相关
     *
     * @param activity
     */
    private void setSwipeBack(final Activity activity) {

    }


    /**
     * 获取Activity 顶部View(用于延伸至状态栏下边)
     *
     * @param target
     * @return
     */
    private View getTopView(View target) {
        if (target != null && target instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) target;
            if (group.getChildCount() > 0) {
                target = ((ViewGroup) target).getChildAt(0);
            }
        }
        return target;
    }
}
