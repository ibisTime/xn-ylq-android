package com.cdkj.ylq.module.borrowmoney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.appmanager.BusinessSings;
import com.cdkj.ylq.databinding.ActivityPutmoneyingBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UseMoneyRecordActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 放款中
 * Created by 李先俊 on 2017/8/9.
 */

public class PutMoneyingActivity extends AbsBaseActivity {

    private ActivityPutmoneyingBinding mBinding;

    /**
     * 打开当前页面
     *
     * @param context 借款编号
     */
    public static void open(Context context, String boorwCoce) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PutMoneyingActivity.class);

        intent.putExtra("code", boorwCoce);

        context.startActivity(intent);
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
        getListData(1, 10);
        initListener();
    }

    /**
     * 同时只能有一个借款
     *
     * @param pageIndex
     * @param limit
     */
    protected void getListData(int pageIndex, int limit) {

        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("status", "0");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getRecordList("623087", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UseMoneyRecordModel>(this) {
            @Override
            protected void onSuccess(UseMoneyRecordModel data, String SucMessage) {
                if (data.getList() != null && data.getList().size() > 0 && data.getList().get(0) != null) {
                    mBinding.tvMoney.setText(MoneyUtils.getShowPriceSign(data.getList().get(0).getAmount()));
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    /**
     * 根据借款编号获取借款数据
     */
    public void getDataRequest(String mCode) {

        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUseMoneyData("623086", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UseMoneyRecordModel.ListBean>(this) {
            @Override
            protected void onSuccess(UseMoneyRecordModel.ListBean data, String SucMessage) {
                BusinessSings.startRecordActivity(PutMoneyingActivity.this, data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    private void initListener() {
        mBinding.btnNext.setOnClickListener(v -> {
            if (getIntent() != null && !TextUtils.isEmpty(getIntent().getStringExtra("code"))) {              //根据CODE获取状态，然后跳转借款详情
                getDataRequest(getIntent().getStringExtra("code"));
            } else {
                UseMoneyRecordActivity.open(this, BusinessSings.USEMONEYRECORD_1);              //没有获取到借款编号时直接跳转列表
            }
        });
    }

}
