package com.cdkj.ylq.module.borrowmoney;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.AddBackCardActivity;
import com.cdkj.baselibrary.activitys.UpdateBackCardActivity;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.BankCardModel;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.model.MyBankCardListMode;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BigDecimalUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivitySigningBinding;
import com.cdkj.ylq.model.PorductListModel;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 签约确认
 * Created by 李先俊 on 2017/8/9.
 */

public class SigningSureActivity extends AbsBaseActivity {

    private ActivitySigningBinding mBinding;
    private PorductListModel.ListBean mProductData;

    private String mCouponId;//优惠券ID
    private String mWillgetMondy;//实到金额
    private BankCardModel mBankCardModel = null;
    private CommonDialog mCommonDialog;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, PorductListModel.ListBean productData, String couponId, String willgetMondy) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, SigningSureActivity.class);
        intent.putExtra("productData", productData);
        intent.putExtra("couponId", couponId);
        intent.putExtra("willgetMondy", willgetMondy);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_signing, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("签约");

        if (getIntent() != null) {
            mProductData = getIntent().getParcelableExtra("productData");
            mCouponId = getIntent().getStringExtra("couponId");
            mWillgetMondy = getIntent().getStringExtra("willgetMondy");
        }
        setShowData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SPUtilHelpr.getUserIsBindCard()) {  //如果没有添加过银行卡 直接提示添加银行卡
            showBindCardSureDialog(view -> {
                AddBackCardActivity.open(SigningSureActivity.this);
            });
            return;
        }
        getBankCardDataRequest(true);
    }

    //
    private void initListener() {
        mBinding.btnSure.setOnClickListener(v -> {
            if (!mBinding.checkbox.isChecked()) {
                showToast("请阅读并同意借款协议");
                return;
            }
//            signingRequest();
//删除  打开打开
            if (!SPUtilHelpr.getUserIsBindCard() || mBankCardModel == null) {
                showBindCardSureDialog(view -> {
                    AddBackCardActivity.open(this);
                });
                return;
            }
            showSureCardInfoDialog();
        });

        mBinding.tvRead.setOnClickListener(v -> {
            SigningTipsWebViewActivity.open(this, mCouponId);
        });
    }


    private void showSureCardInfoDialog() {

        if (mBankCardModel == null) return;

        CommonDialog commonDialog = new CommonDialog(this).builder()
                .setTitle("请确认银行卡信息是否正确").setContentMsg("户名: " + mBankCardModel.getRealName() + "\n\n" +
                        "开户行: " + mBankCardModel.getBankName() + "\n\n" +
                        "银行卡号: " + mBankCardModel.getBankcardNumber())
                .setPositiveBtn("确认", view -> {
                    signingRequest();
                }).setNegativeBtn("修改", view -> {
                    UpdateBackCardActivity.open(this, mBankCardModel);
                }, false);
        commonDialog.show();
    }


    /**
     * 获取银行卡信息
     */
    private void getBankCardDataRequest(boolean isShowDialog) {

        Map<String, String> object = new HashMap<>();

        object.put("systemCode", MyConfig.SYSTEMCODE);
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("userId", SPUtilHelpr.getUserId());
        object.put("start", "1");
        object.put("limit", "10");

//        Call call = RetrofitUtils.getBaseAPiService().getCardListData("802015", StringUtils.getJsonToString(object));
        Call call = RetrofitUtils.getBaseAPiService().getCardListData("802025", StringUtils.getJsonToString(object));

        addCall(call);

        if (isShowDialog) showLoadingDialog();


        call.enqueue(new BaseResponseModelCallBack<MyBankCardListMode>(this) {
            @Override
            protected void onSuccess(MyBankCardListMode data, String SucMessage) {

                if (data != null && data.getList() != null && data.getList().size() > 0) {
                    mBankCardModel = data.getList().get(0);
                    return;
                }

                if (mBankCardModel == null) {
                    showBindCardSureDialog(view -> {
                        AddBackCardActivity.open(SigningSureActivity.this);
                    });
                }

            }

            @Override
            protected void onNull() {
                mBankCardModel = null;
            }

            @Override
            protected void onFinish() {
                if (isShowDialog) disMissLoading();
            }
        });
    }


    protected void showBindCardSureDialog(CommonDialog.OnPositiveListener onPositiveListener) {

        if (isFinishing()) {
            return;
        }

        if (mCommonDialog == null) {
            mCommonDialog = new CommonDialog(this).builder()
                    .setTitle("提示").setContentMsg("您还没有添加银行卡，请先添加银行卡。")
                    .setPositiveBtn("确定", onPositiveListener).setCancelable(true);
        }
        mCommonDialog.show();
    }


    /**
     * 签约请求
     */
    private void signingRequest() {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(mCouponId)) {
            map.put("couponId", mCouponId);
        }
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("productCode", mProductData.getCode());

        Call call = RetrofitUtils.getBaseAPiService().codeRequest("623070", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CodeModel>(this) {

            @Override
            protected void onSuccess(CodeModel data, String SucMessage) {
                if (!TextUtils.isEmpty(data.getCode())) {
                    EventBus.getDefault().post(EventTags.USEMONEYSUREFINISH);//关闭上一个界面
                    PutMoneyingActivity.open(SigningSureActivity.this, data.getCode());
                    finish();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    public void setShowData() {
        if (mProductData == null) return;

        mBinding.tvUserName.setText(SPUtilHelpr.getUserName());
        mBinding.tvMoney.setText(MoneyUtils.showPrice(mProductData.getAmount()) + "元");
        mBinding.tvDay.setText(mProductData.getDuration() + "天");
        mBinding.tvAllRate.setText(getAllRateMoney(mProductData) + "元");
        mBinding.tvUseMoneyState.setText("一次性还款" + MoneyUtils.showPrice(mProductData.getAmount()) + "元");
        mBinding.tvShidaoRate.setText(mWillgetMondy);
//        mBinding.tvDueDate.setText(DateUtil.getShowDayToData(mProductData.getDuration()));

        mBinding.tvRead.setText("《" + SPUtilHelpr.getUserName() + "-借款协议》");

        mBinding.tvUsed.setText("7天内逾期，每天" + MoneyUtils.showPrice(BigDecimalUtils.multiply(mProductData.getYqRate1(), mProductData.getAmount())) + "元\n"
                + "7天外逾期，每天" + MoneyUtils.showPrice(BigDecimalUtils.multiply(mProductData.getYqRate2(), mProductData.getAmount())) + "元");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCommonDialog != null) {
            mCommonDialog.closeDialog();
            mCommonDialog = null;
        }
    }

    /**
     * 计算所有扣除费用
     *
     * @return
     */
    private String getAllRateMoney(PorductListModel.ListBean mData) {
        if (mData == null) {
            return "";
        }

        BigDecimal deductMoney = BigDecimalUtils.add(mData.getXsAmount(), mData.getLxAmount());//审信费 + 利息

        BigDecimal deductMoney2 = BigDecimalUtils.add(mData.getGlAmount(), mData.getFwAmount());//管理费 + 加服务费

        BigDecimal deductMoneyAll = BigDecimalUtils.add(deductMoney, deductMoney2);//总共需要扣除金额

        return MoneyUtils.showPrice(deductMoneyAll);

    }

}
