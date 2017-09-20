package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityModifyPhoneStoreBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.uitls.AppUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**更换手机号码
 * Created by 李先俊 on 2017/6/16.
 */

public class UpdatePhoneActivity extends AbsStoreBaseActivity {

    private ActivityModifyPhoneStoreBinding mBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,UpdatePhoneActivity.class);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_modify_phone_store, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle(getString(R.string.txt_setting));
        setSubLeftImgState(true);


        initListener();

    }

    private void initListener() {
        //发送验证码
        mBinding.btnSendNew.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mBinding.edtPhoneNew.getText().toString())){
                showToast("请输入手机号");
                return;
            }
            sendCodeRequest();
        });

        mBinding.btnConfirm.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mBinding.edtPhoneNew.getText().toString())){
                showToast("请输入手机号");
                return;
            }

            if(TextUtils.isEmpty(mBinding.edtCodeNew.getText().toString())){
                showToast("请输入验证码");
                return;
            }

            updatePhone();

        });

    }

    /**
     * 发送验证码
     */
    private void sendCodeRequest() {
        HashMap<String,String> hashMap=new LinkedHashMap<String, String>();

        hashMap.put("systemCode", MyConfig.SYSTEMCODE);
        hashMap.put("mobile",mBinding.edtPhoneNew.getText().toString());
        hashMap.put("bizType","805047");
        hashMap.put("kind","f1");

        mSubscription.add(RetrofitUtils.getLoaderServer().PhoneCodeSend("805904", StringUtils.getJsonToString(hashMap))                 //发送验证码
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                    if (data !=null && data.isSuccess()){
                        showToast("验证码已经发送请注意查收");
                        mSubscription.add(AppUtils.startCodeDown(60,mBinding.btnSendNew));//启动倒计时
                    }else{
                        showToast("验证码发送失败");
                    }
                },Throwable::printStackTrace));
    }

    /**
     * 更换手机号
     */
    private void updatePhone(){
        Map<String,String> map=new HashMap<>();
        map.put("userId",SPUtilHelpr.getUserId());
        map.put("newMobile",mBinding.edtPhoneNew.getText().toString());
        map.put("smsCaptcha",mBinding.edtCodeNew.getText().toString());
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().PhoneCodeSend("805047", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(data-> data!=null && data.isSuccess())
                .subscribe(data -> {
                     showToast("手机号修改成功");

                    EventBusModel eventBus=new EventBusModel();
                    eventBus.setTag("SettingActivityUpdate_Phone");
                    eventBus.setEvInfo(mBinding.edtPhoneNew.getText().toString());
                     finish();
                },Throwable::printStackTrace));
    }

}
