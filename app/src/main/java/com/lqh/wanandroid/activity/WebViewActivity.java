package com.lqh.wanandroid.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import androidx.core.content.ContextCompat;

import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.IVideo;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.VideoImpl;
import com.lqh.fastlibrary.i.IFastRefreshView;
import com.lqh.fastlibrary.manager.RxJavaManager;
import com.lqh.fastlibrary.modle.activity.FastWebActivity;
import com.lqh.fastlibrary.retrofit.FastObserver;
import com.lqh.fastlibrary.utils.FastStackUtil;
import com.lqh.fastlibrary.utils.FastUtil;
import com.lqh.fastlibrary.utils.SPUtil;
import com.lqh.fastlibrary.utils.SizeUtil;
import com.lqh.fastlibrary.view.core.navigation.NavigationBarUtil;
import com.lqh.fastlibrary.view.core.navigation.NavigationViewHelper;
import com.lqh.fastlibrary.view.core.title.TitleBarView;
import com.lqh.fastlibrary.view.core.util.DrawableUtil;
import com.lqh.fastlibrary.view.core.util.RomUtil;
import com.lqh.wanandroid.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

/**
 * @Author: AriesHoo on 2018/7/30 11:04
 * @E-Mail: AriesHoo@126.com
 * Function: 应用内浏览器
 * Description:
 * 1、2018-7-30 11:04:22 新增图片下载功能
 * 2、2019-4-18 09:34:51 增加BasisDialog 虚拟导航栏沉浸控制
 */
public class WebViewActivity extends FastWebActivity implements IFastRefreshView {

    private static boolean mIsShowTitle = true;
    private RefreshLayout mRefreshLayout;

    public static void start(Context mActivity, String url) {
        start(mActivity, url, true);
    }

    public static void start(Context mActivity, String url, boolean isShowTitle) {
        mIsShowTitle = isShowTitle;
        start(mActivity, WebViewActivity.class, url, true);
    }

    @Override
    protected int getProgressColor() {
        return super.getProgressColor();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String url = intent.getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            start(mContext, url);
            finish();
        }
    }

    @Override
    protected int getProgressHeight() {
        return super.getProgressHeight();
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        if (!mIsShowTitle) {
            titleBar.setStatusBarLightMode(false)
                    .setVisibility(View.GONE);
        }
        titleBar.setTitleMainTextMarquee(true)
                .setDividerVisible(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP);
    }

    @Override
    protected void setAgentWeb(AgentWeb.CommonBuilder mAgentBuilder) {
        super.setAgentWeb(mAgentBuilder);
        //设置 IAgentWebSettings
        mAgentBuilder.setAgentWebWebSettings(AgentWebSettingsImpl.getInstance())
                .setAgentWebWebSettings(getSettings())
                .useMiddlewareWebChrome(new MiddlewareWebChromeBase() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        if (newProgress == 100 && mRefreshLayout != null) {
                            mCurrentUrl = view.getUrl();
                            mRefreshLayout.finishRefresh();
                            int position = (int) SPUtil.get(mContext, mCurrentUrl, 0);
                            view.scrollTo(0, position);
                        }
                    }

                    @Override
                    public void onHideCustomView() {
                        super.onHideCustomView();
                        getIVideo().onHideCustomView();
                        //显示状态栏
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        NavigationBarUtil.setNavigationBarLightMode(mContext);
                    }

                    @Override
                    public void onShowCustomView(View view, CustomViewCallback callback) {
                        super.onShowCustomView(view, callback);
                        getIVideo().onShowCustomView(view, callback);
                        //隐藏状态栏
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        RxJavaManager.getInstance().setTimer(100)
                                .subscribe(new FastObserver<Long>() {
                                    @Override
                                    public void _onNext(Long entity) {
                                        NavigationBarUtil.setNavigationBarDarkMode(mContext);
                                    }
                                });
                    }
                });
    }

    @Override
    protected void setAgentWeb(AgentWeb mAgentWeb) {
        super.setAgentWeb(mAgentWeb);
    }



    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mAgentWeb.getUrlLoader().reload();
    }


//    @Override
//    public void setRefreshLayout(SmartRefreshLayout refreshLayout) {
//        this.mRefreshLayout = refreshLayout;
//        refreshLayout.setRefreshHeader(new StoreHouseHeader(mContext)
//                .initWithString("FastLib Refresh")
//                .setTextColor(ContextCompat.getColor(mContext, R.color.colorTextBlack)))
//                .setPrimaryColorsId(R.color.transparent)
//                .setEnableHeaderTranslationContent(true);
//    }

    @Override
    public boolean setNavigationBar(Dialog dialog, NavigationViewHelper helper, View bottomView) {
        Drawable drawableTop = ContextCompat.getDrawable(mContext, R.color.colorLineGray);
        DrawableUtil.setDrawableWidthHeight(drawableTop, SizeUtil.getScreenWidth(), SizeUtil.dp2px(0.5f));
        helper.setPlusNavigationViewEnable(true)
                .setNavigationViewColor(Color.argb(isTrans() ? 0 : 60, 0, 0, 0))
                .setNavigationViewDrawableTop(drawableTop)
                .setNavigationLayoutColor(Color.WHITE);
        //导航栏在底部控制才有意义,不然会很丑;开发者自己决定;这里仅供参考
        return NavigationBarUtil.isNavigationAtBottom(dialog.getWindow());
    }

    protected boolean isTrans() {
        return (RomUtil.isEMUI() && (RomUtil.getEMUIVersion().compareTo("EmotionUI_4.1") > 0));
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @Override
    public void onBackPressed() {
        Activity activity = FastStackUtil.getInstance().getPrevious();
        if (activity == null) {
            FastUtil.startActivity(mContext, MainActivity.class);
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        WebView webView = mAgentWeb.getWebCreator().getWebView();
        SPUtil.put(mContext, webView.getUrl(), webView.getScrollY());
    }

    private IVideo mIVideo = null;

    private IVideo getIVideo() {
        if (mIVideo == null) {
            mIVideo = new VideoImpl(mContext, mAgentWeb.getWebCreator().getWebView());
        }
        return mIVideo;
    }


    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;

            }
        }

                ;
    }
}
