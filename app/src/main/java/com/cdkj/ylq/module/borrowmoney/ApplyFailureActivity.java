package com.cdkj.ylq.module.borrowmoney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.appmanager.BusinessSings;
import com.cdkj.ylq.databinding.ActivityApplyFailureBinding;
import com.cdkj.ylq.model.PorductListModel;
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
        mBinding.tvState.setText(BusinessSings.getProductState(mData.getUserProductStatus()));
        mBinding.tvLevel.setText("Lv"+mData.getLevel());
        mBinding.tvTips.setText(mData.getApproveNote());
    }

    //
    private void initListener() {
      mBinding.btnRepet.setOnClickListener(v -> {
          if(mData==null)return;
          ProductDetailsActivity.open(this,mData.getCode());
      });

    }


}
