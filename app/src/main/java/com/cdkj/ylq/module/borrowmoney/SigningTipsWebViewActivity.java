package com.cdkj.ylq.module.borrowmoney;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.databinding.ActivityWebviewBinding;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.module.api.MyApiServer;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


/**
 * 签约展示
 */
public class SigningTipsWebViewActivity extends AbsBaseActivity {

    private ActivityWebviewBinding mBinding;

    private WebView webView;

    /**
     * 加载activity
     *
     * @param activity 上下文
     */
    public static void open(Activity activity, String mCouponId) {
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(activity, SigningTipsWebViewActivity.class);
        intent.putExtra("mCouponId", mCouponId);
        activity.startActivity(intent);

    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_webview, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setSubLeftImgState(true);
        initLayout();
        initData();

    }

    private void initLayout() {
        //输入法
        if (getWindow() != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);//js
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
//       webView.getSettings().setSupportZoom(true);   //// 支持缩放
//       webView.getSettings().setBuiltInZoomControls(true);//// 支持缩放
//       webView.getSettings().setDomStorageEnabled(true);//开启DOM
//       webView.getSettings().setLoadWithOverviewMode(false);//// 缩放至屏幕的大小
//       webView.getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小
//       webView.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        webView.setWebChromeClient(new MyWebViewClient1());
//        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mBinding.llAboutUs.addView(webView, 1);
    }

    private void initData() {
        setTopTitle("借款协议");

        singInfoRequest();

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


    private void singInfoRequest() {

        Map<String, String> map = new HashMap<String, String>();
        if (getIntent() != null && !TextUtils.isEmpty(getIntent().getStringExtra("mCouponId"))) {
            map.put("couponId", getIntent().getStringExtra("mCouponId"));
        }
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).singInfoRequest("623092", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<String>(this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                webView.loadData(data, "text/html;charset=UTF-8", "UTF-8");
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    @Override
    public void onBackPressed() {
        goBack();
    }


    @Override
    protected void onDestroy() {
        webView.destroy();
        mBinding.llAboutUs.removeAllViews();
        webView = null;
        super.onDestroy();
    }

    private void goBack() {
        if (webView == null) {
            return;
        }
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

}
