package com.cdkj.ylq.module.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.model.pay.PaySucceedInfo;
import com.cdkj.baselibrary.utils.payutils.PayUtil;
import com.cdkj.ylq.appmanager.BusinessSings;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.pay.fragments.RenewalMoneyOffLineFragment;
import com.cdkj.ylq.module.pay.fragments.RenewalMoneyOnLineFragment;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UseMoneyRecordActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 续期 tab切换
 * Created by 李先俊 on 2017/9/6.
 */
public class RenewalMoneyTabActivity extends CommonTablayoutActivity {


    private UseMoneyRecordModel.ListBean mData;


    public static final String RENEMONEYCALLPAYTAG = "RenewalMoneyTabActivity";

    /**
     * @param context
     */
    public static void open(Context context, UseMoneyRecordModel.ListBean data) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RenewalMoneyTabActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mData = getIntent().getParcelableExtra("data");
        }
        setTopTitle("续期");

        setSubLeftImgState(true);

        mbinding.tablayout.setVisibility(View.GONE);

        super.afterCreate(savedInstanceState);
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(RenewalMoneyOnLineFragment.getInstanse(mData));//实时
//        mFragments.add(RenewalMoneyOffLineFragment.getInstanse(mData));//线下 //线下还款和线下续期在130需求中去除
        return mFragments;
    }

    @Override
    public List<String> getFragmentTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("实时续期");
//        titles.add("线下续期");
        return titles;
    }


    /**
     * 支付回调
     *
     * @param mo
     */
    @Subscribe
    public void PayState(PaySucceedInfo mo) {
        if (mo == null || !TextUtils.equals(mo.getTag(), RENEMONEYCALLPAYTAG)) {
            return;
        }

        if (mo.getCallType() == PayUtil.ALIPAY && mo.isPaySucceed()) { //支付宝支付成功
            paySucceed();
        } else if (mo.getCallType() == PayUtil.WEIXINPAY && mo.isPaySucceed()) {//微信支付成功
            paySucceed();
        }
    }

    private void paySucceed() {
        showToast("续期成功");
        EventBus.getDefault().post(EventTags.AllFINISH);
        UseMoneyRecordActivity.open(this, BusinessSings.USEMONEYRECORD_3);
        finish();
    }

    /**
     * 线下支付 宝付
     */
    @Subscribe
    public void PayState2(String mo) {
        if (TextUtils.equals(mo, EventTags.RENEWALFLAGE)) {
            paySucceed();
        }
    }


}
