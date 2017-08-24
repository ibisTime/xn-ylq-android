package com.cdkj.ylq.module.borrowmoney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.WebViewActivity;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BigDecimalUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivitySigningBinding;
import com.cdkj.ylq.model.PorductListModel;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 签约确认
 * Created by 李先俊 on 2017/8/9.
 */

public class SigningSureActivity extends AbsBaseActivity {

    private ActivitySigningBinding mBinding;
    private PorductListModel.ListBean mProductData;

    private String mCouponId;//优惠券ID
    private String mWillgetMondy;//实到金额

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, PorductListModel.ListBean productData, String couponId, String willgetMondy) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, SigningSureActivity.class);
        intent.putExtra("productData", productData);
        intent.putExtra("couponId", couponId);
        intent.putExtra("willgetMondy", willgetMondy);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_signing, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("签约");

        if (getIntent() != null) {
            mProductData = getIntent().getParcelableExtra("productData");
            mCouponId = getIntent().getStringExtra("couponId");
            mWillgetMondy = getIntent().getStringExtra("willgetMondy");
        }

        setShowData();

        initListener();
    }


    //
    private void initListener() {
        mBinding.btnSure.setOnClickListener(v -> {
            if (!mBinding.checkbox.isChecked()) {
                showToast("请阅读并同意借款协议");
                return;
            }
            signingRequest();
        });

        mBinding.tvRead.setOnClickListener(v -> {
            WebViewActivity.openkey(this, "借款协议", "borrowProtocol");
        });
    }

    /**
     * 签约请求
     */
    private void signingRequest() {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(mCouponId)) {
            map.put("couponId", mCouponId);
        }
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("623070", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                if (!TextUtils.isEmpty(data.getCode())) {
                    EventBus.getDefault().post(EventTags.USEMONEYSUREFINISH);//关闭上一个界面
                    PutMoneyingActivity.open(SigningSureActivity.this);
                    finish();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    public void setShowData() {
        if (mProductData == null) return;

        mBinding.tvUserName.setText(SPUtilHelpr.getUserName());
        mBinding.tvMoney.setText(MoneyUtils.showPrice(mProductData.getAmount()) + "元");
        mBinding.tvDay.setText(mProductData.getDuration() + "天");
        mBinding.tvAllRate.setText(getAllRateMoney(mProductData) + "元");
        mBinding.tvUseMoneyState.setText("一次性还款" + MoneyUtils.showPrice(mProductData.getAmount()) + "元");
        mBinding.tvShidaoRate.setText(mWillgetMondy);
//        mBinding.tvDueDate.setText(DateUtil.getShowDayToData(mProductData.getDuration()));

        mBinding.tvRead.setText("《" + SPUtilHelpr.getUserName() + "-借款协议》");

        mBinding.tvUsed.setText("7天内逾期，每天" + MoneyUtils.showPrice(BigDecimalUtils.multiply(mProductData.getYqRate1(), mProductData.getAmount())) + "元\n"
                + "7天外逾期，每天" + MoneyUtils.showPrice(BigDecimalUtils.multiply(mProductData.getYqRate2(), mProductData.getAmount())) + "元");

    }

    /**
     * 计算所有扣除费用
     *
     * @return
     */
    private String getAllRateMoney(PorductListModel.ListBean mData) {
        if (mData == null) {
            return "";
        }

        BigDecimal deductMoney = BigDecimalUtils.add(mData.getXsAmount(), mData.getLxAmount());//审信费 + 利息

        BigDecimal deductMoney2 = BigDecimalUtils.add(mData.getGlAmount(), mData.getFwAmount());//管理费 + 加服务费

        BigDecimal deductMoneyAll = BigDecimalUtils.add(deductMoney, deductMoney2);//总共需要扣除金额

        return MoneyUtils.showPrice(deductMoneyAll);

    }

}
