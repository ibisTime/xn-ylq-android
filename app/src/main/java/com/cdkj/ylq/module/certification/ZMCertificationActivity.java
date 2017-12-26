package com.cdkj.ylq.module.certification;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityCardandnameInfoCheckBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.ZMCertFirstStepModel;
import com.cdkj.ylq.model.ZMCertSecStepModel;
import com.cdkj.ylq.module.api.MyApiServer;

import org.greenrobot.eventbus.EventBus;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 芝麻认证 （支付宝方式）
 * Created by 李先俊 on 2017/7/26.
 */

public class ZMCertificationActivity extends AbsBaseActivity {

    private ActivityCardandnameInfoCheckBinding mBinding;

    private String mBizNum;//用于唤起支付宝

    private CerttificationInfoModel.InfoIdentifyBean mInfoData;


    /**
     * 打开当前页面
     *
     * @param context 认证数据 是否通过认证
     */
    public static void open(Context context, CerttificationInfoModel.InfoIdentifyBean infoData, boolean isCheckCert) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ZMCertificationActivity.class);
        intent.putExtra("infoData", infoData);
        intent.putExtra("isCheckCert", isCheckCert);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_cardandname_info_check, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTopTitle("芝麻认证");

        setSubLeftImgState(true);

        if (getIntent() != null) {
            mInfoData = getIntent().getParcelableExtra("infoData");

        }

        setDataShow();
        initListener();


    }

    private void setDataShow() {
        if (mInfoData != null) {
            mBinding.editCardNumber.setText(mInfoData.getIdNo());
            mBinding.editName.setText(mInfoData.getRealName());
        }

        //设置按钮状态
        if (getIntent() == null) return;

        if (getIntent().getBooleanExtra("isCheckCert", false)) {
            mBinding.butSure.setBackgroundResource(R.drawable.btn_no_click_gray);
            mBinding.butSure.setEnabled(false);
        } else {
            mBinding.butSure.setBackgroundResource(R.drawable.selector_login_btn);
            mBinding.butSure.setEnabled(true);
        }
    }

    private void initListener() {
        //
        mBinding.butSure.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mBinding.editName.getText().toString())) {
                showToast("请填写姓名");
                return;
            }
            if (TextUtils.isEmpty(mBinding.editCardNumber.getText().toString())) {

                showToast("请填写身份证号码");
                return;
            }

            certRequest();

        });
    }

    /**
     * 认证信息请求
     */
    private void certRequest() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("idNo", mBinding.editCardNumber.getText().toString());
        map.put("realName", mBinding.editName.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("returnUrl", "ylq://cert.back");//认证后返回标识 在intent-filter里配置

        Call call = RetrofitUtils.createApi(MyApiServer.class).ZmCertFirstStep("623045", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<ZMCertFirstStepModel>(this) {
            @Override
            protected void onSuccess(ZMCertFirstStepModel data, String SucMessage) {
                if (!data.isIsSuccess()) {
                    mBizNum = data.getBizNo();
                    doVerify(data.getUrl());
                } else {
                    showToast("已经通过认证");
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    /**
     * 启动支付宝进行认证
     *
     * @param url 开放平台返回的URL
     */
    private void doVerify(String url) {
        if (hasApplication()) {
            Intent action = new Intent(Intent.ACTION_VIEW);
            StringBuilder builder = new StringBuilder();
            // 这里使用固定appid 20000067
            builder.append("alipays://platformapi/startapp?appId=20000067&url=");
            builder.append(URLEncoder.encode(url));
            action.setData(Uri.parse(builder.toString()));
            LogUtil.E("url" + builder.toString());
            startActivity(action);
        } else {
            // 处理没有安装支付宝的情况
            new AlertDialog.Builder(this)
                    .setMessage("是否下载并安装支付宝完成认证?")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent action = new Intent(Intent.ACTION_VIEW);
                            action.setData(Uri.parse("https://m.alipay.com"));
                            startActivity(action);
                        }
                    }).setNegativeButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    /**
     * 判断是否安装了支付宝
     *
     * @return true 为已经安装
     */
    private boolean hasApplication() {
        PackageManager manager = getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse("alipays://"));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("LQ", "scheme:" + intent.getScheme());
        Uri uri = intent.getData();
        if (uri != null) {
            Log.e("LQ", "scheme: " + uri.getScheme());
            Log.e("LQ", "host: " + uri.getHost());
            Log.e("LQ", "port: " + uri.getPort());
            Log.e("LQ", "path: " + uri.getPath());
            Log.e("LQ", "queryString: " + uri.getQuery());
            Log.e("LQ", "queryParameter: " + uri.getQueryParameterNames());



/*            if (null != uri.getQueryParameter("biz_content")) {
                try {
                    JSONObject object = new JSONObject(uri.getQueryParameter("biz_content"));
                    check(object.getString("biz_no"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/

            check(mBizNum);

        }
    }

    private void check(String mBizNum) {
        Map<String, String> map = new HashMap<>();
        map.put("bizNo", mBizNum);
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).ZmCertSecStep("623046", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<ZMCertSecStepModel>(this) {
            @Override
            protected void onSuccess(ZMCertSecStepModel data, String SucMessage) {
                if (data.isIsSuccess()) {
                    EventBus.getDefault().post(EventTags.IDCARDCERTINFOREFRESH);
                    finish();
                } else {
                    certRequest();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }


}
