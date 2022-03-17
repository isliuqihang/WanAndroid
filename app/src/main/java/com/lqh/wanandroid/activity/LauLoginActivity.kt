package com.lqh.wanandroid.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.lqh.fastlibrary.manager.InputTextManager
import com.lqh.fastlibrary.modle.activity.FastTitleActivity
import com.lqh.fastlibrary.view.SubmitButton
import com.lqh.fastlibrary.view.core.title.TitleBarView
import com.lqh.wanandroid.R
import com.lqh.wanandroid.utils.KeyboardWatcher


/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211118
 *     update: 1118
 *     version:1.0
 * </pre>
 */

class LauLoginActivity:FastTitleActivity(), KeyboardWatcher.SoftKeyboardStateListener,
    TextView.OnEditorActionListener {


    private var mLogoView: ImageView? = null

    private var mBodyLayout: ViewGroup? = null
    private var mPhoneView: EditText? = null
    private var mPasswordView: EditText? = null

    private var mForgetView: View? = null
    private var mCommitView: SubmitButton? = null

    private var mOtherView: View? = null
    private var mQQView: View? = null
    private var mWeChatView: View? = null

    /**
     * logo 缩放比例
     */
    private val mLogoScale = 0.8f

    /**
     * 动画时间
     */
    private val mAnimTime = 300
    override fun beforeSetContentView() {
        postDelayed({
            KeyboardWatcher.with(this@LauLoginActivity)
                .setListener(this@LauLoginActivity)
        }, 500)
    }

    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    override fun getContentLayout(): Int {
        return R.layout.activity_login
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {
        mLogoView = findViewById(R.id.iv_login_logo)
        mBodyLayout = findViewById(R.id.ll_login_body)
        mPhoneView = findViewById(R.id.et_login_phone)
        mPasswordView = findViewById(R.id.et_login_password)
        mForgetView = findViewById(R.id.tv_login_forget)
        mCommitView = findViewById(R.id.btn_login_commit)
        mOtherView = findViewById(R.id.ll_login_other)
        mQQView = findViewById(R.id.iv_login_qq)
        mWeChatView = findViewById(R.id.iv_login_wechat)


        mPasswordView?.setOnEditorActionListener(this)



        setOnClickListener(mForgetView, mCommitView, mQQView, mWeChatView)

        mOtherView?.visibility = View.GONE

        InputTextManager.with(this)
            .addView(mPhoneView)
            .addView(mPasswordView)
            .setMain(mCommitView)
            .build()
    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    override fun setTitleBar(titleBar: TitleBarView?) {
    }

    /**
     * 软键盘弹出了
     *
     * @param keyboardHeight            软键盘高度
     */
    override fun onSoftKeyboardOpened(keyboardHeight: Int) {
        // 执行位移动画

        // 执行位移动画
        val objectAnimator =
            ObjectAnimator.ofFloat(mBodyLayout, "translationY", 0f, -mCommitView!!.height.toFloat())
        objectAnimator.duration = mAnimTime.toLong()
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()

        // 执行缩小动画

        // 执行缩小动画
        mLogoView!!.pivotX = mLogoView!!.width / 2f
        mLogoView!!.pivotY = mLogoView!!.height.toFloat()
        val animatorSet = AnimatorSet()
        val scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", 1f, mLogoScale)
        val scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", 1f, mLogoScale)
        val translationY =
            ObjectAnimator.ofFloat(mLogoView, "translationY", 0f, -mCommitView!!.height.toFloat())
        animatorSet.play(translationY).with(scaleX).with(scaleY)
        animatorSet.duration = mAnimTime.toLong()
        animatorSet.start()
    }

    /**
     * 软键盘收起了
     */
    override fun onSoftKeyboardClosed() {
        // 执行位移动画

        // 执行位移动画
        val objectAnimator =
            ObjectAnimator.ofFloat(mBodyLayout, "translationY", mBodyLayout!!.translationY, 0f)
        objectAnimator.duration = mAnimTime.toLong()
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()

        if (mLogoView!!.translationY == 0f) {
            return
        }

        // 执行放大动画

        // 执行放大动画
        mLogoView!!.pivotX = mLogoView!!.width / 2f
        mLogoView!!.pivotY = mLogoView!!.height.toFloat()
        val animatorSet = AnimatorSet()
        val scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", mLogoScale, 1f)
        val scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", mLogoScale, 1f)
        val translationY =
            ObjectAnimator.ofFloat(mLogoView, "translationY", mLogoView!!.translationY, 0f)
        animatorSet.play(translationY).with(scaleX).with(scaleY)
        animatorSet.duration = mAnimTime.toLong()
        animatorSet.start()
    }

    /**
     * Called when an action is being performed.
     *
     * @param v The view that was clicked.
     * @param actionId Identifier of the action.  This will be either the
     * identifier you supplied, or [ EditorInfo.IME_NULL][EditorInfo.IME_NULL] if being called due to the enter key
     * being pressed.
     * @param event If triggered by an enter key, this is the event;
     * otherwise, this is null.
     * @return Return true if you have consumed the action, else false.
     */
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE && mCommitView!!.isEnabled) {
            // 模拟点击登录按钮
            onClick(mCommitView!!)
            return true
        }
        return false
    }
}