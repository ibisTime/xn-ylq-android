package com.cdkj.ylq.module.certification.review;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityHumanreviewBinding;
import com.cdkj.ylq.databinding.ActivityIdCertBinding;
import com.cdkj.ylq.module.certification.ZMCertificationActivity;

/**
 * 人工审核
 * Created by 李先俊 on 2017/8/9.
 */

public class HumanReviewActivity extends AbsBaseActivity {

    private ActivityHumanreviewBinding mBinding;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, HumanReviewActivity.class));
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_humanreview, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("系统审核");

        initListener();
    }

    //
    private void initListener() {

        mBinding.btnWaite.setOnClickListener(v -> {
            finish();
        });

    }


}
