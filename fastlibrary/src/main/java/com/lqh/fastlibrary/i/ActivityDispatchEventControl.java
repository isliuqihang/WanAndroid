package com.lqh.fastlibrary.i;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

import com.lqh.fastlibrary.base.BasisActivity;


/**
 * @Author: AriesHoo on 2018/9/26 16:19
 * @E-Mail: AriesHoo@126.com
 * Activity 必须在前台
 * @Description:
 */
public interface ActivityDispatchEventControl {

    /**
     * {@link BasisActivity#dispatchTouchEvent(MotionEvent)}
     *
     * @param activity
     * @param event
     * @return
     */
    boolean dispatchTouchEvent(Activity activity, MotionEvent event);

    /**
     * {@link BasisActivity#dispatchGenericMotionEvent(MotionEvent)}
     *
     * @param activity
     * @param event
     * @return
     */
    boolean dispatchGenericMotionEvent(Activity activity, MotionEvent event);

    /**
     * {@link BasisActivity#dispatchKeyEvent(KeyEvent)}
     *
     * @param activity
     * @param event
     * @return
     */
    boolean dispatchKeyEvent(Activity activity, KeyEvent event);

    /**
     * {@link BasisActivity#dispatchKeyShortcutEvent(KeyEvent)}
     *
     * @param activity
     * @param event
     * @return
     */
    boolean dispatchKeyShortcutEvent(Activity activity, KeyEvent event);

    /**
     * {@link BasisActivity#dispatchTrackballEvent(MotionEvent)}
     *
     * @param activity
     * @param event
     * @return
     */
    boolean dispatchTrackballEvent(Activity activity, MotionEvent event);

    /**
     * {@link BasisActivity#dispatchPopulateAccessibilityEvent(AccessibilityEvent)}
     *
     * @param activity
     * @param event
     * @return
     */
    boolean dispatchPopulateAccessibilityEvent(Activity activity, AccessibilityEvent event);
}
