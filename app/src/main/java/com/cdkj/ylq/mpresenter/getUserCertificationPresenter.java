package com.cdkj.ylq.mpresenter;

/* 获取认证信息接口
 * Created by 李先俊 on 2017/8/12.
 */

import android.content.Context;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.module.api.MyApiServer;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class getUserCertificationPresenter {

    private getUserCertificationInfoListener mListener;
    private Context mContext;
    private Call call;

    public getUserCertificationPresenter(getUserCertificationInfoListener mListener) {
        this.mListener = mListener;
    }

    public void getCertInfo(boolean showDialog) {

        if(!SPUtilHelpr.isLoginNoStart()){
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getCerttificationInfo("623050", StringUtils.getJsonToString(map));
        mListener.startGetInfo(showDialog);
        call.enqueue(new BaseResponseModelCallBack<CerttificationInfoModel>(mContext) {
            @Override
            protected void onSuccess(CerttificationInfoModel data, String SucMessage) {
                mListener.getInfoSuccess(data,SucMessage);
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
                mListener.getInfoFailed(errorCode+"",errorMessage);
            }

            @Override
            protected void onBuinessFailure(String code, String error) {
                mListener.getInfoFailed(code,error);
            }

            @Override
            protected void onFinish() {
                mListener.endGetInfo(showDialog);
            }
        });

    }

    //处理持有对象
    public void clear() {
        if(this.call!=null){
            this.call.cancel();
            this.call = null;
        }
        this.mListener = null;
        this.mContext = null;
    }

}
