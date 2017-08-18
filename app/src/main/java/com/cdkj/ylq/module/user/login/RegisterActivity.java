package com.cdkj.ylq.module.user.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.cdkj.baselibrary.MyConfig;
import com.cdkj.baselibrary.activitys.BaseLocationActivity;
import com.cdkj.baselibrary.activitys.WebViewActivity;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.interfaces.SendCodeInterface;
import com.cdkj.baselibrary.interfaces.SendPhoneCoodePresenter;
import com.cdkj.baselibrary.model.UserLoginModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityRegisterBinding;
import com.cdkj.ylq.module.api.MyApiServer;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by 李先俊 on 2017/8/8.
 */

public class RegisterActivity extends BaseLocationActivity implements SendCodeInterface {

    private ActivityRegisterBinding mBinding;

    private SendPhoneCoodePresenter mSendCOdePresenter;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, RegisterActivity.class));
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_register, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("注册");

        mSendCOdePresenter = new SendPhoneCoodePresenter(this);

        mBinding.btnSendCode.setOnClickListener(v -> { //发送验证码
            mSendCOdePresenter.sendCodeRequest(mBinding.editUsername.getText().toString(), "805041", MyConfig.USERTYPE, this);
        });
        mBinding.tvIRead.setOnClickListener(v -> {
            WebViewActivity.openkey(this, "注册协议", "regProtocol");
        });

        startLocation();//开始定位

        //注册
        mBinding.btnRegister.setOnClickListener(v -> {

            if (TextUtils.isEmpty(mBinding.editUsername.getText().toString())) {
                showToast("请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(mBinding.editPhoneCode.getText().toString())) {
                showToast("请输入验证码");
                return;
            }
            if (TextUtils.isEmpty(mBinding.editPhoneCode.getText().toString())) {
                showToast("请输入密码");
                return;
            }
            if (!mBinding.checkboxRegi.isChecked()) {
                showToast("请阅读并同意注册协议");
                return;
            }

            registeRequest();


        });

    }

    /**
     * 注册请求
     */
    private void registeRequest() {

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("mobile", mBinding.editUsername.getText().toString());
        hashMap.put("loginPwd", mBinding.editPassword.getText().toString());
        hashMap.put("kind", MyConfig.USERTYPE);
        if (!TextUtils.isEmpty(mBinding.tvLocation.getText().toString())) {
            hashMap.put("city", mBinding.tvLocation.getText().toString());
        }
        hashMap.put("smsCaptcha", mBinding.editPhoneCode.getText().toString());
        hashMap.put("systemCode", MyConfig.SYSTEMCODE);
        hashMap.put("companyCode", MyConfig.COMPANYCODE);


        Call call = RetrofitUtils.createApi(MyApiServer.class).userRegister("805041", StringUtils.getJsonToString(hashMap));

        addCall(call);

        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<UserLoginModel>(this) {
            @Override
            protected void onSuccess(UserLoginModel data, String SucMessage) {
                if (!TextUtils.isEmpty(data.getToken()) || !TextUtils.isEmpty(data.getUserId())) {
                    showToast("注册成功,已自动登录");


                    SPUtilHelpr.saveUserId(data.getUserId());
                    SPUtilHelpr.saveUserToken(data.getToken());
                    EventBus.getDefault().post(EventTags.AllFINISH);
                    EventBus.getDefault().post(EventTags.MAINFINISH);

                    MainActivity.open(RegisterActivity.this);

                    finish();

                } else {
                    showToast("注册失败");
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    //获取验证码相关
    @Override
    public void CodeSuccess(String msg) {
        mSubscription.add(AppUtils.startCodeDown(60, mBinding.btnSendCode));//启动倒计时
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

    //定位相关
    @Override
    protected boolean canShowTipsDialog() {
        return false;
    }

    @Override
    protected void locationSuccessful(AMapLocation aMapLocation) {
        mBinding.tvLocation.setText(aMapLocation.getProvince() + " " + aMapLocation.getCity() + " " + aMapLocation.getDistrict());
    }

    @Override
    protected void locationFailure(AMapLocation aMapLocation) {
    }

    @Override
    protected void onNegativeButton() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSendCOdePresenter != null) {
            mSendCOdePresenter.clear();
            mSendCOdePresenter = null;
        }
    }
}
