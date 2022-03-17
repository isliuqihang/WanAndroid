package com.lqh.fastlibrary.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ObjectUtils
import com.gyf.immersionbar.ImmersionBar
import com.lqh.fastlibrary.FastConstant
import com.lqh.fastlibrary.FastManager
import com.lqh.fastlibrary.R
import com.lqh.fastlibrary.i.*
import com.lqh.fastlibrary.manager.RxJavaManager
import com.lqh.fastlibrary.utils.FastStackUtil
import com.lqh.fastlibrary.utils.FastUtil
import com.lqh.fastlibrary.view.sweetalertdialog.SweetAlertDialog
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus

/**
 * <pre>
 * description:所有Activity的基类
 * date:2021/7/29 0029
 * update:2021/7/29 0029  15:45
 * version:1.0
 * @author LQH
</pre> *
 */
abstract class BasisActivity : RxAppCompatActivity(), IBasisView, HandlerAction, ClickAction,BundleAction,
    KeyboardAction {
    @JvmField
    protected var mContext: Activity? = null

    @JvmField
    protected var mContentView: View? = null

    @JvmField
    protected var mSavedInstanceState: Bundle? = null
    protected var mIsViewLoaded = false
    protected var mIsFirstShow = true
    protected var mIsFirstBack = true
    protected var mDelayBack: Long = 2000
    protected var TAG = javaClass.simpleName
    private var mQuitAppControl: QuitAppControl? = null
    private var mSweetAlertDialog: SweetAlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        if (isEventBusEnable) {
            if (FastUtil.haveEventBusAnnotation(this)) {
                EventBus.getDefault().register(this)
            }
        }
        super.onCreate(savedInstanceState)
        mSavedInstanceState = savedInstanceState
        mContext = this
        beforeSetContentView()
        mContentView = View.inflate(mContext, getContentLayout(), null)
        if (applySystemBarDrawable()) {
            ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(false)
                .navigationBarColor(R.color.white)
                .autoDarkModeEnable(true).init()
        }
        if (this is IFastRefreshLoadView<*>) {
            if (FastUtil.isClassExist(FastConstant.SMART_REFRESH_LAYOUT_CLASS)) {
                if (mContentView!!.javaClass == SmartRefreshLayout::class.java) {
                    val frameLayout = FrameLayout(mContext!!)
                    if (mContentView!!.getLayoutParams() != null) {
                        frameLayout.layoutParams = mContentView!!.getLayoutParams()
                    }
                    frameLayout.addView(mContentView)
                    mContentView = frameLayout
                }
            }
        }
        setContentView(mContentView)
        initSoftKeyboard()
        mIsViewLoaded = true
        beforeInitView(savedInstanceState)
        initView(savedInstanceState)
    }


    override fun getBundle(): Bundle? {
        return intent.extras
    }

    /**
     * 和 setContentView 对应的方法
     */
    fun getContentView(): ViewGroup {
        return findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)!!
    }

    /**
     * 初始化软键盘
     */
    protected fun initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener { v: View? ->
            // 隐藏软键，避免内存泄漏
            hideKeyboard(currentFocus)
        }
    }

    override fun finish() {
        // 隐藏软键，避免内存泄漏
        hideKeyboard(currentFocus)
        super.finish()
    }

    override fun onResume() {
        beforeFastLazyLoad()
        super.onResume()
    }

    override fun onDestroy() {
        if (isEventBusEnable) {
            if (FastUtil.isClassExist(FastConstant.EVENT_BUS_CLASS)) {
                if (FastUtil.haveEventBusAnnotation(this)) {
                    EventBus.getDefault().unregister(this)
                }
            }
        }
        super.onDestroy()
        mContentView = null
        mContext = null
        mSavedInstanceState = null
        mQuitAppControl = null
        mSweetAlertDialog = null
        TAG = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val list = supportFragmentManager.fragments
        if (ObjectUtils.isEmpty(list)) {
            return
        }
        for (f in list) {
            f!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val control = FastManager.getInstance().activityKeyEventControl
        return if (control != null) {
            if (control.onKeyDown(this, keyCode, event)) {
                true
            } else super.onKeyDown(keyCode, event)
        } else super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        val control = FastManager.getInstance().activityKeyEventControl
        return if (control != null) {
            if (control.onKeyUp(this, keyCode, event)) {
                true
            } else super.onKeyUp(keyCode, event)
        } else super.onKeyUp(keyCode, event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent): Boolean {
        val control = FastManager.getInstance().activityKeyEventControl
        return control?.onKeyLongPress(this, keyCode, event) ?: super.onKeyLongPress(keyCode, event)
    }

    override fun onKeyShortcut(keyCode: Int, event: KeyEvent): Boolean {
        val control = FastManager.getInstance().activityKeyEventControl
        return if (control != null) {
            if (control.onKeyShortcut(this, keyCode, event)) {
                true
            } else super.onKeyShortcut(keyCode, event)
        } else super.onKeyShortcut(keyCode, event)
    }

    override fun onKeyMultiple(keyCode: Int, repeatCount: Int, event: KeyEvent): Boolean {
        val control = FastManager.getInstance().activityKeyEventControl
        return if (control != null) {
            if (control.onKeyMultiple(this, keyCode, repeatCount, event)) {
                true
            } else super.onKeyMultiple(keyCode, repeatCount, event)
        } else super.onKeyMultiple(keyCode, repeatCount, event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val control = FastManager.getInstance().activityDispatchEventControl
        return if (control != null) {
            if (control.dispatchTouchEvent(this, ev)) {
                true
            } else super.dispatchTouchEvent(ev)
        } else super.dispatchTouchEvent(ev)
    }

    override fun dispatchGenericMotionEvent(ev: MotionEvent): Boolean {
        val control = FastManager.getInstance().activityDispatchEventControl
        return if (control != null) {
            if (control.dispatchGenericMotionEvent(this, ev)) {
                true
            } else super.dispatchGenericMotionEvent(ev)
        } else super.dispatchGenericMotionEvent(ev)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val control = FastManager.getInstance().activityDispatchEventControl
        return if (control != null) {
            if (control.dispatchKeyEvent(this, event)) {
                true
            } else super.dispatchKeyEvent(event)
        } else super.dispatchKeyEvent(event)
    }

    @SuppressLint("RestrictedApi")
    override fun dispatchKeyShortcutEvent(event: KeyEvent): Boolean {
        val control = FastManager.getInstance().activityDispatchEventControl
        return if (control != null) {
            if (control.dispatchKeyShortcutEvent(this, event)) {
                true
            } else super.dispatchKeyShortcutEvent(
                event)
        } else super.dispatchKeyShortcutEvent(event)
    }

    override fun dispatchTrackballEvent(ev: MotionEvent): Boolean {
        val control = FastManager.getInstance().activityDispatchEventControl
        return if (control != null) {
            if (control.dispatchTrackballEvent(this, ev)) {
                true
            } else super.dispatchTrackballEvent(ev)
        } else super.dispatchTrackballEvent(ev)
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent): Boolean {
        val control = FastManager.getInstance().activityDispatchEventControl
        return if (control != null) {
            if (control.dispatchPopulateAccessibilityEvent(this, event)) {
                true
            } else super.dispatchPopulateAccessibilityEvent(event)
        } else super.dispatchPopulateAccessibilityEvent(event)
    }

    override fun beforeInitView(savedInstanceState: Bundle?) {
        if (FastManager.getInstance().activityFragmentControl != null) {
            FastManager.getInstance().activityFragmentControl.setContentViewBackground(mContentView,
                this.javaClass)
        }
    }

    private fun beforeFastLazyLoad() {
        //确保视图加载及视图绑定完成避免刷新UI抛出异常
        if (!mIsViewLoaded) {
            RxJavaManager.getInstance().setTimer(10)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe { }
        } else {
            fastLazyLoad()
        }
    }

    private fun fastLazyLoad() {
        if (mIsFirstShow) {
            mIsFirstShow = false
            loadData()
        }
    }

    /**
     * 退出程序
     */
    protected fun quitApp() {
        mQuitAppControl = FastManager.getInstance().quitAppControl
        mDelayBack = if (mQuitAppControl != null) mQuitAppControl!!.quipApp(mIsFirstBack,
            this) else mDelayBack
        //时延太小或已是第二次提示直接通知执行最终操作
        if (mDelayBack <= 0 || !mIsFirstBack) {
            if (mQuitAppControl != null) {
                mQuitAppControl!!.quipApp(false, this)
            } else {
                FastStackUtil.getInstance().exit()
            }
            return
        }
        //编写逻辑
        if (mIsFirstBack) {
            mIsFirstBack = false
            RxJavaManager.getInstance().setTimer(mDelayBack)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe {
                    mIsFirstBack = true
                }
        }
    }

    override fun showLoadingView() {
        if (isFinishing || isDestroyed) {
            return
        }
        if (ObjectUtils.isEmpty(mSweetAlertDialog) || !mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        } else {
            mSweetAlertDialog!!.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        }
        mSweetAlertDialog!!.setTitle("正在加载中，请稍等...")
        mSweetAlertDialog!!.hideConfirmButton()
        mSweetAlertDialog!!.setCanceledOnTouchOutside(false)
        if (!mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog!!.show()
        }
    }

    fun showSuccessDialog(successMsg: String?) {
        if (isFinishing || isDestroyed) {
            return
        }
        if (ObjectUtils.isEmpty(mSweetAlertDialog) || !mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            LogUtils.d(" new SweetAlertDialog")
        } else {
            mSweetAlertDialog!!.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
            LogUtils.d("显示成功弹唱")
        }
        mSweetAlertDialog!!.setTitle(successMsg)
        mSweetAlertDialog!!.hideConfirmButton()
        mSweetAlertDialog!!.autoDismissWithAnimation(true)
        if (!mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog!!.show()
        }
    }

    fun showErrorDialog(errorMsg: String?) {
        LogUtils.d("显示错误提示窗")
        if (isFinishing || isDestroyed) {
            return
        }
        if (ObjectUtils.isEmpty(mSweetAlertDialog) || !mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        } else {
            mSweetAlertDialog!!.changeAlertType(SweetAlertDialog.ERROR_TYPE)
        }
        mSweetAlertDialog!!.setTitle(errorMsg)
        mSweetAlertDialog!!.hideConfirmButton()
        mSweetAlertDialog!!.autoDismissWithAnimation(true)
        if (!mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog!!.show()
        }
    }

    fun showWarningDialog(warningMsg: String?) {
        if (isFinishing || isDestroyed) {
            return
        }
        if (ObjectUtils.isEmpty(mSweetAlertDialog) || !mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        } else {
            mSweetAlertDialog!!.changeAlertType(SweetAlertDialog.WARNING_TYPE)
        }
        mSweetAlertDialog!!.setTitle(warningMsg)
        mSweetAlertDialog!!.hideConfirmButton()
        mSweetAlertDialog!!.autoDismissWithAnimation(true)
        if (!mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog!!.show()
        }
    }

    /**
     * 当前加载对话框是否在显示中
     */
    val isShowDialog: Boolean
        get() = mSweetAlertDialog != null && mSweetAlertDialog!!.isShowing

    override fun showLoadingView(loadingText: String?) {
        if (isFinishing || isDestroyed) {
            return
        }
        if (ObjectUtils.isEmpty(mSweetAlertDialog) || !mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        } else {
            mSweetAlertDialog!!.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        }
        mSweetAlertDialog!!.setTitle(loadingText)
        if (!mSweetAlertDialog!!.isShowing) {
            mSweetAlertDialog!!.show()
        }
    }

    override fun hideLoadingView() {
        if (ObjectUtils.isNotEmpty(mSweetAlertDialog) && mSweetAlertDialog!!.isShowing && !isFinishing) {
            mSweetAlertDialog!!.dismiss()
            mSweetAlertDialog = null
        }
    }
}