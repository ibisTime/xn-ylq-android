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
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentOfflineAlsomoneyBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;

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
    private UseMoneyRecordModel.ListBean.Info info;

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

    public static AlsoMoneyOffLineFragment getInstanse(UseMoneyRecordModel.ListBean.Info info) {
        AlsoMoneyOffLineFragment fragment = new AlsoMoneyOffLineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_offline_alsomoney, null, false);

        getKeyData();

        if (getArguments() != null) {
            info = (UseMoneyRecordModel.ListBean.Info) getArguments().getSerializable("info");
            if (info != null) {
                mBinding.llQs.setVisibility(View.VISIBLE);
                mBinding.tvQs.setText(info.getRemark());
                mBinding.tvMoney.setText(MoneyUtils.showPrice(info.getAmount()));
            } else {
                mCode = getArguments().getString("code");
                mBinding.tvMoney.setText(getArguments().getString("money"));
            }

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
     * 线下还款   (意思就是  私底下转给放款人,这个app就是调用一下接口,申请一下)
     * <p>
     * 分为  分期和不分期  调用接口不同  根据是否有分期的对象来判断是否该进行分期
     */
    private void payRequest() {
        Map<String, String> map = new HashMap<>();
        String code;
        if (!TextUtils.isEmpty(mCode)) {
            code = "623180";
            map.put("orderCode", mCode);
        } else if (info != null) {
            code = "623182";
            map.put("stagingCode", info.getStageCode());
        } else {
            return;
        }
        Call call = RetrofitUtils.getBaseAPiService().stringRequest(code, StringUtils.getJsonToString(map));
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
        map.put("key", "repayOfflineAccount");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("623917", StringUtils.getJsonToString(map));
        addCall(call);

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    mBinding.webView.loadData("信息获取失败,请重新获取", "text/html;charset=UTF-8", "UTF-8");
                    return;
                }
                mBinding.webView.loadData(data.getCvalue(), "text/html;charset=UTF-8", "UTF-8");
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
                super.onReqFailure(errorCode, errorMessage);
                mBinding.webView.loadData("信息获取失败,请重新获取", "text/html;charset=UTF-8", "UTF-8");
            }

            @Override
            protected void onBuinessFailure(String code, String error) {
                super.onBuinessFailure(code, error);
                mBinding.webView.loadData("信息获取失败,请重新获取", "text/html;charset=UTF-8", "UTF-8");
            }

            @Override
            protected void onFinish() {

            }
        });
    }
}
