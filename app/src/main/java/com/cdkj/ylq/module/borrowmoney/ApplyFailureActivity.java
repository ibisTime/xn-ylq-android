package com.cdkj.ylq.module.borrowmoney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityApplyFailureBinding;
import com.cdkj.ylq.databinding.ActivityIdCertBinding;
import com.cdkj.ylq.model.PorductListModel;
import com.cdkj.ylq.module.certification.ZMCertificationActivity;
import com.cdkj.ylq.module.product.ProductDetailsActivity;

/**
 * 申请失败
 * Created by 李先俊 on 2017/8/9.
 */

public class ApplyFailureActivity extends AbsBaseActivity {

    private ActivityApplyFailureBinding mBinding;

    private PorductListModel.ListBean mData;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context,PorductListModel.ListBean data) {
        if (context == null) {
            return;
        }
        Intent intent= new Intent(context, ApplyFailureActivity.class);
        intent.putExtra("data",data);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_apply_failure, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("认证失败");

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
        }

        setShowData();

        initListener();
    }

    private void setShowData() {
        if(mData==null) return;
        mBinding.tvMoney.setText(MoneyUtils.showPrice(mData.getAmount()));
        mBinding.tvMakeDay.setText( mData.getDuration() + "天");
        mBinding.tvState.setText(  getState(mData.getUserProductStatus()));
        mBinding.tvLevel.setText("Lv"+mData.getLevel());

        mBinding.tvTips.setText(mData.getApproveNote());

    }

    //("0", "可申请"),("1", "认证中"),("2", "人工审核中"),( "3", "已驳回"),("4", "已有额度"),("5", "等待放款中"),( "6", "生效中"),("7", "已逾期")
    private String getState(String state) {

        if(TextUtils.isEmpty(state)){
            return "";
        }

        String stateStr = "";
        ;
        switch (state) {
            case "0":
                stateStr = "可申请";
                break;
            case "1":
                stateStr = "认证中";
                break;
            case "2":
                stateStr = "人工审核中";
                break;
            case "3":
                stateStr = "已驳回";
                break;
            case "4":
                stateStr = "已有额度";
                break;
            case "5":
                stateStr = "等待放款中";
                break;
            case "6":
                stateStr = "生效中";
                break;
            case "7":
                stateStr = "已逾期";
                break;
        }

        return stateStr;
    }


    //
    private void initListener() {
      mBinding.btnRepet.setOnClickListener(v -> {
          if(mData==null)return;
          ProductDetailsActivity.open(this,mData.getCode());
      });

    }


}
