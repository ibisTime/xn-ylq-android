package com.cdkj.baselibrary.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.databinding.ActivityModifyPasswordBinding;
import com.cdkj.baselibrary.interfaces.SendCodeInterface;
import com.cdkj.baselibrary.interfaces.SendPhoneCoodePresenter;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;

import retrofit2.Call;

/**
 * 找回密码
 */
public class FindPwdActivity extends AbsBaseActivity implements SendCodeInterface {

    private ActivityModifyPasswordBinding mBinding;

    private String mPhoneNumber;

    private SendPhoneCoodePresenter mSendCOdePresenter;


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, String mPhoneNumber) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, FindPwdActivity.class);
        intent.putExtra("phonenumber", mPhoneNumber);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_modify_password, null, false);
        return mBinding.getRoot();
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTopTitle("修改密码");
        setSubLeftImgState(true);
        mSendCOdePresenter=new SendPhoneCoodePresenter(this);
        if (getIntent() != null) {
            mPhoneNumber = getIntent().getStringExtra("phonenumber");
        }

        if (!TextUtils.isEmpty(mPhoneNumber)) {
            mBinding.edtPhone.setText(mPhoneNumber);
            mBinding.edtPhone.setSelection(mBinding.edtPhone.getText().toString().length());
        }

        initListener();
    }

    /**
     *
     */
    private void initListener() {

        //发送验证码
        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSendCOdePresenter.sendCodeRequest(mBinding.edtPhone.getText().toString(),"805063",MyConfig.USERTYPE,FindPwdActivity.this);
            }
        });


        //确定
        mBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.edtPhone.getText().toString())) {
                    showToast("请输入手机号");
                    return;
                }

                if (TextUtils.isEmpty(mBinding.edtCode.getText().toString())) {
                    showToast("请输入验证码");
                    return;
                }

                if (TextUtils.isEmpty(mBinding.edtPassword.getText().toString())) {
                    showToast("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(mBinding.edtRepassword.getText().toString())) {
                    showToast("请重新输入密码");
                    return;
                }

                if (mBinding.edtPassword.getText().length() < 6) {
                    showToast("密码不少于6位");
                    return;
                }

                if (!mBinding.edtPassword.getText().toString().equals(mBinding.edtRepassword.getText().toString())) {
                    showToast("两次密码输入不一致");
                    return;
                }

                findPwdReqeust();
            }
        });
    }


    /**
     * 找回密码请求
     */
    private void findPwdReqeust() {

        HashMap<String, String> hashMap = new LinkedHashMap<String, String>();

        hashMap.put("mobile", mBinding.edtPhone.getText().toString());
        hashMap.put("newLoginPwd", mBinding.edtPassword.getText().toString());
        hashMap.put("smsCaptcha", mBinding.edtCode.getText().toString());
        hashMap.put("kind", MyConfig.USERTYPE);
        hashMap.put("systemCode", MyConfig.SYSTEMCODE);
        hashMap.put("companyCode", MyConfig.COMPANYCODE);

        Call call=RetrofitUtils.getBaseAPiService().successRequest("805063", StringUtils.getJsonToString(hashMap));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(FindPwdActivity.this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    showToast("密码修改成功");
                    finish();
                } else {
                    showToast("密码修改失败");
                }
            }

            @Override
            protected void onFinish() {
             disMissLoading();
            }
        });


    }


    @Override
    public void CodeSuccess(String msg) {
        mSubscription.add(AppUtils.startCodeDown(60, mBinding.btnSend));
    }

    @Override
    public void CodeFailed(String code, String msg) {
        showToast(msg);
    }

    @Override
    public void StartSend() {
        showLoadingDialog();
    }

    @Override
    public void EndSend() {
        disMissLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mSendCOdePresenter!=null){
            mSendCOdePresenter.clear();
            mSendCOdePresenter=null;
        }
    }
}
