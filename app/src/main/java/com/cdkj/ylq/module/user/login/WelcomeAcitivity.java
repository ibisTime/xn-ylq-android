package com.cdkj.ylq.module.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cdkj.baselibrary.appmanager.ARouteConfig;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.BuildConfig;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
        mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {//延迟两秒进行跳转
                    getServiceTime();
                }, Throwable::printStackTrace));
    }

    //获取系统参数 环境配置  如果获取的参数和本地相同则说明是审核状态（显示商城） 反之是使用状态（显示九州宝）
    public void getServiceTime() {

        Map<String, String> map = new HashMap<>();
        map.put("ckey", "androidShowFlag");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(WelcomeAcitivity.this) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    ARouter.getInstance().build(ARouteConfig.StoreMain)
                            .navigation();
                    finish();
                    return;
                }

                LogUtil.E("SHOW_TYPE 本地"+BuildConfig.SHOW_TYPE +" ——————网络:  "+data.getCvalue());

                if (data.getCvalue().equals(BuildConfig.SHOW_TYPE)) {
                    ARouter.getInstance().build(ARouteConfig.StoreMain).navigation();
                    return;
                }
                MainActivity.open(WelcomeAcitivity.this);
                finish();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
