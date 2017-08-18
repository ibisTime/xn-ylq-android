package com.cdkj.ylq.module.borrowmoney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityPutmoneyingBinding;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UseMoneyRecordActivity;

/**
 * 放款中
 * Created by 李先俊 on 2017/8/9.
 */

public class PutMoneyingActivity extends AbsBaseActivity {

    private ActivityPutmoneyingBinding mBinding;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, PutMoneyingActivity.class));
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_putmoneying, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("放款中");

        initListener();
    }

    //
    private void initListener() {
      mBinding.btnNext.setOnClickListener(v -> {
          UseMoneyRecordActivity.open(this);
      });
    }

}
