package com.cdkj.baselibrary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 李先俊 on 2017/6/8.
 */

public class AppUtils {


    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isActivityExist(Activity clz) {

        Activity activity = clz;
        if (activity == null) {
            return false;
        }

        if(activity.isFinishing()){
            return false;
        }

        if (!getAndroidVersion(Build.VERSION_CODES.JELLY_BEAN_MR1)) //如果版本小于 4.2
        {
            return true;
        }

        if (activity.isDestroyed()) {
            return false;
        }

        return true;
    }

    public static Boolean getAndroidVersion(int version) {
        if (Build.VERSION.SDK_INT >= version) {
            return true;

        } else {
            return false;
        }
    }
    /**
     * 根据包名跳转到系统自带的应用程序信息界面
     *
     * @param activity
     */
    public static void startDetailsSetting(Activity activity) {
        Uri packageURI = Uri.parse("package:" + getPackgeName(activity));
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        activity.startActivity(intent);
        activity.finish();
    }


    /*获取版本信息*/
    public static String getPackgeName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.packageName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /*获取版本信息*/
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    /**
     * 验证码倒计时
     *
     * @param count 秒数
     * @param btn   按钮
     * @return
     */
    public static Disposable startCodeDown(final int count, final Button btn) {
        return Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())    // 创建一个按照给定的时间间隔发射从0开始的整数序列
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .take(count)//只发射开始的N项数据或者一定时间内的数据
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        RxView.enabled(btn).accept(false);
                        RxTextView.text(btn).accept("60秒后重发");
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        RxView.enabled(btn).accept(false);
                        RxTextView.text(btn).accept((count - aLong) + "秒后重发");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        RxView.enabled(btn).accept(true);
                        RxTextView.text(btn).accept("重发验证码");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        RxView.enabled(btn).accept(true);
                        RxTextView.text(btn).accept("重发验证码");
                    }
                });
    }


    public static void startWeb(Context context, String url) {

        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }

        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            if (!hasPreferredApplication(context, intent)) {
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    //如果info.activityInfo.packageName为android,则没有设置,否则,有默认的程序.
    //判断系统是否设置了默认浏览器
    public static boolean hasPreferredApplication(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return !"android".equals(info.activityInfo.packageName);
    }


}
