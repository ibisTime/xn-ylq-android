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
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.databinding.ActivityWebviewBinding;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.model.ContractMode;
import com.cdkj.ylq.module.api.MyApiServer;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


/**
 * 合同展示
 */
public class ContractShowActivity extends AbsBaseActivity {

    private ActivityWebviewBinding mBinding;


    /**
     * 加载activity
     *
     * @param activity 上下文 合同编号
     */
    public static void open(Activity activity, String code) {
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(activity, ContractShowActivity.class);
        intent.putExtra("code", code);
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

        mBinding.webView.getSettings().setJavaScriptEnabled(true);//js
        mBinding.webView.getSettings().setDefaultTextEncodingName("UTF-8");
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
        setTopTitle("借款合同");

        contractInfoRequest();

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

    /**
     * 合同数据请求
     */

    private void contractInfoRequest() {

        if (getIntent() == null || TextUtils.isEmpty(getIntent().getStringExtra("code"))) {
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("code", getIntent().getStringExtra("code"));
        Call call = RetrofitUtils.createApi(MyApiServer.class).contractInfoRequest("623093", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ContractMode>(this) {
            @Override
            protected void onSuccess(ContractMode data, String SucMessage) {
                mBinding.webView.loadData(data.getContent(), "text/html;charset=UTF-8", "UTF-8");
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

    private void goBack() {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();
        } else {
            finish();
        }
    }

}
