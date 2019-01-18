package com.cdkj.ylq.module.user.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.model.RootMenuCodeBean;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;

/**
 * 启动页
 * Created by 李先俊 on 2017/6/8.
 */

public class WelcomeAcitivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用于第一次安装APP，进入到除这个启动activity的其他activity，点击home键，再点击桌面启动图标时，
        // 系统会重启此activty，而不是直接打开之前已经打开过的activity，因此需要关闭此activity

        try {
            if (getIntent() != null && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        } catch (Exception e) {
        }
        setContentView(R.layout.activity_welcom);
//        ImageView img = (ImageView) findViewById(R.id.img_start);
//        img.setImageResource(R.drawable.start);
        //获取权限
        getCompanyCode();
        //判断有没有七牛地址
        if (TextUtils.isEmpty(SPUtilHelpr.getQiNiuUrl())) {
            getQiNiuUrl();
        } else {
            LogUtil.E("七牛地址为:" + SPUtilHelpr.getQiNiuUrl());
        }
    }

    private void getQiNiuUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "qiniu_domain");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.SYSTEMCODE);//获取七牛的地址,参数这样传,不用公司编号

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(WelcomeAcitivity.this) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (!TextUtils.isEmpty(data.getCvalue())) {
                    SPUtilHelpr.saveQiNiuUrl("http://" + data.getCvalue() + "/");
                }
                LogUtil.E("七牛地址为:" + SPUtilHelpr.getQiNiuUrl());
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                showToast("图片地址获取失败");
            }

            @Override
            protected void onFinish() {

            }
        });
    }


    /**
     * 获取 权限,就是mainActivity能展示哪些功能
     */
    private void getCompanyCode() {

        Map<String, String> map = new HashMap<>();
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getRootMenu("630118", StringUtils.getJsonToString(map));
        call.enqueue(new BaseResponseModelCallBack<RootMenuCodeBean>(this) {
            @Override
            protected void onSuccess(RootMenuCodeBean data, String SucMessage) {
                SPUtilHelpr.saveIsFK(data.getIsFk());
                SPUtilHelpr.saveIsJT(data.getIsJt());

                mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {//延迟两秒进行跳转
                            MainActivity.open(WelcomeAcitivity.this);
                            finish();
                        }, Throwable::printStackTrace));

            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
//                super.onReqFailure(errorCode, errorMessage);
                UITipDialog.showSuccess(WelcomeAcitivity.this, "获取配置失败,请重启", new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
            }

            @Override
            protected void onBuinessFailure(String code, String error) {
//                super.onBuinessFailure(code, error);
                UITipDialog.showSuccess(WelcomeAcitivity.this, "获取配置失败,请重启", new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
            }

            @Override
            protected void onFinish() {

            }
        });
    }
}
