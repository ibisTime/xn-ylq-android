package com.cdkj.baselibrary.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.databinding.ActivityModifyPayPasswordBinding;
import com.cdkj.baselibrary.interfaces.SendCodeInterface;
import com.cdkj.baselibrary.interfaces.SendPhoneCoodePresenter;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 修改 设置 支付密码
 * Created by 李先俊 on 2017/6/29.
 */
public class PayPwdModifyActivity extends AbsBaseActivity implements SendCodeInterface {

    private ActivityModifyPayPasswordBinding mBinding;

    private boolean mIsSetPwd;//是否设置过密码

    private SendPhoneCoodePresenter mSendCoodePresenter;


    /**
     * @param context
     * @param isSetPwd//是否设置过支付密码
     */
    public static void open(Context context, boolean isSetPwd, String mobile) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PayPwdModifyActivity.class);
        intent.putExtra("isSetPwd", isSetPwd);
        intent.putExtra("mobile", mobile);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_modify_pay_password, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setSubLeftImgState(true);

        if (getIntent() != null) {
            mIsSetPwd = getIntent().getBooleanExtra("isSetPwd", false);
            mBinding.edtPhone.setText(getIntent().getStringExtra("mobile"));
            mBinding.edtPhone.setSelection(mBinding.edtPhone.getText().length());
        }

        if (mIsSetPwd) {
            setTopTitle("修改支付密码");
        } else {
            setTopTitle("设置支付密码");
        }
        mSendCoodePresenter = new SendPhoneCoodePresenter(this);
        setListener();
    }

    /**
     * 设置事件
     */
    private void setListener() {
//发送验证码
        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bizType = "";
                if (mIsSetPwd) {
                    bizType = "805067";//修改
                } else {
                    bizType = "805066";

                }

                mSendCoodePresenter.sendCodeRequest(mBinding.edtPhone.getText().toString(), bizType, MyConfig.USERTYPE, PayPwdModifyActivity.this);
            }
        });
//确认
        mBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.edtPhone.getText())) {
                    showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(mBinding.edtCode.getText())) {
                    showToast("请输入验证码");
                    return;
                }

                if (TextUtils.isEmpty(mBinding.edtRepassword.getText())) {
                    showToast("请输入支付密码");
                    return;
                }
                if (mBinding.edtRepassword.getText().length() < 6) {
                    showToast("支付密码不能低于6位数");
                    return;
                }

                setPwd();

            }
        });
    }


    private void setPwd() {

        Map<String, String> object = new HashMap<>();

        object.put("userId", SPUtilHelpr.getUserId());
        object.put("token", SPUtilHelpr.getUserToken());
        if (mIsSetPwd) {
            object.put("newTradePwd", mBinding.edtRepassword.getText().toString().trim());
        } else {
            object.put("tradePwd", mBinding.edtRepassword.getText().toString().trim());
        }

        object.put("smsCaptcha", mBinding.edtCode.getText().toString().toString());

        String code = "";
        if (mIsSetPwd) {  //修改
            code = "805067";
        } else {
            code = "805066";
        }

        Call call = RetrofitUtils.getBaseAPiService().successRequest(code, StringUtils.getJsonToString(object));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (!data.isSuccess()) {
                    return;
                }

                if (mIsSetPwd) {
                    showToast("修改成功");
                } else {
                    showToast("设置成功");

                    EventBusModel eventBusModel = new EventBusModel();
                    eventBusModel.setTag(EventTags.CHANGE_PAY_PWD_REFRESH);
                    EventBus.getDefault().post(eventBusModel);

                }

                finish();
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
        if (mSendCoodePresenter != null) {
            mSendCoodePresenter.clear();
            mSendCoodePresenter = null;
        }
    }
}
