package com.cdkj.ylq.module.user.userinfo.usemoneyrecord;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityUseingMoneyBinding;
import com.cdkj.ylq.databinding.ActivityWaiteMoneyDetailsBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.borrowmoney.PayActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.utils.DateUtil.DATE_YMD;

/**
 * 借款生效中详情 (已还款显示 fra_state_note 其他隐藏  逾期显示) （借款记录）
 * Created by 李先俊 on 2017/8/9.
 */

public class UseingMoneyDetailsActivity extends AbsBaseActivity {

    private ActivityUseingMoneyBinding mBinding;

    private UseMoneyRecordModel.ListBean mData;

    private String mCode;


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, UseMoneyRecordModel.ListBean state, String code) {
        if (context == null) {
            return;
        }

        Intent i = new Intent(context, UseingMoneyDetailsActivity.class);
        i.putExtra("data", state);
        i.putExtra("code", code);
        context.startActivity(i);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_useing_money, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);


        if (getIntent() != null) {

            mCode = getIntent().getStringExtra("code");

            if (TextUtils.isEmpty(mCode)) {
                mData = getIntent().getParcelableExtra("data");

                setShowData();

            } else {
                getDataRequest();
            }
        }

        initListener();
    }

    private void setShowData() {

        if (mData == null) return;


        if (TextUtils.equals(mData.getStatus(), "1")) { //生效中
            setSubRightTitleAndClick("还款", v -> {
                if (mData == null) return;
                PayActivity.open(this, mData.getCode(), MoneyUtils.showPrice(mData.getAmount()));
            });
            setTopTitle("放款中详情");

            mBinding.fraStateNote.setVisibility(View.GONE);
            mBinding.imgState.setImageResource(R.drawable.money_state1);

        } else {
            mBinding.fraStateNote.setVisibility(View.VISIBLE);
            mBinding.imgState.setImageResource(R.drawable.money_state2);
            setTopTitle("已还款详情");
        }


        mBinding.tvMoney.setText(MoneyUtils.showPrice(mData.getAmount()));
        mBinding.tvMoney2.setText(MoneyUtils.showPrice(mData.getAmount()) + "元");
        mBinding.tvCode.setText(mData.getCode());
        mBinding.tvSignData.setText(DateUtil.formatStringData(mData.getSignDatetime(), DATE_YMD));
        mBinding.tvDay.setText(mData.getDuration() + "天");
        mBinding.tvDakuan.setText(DateUtil.formatStringData(mData.getFkDatetime(), DATE_YMD));
        mBinding.tvJixi.setText(DateUtil.formatStringData(mData.getJxDatetime(), DATE_YMD));
        mBinding.tvHuankuan.setText(DateUtil.formatStringData(mData.getHkDatetime(), DATE_YMD));
        mBinding.tvKuaisu.setText(MoneyUtils.showPrice(mData.getXsAmount()) + "元");
        mBinding.tvGuanli.setText(MoneyUtils.showPrice(mData.getGlAmount()) + "元");
        mBinding.tvLixi.setText(MoneyUtils.showPrice(mData.getLxAmount()) + "元");
        mBinding.tvJianmian.setText(MoneyUtils.showPrice(mData.getYhAmount()) + "元");
        mBinding.tvDaoqi.setText(MoneyUtils.showPrice(mData.getTotalAmount()) + "元");


    }


    private void initListener() {

    }


    public void getDataRequest() {

        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUseMoneyData("623086", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UseMoneyRecordModel.ListBean>(this) {
            @Override
            protected void onSuccess(UseMoneyRecordModel.ListBean data, String SucMessage) {
                mData = data;
                setShowData();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
