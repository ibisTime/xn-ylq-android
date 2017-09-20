
package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityCallFriendBinding;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 邀请好友
 * Created by 李先俊 on 2017/6/16.
 */
//分支测试
public class CallFriendsActivity extends AbsStoreBaseActivity {

    private ActivityCallFriendBinding mBinding;
    private UserInfoModel mUserInfo;

    private String mShareUrl;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, UserInfoModel userInfo) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, CallFriendsActivity.class);
        intent.putExtra("userinfo", userInfo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_call_friend, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("邀请好友");
        setSubLeftImgState(true);

        if (getIntent() != null) {
            mUserInfo = getIntent().getParcelableExtra("userinfo");
        }

        setShowData();

        mBinding.layoutTop.radiogroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.radio_left) {
                mBinding.imgFriendCode.setVisibility(View.VISIBLE);
                mBinding.layoutCode.setVisibility(View.GONE);

                mBinding.tvTipsinfo.setText("扫描上面的二维码，成为我的好友吧！");


            } else if (checkedId == R.id.radio_right) {
                mBinding.layoutCode.setVisibility(View.VISIBLE);
                mBinding.imgFriendCode.setVisibility(View.GONE);

                mBinding.tvTipsinfo.setText("邀请好友");


            }

        });

        mBinding.btnShare.setOnClickListener(v -> {
//            ShareActivity.open(this,mShareUrl);
        });

        getDataReqeust();
    }

    private void setShowData() {
        if (mUserInfo == null) {
            return;
        }

        mBinding.tvname.setText(mUserInfo.getNickname());
        mBinding.tvMycode.setText(mUserInfo.getInviteCode());
        mBinding.tvMyfriend.setText("我的小伙伴 "+mUserInfo.getReferrerNum());


        if(mUserInfo.getUserExt() == null) return;

        ImgUtils.loadImgLogo(this, MyConfig.IMGURL+mUserInfo.getUserExt().getPhoto(),mBinding.imgLogo);

    }


    public void getDataReqeust() {

        Map<String ,String > map=new HashMap<>();
        map.put("ckey","domainUrl");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());
        mSubscription.add( RetrofitUtils.getLoaderServer().getInfoByKey("807717", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(null))
                .filter(s-> s!=null && !TextUtils.isEmpty(s.getCvalue()))

                .map(s->{
                    mShareUrl=s.getCvalue()+ "?kind=f1&mobile="+ SPUtilHelpr.getUserPhoneNum();
                    return mShareUrl;
                })

                .map(s-> CodeUtils.createImage(s,300, 300, null))

                .filter(bitmap -> bitmap!=null)

                .map(bitmap -> {

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                    byte[] bytes=baos.toByteArray();

                    return bytes;

                }).subscribe(bytes -> {
                    Glide.with(this)
                            .load(bytes)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mBinding.imgFriendCode);

                },Throwable::printStackTrace));
    }


}
