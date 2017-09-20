package com.chengdai.ehealthproject.base;

import android.app.Application;
import android.content.Context;

import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.chengdai.ehealthproject.BuildConfig;
import com.chengdai.ehealthproject.uitls.NineGridViewImageLoader;
import com.lzy.ninegrid.NineGridView;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.greenrobot.eventbus.EventBus;


/**
 * 基础Application
 * Created by Administrator on 2016/8/29.
 */
public class BaseStoreApplication extends Application {

    private static Context application;
    public static void initialize(Context context, boolean isDebug) {
        NineGridView.setImageLoader(new NineGridViewImageLoader());
        ZXingLibrary.initDisplayOpinion(context);
        application = context;
    }

    public static Context getInstance(){
        return application;
    }
}

