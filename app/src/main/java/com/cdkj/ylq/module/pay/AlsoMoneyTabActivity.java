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
import com.cdkj.ylq.module.pay.fragments.AlsoMoneyOffLineFragment;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UseMoneyRecordActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 还款 tab切换
 * Created by 李先俊 on 2017/9/6.
 */

public class AlsoMoneyTabActivity extends CommonTablayoutActivity {


    private String mCode;
    private String mMoney;

    public static final String ALSOMONEYCALLPAYTAG = "AlsoMoneyTabActivity";
    private UseMoneyRecordModel.ListBean.Info info;

    /**
     * @param context
     * @param code    还款产品编号
     * @param money   金额
     */
    public static void open(Context context, String code, String money) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AlsoMoneyTabActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("money", money);
        context.startActivity(intent);
    }

    /**
     * @param context
     */
    public static void open(Context context, UseMoneyRecordModel.ListBean.Info info) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AlsoMoneyTabActivity.class);
        intent.putExtra("info", info);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");
            mMoney = getIntent().getStringExtra("money");
            info = (UseMoneyRecordModel.ListBean.Info) getIntent().getSerializableExtra("info");
        }

        setTopTitle("还款");

        setSubLeftImgState(true);

        mbinding.tablayout.setVisibility(View.GONE);
        super.afterCreate(savedInstanceState);

    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();
//        mFragments.add(AlsoMoneyOnLineFragment.getInstanse(mCode, mMoney));//实时还款
        if (info != null) {
            mFragments.add(AlsoMoneyOffLineFragment.getInstanse(info));
        } else {
            mFragments.add(AlsoMoneyOffLineFragment.getInstanse(mCode, mMoney));//线下还款 //线下还款和线下续期在130需求中去除
        }
        return mFragments;
    }

    @Override
    public List<String> getFragmentTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("实时还款");
//        titles.add("线下还款");
        return titles;
    }


    /**
     * 支付回调
     *
     * @param mo
     */
    @Subscribe
    public void PayState(PaySucceedInfo mo) {
        if (mo == null || !TextUtils.equals(mo.getTag(), ALSOMONEYCALLPAYTAG)) {
            return;
        }

        if (mo.getCallType() == PayUtil.ALIPAY && mo.isPaySucceed()) { //支付宝支付成功
            paySucceed();
        } else if (mo.getCallType() == PayUtil.WEIXINPAY && mo.isPaySucceed()) {//微信支付成功
            paySucceed();
        }
    }

    private void paySucceed() {
        showToast("还款成功");
        EventBus.getDefault().post(EventTags.AllFINISH);
        UseMoneyRecordActivity.open(this, BusinessSings.USEMONEYRECORD_4);
        finish();
    }

    /**
     * 线下支付
     * AlsoMoneyOffLineFragment  界面发送的消息
     */
    @Subscribe
    public void PayState2(String mo) {
        if (TextUtils.equals(mo, EventTags.ALSOOFFLINE)) {
            showToast("申请还款成功");
            EventBus.getDefault().post(EventTags.AllFINISH);
            //不用跳转到  已还款界面
//            UseMoneyRecordActivity.open(this, BusinessSings.USEMONEYRECORD_4);
            finish();
        }
    }
}
