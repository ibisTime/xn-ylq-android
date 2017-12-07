package com.cdkj.ylq;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cdkj.baselibrary.BaseApplication;
import com.cdkj.baselibrary.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;


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
        BaseApplication.initialize(this, BuildConfig.LOG_DEBUG, "");
    }

    public static MyApplication getInstance() {
        return application;
    }
}

