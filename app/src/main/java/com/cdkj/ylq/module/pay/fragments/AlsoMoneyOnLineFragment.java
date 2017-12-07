package com.cdkj.ylq.module.pay.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.base.BaseFragment;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.model.pay.AliPayRequestMode;
import com.cdkj.baselibrary.model.pay.WxPayRequestModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.payutils.PayUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.appmanager.BusinessSings;
import com.cdkj.ylq.databinding.ActivityPayBinding;
import com.cdkj.ylq.module.pay.AlsoMoneyTabActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 还钱 线上支付 支付回调已经在父activity 里处理
 * Created by 李先俊 on 2017/9/6.
 */
//TODO 微信支付布局隐藏
public class AlsoMoneyOnLineFragment extends BaseFragment {

    private ActivityPayBinding mBinding;
    private String mCode;

    private int mPayType;// 2=微信 3=支付宝 5//宝付

    /**
     * @param code  还款编号
     * @param money 还款金额
     * @return
     */
    public static AlsoMoneyOnLineFragment getInstanse(String code, String money) {
        AlsoMoneyOnLineFragment fragment = new AlsoMoneyOnLineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("money", money);
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.activity_pay, null, false);

        if (getArguments() != null) {
            mCode = getArguments().getString("code");
            mBinding.tvMoney.setText(getArguments().getString("money"));
        }
        initListener();
        return mBinding.getRoot();
    }

    //
    private void initListener() {
        mBinding.imgZhifubao.setImageResource(R.drawable.pay_select);
        mBinding.imgWeixin.setImageResource(R.drawable.un_select);
        mPayType = 3;

//        mBinding.linWeixin.setOnClickListener(v -> {
//            mBinding.imgWeixin.setImageResource(R.drawable.pay_select);
//            mBinding.imgZhifubao.setImageResource(R.drawable.un_select);
//            mPayType = 2;
//        });

        mBinding.linZhifubao.setOnClickListener(v -> {
            mBinding.imgZhifubao.setImageResource(R.drawable.pay_select);
            mBinding.imgBaofu.setImageResource(R.drawable.un_select);
            mPayType = 3;
        });

        mBinding.linBaofu.setOnClickListener(v -> {
            mBinding.imgZhifubao.setImageResource(R.drawable.un_select);
            mBinding.imgBaofu.setImageResource(R.drawable.pay_select);
            mPayType = 5;
        });

        mBinding.btnSure.setOnClickListener(v -> {

            if (mPayType == 2) {  //微信支付
                //    wxPayRequest();
            } else if (mPayType == 3) {  //支付宝支付
                AliPayRequest();
            } else if (mPayType == 5) {//宝付
                baoFuPayRequest();
            }

        });

    }

    /**
     * 宝付请求
     */
    private void baoFuPayRequest() {
        if (TextUtils.isEmpty(mCode)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);
        map.put("payType", "5");

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623072", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(mActivity) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if(data.isSuccess()){
                    EventBus.getDefault().post(EventTags.ALSOOFFLINE);
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    private void wxPayRequest() {
        if (TextUtils.isEmpty(mCode)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);
        map.put("payType", "2");

        Call call = RetrofitUtils.getBaseAPiService().wxPayRequest("623072", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<WxPayRequestModel>(mActivity) {
            @Override
            protected void onSuccess(WxPayRequestModel data, String SucMessage) {
                PayUtil.callWXPay(mActivity, data, AlsoMoneyTabActivity.ALSOMONEYCALLPAYTAG);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    private void AliPayRequest() {
        if (TextUtils.isEmpty(mCode)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);
        map.put("payType", "3");

        Call call = RetrofitUtils.getBaseAPiService().aliPayRequest("623072", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<AliPayRequestMode>(mActivity) {
            @Override
            protected void onSuccess(AliPayRequestMode data, String SucMessage) {
                PayUtil.callAlipay(mActivity, data.getSignOrder(), AlsoMoneyTabActivity.ALSOMONEYCALLPAYTAG);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
