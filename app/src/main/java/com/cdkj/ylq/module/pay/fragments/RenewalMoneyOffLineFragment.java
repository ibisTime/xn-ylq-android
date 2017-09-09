package com.cdkj.ylq.module.pay.fragments;

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
import com.cdkj.baselibrary.utils.BigDecimalUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentOfflineAlsomoneyBinding;
import com.cdkj.ylq.databinding.FragmentRenewalPayOfflineBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 续期 线下
 * Created by 李先俊 on 2017/9/6.
 */

public class RenewalMoneyOffLineFragment extends BaseFragment {

    private FragmentRenewalPayOfflineBinding mBinding;
    private UseMoneyRecordModel.ListBean mData;
    /**
     * @return
     */
    public static RenewalMoneyOffLineFragment getInstanse(UseMoneyRecordModel.ListBean data) {
        RenewalMoneyOffLineFragment fragment = new RenewalMoneyOffLineFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_renewal_pay_offline, null, false);
        getKeyData();
        if (getArguments() != null) {
            mData = getArguments().getParcelable("data");
        }
        setShowData();
        initListener();
        return mBinding.getRoot();
    }

    private void setShowData() {
        if (mData == null) {
            return;
        }

        mBinding.tvMoney.setText(MoneyUtils.showPrice(mData.getRenewalAmount()));

        mBinding.tvRenewalStart.setText(DateUtil.formatStringData(mData.getRenewalStartDate(),DateUtil.DATE_YMD));
        mBinding.tvRenewalEnd.setText(DateUtil.formatStringData(mData.getRenewalEndDate(),DateUtil.DATE_YMD));

        //续期利息 =逾期总额-逾期利息
        mBinding.tvMoneyTips.setText("续期金额=逾期利息("+MoneyUtils.showPrice(mData.getYqlxAmount())+"元)+续期利息("+
                MoneyUtils.showPrice(BigDecimalUtils.subtract(mData.getRenewalAmount(),mData.getYqlxAmount()))+"元)");

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
        if (mData==null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", mData.getCode());
        map.put("payType", "4");

        Call call = RetrofitUtils.getBaseAPiService().stringRequest("623078", StringUtils.getJsonToString(map));
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
