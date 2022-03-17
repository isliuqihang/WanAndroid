package com.lqh.fastlibrary.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import com.lqh.fastlibrary.FastConstant
import com.lqh.fastlibrary.FastManager
import com.lqh.fastlibrary.i.*
import com.lqh.fastlibrary.manager.LauLogger
import com.lqh.fastlibrary.manager.RxJavaManager
import com.lqh.fastlibrary.utils.FastUtil
import com.lqh.fastlibrary.view.sweetalertdialog.SweetAlertDialog
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.components.support.RxFragment
import org.greenrobot.eventbus.EventBus

/**
 * <pre>
 * description:所有Fragment的基类实现懒加载
 * date:2021/7/29 0029
 * update:2021/7/29 0029  15:47
 * version:1.0
 * @author LQH
</pre> *
 */
abstract class BasisFragment : RxFragment(), IBasisView, HandlerAction, ClickAction,BundleAction {
    protected var mContext: Activity? = null

    @JvmField
    protected var mContentView: View? = null
    protected var mIsFirstShow = false
    protected var mIsViewLoaded = false
    protected var TAG = javaClass.simpleName
    protected var mIsVisibleChanged = false

    /**
     * 是否在ViewPager
     *
     * @return
     */
    private var isInViewPager = false

    @JvmField
    protected var mSavedInstanceState: Bundle? = null
    private val mSweetAlertDialog: SweetAlertDialog? = null

    /**
     * 检查Fragment或FragmentActivity承载的Fragment是否只有一个
     *
     * @return
     */
    private val isSingleFragment: Boolean
        get() {
            var size = 0
            val manager = fragmentManager
            manager?.let {
                size = it.fragments.size
            }
            LauLogger.i(TAG, "$TAG;FragmentManager承载Fragment数量:$size")
            return size <= 1
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as Activity
        mIsFirstShow = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mSavedInstanceState = savedInstanceState
        beforeSetContentView()
        mContentView = inflater.inflate(getContentLayout(), container, false)
        //解决StatusLayoutManager与SmartRefreshLayout冲突
        if (this is IFastRefreshLoadView<*>) {
            if (FastUtil.isClassExist(FastConstant.SMART_REFRESH_LAYOUT_CLASS)) {
                if (mContentView!!.javaClass == SmartRefreshLayout::class.java) {
                    val frameLayout = FrameLayout(mContext!!)
                    if (mContentView!!.layoutParams != null) {
                        frameLayout.layoutParams = mContentView!!.layoutParams
                    }
                    frameLayout.addView(mContentView)
                    mContentView = frameLayout
                }
            }
        }
        mIsViewLoaded = true
        if (isEventBusEnable) {
            if (FastUtil.haveEventBusAnnotation(this)) {
                EventBus.getDefault().register(this)
            }
        }
        beforeInitView(savedInstanceState)
        initView(savedInstanceState)
        if (isSingleFragment && !mIsVisibleChanged) {
            if (userVisibleHint || isVisible || !isHidden) {
                onVisibleChanged(true)
            }
        }
        LauLogger.i(TAG, TAG + ";mIsVisibleChanged:" + mIsVisibleChanged
                + ";getUserVisibleHint:" + userVisibleHint
                + ";isHidden:" + isHidden + ";isVisible:" + isVisible)
        return mContentView
    }

    override fun beforeInitView(savedInstanceState: Bundle?) {
        if (FastManager.getInstance().activityFragmentControl != null) {
            FastManager.getInstance().activityFragmentControl.setContentViewBackground(mContentView,
                this.javaClass)
        }
    }

    /**
     * 根据资源 id 获取一个 View 对象
     */
    override fun <V : View?> findViewById(@IdRes id: Int): V {
        return mContentView!!.findViewById(id)
    }

    override fun getBundle(): Bundle? {
        return arguments
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mContentView = null
        mContext = null
        mSavedInstanceState = null
        TAG = null
    }

