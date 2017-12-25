package com.cdkj.ylq.module;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.databinding.ActivityWebviewBinding;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.IsBorrowFlagModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.certification.review.HumanReviewActivity;
import com.cdkj.ylq.mpresenter.GetUserCertificationInfoListener;
import com.cdkj.ylq.mpresenter.GetUserCertificationPresenter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;

/**
 * 同盾运营商认证
 * Created by cdkj on 2017/12/12.
 */

public class TdOperatorCertActivity extends AbsBaseActivity implements GetUserCertificationInfoListener {

    private WebView webView;

    private ActivityWebviewBinding mBinding;

    private static final String tdUrl = "https://open.shujumohe.com/box/yys?";
    //TODO  同盾boxToken 替换
    private static final String boxToken = "5613A6F334DC4E12944AF748EE11FDEA";

    private static final String nextUrl = "https://me/do/next";//用户验证完成后的要打开的url

    private GetUserCertificationPresenter mCertInfoPresenter;//获取认证结果接口

    private boolean isFirst = true;//是否第一次请求

    private UITipDialog tipDialog;


    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, TdOperatorCertActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), com.cdkj.baselibrary.R.layout.activity_webview, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTopTitle("运营商认证");
        initLayout();
        mCertInfoPresenter = new GetUserCertificationPresenter(this);
        mCertInfoPresenter.getCertInfo(true);
    }


    /**
     * 获取要加载的url
     *
     * @return
     */
    /*https://open.shujumohe.com/box/yys?box_token=yys?box_token=5613A6F334DC4E12944AF748EE11FDEA&real_name=%E6%9D%8E%E5%85%88%E4%BF%8A&identity_code=dfdfdfddfdd&user_mobile=55656565&real_name=%E6%9D%8E%E5%85%88%E4%BF%8A*/
    private void startLoadUrl(CerttificationInfoModel certData) {

        StringBuffer stringBuffer = new StringBuffer(tdUrl);

        stringBuffer.append("box_token=" + boxToken);

        stringBuffer.append("&cb=" + nextUrl); //完成后的url

        if (certData != null && certData.getInfoIdentify() != null) {
            if (!TextUtils.isEmpty(certData.getInfoIdentify().getRealName())) {
                stringBuffer.append("&real_name=" + certData.getInfoIdentify().getRealName()); //姓名
            }
            if (!TextUtils.isEmpty(certData.getInfoIdentify().getIdNo())) {
                stringBuffer.append("&identity_code=" + certData.getInfoIdentify().getIdNo());//身份证号
            }
        }

        if (!TextUtils.isEmpty(SPUtilHelpr.getUserPhoneNum())) {
            stringBuffer.append("&user_mobile=" + SPUtilHelpr.getUserPhoneNum());//手机号
        }

        LogUtil.E("同盾" + stringBuffer.toString());

        webView.loadUrl(stringBuffer.toString());

    }

    private void initLayout() {
        //输入法
        if (getWindow() != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        webView = new WebView(getApplicationContext());

        webView.setWebChromeClient(new MyWebViewClient1());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受网站证书
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //url拦截 nextUrl?all_submit=1&task_id=TASKYYS00000xxxxxxxxxxxxxxxxxx
                LogUtil.E("同盾" + url);
                if (url.startsWith(nextUrl)) {             //获取taks_id  用于查询认证结果
                    callBackgroundRequest();
                    return true;
                }

                return false;
            }
        };
        webView.setWebViewClient(webViewClient);

        if (webView.getSettings() != null) {
            webView.getSettings().setJavaScriptEnabled(true);//js
            webView.getSettings().setDefaultTextEncodingName("UTF-8");
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        mBinding.llAboutUs.addView(webView, 1);

    }

    /**
     * 告诉后台用户已经进行过运营商认证
     */
    private void callBackgroundRequest() {

        if (!SPUtilHelpr.isLoginNoStart()) { //如果用户没有登录
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());

        showLoadingDialog();

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("623056", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack(this) {
            @Override
            protected void onSuccess(Object data, String SucMessage) {

            }

            @Override
            protected void onFinish() {
                disMissLoading();
                startTime();
            }
        });

    }

    public void startTime() {
        showWaiteDialog();
        mSubscription.add(Observable.timer(5, TimeUnit.SECONDS)    // 定时器 5秒查询一次
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mCertInfoPresenter.getCertInfo(false);
                }, throwable -> {
                    dismissWaiteDialog();
                }));
    }

    /**
     * 获取url里的TaskId
     *
     * @param url
     * @return
     */
    private String getTaskIdByUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            return "";
        }

        int index = url.indexOf("task_id=") + "task_id=".length();

        return StringUtils.subStringEnd(url, index);

    }

    @Override
    public void getInfoSuccess(CerttificationInfoModel mCertData, String msg) {
        if (isFirst) {
            isFirst = false;
            startLoadUrl(mCertData);
        } else {
            if (TextUtils.equals("1", mCertData.getInfoCarrierFlag())) { //已认证 //已过期
                dismissWaiteDialog();
                showSureDialog("运营商认证成功。", view -> getIsBorrowFlag());
            } else if (TextUtils.equals("3", mCertData.getInfoCarrierFlag())) {//认证中
                startTime();//重新开始定时请求
            } else {
                dismissWaiteDialog();
                showSureDialog("运营商认证失败,请重试。", view -> finish());
            }
        }

    }

    @Override
    public void getInfoFailed(String code, String msg) {
        startLoadUrl(null);
    }

    @Override
    public void startGetInfo(boolean showDialog) {
        showLoadingDialog();
    }

    @Override
    public void endGetInfo(boolean showDialog) {
        disMissLoading();
    }


    /**
     * 获取认证标识
     */
    public void getIsBorrowFlag() {
        if (!SPUtilHelpr.isLoginNoStart()) {
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getIsBorrowFlag("623091", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsBorrowFlagModel>(this) {
            @Override
            protected void onSuccess(IsBorrowFlagModel data, String SucMessage) {
                if (TextUtils.equals("1", data.getIsBorrowFlag())) {
                    HumanReviewActivity.open(TdOperatorCertActivity.this);
                }
                finish();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
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


    @Override
    protected boolean canFinish() {
        return false;
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }

        if (mCertInfoPresenter != null) {
            mCertInfoPresenter.clear();
        }

        if (tipDialog != null) {
            tipDialog.dismiss();
            tipDialog = null;
        }

        super.onDestroy();
    }

    private void goBack() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }


    public void showWaiteDialog() {
        if (tipDialog == null) {
            tipDialog = new UITipDialog.Builder(this)
                    .setIconType(UITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("认证中...")
                    .create();
        }
        if (!tipDialog.isShowing()) {
            tipDialog.show();
        }
    }

    public void dismissWaiteDialog() {
        if (tipDialog != null && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
    }

}
