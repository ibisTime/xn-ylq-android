package com.cdkj.ylq.module.user.userinfo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.MyConfig;
import com.cdkj.baselibrary.activitys.FindPwdActivity;
import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.activitys.PayPwdModifyActivity;
import com.cdkj.baselibrary.activitys.UpdatePhoneActivity;
import com.cdkj.baselibrary.activitys.WebViewActivity;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.model.UserInfoModel;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.QiNiuUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityPersonalBinding;
import com.cdkj.ylq.module.user.login.LoginActivity;
import com.qiniu.android.http.ResponseInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 个人中心
 */
public class PersonalActivity extends AbsBaseActivity {

    private ActivityPersonalBinding mBinding;
    private UserInfoModel mUserInfoData;
    public final int PHOTOFLAG = 110;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, UserInfoModel data) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_personal, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mUserInfoData = getIntent().getParcelableExtra("data");
        }


        setShowData();

        setTopTitle("个人中心");
        setSubLeftImgState(true);

        initListener();
    }

    private void setShowData() {
        if (mUserInfoData == null) {
            return;
        }

        ImgUtils.loadActLogo(PersonalActivity.this,MyConfig.IMGURL+mUserInfoData.getPhoto(),mBinding.imgPhoto);

        mBinding.txtPhone.setText(mUserInfoData.getMobile());

        if (mUserInfoData.isTradepwdFlag()) {  //设置支付密码
            mBinding.tvPayPassword.setText("修改支付密码");
        } else {
            mBinding.tvPayPassword.setText("设置支付密码");
        }


    }

    /**
     * 事件
     */
    private void initListener() {

        mBinding.layoutPhoneNumber.setOnClickListener(v -> {
            UpdatePhoneActivity.open(this);
        });

        //修改登录密码
        mBinding.layoutAccount.setOnClickListener(v -> {
            String phone = "";
            if (mUserInfoData != null) {
                phone = mUserInfoData.getMobile();
            }
            FindPwdActivity.open(this, phone);
        });

        mBinding.linPayPassword.setOnClickListener(v -> {
            //支付密码
            if (mUserInfoData == null) {
                return;
            }
            PayPwdModifyActivity.open(this, mUserInfoData.isTradepwdFlag(), mUserInfoData.getMobile());

        });

        //头像
        mBinding.layoutPhoto.setOnClickListener(v -> {
            ImageSelectActivity.launch(this, PHOTOFLAG);
        });
        //退出登录
        mBinding.btnLogout.setOnClickListener(v -> {
            showDoubleWarnListen("确认退出登录？",view -> {
                logOut();
            });

        });

        mBinding.layoutAbout.setOnClickListener(v -> {
            WebViewActivity.openkey(this,"关于我们","aboutUs");
        });
    }

    private void logOut() {

        SPUtilHelpr.logOutClear();
        EventBus.getDefault().post(EventTags.AllFINISH);
        EventBusModel eventBusModel=new EventBusModel();
        eventBusModel.setTag(EventTags.MAINFINISH);

        EventBus.getDefault().post(eventBusModel);

        LoginActivity.open(this,true);
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == PHOTOFLAG) {
            String path = data.getStringExtra(ImageSelectActivity.staticPath);
            new QiNiuUtil(PersonalActivity.this).getQiniuURL(new QiNiuUtil.QiNiuCallBack() {
                @Override
                public void onSuccess(String key, ResponseInfo info, JSONObject res) {
                    updateUserPhoto(key);
                }

                @Override
                public void onFal(String info) {
                }
            }, path);

        }
    }

    /**
     * 更新哟哦能互头像
     * @param key
     */
    private void updateUserPhoto(final String key) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("photo", key);
        map.put("token", SPUtilHelpr.getUserToken());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("805080", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(PersonalActivity.this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    ImgUtils.loadActLogo(PersonalActivity.this, MyConfig.IMGURL + key, mBinding.imgPhoto);
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Subscribe
    public void updateDataEvent(EventBusModel e) {
        if (e == null) {
            return;
        }
        if (TextUtils.equals(e.getTag(), EventTags.CHANGEPHONENUMBER_REFRESH)) {  //修改电话成功刷新界面
            mUserInfoData.setMobile(e.getEvInfo());
            setShowData();
        } else if (TextUtils.equals(e.getTag(), EventTags.CHANGE_PAY_PWD_REFRESH)) {  //修改支付密码成功刷新界面
            mUserInfoData.setTradepwdFlag(true);
            setShowData();
        }
    }

}
