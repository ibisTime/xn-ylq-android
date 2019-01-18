package com.cdkj.ylq;

import android.app.Application;

import com.cdkj.baselibrary.BaseApplication;
import com.moxie.client.manager.MoxieSDK;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;


/**
 * 基础Application
 * Created by Administrator on 2016/8/29.
 */

//TODO 项目目前需配置参数第三方 支付宝 高德 微信
public class MyApplication extends Application {

    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        BaseApplication.initialize(this, BuildConfig.LOG_DEBUG);
        //魔蝎认证初始化
        MoxieSDK.init(this);
        //二维码扫描初始化
        ZXingLibrary.initDisplayOpinion(this);
    }

    public static MyApplication getInstance() {
        return application;
    }
}

