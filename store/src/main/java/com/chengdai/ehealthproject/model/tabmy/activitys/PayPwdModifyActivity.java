package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityModifyTradeBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/** 修改 设置 支付密码
 * Created by 李先俊 on 2017/6/29.
 */

public class PayPwdModifyActivity extends AbsStoreBaseActivity {

    private ActivityModifyTradeBinding mBinding;

    private boolean mIsSetPwd;//是否设置过密码


    /**
     *
     * @param context
     * @param isSetPwd//是否设置过支付密码
     */
    public static void open(Context context, boolean isSetPwd,String mobile){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,PayPwdModifyActivity.class);
        intent.putExtra("isSetPwd",isSetPwd);
        intent.putExtra("mobile",mobile);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_modify_trade, null, false);
        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);

        if(getIntent()!=null){
            mIsSetPwd=getIntent().getBooleanExtra("isSetPwd",false);
            mBinding.edtPhone.setText(getIntent().getStringExtra("mobile"));
            mBinding.edtPhone.setSelection(mBinding.edtPhone.getText().length());
        }

        if(mIsSetPwd){
            setTopTitle("修改支付密码");
        }else{
            setTopTitle("设置支付密码");
        }

        setListener();

    }

    /**
     * 设置事件
     */
    private void setListener() {

        mBinding.btnSend.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mBinding.edtPhone.getText())){
                showToast("请输入手机号");
                return;
            }
            sendCodeRequest();
        });

        mBinding.btnConfirm.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mBinding.edtPhone.getText())){
                showToast("请输入手机号");
                return;
            }
            if(TextUtils.isEmpty(mBinding.edtCode.getText())){
                showToast("请输入验证码");
                return;
            }
            if(TextUtils.isEmpty(mBinding.edtRepassword.getText())){
                showToast("请输入支付密码");
                return;
            }
            setPwd();
        });
    }


    private void setPwd(){

        Map<String,String> object=new HashMap<>();

            object.put("userId", SPUtilHelpr.getUserId());
            object.put("token", SPUtilHelpr.getUserToken());
            if (mIsSetPwd) {
                object.put("newTradePwd",mBinding.edtRepassword.getText().toString().trim());
            } else {
                object.put("tradePwd", mBinding.edtRepassword.getText().toString().trim());
            }

            object.put("smsCaptcha", mBinding.edtCode.getText().toString().toString());
            object.put("tradePwdStrength", "1");

        String code = "";
        if (mIsSetPwd) {
            code = "805057";
        } else {
            code = "805045";
        }

       mSubscription.add( RetrofitUtils.getLoaderServer().updatePhone(code, StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(isSuccess-> isSuccess!=null && isSuccess.isSuccess())
                .subscribe(isSuccess-> {
                    if (mIsSetPwd) {
                      showToast("修改成功");
                    } else {
                        showToast("设置成功");
                        EventBusModel e=new EventBusModel();
                        e.setTag("SettingActivityUpdate_IsSetPwd");//更新上一页数据
                        EventBus.getDefault().post(e);
                    }

                    finish();
                },Throwable::printStackTrace));

    }


    /**
     * 发送验证码
     */
    private void sendCodeRequest() {
        HashMap<String,String> hashMap=new LinkedHashMap<String, String>();

        hashMap.put("systemCode", MyConfigStore.SYSTEMCODE);
        hashMap.put("mobile",mBinding.edtPhone.getText().toString());
          if (mIsSetPwd) {
                hashMap.put("bizType", "805057");
            } else {
                hashMap.put("bizType", "805045");
            }
        hashMap.put("kind","f1");
        mSubscription.add(RetrofitUtils.getLoaderServer().PhoneCodeSend("805904", StringUtils.getJsonToString(hashMap))                 //发送验证码
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                    if (data !=null && data.isSuccess()){
                        showToast("验证码已经发送请注意查收");
                        mSubscription.add(AppUtils.startCodeDown(60,mBinding.btnSend));//启动倒计时
                    }else{
                        showToast("验证码发送失败");
                    }
                },Throwable::printStackTrace));
    }



}
