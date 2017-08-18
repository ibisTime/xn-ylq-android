package com.cdkj.ylq.module.user.userinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cdkj.baselibrary.utils.WxUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityShareBinding;

/**
 * Created by 李先俊 on 2017/8/1.
 */

public class ShareActivity extends Activity {

    private ActivityShareBinding mbinding;

    private String mShareUrl;//需要分享的URL

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, String shareUrl) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra("shareUrl", shareUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_share);

        if (getIntent() != null) {
            mShareUrl = getIntent().getStringExtra("shareUrl");
        }

        initListener();

    }

    /**
     * 初始化事件
     */
    private void initListener() {

        mbinding.txtCancel.setOnClickListener(v -> {
            finish();
        });

        mbinding.imgPyq.setOnClickListener(v -> {
            WxUtil.shareToPYQ(ShareActivity.this, mShareUrl,
                    "邀请好友", "邀请好友送优惠券，多邀多得。", R.mipmap.logo);
            finish();
        });

        mbinding.imgWx.setOnClickListener(v -> {
            WxUtil.shareToWX(ShareActivity.this, mShareUrl,
                    "邀请好友", "邀请好友送优惠券，多邀多得", R.mipmap.logo);
            finish();
        });

    }
}
