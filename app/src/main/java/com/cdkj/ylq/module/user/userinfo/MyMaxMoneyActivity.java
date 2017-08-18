package com.cdkj.ylq.module.user.userinfo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityMoneyCeilingBinding;
import com.cdkj.ylq.model.CanUseMoneyModel;
import com.cdkj.ylq.model.PorductListModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.borrowmoney.UseMoneySureDetailsActivity;
import com.cdkj.ylq.module.product.ProductDetailsActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 我的额度
 * Created by 李先俊 on 2017/8/13.
 */

public class MyMaxMoneyActivity extends AbsBaseActivity {

    public ActivityMoneyCeilingBinding mBinding;

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
            getCanUseProductData();
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
        call.enqueue(new BaseResponseModelCallBack<CanUseMoneyModel>(this) {
            @Override
            protected void onSuccess(CanUseMoneyModel data, String SucMessage) {
                mBinding.tvMoney.setText(MoneyUtils.showPrice(data.getSxAmount()));
//
//               Date date= DateUtil.parseStringData(data.getValidDatetime(),DateUtil.DATE_YMD);
//
//                mBinding.tvRemainingDays.setText("还有"+DateUtil.getDatesBetweenTwoDate(new Date(),date).size()+"天当前额度失效");
                mBinding.tvRemainingDays.setText("还有"+data.getValidDays()+"天当前额度失效");}

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
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


}
