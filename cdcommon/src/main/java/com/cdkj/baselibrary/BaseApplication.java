
package com.cdkj.baselibrary;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

public class BaseApplication extends Application {
    /**
     * Global application context.
     */
    private static Application sContext;

    /**
     * Construct of LitePalApplication. Initialize application context.
     */
    public BaseApplication() {
        sContext = this;
    }

    public static void initialize(Application context, boolean isDebug) {
        LogUtil.isDeBug = isDebug;
        sContext = context;
        if (isDebug) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(context); // 尽可能早，推荐在Application中初始化
        EventBus.builder().throwSubscriberException(isDebug).installDefaultEventBus();
    }

    public static Context getContext() {
        return sContext;
    }

}
