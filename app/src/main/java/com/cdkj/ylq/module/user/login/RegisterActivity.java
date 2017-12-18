package com.cdkj.ylq.module.user.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.WebViewActivity;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.interfaces.LocationCallBackListener;
import com.cdkj.baselibrary.interfaces.SendCodeInterface;
import com.cdkj.baselibrary.interfaces.SendPhoneCoodePresenter;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.model.LocationModel;
import com.cdkj.baselibrary.model.UserLoginModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.AppUtils;
import com.cdkj.baselibrary.utils.GaoDeLocation;
import com.cdkj.baselibrary.utils.LocationHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityRegisterBinding;
import com.cdkj.ylq.module.api.MyApiServer;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by 李先俊 on 2017/8/8.
 */
public class RegisterActivity extends AbsBaseActivity implements SendCodeInterface {

    private ActivityRegisterBinding mBinding;

    private SendPhoneCoodePresenter mSendCOdePresenter;

    private CityPicker mCityPicker;//城市选择

    private LocationHelper mapLocation;//定位工具类

    private LocationModel mLocationModel;//定位获取的数据


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
//        initCityPicker();
        initListener();

        initLocation();
    }

    private void initLocation() {

        showLoadingDialog();

        mapLocation = new LocationHelper(this, new GaoDeLocation(), new LocationCallBackListener() {
            @Override
            public void locationSuccessful(LocationModel locationModel) {
                disMissLoading();
                mLocationModel = locationModel;
            }

            @Override
            public void locationFailure(String msg) {
                disMissLoading();
            }

            @Override
            public void noPermissions() {
                disMissLoading();
            }
        });
        mapLocation.startLocation();//开始定位
    }

    private void initListener() {
        mBinding.btnSendCode.setOnClickListener(v -> { //发送验证码
            checkPhoneNumAndSendCode();
        });
        mBinding.tvIRead.setOnClickListener(v -> {
            WebViewActivity.openkey(this, "借款服务与隐私协议", "regProtocol");
        });

        mBinding.tvRead2.setOnClickListener(v -> {
            WebViewActivity.openkey(this, "信息规则", "infoCollectRule");
        });

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
            if (TextUtils.isEmpty(mBinding.editPassword.getText().toString())) {
                showToast("请输入密码");
                return;
            }
            if (!mBinding.checkboxRegi.isChecked()) {
                showToast("请阅读并同意注册协议");
                return;
            }

            registeRequest();


        });

        mBinding.layoutLocation.setOnClickListener(v -> {
            if (mCityPicker == null) return;
            mCityPicker.show();
        });

    }

    /**
     * 检车手机号是否可以注册然后发送验证码
     */
    private void checkPhoneNumAndSendCode() {
        if (TextUtils.isEmpty(mBinding.editUsername.getText().toString())) {
            showToast("请输入手机号");
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("mobile", mBinding.editUsername.getText().toString());
        map.put("kind", MyConfig.USERTYPE);
        Call call = RetrofitUtils.getBaseAPiService().successRequest("805040", StringUtils.getJsonToString(map));

        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    mSendCOdePresenter.sendCodeRequest(mBinding.editUsername.getText().toString(), "805041", MyConfig.USERTYPE, RegisterActivity.this);
                } else {
                    showToast("手机号已经存在");
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    /**
     * 城市选择
     */
    private void initCityPicker() {
        mCityPicker = new CityPicker.Builder(this)
                .textSize(18)
                .titleBackgroundColor("#ffffff")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#3DA3FF")
                .cancelTextColor("#3DA3FF")
                .province("北京市")
                .city("北京市")
                .district("昌平区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();


        //监听方法，获取选择结果
        mCityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                mBinding.tvLocation.setText(citySelected[0] + " " + citySelected[1] + " " + citySelected[2]);
            }

            @Override
            public void onCancel() {

            }
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

        if (mLocationModel != null) {
            hashMap.put("province", mLocationModel.getProvince());
            hashMap.put("city", mLocationModel.getCity());
            hashMap.put("area", mLocationModel.getDistrict());
            StringBuffer sbaddress = new StringBuffer();
            if (!TextUtils.isEmpty(mLocationModel.getStreet())) {
                sbaddress.append(mLocationModel.getStreet());
            }
            if (!TextUtils.isEmpty(mLocationModel.getStreetNum())) {
                sbaddress.append(mLocationModel.getStreet());
            }
            if (!TextUtils.isEmpty(sbaddress.toString())) {
                hashMap.put("address", sbaddress.toString());
            }
        }
        hashMap.put("smsCaptcha", mBinding.editPhoneCode.getText().toString());
        hashMap.put("systemCode", MyConfig.SYSTEMCODE);
        hashMap.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.createApi(MyApiServer.class).userRegister("623800", StringUtils.getJsonToString(hashMap));

        addCall(call);

        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<UserLoginModel>(this) {
            @Override
            protected void onSuccess(UserLoginModel data, String SucMessage) {
                if (!TextUtils.isEmpty(data.getToken()) && !TextUtils.isEmpty(data.getUserId())) {

                    showToast("注册成功,已自动登录");

                    SPUtilHelpr.saveUserId(data.getUserId());
                    SPUtilHelpr.saveUserToken(data.getToken());
                    SPUtilHelpr.saveUserPhoneNum(mBinding.editUsername.getText().toString());
                    EventBus.getDefault().post(EventTags.AllFINISH);
                    EventBus.getDefault().post(EventTags.MAINFINISH);

                    MainActivity.open(RegisterActivity.this);

                    finish();

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
        ToastUtil.show(this, msg);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSendCOdePresenter != null) {
            mSendCOdePresenter.clear();
            mSendCOdePresenter = null;
        }
        if (mapLocation != null) {
            mapLocation.destroyLocation();
        }
    }
}
