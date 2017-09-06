package com.cdkj.ylq.module.user.userinfo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityMsgDetailsBinding;
import com.cdkj.ylq.model.MsgListModel;

/**
 * 消息详情
 * Created by 李先俊 on 2017/9/4.
 */

public class MsgDetailsActivity extends AbsBaseActivity {

    private ActivityMsgDetailsBinding mBinding;

    private MsgListModel.ListBean mData;


    public static void open(Context context, MsgListModel.ListBean data) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MsgDetailsActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_msg_details, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mData = getIntent().getParcelableExtra("data");
        }

        setTopTitle("消息详情");

        setSubLeftImgState(true);

        setShowData();

    }

    private void setShowData() {

        if (mData == null) return;
        mBinding.tvTitle.setText(mData.getSmsTitle());
        mBinding.tvContent.loadData(mData.getSmsContent(), "text/html;charset=UTF-8", "UTF-8");

    }
}
