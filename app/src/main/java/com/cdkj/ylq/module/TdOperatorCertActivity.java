package com.cdkj.ylq.module;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.mpresenter.GetUserCertificationInfoListener;
import com.cdkj.ylq.mpresenter.GetUserCertificationPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.EventTags.ISTDOPERATORCERTBACK;

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

    private static final String nextUrl = "https://do/next";//用户验证完成后的要打开的url

    private GetUserCertificationPresenter mCertInfoPresenter;//获取认证结果接口

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
        setSubLeftImgState(true);
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

        if (webView != null) {
            webView.post(() -> {
                webView.loadUrl(stringBuffer.toString());
            });
        }
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
                    callBackgroundRequest(getTaskIdByUrl(url));
                    return true;
                }

                return false;
            }
        };
        webView.setWebViewClient(webViewClient);

        if (webView.getSettings() != null) {
            webView.getSettings().setJavaScriptEnabled(true);//js
            webView.getSettings().setDefaultTextEncodingName("utf-8");
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        mBinding.llAboutUs.addView(webView, 1);

    }

    /**
     * 告诉后台用户已经进行过运营商认证
     */
    private void callBackgroundRequest(String taskId) {

        if (!SPUtilHelpr.isLoginNoStart() || TextUtils.isEmpty(taskId)) { //如果用户没有登录
            showToast("运营商认证失败,请重试");
            finish();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("taskId", taskId);

        showLoadingDialog();

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623056", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                EventBus.getDefault().post(ISTDOPERATORCERTBACK);  //已经通知上一页已经进行过通讯录认证
                showToast("运营商数据正在认证，请稍后");
                finish();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

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
        startLoadUrl(mCertData);

    }

    @Override
    public void getInfoFailed(String code, String msg) {
        startLoadUrl(null);
    }

    @Override
    public void startGetInfo(boolean showDialog) {
        if (showDialog) {
            showLoadingDialog();
        }
    }

    @Override
    public void endGetInfo(boolean showDialog) {
        if (showDialog) {
            disMissLoading();
        }
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


        super.onDestroy();
    }

    private void goBack() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }


}
