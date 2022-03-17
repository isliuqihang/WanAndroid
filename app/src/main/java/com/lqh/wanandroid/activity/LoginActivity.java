package com.lqh.wanandroid.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lqh.fastlibrary.manager.InputTextManager;
import com.lqh.fastlibrary.modle.activity.FastTitleActivity;
import com.lqh.fastlibrary.utils.ToastUtil;
import com.lqh.fastlibrary.view.SubmitButton;
import com.lqh.fastlibrary.view.core.title.TitleBarView;
import com.lqh.fastlibrary.view.sweetalertdialog.SweetAlertDialog;
import com.lqh.wanandroid.ConstantValues;
import com.lqh.wanandroid.R;
import com.lqh.wanandroid.entity.takeout.LoginInfoEntity;
import com.lqh.wanandroid.network.ApiClient;
import com.lqh.wanandroid.utils.KeyboardWatcher;
import com.rxjava.rxlife.RxLife;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;


/**
 * <pre>
 *     description:登录界面
 *     date:2021/8/9 0009
 *     update:2021/8/9 0009  11:48
 *     version:1.0
 *     @author LQH
 * </pre>
 */

public final class LoginActivity extends FastTitleActivity implements
        KeyboardWatcher.SoftKeyboardStateListener,
        TextView.OnEditorActionListener {


    private ImageView mLogoView;

    private ViewGroup mBodyLayout;
    private EditText mPhoneView;
    private EditText mPasswordView;

    private View mForgetView;
    private SubmitButton mCommitView;

    private View mOtherView;
    private View mQQView;
    private View mWeChatView;

    /**
     * logo 缩放比例
     */
    private final float mLogoScale = 0.8f;
    /**
     * 动画时间
     */
    private final int mAnimTime = 300;


    /**
     * Activity或Fragment 布局xml
     *
     * @return
     */
    @Override
    public int getContentLayout() {
        return R.layout.activity_login;
    }


    @Override
    public void beforeSetContentView() {
        postDelayed(() -> KeyboardWatcher.with(LoginActivity.this)
                .setListener(LoginActivity.this), 500);
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    @Override
    public void initView(Bundle savedInstanceState) {
        mLogoView = findViewById(R.id.iv_login_logo);
        mBodyLayout = findViewById(R.id.ll_login_body);
        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mForgetView = findViewById(R.id.tv_login_forget);
        mCommitView = findViewById(R.id.btn_login_commit);
        mOtherView = findViewById(R.id.ll_login_other);
        mQQView = findViewById(R.id.iv_login_qq);
        mWeChatView = findViewById(R.id.iv_login_wechat);


        mPasswordView.setOnEditorActionListener(this);


        setOnClickListener(mForgetView, mCommitView, mQQView, mWeChatView);
        mOtherView.setVisibility(View.GONE);

        InputTextManager.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .build();
    }

    /**
     * 一般用于最终实现子类设置TitleBarView 其它属性
     *
     * @param titleBar
     */
    @Override
    public void setTitleBar(TitleBarView titleBar) {

        titleBar.setRightTextDrawable(ResourceUtils.getDrawable(R.drawable.ic_q_a))
                .setRightTextDrawableHeight(ConvertUtils.dp2px(24))
                .setRightTextDrawableWidth(ConvertUtils.dp2px(24))
                .setOnRightTextClickListener(v -> showQADialog());
    }

    private void showQADialog() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mContext);
        sweetAlertDialog.setTitle("功能介绍");
        sweetAlertDialog.setContentText("该页面展示了串行请求相关的逻辑代码。比如获取token后进行登录操作。");
        sweetAlertDialog.setConfirmButton("查看", sweetAlertDialog1 -> WebViewActivity.start(mContext, "https://juejin.cn/post/6844903870074732551"));
        sweetAlertDialog.show();
    }


    /**
     * {@link KeyboardWatcher.SoftKeyboardStateListener}
     */

    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        // 执行位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", 0, -mCommitView.getHeight());
        objectAnimator.setDuration(mAnimTime);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

        // 执行缩小动画
        mLogoView.setPivotX(mLogoView.getWidth() / 2f);
        mLogoView.setPivotY(mLogoView.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", 1f, mLogoScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", 1f, mLogoScale);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", 0f, -mCommitView.getHeight());
        animatorSet.play(translationY).with(scaleX).with(scaleY);
        animatorSet.setDuration(mAnimTime);
        animatorSet.start();
    }

    @Override
    public void onSoftKeyboardClosed() {
        // 执行位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", mBodyLayout.getTranslationY(), 0f);
        objectAnimator.setDuration(mAnimTime);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

        if (mLogoView.getTranslationY() == 0) {
            return;
        }

        // 执行放大动画
        mLogoView.setPivotX(mLogoView.getWidth() / 2f);
        mLogoView.setPivotY(mLogoView.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", mLogoScale, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", mLogoScale, 1f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", mLogoView.getTranslationY(), 0f);
        animatorSet.play(translationY).with(scaleX).with(scaleY);
        animatorSet.setDuration(mAnimTime);
        animatorSet.start();
    }

    /**
     * {@link TextView.OnEditorActionListener}
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE && mCommitView.isEnabled()) {
            // 模拟点击登录按钮
            onClick(mCommitView);
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        if (view == mCommitView) {
            if (mPhoneView.getText().toString().length() != 11) {
                mPhoneView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake_anim));
                mCommitView.showError(3000);
                ToastUtil.showWarning("请输入手机号码");
                return;
            }
            // 隐藏软键盘
            hideKeyboard(getCurrentFocus());


            getTokenRequest();
        }
    }

    private String type = "8";
    private String appId = "109";

    private void getTokenRequest() {
        String phone = mPhoneView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        Observable<String> tokenObservableLife = ApiClient.getTokenObservableLife();
        tokenObservableLife.flatMap((Function<String, ObservableSource<LoginInfoEntity>>) string -> {
            LogUtils.d("获取token: " + string);
            SPUtils.getInstance().put(ConstantValues.TOKEN, string);

            return ApiClient.loginByPhoneObservableLife(phone, password, type, appId);
        })
                .doOnSubscribe(disposable -> showLoadingView("正在登录..."))
                .doFinally(() -> hideLoadingView())
                .to(RxLife.toMain(this))
                .subscribe(loginInfoEntity -> LogUtils.d(loginInfoEntity.getStoreName()), throwable -> LogUtils.i(throwable.getMessage()));


//        Observable<LoginInfoEntity> loginInfoEntityObservable = ApiClient.loginByPhoneObservableLife(phone, password, type, appId);
//        loginInfoEntityObservable
//                .doOnSubscribe(disposable -> {
//                    showLoadingView("正在登录,请稍等...");
//                })
//                .doFinally(() -> hideLoadingView())
//                .to(RxLife.toMain(this))
//                .subscribe(loginInfoEntity -> {
//
//
//                    LogUtils.d(loginInfoEntity.getStoreName());
//
//                    Observable<ShopInfoEntity> shopInfoObservableLife = ApiClient.getShopInfoObservableLife();
//                    shopInfoObservableLife.doOnSubscribe(disposable -> {
//                        showLoadingView("正在获取店铺信息,请稍等...");
//                    })
//                            .doFinally(() -> hideLoadingView())
//                            .to(RxLife.toMain(this))
//                            .subscribe(shopInfoEntity -> {
//
//
//                                LogUtils.d(shopInfoEntity.getStoreName());
//                            }, (OnError) error -> {
//                                int errorCode = error.getErrorCode();
//                                String errorMsg = error.getErrorMsg();
//                                LogUtils.d("错误信息: " + errorCode + "   " + errorMsg);
//
//                                mCommitView.reset();
//                                showErrorDialog(errorMsg);
//                            });
//                }, (OnError) error -> {
//                    int errorCode = error.getErrorCode();
//                    String errorMsg = error.getErrorMsg();
//                    LogUtils.d("错误信息: " + errorCode + "   " + errorMsg);
//
//                    mCommitView.reset();
//                    showErrorDialog(errorMsg);
//                });

    }
}