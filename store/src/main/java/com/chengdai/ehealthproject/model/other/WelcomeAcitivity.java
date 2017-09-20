package com.chengdai.ehealthproject.model.other;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseStoreActivity;
import com.chengdai.ehealthproject.uitls.nets.NetUtils;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * 启动页
 * Created by 李先俊 on 2017/6/8.
 */
@Route(path = "/store/welcome")
public class WelcomeAcitivity extends BaseStoreActivity {

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
        }catch (Exception e){

        }
        setContentView(R.layout.activity_welcom);

        mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(aLong -> {//延迟两秒进行跳转
                    MainActivity.open(this, 1);
                    finish();
                }, Throwable::printStackTrace));
    }

}
