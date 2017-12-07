package com.cdkj.ylq.module.pay.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.base.BaseFragment;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentOfflineAlsomoneyBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 还钱 线下
 * Created by 李先俊 on 2017/9/6.
 */

public class AlsoMoneyOffLineFragment extends BaseFragment {

    private FragmentOfflineAlsomoneyBinding mBinding;
    private String mCode;

    /**
     * @param code  还款编号
     * @param money 还款金额
     * @return
     */
    public static AlsoMoneyOffLineFragment getInstanse(String code, String money) {
        AlsoMoneyOffLineFragment fragment = new AlsoMoneyOffLineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("money", money);
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_offline_alsomoney, null, false);
        getKeyData();
        if (getArguments() != null) {
            mCode = getArguments().getString("code");
            mBinding.tvMoney.setText(getArguments().getString("money"));
        }
        initListener();
        return mBinding.getRoot();
    }

    private void initListener() {
        mBinding.btnSure.setOnClickListener(v -> {

            CommonDialog commonDialog = new CommonDialog(mActivity).builder()
                    .setTitle("提示").setContentMsg("确认已经进行打款？")
                    .setPositiveBtn("确定", view -> {
                        payRequest();
                    })
                    .setNegativeBtn("取消", null, false);

            commonDialog.show();

        });
    }

    /**
     * 支付请求
     */
    private void payRequest() {
        if (TextUtils.isEmpty(mCode)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);
        map.put("payType", "4");

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("623072", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<String>(mActivity) {
            @Override
            protected void onSuccess(String data, String SucMessage) {
                EventBus.getDefault().post(EventTags.ALSOOFFLINE);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    /**
     * 获取配置数据
     */
    public void getKeyData() {

        Map<String, String> map = new HashMap<>();
        map.put("ckey", "repayOfflineAccount");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));
        ;

        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    return;
                }
                mBinding.webView.loadData(data.getCvalue(), "text/html;charset=UTF-8", "UTF-8");
            }

            @Override
            protected void onFinish() {

            }
        });

    }



}
