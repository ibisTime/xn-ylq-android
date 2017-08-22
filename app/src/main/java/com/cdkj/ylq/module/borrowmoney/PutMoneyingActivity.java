package com.cdkj.ylq.module.borrowmoney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
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
        getListData(1, 10);
        initListener();
    }

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
                if (data.getList() != null && data.getList().size() > 0) {
                    mBinding.tvMoney.setText(MoneyUtils.getShowPriceSign(data.getList().get(0).getAmount()));
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    //
    private void initListener() {
        mBinding.btnNext.setOnClickListener(v -> {
            UseMoneyRecordActivity.open(this);
        });
    }

}
