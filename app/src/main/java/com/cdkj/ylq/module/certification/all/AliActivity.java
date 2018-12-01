package com.cdkj.ylq.module.certification.all;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityAliBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * @updateDts 2018/11/23
 */

public class AliActivity extends AbsBaseActivity {

    private ActivityAliBinding mBinding;

    public static void open(Context context) {
        Intent intent = new Intent(context, AliActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_ali, null, false);
        setSubLeftImgState(true);
        setTopTitle("支付宝认证");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBinding.btnConfirm.setOnClickListener(v -> {
            if (check()) {
                UpLoadRequest();
            }
        });
    }

    private boolean check() {
        if (TextUtils.isEmpty(mBinding.etPsw.getText().toString())) {
            return false;
        }

        if (TextUtils.isEmpty(mBinding.etZh.getText().toString())) {
            return false;
        }
        return true;
    }

    /**
     * 把获取到的七牛url上传
     */
    private void UpLoadRequest() {

        showLoadingDialog();
        Map<String, String> map = new HashMap<String, String>();
        map.put("accountNumber", mBinding.etZh.getText().toString());
        map.put("password", mBinding.etPsw.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623057", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    ToastUtil.show(AliActivity.this, "认证成功");
//                    UITipDialog.showSuccess(AliActivity.this, "认证成功", dialog -> {
//                    finish();
//                    });
                    finish();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
