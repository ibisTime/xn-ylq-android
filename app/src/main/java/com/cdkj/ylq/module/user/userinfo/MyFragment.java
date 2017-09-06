package com.cdkj.ylq.module.user.userinfo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.activitys.WebViewActivity;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.model.CanUseMoneyModel;
import com.cdkj.ylq.model.CoupoonsModel;
import com.cdkj.ylq.model.UserInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentMyBinding;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UseMoneyRecordActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 我的
 * Created by 李先俊 on 2017/8/8.
 */
//TODO 黑名单 blacklistFlag
public class MyFragment extends BaseLazyFragment {

    private FragmentMyBinding mBinding;

    private UserInfoModel mUserInfoMode;

    /**
     * 获得fragment实例
     *
     * @return
     */
    public static MyFragment getInstanse() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_my, null, false);

        initListtener();

        return mBinding.getRoot();
    }

    private void initListtener() {
        mBinding.linSetting.setOnClickListener(v -> {
            PersonalActivity.open(mActivity, mUserInfoMode);
        });

        mBinding.fralayoutMaxMoney.setOnClickListener(v -> MyMaxMoneyActivity.open(mActivity));

        mBinding.layoutRecord.setOnClickListener(v -> {
            UseMoneyRecordActivity.open(mActivity);
        });
        mBinding.fralayoutCoupons.setOnClickListener(v -> {
            MyCouponsActivity.open(mActivity);
        });
        mBinding.linInvitaion.setOnClickListener(v -> {
            InvitaionFriendActivity.open(mActivity);
        });

        mBinding.layoutHelp.setOnClickListener(v -> {
            WebViewActivity.openkey(mActivity, "帮助中心", "helpCenter");
        });

        mBinding.layoutKefu.setOnClickListener(v -> {
            WebViewActivity.openkey(mActivity, "联系客服", "customerService ");
        });

    }

    private void setShowData(UserInfoModel data) {
        if (data == null) return;

        SPUtilHelpr.saveUserPhoneNum(data.getMobile());
        SPUtilHelpr.saveUserName(data.getRealName());

        ImgUtils.loadActLogo(mActivity, MyConfig.IMGURL + data.getPhoto(), mBinding.imtUserLogo);

        mBinding.tvUserName.setText(data.getMobile());


    }

    /**
     * 获取用户信息
     */
    public void getUserInfoRequest() {
        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUserInfoDetails("805121", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UserInfoModel>(mActivity) {
            @Override
            protected void onSuccess(UserInfoModel data, String SucMessage) {
                mUserInfoMode = data;
                SPUtilHelpr.saveUserIsBindCard(TextUtils.equals("1",data.getBankcardFlag()));
                setShowData(mUserInfoMode);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    //获取额度
    public void getCanUseMoneyData() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getCanUseMoney("623051", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<CanUseMoneyModel>(mActivity) {

            @Override
            protected void onSuccess(CanUseMoneyModel data, String SucMessage) {
                mBinding.tvMoney.setText(MoneyUtils.showPrice(data.getSxAmount()));
            }

            @Override
            protected void onFinish() {
            }
        });
    }

    //获取优惠券数量
    public void getCouponNums() {

//        /0=可使用 1=已使用 2=已过期 12=已使用或已过期
        Map<String, String> map = new HashMap<>();
        map.put("limit", "1");
        map.put("start", "1");
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("status", "0");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCouponsListData("623147", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<CoupoonsModel>(mActivity) {
            @Override
            protected void onSuccess(CoupoonsModel data, String SucMessage) {
                mBinding.tvCouponnsNum.setText(data.getTotalCount() + "");
            }

            @Override
            protected void onFinish() {
            }
        });
    }

    //获取系统参数 服务时间
    public void getServiceTime() {

        Map<String, String> map = new HashMap<>();
        map.put("ckey", "time");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));
        
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    return;
                }

                mBinding.tvServiceTime.setText("服务时间："+data.getCvalue());

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }    //获取系统参数 服务电话

    public void getServiceTelephone() {

        Map<String, String> map = new HashMap<>();
        map.put("ckey", "telephone");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    return;
                }

                mBinding.tvServicePhone.setText(data.getCvalue());

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }




    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && mBinding != null) {
            getAllData();
        }
    }

    @Override
    protected void lazyLoad() {
        if (mBinding != null) {
            getAllData();
        }
    }

    private void getAllData() {
        getCouponNums();
        getCanUseMoneyData();
        getUserInfoRequest();
        getServiceTelephone();
        getServiceTime();
    }

    @Override
    protected void onInvisible() {

    }
}
