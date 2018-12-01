package com.cdkj.ylq.module.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
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
        ImageView img = (ImageView) findViewById(R.id.img_start);
        img.setImageResource(R.drawable.start);

        //判断有没有七牛地址
//        if (TextUtils.isEmpty(SPUtilHelpr.getQiNiuUrl())) {
//            getQiNiuUrl();
//        }
        mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {//延迟两秒进行跳转
                    MainActivity.open(this);
                    finish();
                }, Throwable::printStackTrace));

    }

    //    //获取七牛地址
//    private void getQiNiuUrl() {
//        NetUtils.getSystemParameter(this, "qiniu_domain", false, new NetUtils.OnSuccessSystemInterface() {
//            @Override
//            public void onSuccessSystem(IntroductionInfoModel data) {
//                SPUtilHelpr.saveQiNiuUrl(WelcomeAcitivity.this, data.getCvalue());
//            }
//
//
//            @Override
//            public void onError(String errorMessage) {
//                showToast("图片地址获取失败");
//            }
//        });
//    }
    public void getQiNiuUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "qiniu_domain");
        map.put("systemCode", "CD-YLQ000014");
        map.put("companyCode", "CD-YLQ000014");

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(WelcomeAcitivity.this) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                Log.e("pppppp", "onSuccess: 图片地址" + data.getCvalue());
                if (!TextUtils.isEmpty(data.getCvalue())) {
                    SPUtilHelpr.saveQiNiuUrl("http://" + data.getCvalue());
                }
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

}
