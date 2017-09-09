package com.cdkj.ylq.module.renewal;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityRenewalDetailsBinding;
import com.cdkj.ylq.model.RenewalListModel;

/**续期详情
 * Created by 李先俊 on 2017/9/6.
 */
public class RenewalDetailsActivity extends AbsBaseActivity {

    private ActivityRenewalDetailsBinding mBinding;

    private RenewalListModel.ListBean mData;

    /**
     * 续期记录数据
     * @param context
     * @param data
     */
    public static void open(Context context, RenewalListModel.ListBean data) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RenewalDetailsActivity.class);
        intent.putExtra("data",data);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_renewal_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setTopTitle("续期详情");

        setSubLeftImgState(true);

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
        }

        setShowData();

    }

    /**
     * 设置显示数据
     */
    private void setShowData() {
        if(mData==null){
            return;
        }

        mBinding.tvRenewalDay.setText(DateUtil.formatStringData(mData.getCreateDatetime(),DateUtil.DATE_YMD));
        mBinding.tvRenewalNum.setText(mData.getStep()+" 天");
        mBinding.tvRenewalStart.setText(DateUtil.formatStringData(mData.getStartDate(),DateUtil.DATE_YMD));
        mBinding.tvRenewalEnd.setText(DateUtil.formatStringData(mData.getEndDate(),DateUtil.DATE_YMD));
        mBinding.tvKuaisu.setText(MoneyUtils.showPrice(mData.getXsAmount())+"元");
        mBinding.tvGuanli.setText(MoneyUtils.showPrice(mData.getGlAmount())+"元");
        mBinding.tvLixi.setText(MoneyUtils.showPrice(mData.getLxAmount())+"元");
        mBinding.tvFuwu.setText(MoneyUtils.showPrice(mData.getFwAmount())+"元");
        mBinding.tvRenewalSum.setText(MoneyUtils.showPrice(mData.getTotalAmount())+"元");

    }
}
