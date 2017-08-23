package com.cdkj.ylq.module.borrowmoney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.pay.AliPayRequestMode;
import com.cdkj.baselibrary.model.pay.PaySucceedInfo;
import com.cdkj.baselibrary.model.pay.WxPayRequestModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.payutils.PayUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityApplyFailureBinding;
import com.cdkj.ylq.databinding.ActivityPayBinding;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 支付（还钱）
 * Created by 李先俊 on 2017/8/9.
 */
public class PayActivity extends AbsBaseActivity {

    private ActivityPayBinding mBinding;

    private String mCode;

    private int mPayType;// 2=微信 3=支付宝

    private static final String CALLPAYTAG = "PayActivity";

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, String code, String money) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("money", money);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pay, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("还款");

        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");
            mBinding.tvMoney.setText(getIntent().getStringExtra("money"));
        }

        initListener();
    }

    //
    private void initListener() {
        mBinding.imgWeixin.setImageResource(R.drawable.pay_select);
        mBinding.imgZhifubao.setImageResource(R.drawable.un_select);
        mPayType = 2;
        mBinding.linWeixin.setOnClickListener(v -> {
            mBinding.imgWeixin.setImageResource(R.drawable.pay_select);
            mBinding.imgZhifubao.setImageResource(R.drawable.un_select);
            mPayType = 2;
        });

        mBinding.linZhifubao.setOnClickListener(v -> {
            mBinding.imgZhifubao.setImageResource(R.drawable.pay_select);
            mBinding.imgWeixin.setImageResource(R.drawable.un_select);
            mPayType = 3;
        });

        mBinding.btnSure.setOnClickListener(v -> {


            if (mPayType == 2) {
                showToast("微信支付未开放,还款请联系客服");
                //    wxPayRequest();
            } else if (mPayType == 3) {
                showToast("支付宝未开放,还款请联系客服");
//                AliPayRequest();
            }

        });

    }

    private void wxPayRequest() {
        if (TextUtils.isEmpty(mCode)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);
        map.put("payType", mPayType + "");

        Call call = RetrofitUtils.getBaseAPiService().wxPayRequest("623072", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<WxPayRequestModel>(this) {
            @Override
            protected void onSuccess(WxPayRequestModel data, String SucMessage) {
                PayUtil.callWXPay(PayActivity.this, data, CALLPAYTAG);
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
        map.put("payType", mPayType + "");

        Call call = RetrofitUtils.getBaseAPiService().aliPayRequest("623072", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<AliPayRequestMode>(this) {
            @Override
            protected void onSuccess(AliPayRequestMode data, String SucMessage) {
                PayUtil.callAlipay(PayActivity.this, data.getSignOrder(), CALLPAYTAG);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 支付回调
     *
     * @param mo
     */
    @Subscribe
    public void PayState(PaySucceedInfo mo) {
        if (mo == null || !TextUtils.equals(mo.getTag(), CALLPAYTAG)) {
            return;
        }

        if (mo.getCallType() == PayUtil.ALIPAY && mo.isPaySucceed()) { //支付宝支付成功
            showToast("还款成功");
            finish();
        } else if (mo.getCallType() == PayUtil.WEIXINPAY && mo.isPaySucceed()) {//微信支付成功

        }
    }


}
