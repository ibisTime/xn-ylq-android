package com.chengdai.ehealthproject.model.common.model.activitys;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityWebviewStoreBinding;


/**
 * 介绍类webview
 */
public class WebViewActivity extends AbsStoreBaseActivity {

    private ActivityWebviewStoreBinding mBinding;


    /**
     * 加载activity
     *
     * @param activity 上下文
     */
    public static void open(Activity activity, String url) {
        if (activity == null) {
            return;
        }
        activity.startActivity(new Intent(activity, WebViewActivity.class).putExtra("url", url));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_webview_store, null, false);
        addMainView(mBinding.getRoot());
        setSubLeftImgState(true);
        setTopTitle("详情");
        initLayout();
        initData();
    }

    private void initLayout() {
        //输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mBinding.webView.getSettings().setJavaScriptEnabled(true);//js
//        mBinding.webView.getSettings().setSupportZoom(true);   //// 支持缩放
//        mBinding.webView.getSettings().setBuiltInZoomControls(true);//// 支持缩放
//        mBinding.webView.getSettings().setDomStorageEnabled(true);//开启DOM
//        mBinding.webView.getSettings().setLoadWithOverviewMode(false);//// 缩放至屏幕的大小
//        mBinding.webView.getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小
//        mBinding.webView.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        mBinding.webView.setWebChromeClient(new MyWebViewClient1());
//        webView.setWebViewClient(new MyWebViewClient());
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    private void initData() {
        if (getIntent() == null) {
            return;
        }
        mBinding.webView.loadUrl(getIntent().getStringExtra("url"));
    }

    private class MyWebViewClient1 extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mBinding.pb.setProgress(newProgress);

            if (newProgress > 90) {
                mBinding.pb.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }


    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        if (mBinding.webView.canGoBack() ) {
            mBinding.webView.goBack();
        } else {
            finish();
        }
    }

}
