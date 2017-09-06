package com.cdkj.ylq.module.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.model.pay.PaySucceedInfo;
import com.cdkj.baselibrary.utils.payutils.PayUtil;
import com.cdkj.ylq.module.pay.fragments.AlsoMoneyOffLineFragment;
import com.cdkj.ylq.module.pay.fragments.AlsoMoneyOnLineFragment;

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


    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");
            mMoney = getIntent().getStringExtra("money");
        }

        setTopTitle("还款");

        setSubLeftImgState(true);

        super.afterCreate(savedInstanceState);
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(AlsoMoneyOnLineFragment.getInstanse(mCode, mMoney));//实时还款
        mFragments.add(AlsoMoneyOffLineFragment.getInstanse(mCode, mMoney));//线下还款
        return mFragments;
    }

    @Override
    public List<String> getFragmentTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("实时还款");
        titles.add("线下还款");
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
            showToast("还款成功");
            EventBus.getDefault().post(EventTags.AllFINISH);
            finish();
        } else if (mo.getCallType() == PayUtil.WEIXINPAY && mo.isPaySucceed()) {//微信支付成功

        }
    }

    /**
     * 线下支付
     */
    @Subscribe
    public void PayState2(String mo) {
        if (TextUtils.equals(mo, EventTags.ALSOOFFLINE)) {
            showToast("还款成功");
            EventBus.getDefault().post(EventTags.AllFINISH);
            finish();
        }
    }


}
