package com.cdkj.ylq.module.user.userinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.baselibrary.activitys.WebViewActivity;
import com.cdkj.ylq.module.borrowmoney.BorrowMoneyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的优惠券
 * Created by 李先俊 on 2017/8/16.
 */

public class MyCouponsActivity extends CommonTablayoutActivity {

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, MyCouponsActivity.class));
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setSubLeftImgState(true);
        setTopTitle("优惠券");

        setSubRightTitleAndClick("说明", v -> {
            WebViewActivity.openkey(this, "优惠券说明", "couponExplain");

        });

        super.afterCreate(savedInstanceState);

    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(CouponsListFragment.getInstanse(CouponsListFragment.CANUSE));
        mFragments.add(CouponsListFragment.getInstanse(CouponsListFragment.CANUSET));

        return mFragments;
    }

    @Override
    public List<String> getFragmentTitles() {
        List<String> mTitles = new ArrayList<>();

        mTitles.add("可使用");
        mTitles.add("已失效");

        return mTitles;
    }

}
