package com.cdkj.ylq.module.user.userinfo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityMoneyCeilingBinding;
import com.cdkj.ylq.model.CanUseMoneyModel;
import com.cdkj.ylq.model.PorductListModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.borrowmoney.UseMoneySureDetailsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.EventTags.MAINCHANGESHOWINDEX;
import static com.cdkj.ylq.MainActivity.SHOWMONEYPRODUCT;

/**
 * 我的额度
 * Created by 李先俊 on 2017/8/13.
 */

public class MyMaxMoneyActivity extends AbsBaseActivity {

    public ActivityMoneyCeilingBinding mBinding;

    private CanUseMoneyModel mData;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, MyMaxMoneyActivity.class));
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_money_ceiling, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTopTitle("我的额度");
        setSubLeftImgState(true);
        getCanUseMoneyData();

        mBinding.btnUse.setOnClickListener(v -> {

            if (mData == null) {
                return;
            }
            EventBusModel eventBusModel = new EventBusModel();
            eventBusModel.setTag(MAINCHANGESHOWINDEX);
            if (TextUtils.equals("0", mData.getFlag())) {//没有额度
                eventBusModel.setEvInt(SHOWMONEYPRODUCT);
                EventBus.getDefault().post(eventBusModel);         //跳转到申请界面
                finish();
            } else if (TextUtils.equals("1", mData.getFlag())) {//已有额度
                getCanUseProductData();
            } else if (TextUtils.equals("2", mData.getFlag())) {//已经过期
                eventBusModel.setEvInt(SHOWMONEYPRODUCT);
                EventBus.getDefault().post(eventBusModel);         //跳转到认证界面
                finish();
            }
        });

    }

    /**
     * 获取额度数据
     */
    public void getCanUseMoneyData() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getCanUseMoney("623051", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<CanUseMoneyModel>(this) {
            @Override
            protected void onSuccess(CanUseMoneyModel data, String SucMessage) {
                mData = data;
                setState(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    private void setState(CanUseMoneyModel data) {
        mBinding.tvMoney.setText(MoneyUtils.showPrice(data.getSxAmount()));

        if (TextUtils.equals("0", data.getFlag())) {//没有额度
            mBinding.tvRemainingDays.setText("您目前没有额度");
            mBinding.btnUse.setText("申请额度");
        } else if (TextUtils.equals("1", data.getFlag())) {//已有额度

            if (data.getSxAmount() != null && data.getSxAmount().doubleValue() <= 0) {
                mBinding.tvRemainingDays.setText("您的额度已使用完");
            } else {
                mBinding.tvRemainingDays.setText("还有" + data.getValidDays() + "天当前额度失效");
            }
            mBinding.btnUse.setText("签约");

        } else if (TextUtils.equals("2", data.getFlag())) {//已经过期
            mBinding.tvRemainingDays.setText("当前额度已失效");
            mBinding.btnUse.setText("重新申请额度");
        }
    }

    /**
     * 获取可用产品数据
     */
    public void getCanUseProductData() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.createApi(MyApiServer.class).getCanUseProduct("623013", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<PorductListModel.ListBean>(this) {

            @Override
            protected void onSuccess(PorductListModel.ListBean data, String SucMessage) {
                UseMoneySureDetailsActivity.open(MyMaxMoneyActivity.this, data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Subscribe
    public void EventMoth(String tag) {
        if (TextUtils.equals(tag, EventTags.USEMONEYSUREFINISH)) {
            finish();
        }
    }
}
