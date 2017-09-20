package com.chengdai.ehealthproject.uitls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.cdkj.baselibrary.utils.ToastUtil;
import com.chengdai.ehealthproject.base.BaseStoreApplication;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

/**
 * 用于处理服务器 错误码
 * Created by Administrator on 2016-09-06.
 */
public class OnLoginFailure {

    public static void StartDoFailure(final Context context, String errorMessage) {

        SPUtilHelpr.saveUserId("");
        SPUtilHelpr.saveUserToken("");
        SPUtilHelpr.saveUserPhoneNum("");
        SPUtilHelpr.saveAmountaccountNumber("");

        if (context == null) {
            Intent intent = new Intent(BaseStoreApplication.getInstance(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("isStartMain", false);
            BaseStoreApplication.getInstance().startActivity(intent);
            ToastUtil.show(BaseStoreApplication.getInstance(),errorMessage);
            return;
        }

        try{
            if(context instanceof  Activity){
                LoginActivity.open(context,false);
            }

        }catch (Exception e){

        }
    }

}
