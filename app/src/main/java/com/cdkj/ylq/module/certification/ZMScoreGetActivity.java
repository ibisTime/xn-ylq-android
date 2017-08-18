package com.cdkj.ylq.module.certification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.ZmScoreGetModel;
import com.cdkj.ylq.module.api.MyApiServer;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 芝麻分数获取
 * Created by 李先俊 on 2017/7/26.
 */

public class ZMScoreGetActivity extends CommonZMPermissionsCheckActivity {

    private CerttificationInfoModel.InfoIdentifyBean mInfoData;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, CerttificationInfoModel.InfoIdentifyBean data) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ZMScoreGetActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        super.afterCreate(savedInstanceState);
        setTopTitle("芝麻信用评分");

        if (getIntent() != null) {
            mInfoData = getIntent().getParcelableExtra("data");
        }

        if (mInfoData != null) {
            mBinding.editCardNumber.setText(mInfoData.getIdNo());
            mBinding.editName.setText(mInfoData.getRealName());
        }


    }

    /**
     * 检查是否授权过 如果已授权直接展示芝麻分 如果没授权则申请授权
     */

    @Override
    protected void checkRequest() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getZmScore("623047", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<ZmScoreGetModel>(this) {

            @Override
            protected void onSuccess(ZmScoreGetModel data, String SucMessage) {
                if(data.isAuthorized()){
                    finish();
                }else{
                    creditApp.authenticate(ZMScoreGetActivity.this, data.getAppId(), null,data.getParam(), data.getSignature(), null,ZMScoreGetActivity.this);
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


}
