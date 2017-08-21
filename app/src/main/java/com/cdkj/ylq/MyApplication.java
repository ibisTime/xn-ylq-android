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
public class MyApplication extends Application {

	private static MyApplication application;

	@Override
	public void onCreate() {
		super.onCreate();
		BaseApplication.initialize(this);
        EventBus.builder().throwSubscriberException(BuildConfig.LOG_DEBUG).installDefaultEventBus();
		application=this;
		if (BuildConfig.LOG_DEBUG) {
			ARouter.openLog();     // 打印日志
			ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
		}
		ARouter.init(application); // 尽可能早，推荐在Application中初始化

		LogUtil.isDeBug =BuildConfig.LOG_DEBUG;    //build.gradle
	}

	public static MyApplication getInstance(){
		return application;
	}
}

