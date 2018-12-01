package com.cdkj.ylq.utils;

import android.content.Context;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * @updateDts 2018/11/21
 */

public class NetUtils {

    /**
     *
     * @param context
     * @param cKey  kay
     * @param isShowLoading  是否需要显示 加载框
     * @param onSuccessSystemInterface  成功失败回调
     */
    public static void getSystemParameter(Context context, String cKey, Boolean isShowLoading, OnSuccessSystemInterface onSuccessSystemInterface) {
        Map<String, String> map = new HashMap<>();
        map.put("key", cKey);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));
        if (isShowLoading) {
            if (context instanceof BaseActivity) {
                ((BaseActivity) context).showLoadingDialog();
            }
        }
        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(context) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                onSuccessSystemInterface.onSuccessSystem(data);
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                onSuccessSystemInterface.onError(errorMessage);
            }

            @Override
            protected void onFinish() {

                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).disMissLoading();
                }
            }
        });

    }

    public interface OnSuccessSystemInterface {

        void onSuccessSystem(IntroductionInfoModel data);

        void onError(String errorMessage);
    }
}
