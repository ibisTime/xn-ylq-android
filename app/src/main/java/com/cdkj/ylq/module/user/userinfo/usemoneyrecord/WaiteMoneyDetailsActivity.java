package com.cdkj.ylq.module.user.userinfo.usemoneyrecord;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityWaiteMoneyDetailsBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;

import static com.cdkj.baselibrary.utils.DateUtil.DATE_YMD;

/**
 * 借款待放款详情
 * Created by 李先俊 on 2017/8/9.
 */

public class WaiteMoneyDetailsActivity extends AbsBaseActivity {

    private ActivityWaiteMoneyDetailsBinding mBinding;


    private UseMoneyRecordModel.ListBean mData;


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, UseMoneyRecordModel.ListBean state) {
        if (context == null) {
            return;
        }

        Intent i = new Intent(context, WaiteMoneyDetailsActivity.class);
        i.putExtra("data", state);
        context.startActivity(i);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_waite_money_details, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("放款中详情");

        if (getIntent() != null) {
            mData = getIntent().getParcelableExtra("data");
        }

        setShowData();

        initListener();
    }

    private void setShowData() {

        if (mData == null) return;

        mBinding.tvMoney.setText(MoneyUtils.showPrice(mData.getAmount()));
        mBinding.tvMoney2.setText(MoneyUtils.showPrice(mData.getAmount()) + "元");
        mBinding.tvCode.setText(mData.getCode());
        mBinding.tvSignData.setText(DateUtil.formatStringData(mData.getSignDatetime(), DATE_YMD));
        mBinding.tvState2.setText(mData.getRemark());
        mBinding.tvDay.setText(mData.getDuration()+"天");

    }


    private void initListener() {

    }


}
