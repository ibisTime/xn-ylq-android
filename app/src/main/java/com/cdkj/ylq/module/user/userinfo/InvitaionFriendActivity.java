package com.cdkj.ylq.module.user.userinfo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityInvitationFriendBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 邀请好友
 * Created by 李先俊 on 2017/8/9.
 */

public class InvitaionFriendActivity extends AbsBaseActivity {

    private ActivityInvitationFriendBinding mBinding;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, InvitaionFriendActivity.class));
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_invitation_friend, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("邀请好友");

        setSubRightTitleAndClick("推荐历史", v -> {
            HuokeListActivity.open(this);
        });
        initListener();
    }

    //
    private void initListener() {
       mBinding.btnInvitation.setOnClickListener(v -> {

           Map<String, String> map = new HashMap<>();
           map.put("ckey", "domainUrl");
           map.put("systemCode", MyConfig.SYSTEMCODE);
           map.put("companyCode", MyConfig.COMPANYCODE);

           Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));
           ;

           addCall(call);

           showLoadingDialog();

           call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(this) {
               @Override
               protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                   if (TextUtils.isEmpty(data.getCvalue())) {
                       return;
                   }

                   ShareActivity.open(InvitaionFriendActivity.this,data.getCvalue());
               }

               @Override
               protected void onFinish() {
                   disMissLoading();
               }
           });




       });
    }


}
