package com.chengdai.ehealthproject.model.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityFindPwdBinding;
import com.chengdai.ehealthproject.uitls.AppUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**找回密码
 * Created by 李先俊 on 2017/6/16.
 */

public class FindPassWordActivity extends AbsStoreBaseActivity {


    private ActivityFindPwdBinding mBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,boolean isShowLogo){
        if(context==null){
            return;
        }
        Intent i=new Intent(context,FindPassWordActivity.class);
        i.putExtra("isShowLogo",isShowLogo);
        context.startActivity(i);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_find_pwd, null, false);
        addMainView(mBinding.getRoot());

        setTopTitle("找回密码");

        setSubLeftImgState(true);

        if(getIntent()!=null){
            if(getIntent().getBooleanExtra("isShowLogo",true)){
                mBinding.imgLoginIcon.setVisibility(View.VISIBLE);
            }else{
                mBinding.imgLoginIcon.setVisibility(View.GONE);
            }
        }

        initViews();

    }

    private void initViews() {

        //发送验证码
        mBinding.btnSendCode.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mBinding.editUsername.getText().toString())){
                showToast("请输入手机号");
                return;
            }
            sendCodeRequest();

        });

        mBinding.btnSureNext.setOnClickListener(v -> {

            if(TextUtils.isEmpty(mBinding.editUsername.getText().toString())){
                showToast("请输入手机号");
                return;
            }

            if(TextUtils.isEmpty(mBinding.editPhoneCode.getText().toString())){
                showToast("请输入验证码");
                return;
            }

            if(TextUtils.isEmpty(mBinding.editPassword.getText().toString())){
                showToast("请输入密码");
                return;
            }
           if(TextUtils.isEmpty(mBinding.editPasswordRepet.getText().toString())){
                showToast("请重新输入密码");
                return;
            }
           if(!mBinding.editPasswordRepet.getText().toString().equals(mBinding.editPassword.getText().toString())){
                showToast("两次密码输入不一致");
                return;
            }

            findPwdReqeust();
        });

    }




    /**
     * 找回密码请求
     */
    private void findPwdReqeust() {

        HashMap<String,String> hashMap=new LinkedHashMap<String, String>();

        hashMap.put("mobile",mBinding.editUsername.getText().toString());
        hashMap.put("newLoginPwd",mBinding.editPassword.getText().toString());
        hashMap.put("loginPwdStrength","2");
        hashMap.put("smsCaptcha",mBinding.editPhoneCode.getText().toString());
        hashMap.put("kind","f1");
        hashMap.put("systemCode",MyConfig.SYSTEMCODE);


        mSubscription.add(RetrofitUtils.getLoaderServer().FindPassWord("805048",StringUtils.getJsonToString(hashMap) )
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                    if(data!=null && data.isSuccess()){
                        showToast("密码修改成功");
                        finish();
                    }else{
                        showToast("密码重置失败");
                    }
                },Throwable::printStackTrace));



    }



    /**
     * 发送验证码
     */
    private void sendCodeRequest() {
        HashMap<String,String> hashMap=new LinkedHashMap<String, String>();

        hashMap.put("systemCode", MyConfig.SYSTEMCODE);
        hashMap.put("mobile",mBinding.editUsername.getText().toString());
        hashMap.put("bizType","805048");
        hashMap.put("kind","f1");

        mSubscription.add(RetrofitUtils.getLoaderServer().PhoneCodeSend("805904", StringUtils.getJsonToString(hashMap))                 //发送验证码
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                    if (data !=null && data.isSuccess()){
                        showToast("验证码已经发送请注意查收");
                        mSubscription.add(AppUtils.startCodeDown(60,mBinding.btnSendCode));//启动倒计时
                    }else{
                        showToast("验证码发送失败");
                    }
                },Throwable::printStackTrace));
    }


}
