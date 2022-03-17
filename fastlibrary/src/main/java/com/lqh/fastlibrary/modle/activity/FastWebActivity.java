package com.lqh.fastlibrary.modle.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.lqh.fastlibrary.FastManager;
import com.lqh.fastlibrary.R;
import com.lqh.fastlibrary.i.TitleBarViewControl;
import com.lqh.fastlibrary.utils.FastUtil;
import com.lqh.fastlibrary.view.core.navigation.NavigationBarControl;
import com.lqh.fastlibrary.view.core.navigation.NavigationViewHelper;
import com.lqh.fastlibrary.view.core.title.TitleBarView;
import com.lqh.fastlibrary.view.core.util.DrawableUtil;

/**
 * @Author: AriesHoo on 2018/6/28 14:59
 * @E-Mail: AriesHoo@126.com
 * Function: App内快速实现WebView功能
 * Description:
 * 1、调整WebView自适应屏幕代码属性{@link #initAgentWeb()}
 * 2、2019-3-20 11:45:07 增加url自动添加http://功能及规范url
 * 3、2021-04-06 16:41:00 增加httpUrl参数配置
 */
public abstract class FastWebActivity extends FastTitleActivity implements NavigationBarControl {

    protected ViewGroup mContainer;
    /**
     * {@use mUrl}
     */
    @Deprecated
    protected String url = "";
    protected String mUrl = "";
    protected String mCurrentUrl;
    protected AlertDialog mAlertDialog;
    protected AgentWeb mAgentWeb;
    protected AgentWeb.CommonBuilder mAgentBuilder;
    private TitleBarViewControl mTitleBarViewControl;
    /**
     * WebView是否处于暂停状态
     */
    private boolean mIsPause;

    public void onWebViewPause() {
        onPause();
    }


    public void onWebViewResume() {
        onResume();
    }


    protected static void start(Context mActivity, Class<? extends FastWebActivity> activity, String url, boolean httpUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("httpUrl", httpUrl);
        FastUtil.startActivity(mActivity, activity, bundle);
    }


    protected void setAgentWeb(AgentWeb mAgentWeb) {
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);
        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false);
    }

    protected void setAgentWeb(AgentWeb.CommonBuilder mAgentBuilder) {

    }

    /**
     * 设置进度条颜色
     *
     * @return
     */
    @ColorInt
    protected int getProgressColor() {
        return -1;
    }

    /**
     * 设置进度条高度 注意此处最终AgentWeb会将其作为float 转dp2px
     *
     * @return
     */
    protected int getProgressHeight() {
        return 2;
    }


    @Override
    public void beforeSetContentView() {
        mTitleBarViewControl = FastManager.getInstance().getTitleBarViewControl();

    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        mContainer = findViewById(R.id.lLayout_containerFastWeb);
        mUrl = getIntent().getStringExtra("url");
        boolean httpUrl = getIntent().getBooleanExtra("httpUrl", true);
        if (httpUrl && !TextUtils.isEmpty(mUrl)) {
            mUrl = mUrl.startsWith("http") ? mUrl : "http://" + mUrl;
            getIntent().putExtra("url", mUrl);
        }
        url = mUrl;
        mCurrentUrl = mUrl;
        initAgentWeb();
        super.beforeInitView(savedInstanceState);

    }

    @Override
    public void beforeSetTitleBar(TitleBarView titleBar) {
        super.beforeSetTitleBar(titleBar);
        if (mTitleBarViewControl != null) {
            mTitleBarViewControl.createTitleBarViewControl(titleBar, this.getClass());
        }
        titleBar.setOnLeftTextClickListener(v -> onBackPressed())
                .setRightTextDrawable(DrawableUtil.setTintDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.fast_ic_more),
                        ContextCompat.getColor(mContext, R.color.colorTitleText)))
                .setOnRightTextClickListener(v -> showActionSheet())
                .addLeftAction(titleBar.new ImageAction(
                        DrawableUtil.setTintDrawable(ContextCompat.getDrawable(mContext, R.drawable.fast_ic_close),
                                ContextCompat.getColor(mContext, R.color.colorTitleText)), v -> showDialog()));
    }

    @Override
    public int getContentLayout() {
        return R.layout.fast_activity_fast_web;
    }

    protected void initAgentWeb() {
        mAgentBuilder = AgentWeb.with(this)
                .setAgentWebParent(mContainer, new ViewGroup.LayoutParams(-1, -1))
                .useDefaultIndicator(getProgressColor() != -1 ? getProgressColor() : ContextCompat.getColor(mContext, R.color.colorTitleText),
                        getProgressHeight())
                .useMiddlewareWebChrome(new MiddlewareWebChromeBase() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        mCurrentUrl = view.getUrl();
                        mTitleBar.setTitleMainText(title);
                    }
                })
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK);
        setAgentWeb(mAgentBuilder);
        mAgentWeb = mAgentBuilder
                .createAgentWeb()//
                .ready()
                .go(mUrl);
        WebSettings webSettings = mAgentWeb.getAgentWebSettings().getWebSettings();
        //设置webView自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        setAgentWeb(mAgentWeb);
    }

    protected void showDialog() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.fast_web_alert_title)
                    .setMessage(R.string.fast_web_alert_msg)
                    .setNegativeButton(R.string.fast_web_alert_left, (dialog, which) -> {
                        if (mAlertDialog != null) {
                            mAlertDialog.dismiss();
                        }
                    })
                    .setPositiveButton(R.string.fast_web_alert_right, (dialog, which) -> {
                        if (mAlertDialog != null) {
                            mAlertDialog.dismiss();
                        }
                        mContext.finish();
                    }).create();
        }
        mAlertDialog.show();
        //show之后可获取对应Button对象设置文本颜色--show之前获取对象为null
        mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);
    }

    protected void showActionSheet() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb != null && mAgentWeb.back()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null && mAgentWeb.getWebLifeCycle() != null && !mIsPause && !isFinishing()) {
            mAgentWeb.getWebLifeCycle().onPause();
            mIsPause = true;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null && mAgentWeb.getWebLifeCycle() != null && mIsPause) {
            mAgentWeb.getWebLifeCycle().onResume();
            mIsPause = false;
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWeb != null && mAgentWeb.getWebLifeCycle() != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean setNavigationBar(Dialog dialog, NavigationViewHelper helper, View bottomView) {
        return false;
    }

}