    override fun onDestroy() {
        if (isEventBusEnable) {
            if (FastUtil.haveEventBusAnnotation(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        LauLogger.i(TAG, "onResume-isAdded:" + isAdded + ";getUserVisibleHint:" + userVisibleHint
                + ";isHidden:" + isHidden + ";isVisible:" + isVisible + ";isResume:" + isResumed + ";isVisibleToUser:" + isVisibleToUser(
            this) + ";host:")
        if (isAdded && isVisibleToUser(this)) {
            onVisibleChanged(true)
        }
    }

    /**
     * @param fragment
     * @return
     */
    private fun isVisibleToUser(fragment: BasisFragment?): Boolean {
        if (fragment == null) {
            return false
        }
        if (fragment.parentFragment != null) {
            return isVisibleToUser(fragment.parentFragment as BasisFragment?) && if (fragment.isInViewPager) fragment.userVisibleHint else fragment.isVisible
        }
        return if (fragment.isInViewPager) fragment.userVisibleHint else fragment.isVisible
    }

    /**
     * 不在viewpager中Fragment懒加载
     */
    @SuppressLint("CheckResult")
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!mIsViewLoaded) {
            RxJavaManager.getInstance().setTimer(10)
                .compose(bindUntilEvent(FragmentEvent.DESTROY)).subscribe {
                    onHiddenChanged(hidden)
                }
        } else {
            onVisibleChanged(!hidden)
        }
    }

    /**
     * 在viewpager中的Fragment懒加载
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isInViewPager = true
        if (!mIsViewLoaded) {
            RxJavaManager.getInstance().setTimer(10)
                .compose(bindUntilEvent(FragmentEvent.DESTROY)).subscribe {
                    userVisibleHint = isVisibleToUser
                }
        } else {
            onVisibleChanged(isVisibleToUser)
        }
    }

    /**
     * 用户可见变化回调
     *
     * @param isVisibleToUser
     */
    protected fun onVisibleChanged(isVisibleToUser: Boolean) {
        LauLogger.i(TAG, "onVisibleChanged-isVisibleToUser:$isVisibleToUser")
        mIsVisibleChanged = true
        if (isVisibleToUser) {
            //避免因视图未加载子类刷新UI抛出异常
            if (!mIsViewLoaded) {
                RxJavaManager.getInstance().setTimer(10)
                    .compose(bindUntilEvent(FragmentEvent.DESTROY)).subscribe {
                        onVisibleChanged(true)
                    }
            } else {
                fastLazyLoad()
            }
        }
    }

    private fun fastLazyLoad() {
        if (mIsFirstShow && mIsViewLoaded) {
            mIsFirstShow = false
            loadData()
        }
    }

    /**
     * 当前加载对话框是否在显示中
     */
    val isShowDialog: Boolean
        get() {
            if (mContext != null && mContext is BasisActivity) {
                val basisActivity = mContext as BasisActivity
                return basisActivity.isShowDialog
            }
            return false
        }

    /**
     * 显示加载对话框
     */
    fun showDialog() {
        if (mContext != null && mContext is BasisActivity) {
            val basisActivity = mContext as BasisActivity
            basisActivity.showLoadingView()
        }
    }

    /**
     * 隐藏加载对话框
     */
    fun hideDialog() {
        if (mContext != null && mContext is BasisActivity) {
            val basisActivity = mContext as BasisActivity
            basisActivity.hideLoadingView()
        }
    }

    override fun showLoadingView() {
        if (mContext != null && mContext is BasisActivity) {
            if (mContext!!.isFinishing || mContext!!.isDestroyed) {
                return
            }
            val basisActivity = mContext as BasisActivity
            basisActivity.showLoadingView("")
        }
    }

    override fun showLoadingView(loadingText: String?) {
        if (mContext != null && mContext is BasisActivity) {
            if (mContext!!.isFinishing || mContext!!.isDestroyed) {
                return
            }
            val basisActivity = mContext as BasisActivity
            basisActivity.showLoadingView(loadingText)
        }
    }

    fun showSuccessDialog(successMsg: String?) {
        if (mContext != null && mContext is BasisActivity) {
            if (mContext!!.isFinishing || mContext!!.isDestroyed) {
                return
            }
            val basisActivity = mContext as BasisActivity
            basisActivity.showSuccessDialog(successMsg)
        }
    }

    fun showErrorDialog(errorMsg: String?) {
        if (mContext != null && mContext is BasisActivity) {
            if (mContext!!.isFinishing || mContext!!.isDestroyed) {
                return
            }
            val basisActivity = mContext as BasisActivity
            basisActivity.showErrorDialog(errorMsg)
        }
    }

    fun showWarningDialog(warningMsg: String?) {
        if (mContext != null && mContext is BasisActivity) {
            if (mContext!!.isFinishing || mContext!!.isDestroyed) {
                return
            }
            val basisActivity = mContext as BasisActivity
            basisActivity.showWarningDialog(warningMsg)
        }
    }

    override fun hideLoadingView() {
        if (mContext != null && mContext is BasisActivity) {
            val basisActivity = mContext as BasisActivity
            basisActivity.hideLoadingView()
        }
    }
}