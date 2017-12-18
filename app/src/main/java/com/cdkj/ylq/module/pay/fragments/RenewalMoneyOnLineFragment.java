package com.cdkj.ylq.module.pay.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.cdkj.baselibrary.utils.BigDecimalUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.payutils.PayUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentRenewalPayOnlineBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.ylq.module.pay.RenewalMoneyTabActivity.RENEMONEYCALLPAYTAG;

/**
 * 续期 线上支付 支付回调已经在父activity 里处理
 * Created by 李先俊 on 2017/9/6.
 */

public class RenewalMoneyOnLineFragment extends BaseFragment {

    private FragmentRenewalPayOnlineBinding mBinding;

    private int mPayType;// 2=微信 3=支付宝

    private UseMoneyRecordModel.ListBean mData;

    /**
     * 产品数据
     *
     * @param data
     * @return
     */
    public static RenewalMoneyOnLineFragment getInstanse(UseMoneyRecordModel.ListBean data) {
        RenewalMoneyOnLineFragment fragment = new RenewalMoneyOnLineFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_renewal_pay_online, null, false);

        if (getArguments() != null) {
            mData = getArguments().getParcelable("data");
        }

        setShowData();
        initListener();
        return mBinding.getRoot();
    }

    private void setShowData() {
        if (mData == null) {
         return;
        }

        mBinding.tvMoney.setText(MoneyUtils.showPrice(mData.getRenewalAmount()));

        mBinding.tvRenewalStart.setText(DateUtil.formatStringData(mData.getRenewalStartDate(),DateUtil.DATE_YMD));
        mBinding.tvRenewalEnd.setText(DateUtil.formatStringData(mData.getRenewalEndDate(),DateUtil.DATE_YMD));

        //续期利息 =逾期总额-逾期利息
        mBinding.tvMoneyTips.setText("续期金额=逾期利息("+MoneyUtils.showPrice(mData.getYqlxAmount())+"元)+续期利息("+
                MoneyUtils.showPrice(BigDecimalUtils.subtract(mData.getRenewalAmount(),mData.getYqlxAmount()))+"元)");

    }

    //
    private void initListener() {
        mBinding.imgWeixin.setImageResource(R.drawable.un_select);
        mBinding.imgZhifubao.setImageResource(R.drawable.pay_select);
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
        if (mData == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mData.getCode());
        map.put("payType", "5");

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623078", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(mActivity) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if(data.isSuccess()){
                    EventBus.getDefault().post(EventTags.RENEWALFLAGE);
                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }



    private void wxPayRequest() {
        if (mData == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mData.getCode());
        map.put("payType", "2");

        Call call = RetrofitUtils.getBaseAPiService().wxPayRequest("623078", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<WxPayRequestModel>(mActivity) {
            @Override
            protected void onSuccess(WxPayRequestModel data, String SucMessage) {
                PayUtil.callWXPay(mActivity, data, RENEMONEYCALLPAYTAG);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

    private void AliPayRequest() {
        if (mData == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mData.getCode());
        map.put("payType", "3");

        Call call = RetrofitUtils.getBaseAPiService().aliPayRequest("623078", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<AliPayRequestMode>(mActivity) {
            @Override
            protected void onSuccess(AliPayRequestMode data, String SucMessage) {
                PayUtil.callAlipay(mActivity, data.getSignOrder(), RENEMONEYCALLPAYTAG);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


}
