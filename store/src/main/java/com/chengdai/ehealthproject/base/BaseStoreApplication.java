package com.chengdai.ehealthproject.base;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;


/**
 * 基础Application
 * Created by Administrator on 2016/8/29.
 */
public class BaseStoreApplication extends Application {

    private static Context application;
    public static void initialize(Context context, boolean isDebug) {
        ZXingLibrary.initDisplayOpinion(context);
        application = context;
    }

    public static Context getInstance(){
        return application;
    }
}

