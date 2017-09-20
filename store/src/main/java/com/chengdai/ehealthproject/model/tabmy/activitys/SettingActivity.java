package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySettingBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.model.common.model.activitys.AddressSelectActivity;
import com.chengdai.ehealthproject.model.common.model.activitys.IntroductionActivity;
import com.chengdai.ehealthproject.model.user.FindPassWordActivity;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by 李先俊 on 2017/6/16.
 */

public class SettingActivity extends AbsStoreBaseActivity {

    private ActivitySettingBinding mBinding;
    private UserInfoModel mUserInfo;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,UserInfoModel userInfo){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,SettingActivity.class);
        intent.putExtra("userinfo",userInfo);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_setting, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle(getString(R.string.txt_setting));
        setSubLeftImgState(true);

        if(getIntent()!=null){
            mUserInfo=getIntent().getParcelableExtra("userinfo");
        }
       //银行卡
        mBinding.layoutBankCard.setOnClickListener(v -> {
            BackCardListActivity.open(this,false);
        });

        if(mUserInfo!=null){
            mBinding.txtPhone.setText(mUserInfo.getMobile());
        }

        //支付密码
        mBinding.layoutPayPwd.setOnClickListener(v -> {

            if(TextUtils.equals("0",mUserInfo.getTradepwdFlag())) { //未设置支付密码
                PayPwdModifyActivity.open(this,false,mBinding.txtPhone.getText().toString());

            }else if(TextUtils.equals("1",mUserInfo.getTradepwdFlag())){//设置过支付密码
                PayPwdModifyActivity.open(this,true,mBinding.txtPhone.getText().toString());
            }

        });

        //修改登录密码
        mBinding.layoutLoginPwd.setOnClickListener(v -> {
            FindPassWordActivity.open(this,false);
        });

        //修改手机号
        mBinding.layoutPhone.setOnClickListener(v -> {
            UpdatePhoneActivity.open(this);
        });

        //关于我们
        mBinding.layoutAbout.setOnClickListener(v -> {
            IntroductionActivity.open(this,"aboutus","关于我们");
        });

        //收货地址
        mBinding.layoutAddress.setOnClickListener(v -> {
            AddressSelectActivity.open(this);
        });

        mBinding.txtLogout.setOnClickListener(v -> {
            SPUtilHelpr.saveUserId("");
            SPUtilHelpr.saveUserToken("");
            SPUtilHelpr.saveUserPhoneNum("");
            SPUtilHelpr.saveAmountaccountNumber("");
            SPUtilHelpr.saveLocationInfo("");
            SPUtilHelpr.saveRestLocationInfo("");

            EventBusModel eventBusModel=new EventBusModel();
            eventBusModel.setTag("AllFINISH");
            EventBus.getDefault().post(eventBusModel); //结束掉所有界面

            EventBusModel loginEvModel=new EventBusModel();
            loginEvModel.setTag("LOGINSTATEREFHSH");
            loginEvModel.setEvBoolean(false);
            EventBus.getDefault().post(loginEvModel); //刷新未登录数据
            LoginActivity.open(this,true);

            EventBusModel eventBusMode2=new EventBusModel();//结束主页
            eventBusMode2.setTag("MainActivityFinish");
            EventBus.getDefault().post(eventBusMode2); //结束掉所有界面

            finish();
        });

    }

    @Subscribe
    public void SettingActivityUpdate(EventBusModel eventBusModel){
        if(eventBusModel == null) return;

        if(TextUtils.equals(eventBusModel.getTag(),"SettingActivityUpdate_IsSetPwd")){
           if(mUserInfo!=null){
               mUserInfo.setTradepwdFlag("1");
           }
        }
      if(TextUtils.equals(eventBusModel.getTag(),"SettingActivityUpdate_Phone")){
           if(mUserInfo!=null){
               mUserInfo.setMobile(eventBusModel.getEvInfo());
           }
           mBinding.txtPhone.setText(mUserInfo.getMobile());
        }

    }

}
