package com.cdkj.ylq.module.user.userinfo.usemoneyrecord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.ylq.appmanager.BusinessSings;

import java.util.ArrayList;
import java.util.List;

/**
 * 借款记录
 * Created by 李先俊 on 2017/8/16.
 */

public class UseMoneyRecordActivity extends CommonTablayoutActivity {

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context,String showTag) {
        if (context == null) {
            return;
        }
        Intent intent=  new Intent(context, UseMoneyRecordActivity.class);
        intent.putExtra("showTag",showTag);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setSubLeftImgState(true);
        setTopTitle("借款记录");
        mbinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        super.afterCreate(savedInstanceState);

        //根据tag显示相应界面
        if(getIntent()!=null && !TextUtils.isEmpty(getIntent().getStringExtra("showTag"))){
            switch (getIntent().getStringExtra("showTag")) {
                case BusinessSings.USEMONEYRECORD_3:
                    mbinding.viewpager.setCurrentItem(4);
                    break;
                case BusinessSings.USEMONEYRECORD_4:
                    mbinding.viewpager.setCurrentItem(5);
                    break;
            }
        }

    }

    @Override
    public List<Fragment> getFragments() {

          //0 待审核 2 审核不通过 1待放款  7 打款失败 3生效中 4已还款 5已逾期

        List<Fragment> mFragments = new ArrayList<>();

        mFragments.add(UseMoneyRecordFragment.getInstanse(BusinessSings.USEMONEYRECORD_0));
        mFragments.add(UseMoneyRecordFragment.getInstanse(BusinessSings.USEMONEYRECORD_2));
        mFragments.add(UseMoneyRecordFragment.getInstanse(BusinessSings.USEMONEYRECORD_1));
        mFragments.add(UseMoneyRecordFragment.getInstanse(BusinessSings.USEMONEYRECORD_7));
        mFragments.add(UseMoneyRecordFragment.getInstanse(BusinessSings.USEMONEYRECORD_3));
        mFragments.add(UseMoneyRecordFragment.getInstanse(BusinessSings.USEMONEYRECORD_4));
        mFragments.add(UseMoneyRecordFragment.getInstanse(BusinessSings.USEMONEYRECORD_5));

        return mFragments;
    }

    @Override
    public List<String> getFragmentTitles() {
        List<String> mTitles = new ArrayList<>();

        mTitles.add("待审核");
        mTitles.add("审核不通过");
        mTitles.add("待放款");
        mTitles.add("打款失败");
        mTitles.add("待还款");
        mTitles.add("已还款");
        mTitles.add("已逾期");

        return mTitles;
    }

}
