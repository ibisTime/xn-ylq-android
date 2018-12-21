package com.cdkj.ylq.module.credit;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.FragmentCreditBinding;
import com.cdkj.ylq.model.CreditTypeBean;
import com.cdkj.ylq.model.SuccessModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.certification.all.AllCertificationListActivity;
import com.cdkj.ylq.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.EventTags.MAINCHANGESHOWINDEX;

//import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditFragment extends BaseLazyFragment {

    FragmentCreditBinding mBinding;

    public static CreditFragment getInstanse() {
        CreditFragment fragment = new CreditFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_credit, null, false);
        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        initData();
    }

    @Override
    protected void onInvisible() {

    }

    /**
     * 获取数据,获取认证的状态
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        Call<BaseResponseModel<CreditTypeBean>> creditType = RetrofitUtils.createApi(MyApiServer.class).getCreditType("623033", StringUtils.getJsonToString(map));
        showLoadingDialog();
        creditType.enqueue(new BaseResponseModelCallBack<CreditTypeBean>(mActivity) {
            @Override
            protected void onSuccess(CreditTypeBean data, String SucMessage) {
                setViewData(data);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 设置当前界面信息
     */
    private void setViewData(CreditTypeBean data) {
        switch (data.getStatus()) {
            case "0"://未认证  去认证  1
                mBinding.btnWaite.setOnClickListener(v -> {
                    requestXYF(true);
                });
                mBinding.btnWaite.setText("去认证");
                mBinding.tvRemainingDays.setText("未认证");
                mBinding.tvMoney.setText(MoneyUtils.showPriceInt(data.getCreditScore()));
                mBinding.llStep1.setVisibility(View.VISIBLE);
                mBinding.llStep2.setVisibility(View.GONE);
                mBinding.llStep3.setVisibility(View.GONE);
                mBinding.llStep4.setVisibility(View.GONE);
                mBinding.llStep5.setVisibility(View.GONE);

                //获取可配置的说明
                getCreditScore();

                break;
            case "1"://认证中  完善资料
                mBinding.btnWaite.setOnClickListener(v -> {
                    AllCertificationListActivity.open(mActivity);
                });
                mBinding.btnWaite.setText("完善资料");
                mBinding.tvRemainingDays.setText("认证中");
                mBinding.tvMoney.setText(MoneyUtils.showPriceInt(data.getCreditScore()));
                mBinding.llStep1.setVisibility(View.GONE);
                mBinding.llStep2.setVisibility(View.GONE);
                mBinding.llStep3.setVisibility(View.GONE);
                mBinding.llStep4.setVisibility(View.GONE);
                mBinding.llStep5.setVisibility(View.VISIBLE);
                break;
            case "2"://待审核   请耐心等待
                mBinding.btnWaite.setOnClickListener(null);
                mBinding.btnWaite.setText("请耐心等待");
                mBinding.tvRemainingDays.setText("待审核");
                mBinding.tvMoney.setText(MoneyUtils.showPriceInt(data.getCreditScore()));
                mBinding.llStep1.setVisibility(View.GONE);
                mBinding.llStep2.setVisibility(View.VISIBLE);
                mBinding.llStep3.setVisibility(View.GONE);
                mBinding.llStep4.setVisibility(View.GONE);
                mBinding.llStep5.setVisibility(View.GONE);
                break;
            case "3"://核准失败   重新提交 1
                mBinding.btnWaite.setOnClickListener(v -> {
                    requestXYF(false);
                });
                mBinding.btnWaite.setText("重新提交");
                mBinding.tvRemainingDays.setText("核准失败");
                CreditTypeBean.ApplyBean apply = data.getApply();
                if (apply != null) {
                    mBinding.tvErrorMsg.setText("失败理由:" + apply.getApproveNote());
                }
                mBinding.tvMoney.setText(MoneyUtils.showPriceInt(data.getCreditScore()));
                mBinding.llStep1.setVisibility(View.GONE);
                mBinding.llStep2.setVisibility(View.GONE);
                mBinding.llStep3.setVisibility(View.GONE);
                mBinding.llStep4.setVisibility(View.VISIBLE);
                mBinding.llStep5.setVisibility(View.GONE);
                break;
            case "4"://已核准   使用信用分
                mBinding.btnWaite.setOnClickListener(v -> {
                    //使用信用分就跳转到  首页
                    EventBusModel bean = new EventBusModel();
                    bean.setTag(MAINCHANGESHOWINDEX);
                    bean.setEvInt(0);
                    EventBus.getDefault().post(bean);
                });
                mBinding.btnWaite.setText("使用信用分");
                mBinding.tvRemainingDays.setText("还有" + data.getValidDays() + "天当前信用分失效");
                mBinding.tvMoney.setText(MoneyUtils.showPriceInt(data.getCreditScore()));
//                mBinding.tvValidDays.setText("剩余x天到期");
                mBinding.llStep1.setVisibility(View.GONE);
                mBinding.llStep2.setVisibility(View.GONE);
                mBinding.llStep3.setVisibility(View.GONE);
                mBinding.llStep4.setVisibility(View.GONE);
                mBinding.llStep5.setVisibility(View.GONE);
                break;
            case "5"://信用分失效  重新申请   1
                mBinding.btnWaite.setOnClickListener(v -> {
                    requestXYF(false);
                });
                mBinding.btnWaite.setText("重新申请");
                mBinding.tvRemainingDays.setText("信用分失效");
                mBinding.tvMoney.setText(MoneyUtils.showPriceInt(data.getCreditScore()));
                mBinding.llStep1.setVisibility(View.GONE);
                mBinding.llStep2.setVisibility(View.GONE);
                mBinding.llStep3.setVisibility(View.VISIBLE);
                mBinding.llStep4.setVisibility(View.GONE);
                mBinding.llStep5.setVisibility(View.GONE);
                break;
            case "6"://信用分为0  重新申请  重新申请 1
                mBinding.btnWaite.setOnClickListener(v -> {
                    requestXYF(false);
                });
                mBinding.btnWaite.setText("重新申请");
                mBinding.tvRemainingDays.setText("重新申请");
                mBinding.tvMoney.setText(MoneyUtils.showPriceInt(data.getCreditScore()));
                mBinding.llStep1.setVisibility(View.GONE);
                mBinding.llStep2.setVisibility(View.GONE);
                mBinding.llStep3.setVisibility(View.VISIBLE);
                mBinding.llStep4.setVisibility(View.GONE);
                mBinding.llStep5.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 获取信用分 用途说明
     */
    private void getCreditScore() {

        NetUtils.getSystemParameter(mActivity, "creditScore", true, new NetUtils.OnSuccessSystemInterface() {
            @Override
            public void onSuccessSystem(IntroductionInfoModel data) {
                mBinding.tvPurpose.setText(data.getCvalue());
            }

            @Override
            public void onError(String errorMessage) {
                mBinding.tvPurpose.setText(errorMessage);
            }
        });

    }

    /**
     * 申请信用分
     * 要不要跳转,不要跳转的话,就刷新界面数据
     */
    private void requestXYF(boolean isJump) {

        Map<String, String> map = new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("companyCode", MyConfig.COMPANYCODE);
        Call<BaseResponseModel<SuccessModel>> baseResponseModelCall = RetrofitUtils.createApi(MyApiServer.class).requestXYF("623020", StringUtils.getJsonToString(map));
        baseResponseModelCall.enqueue(new BaseResponseModelCallBack<SuccessModel>(mActivity) {
            @Override
            protected void onSuccess(SuccessModel data, String SucMessage) {
                if (isJump) {
                    AllCertificationListActivity.open(mActivity);
                } else {
                    initData();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 从AllCertificationListActivity界面发送的通知
     *
     * @param tag
     */
    @Subscribe
    public void onRefresh(String tag) {
        if (TextUtils.equals(tag, "刷新界面数据")) {
            initData();
        }
    }
}
