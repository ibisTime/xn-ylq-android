package com.cdkj.ylq.module.stages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityStagesDetailsBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;

public class StagesDetailsActivity extends AbsBaseActivity {
    ActivityStagesDetailsBinding mBinding;
    private UseMoneyRecordModel.ListBean.StageListBean stageListBean;

    public static void open(Context context, UseMoneyRecordModel.ListBean.StageListBean stageListBean) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, StagesDetailsActivity.class);
        intent.putExtra("stageListBean", stageListBean);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_stages_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTopTitle("分期详情");
        setSubLeftImgState(true);
        init();
        showViewData();
    }


    private void init() {
        if (getIntent() != null) {
            stageListBean = (UseMoneyRecordModel.ListBean.StageListBean) getIntent().getSerializableExtra("stageListBean");
        }
    }

    private void showViewData() {
        mBinding.tvBqyh.setText(MoneyUtils.showPrice(stageListBean.getAmount()));
        mBinding.tvBj.setText(MoneyUtils.showPrice(stageListBean.getMainAmount()));
        mBinding.tvLx.setText(MoneyUtils.showPrice(stageListBean.getLxAmount()));
        mBinding.tvHkqs.setText(stageListBean.getRemark());
        mBinding.tvJksj.setText(stageListBean.getDate());

        mBinding.tvMoney.setText(MoneyUtils.showPrice(stageListBean.getAmount()));
        mBinding.tvDate.setText(stageListBean.getDate());
        mBinding.tvInterest.setText("包含利息" + MoneyUtils.showPrice(stageListBean.getLxAmount()));
    }
}
