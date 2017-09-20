package com.chengdai.ehealthproject.model.tabmy.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseFragment;
import com.chengdai.ehealthproject.databinding.FragmentInLineRechargeBinding;
import com.chengdai.ehealthproject.model.tabmy.activitys.RechargeTabActivity;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.AppOhterManager;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/8/29.
 */

public class RechargeInLineFragment extends BaseFragment {

    private FragmentInLineRechargeBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_in_line_recharge, null, false);
        getDataReqeust();

        initViews();
        return mBinding.getRoot();
    }


    private void initViews() {

        //限制金额数据
        mBinding.editMoney.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});

        mBinding.btnConfirm.setOnClickListener(v -> {

            if (TextUtils.isEmpty(mBinding.editBankInfo.getText().toString())) {
                ToastUtil.show(mActivity, "请输入开户行信息");
                return;
            }
            if (TextUtils.isEmpty(mBinding.editCard.getText().toString())) {
                ToastUtil.show(mActivity, "请输入银行卡");
                return;
            }
            if (TextUtils.isEmpty(mBinding.editMoney.getText().toString())) {
                ToastUtil.show(mActivity, "请输入充值金额");
                return;
            }

            rechargeRequest();

        });
    }

    /**
     *
     */
    private void rechargeRequest() {

        Map<String, String> map = new HashMap<>();

        map.put("accountNumber", SPUtilHelpr.getAmountaccountNumber());
        map.put("amount", StringUtils.getRequestPrice(mBinding.editMoney.getText().toString()));
        map.put("payCardInfo", mBinding.editBankInfo.getText().toString());
        map.put("payCardNo", mBinding.editCard.getText().toString());
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());
        map.put("bizType", "11");

        mSubscription.add(RetrofitUtils.getLoaderServer().rechargeIn("802700", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(null))
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s.getCode())) {
                        EventBus.getDefault().post(RechargeTabActivity.CALLPAYTAG);
                    }
                }, Throwable::printStackTrace));

    }


    public void getDataReqeust() {

        Map<String, String> map = new HashMap<>();
        map.put("ckey", "chargeAccount");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());
        mSubscription.add(RetrofitUtils.getLoaderServer().getInfoByKey("807717", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(null))
                .filter(s -> s != null && !TextUtils.isEmpty(s.getNote()))
                .subscribe(s -> {
                    AppOhterManager.showRichText(mActivity,mBinding.tvTips,s.getNote());
                }, Throwable::printStackTrace));
    }


}
