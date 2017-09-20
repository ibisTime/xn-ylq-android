package com.chengdai.ehealthproject.model.tabmy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentMyStoreBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.model.tabmy.activitys.CallFriendsActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.HotelOrderStateLookActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.MyAmountActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.MyInfoActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.MyJFDetailsActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.MyTestHistoryActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.SettingActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.ShopAllOrderLookActivity;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的
 * Created by 李先俊 on 2017/6/8.
 */

public class MyFragment extends BaseLazyFragment {

    private FragmentMyStoreBinding mBinding;

    private boolean isCreate;
    private UserInfoModel mUserInfoData;

    private String accountNumber;//积分流水账号
    private String mAmountaccountNumber;//余额流水账号
    private String mAmount;//账户余额

    /**
     * 获得fragment实例
     *
     * @return
     */
    public static MyFragment getInstanse() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_my_store, null, false);

        //周边
        mBinding.tvSurroundingService.setOnClickListener(v -> {
            HotelOrderStateLookActivity.open(mActivity);
        });

        //健康商城
        mBinding.linShop.setOnClickListener(v -> {
            ShopAllOrderLookActivity.open(mActivity);

        });
        //设置
        mBinding.linSetting.setOnClickListener(v -> {
            SettingActivity.open(mActivity, mUserInfoData);
        });
        //健康圈
        mBinding.healthcircle.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(mActivity)) {
                return;
            }
//            MyLuntanActivity.open(mActivity, SPUtilHelpr.getUserId());
        });

        //健康任务
        mBinding.linHealthTask.setOnClickListener(v -> {
        });

        //个人信息
        mBinding.linMyInfo.setOnClickListener(v -> {
            MyInfoActivity.open(mActivity, mUserInfoData);
        });

        //健康档案
        mBinding.linHealthDoc.setOnClickListener(v -> {
            MyTestHistoryActivity.open(mActivity);
        });

        //积分流水
        mBinding.fraJf.setOnClickListener(v -> {
            MyJFDetailsActivity.open(mActivity, mBinding.tvJf.getText().toString(), accountNumber);
        });
        //余额流水
        mBinding.fraAmount.setOnClickListener(v -> {
            MyAmountActivity.open(mActivity, mAmount, mAmountaccountNumber, mUserInfoData);
        });
        //邀请好友
        mBinding.layoutCallFriend.setOnClickListener(v -> {
            CallFriendsActivity.open(mActivity, mUserInfoData);
        });

        isCreate = true;

        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        if (isCreate) {
            getAmountRequest();
            getJifenRequest();
            getUserInfoRequest();
            getPhoneReqeust();
            getTimeReqeust();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (SPUtilHelpr.isLoginNoStart() && getUserVisibleHint()) {
            getAmountRequest();
            getJifenRequest();
            getUserInfoRequest();
            getPhoneReqeust();
            getTimeReqeust();
        }
    }

    @Override
    protected void onInvisible() {

    }

    /**
     * 获取余额请求
     */
    private void getAmountRequest() {

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("currency", "CNY");
        map.put("token", SPUtilHelpr.getUserToken());


        mSubscription.add(RetrofitUtils.getLoaderServer().getAmount("802503", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(null))
                .filter(r -> r != null && r.size() > 0 && r.get(0) != null)
                .subscribe(r -> {
                    mAmountaccountNumber = r.get(0).getAccountNumber();
                    SPUtilHelpr.saveAmountaccountNumber(mAmountaccountNumber);
                    mAmount = MoneyUtils.showPrice(r.get(0).getAmount());
                    mBinding.tvYe.setText(mAmount);

                }, Throwable::printStackTrace));

    }

    /**
     * 获取积分请求
     */
    private void getJifenRequest() {

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("currency", "JF");
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().getAmount("802503", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(mActivity))
                .filter(r -> r != null && r.size() > 0 && r.get(0) != null)
                .subscribe(r -> {

                    accountNumber = r.get(0).getAccountNumber();
                    mBinding.tvJf.setText(MoneyUtils.showPrice(r.get(0).getAmount()));

                }, Throwable::printStackTrace));


    }

    /**
     * 获取用户信息请求
     */
    private void getUserInfoRequest() {

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().GetUserInfo("805056", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(null))

                .filter(r -> r != null)

                .subscribe(r -> {
                    mUserInfoData = r;

                    SPUtilHelpr.saveUserPhoneNum(mUserInfoData.getMobile());

                    mBinding.tvUserName.setText(r.getNickname());

                    if (r.getUserExt() == null) return;

                    ImgUtils.loadActLogo(mActivity, MyConfigStore.IMGURL + r.getUserExt().getPhoto(), mBinding.imtUserLogo);

                    if (MyConfigStore.GENDERMAN.equals(r.getUserExt().getGender())) {
                        ImgUtils.loadImgId(mActivity, R.mipmap.man, mBinding.imgUserSex);
                    } else if (MyConfigStore.GENDERWOMAN.equals(r.getUserExt().getGender())) {
                        ImgUtils.loadImgId(mActivity, R.mipmap.woman, mBinding.imgUserSex);
                    }

                    if (MyConfigStore.LEVEL_NOT_VIP.equals(r.getLevel())) {
                        mBinding.imgVip.setVisibility(View.GONE);
                    } else if (MyConfigStore.LEVEL_VIP.equals(r.getLevel())) {
                        mBinding.imgVip.setVisibility(View.VISIBLE);
                    }


                }, Throwable::printStackTrace));
    }

    /**
     *获取服务时间
     */
    public void getTimeReqeust() {

        Map<String ,String > map=new HashMap<>();
        map.put("ckey","telephone");
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());
        mSubscription.add( RetrofitUtils.getLoaderServer().getInfoByKey("807717", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mActivity))
                .filter(s-> s!=null && !TextUtils.isEmpty(s.getNote()))
                .subscribe(s -> {
                    mBinding.tvServiceTime.setText("服务时间:"+s.getNote());
                },Throwable::printStackTrace));
    }

    /**
     * 获取服务电话
     */
    public void getPhoneReqeust() {

        Map<String ,String > map=new HashMap<>();
        map.put("ckey","time");
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());
        mSubscription.add( RetrofitUtils.getLoaderServer().getInfoByKey("807717", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mActivity))
                .filter(s-> s!=null && !TextUtils.isEmpty(s.getNote()))
                .subscribe(s -> {
                    mBinding.imgPhone.setVisibility(View.VISIBLE);
                    mBinding.tvServicePhone.setText(s.getNote());
                },Throwable::printStackTrace));
    }


    @Subscribe
    public void refeshEvent(EventBusModel e) {
        if (e == null) {
            return;
        }
        if (TextUtils.equals("MyFragmentRefeshUserIfo", e.getTag()))//刷新用户数据
        {
//            getUserInfoRequest();
        }
        if (TextUtils.equals("MyFragmentRefeshJF", e.getTag()))//刷新用户积分
        {
//            getJifenRequest();
        }

    }

}
