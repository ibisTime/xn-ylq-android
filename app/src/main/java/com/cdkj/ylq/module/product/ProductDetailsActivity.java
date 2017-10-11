package com.cdkj.ylq.module.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BigDecimalUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityProductDetailsBinding;
import com.cdkj.ylq.model.CanUseCouponsModel;
import com.cdkj.ylq.model.PorductListModel;
import com.cdkj.ylq.model.ProductSingModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.borrowmoney.PutMoneyingActivity;
import com.cdkj.ylq.module.certification.review.HumanReviewActivity;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.EventTags.BORROWMONEYFRAGMENTREFRESH;

/**
 * 产品详情
 * Created by 李先俊 on 2017/8/9.
 */

public class ProductDetailsActivity extends AbsBaseActivity {

    private ActivityProductDetailsBinding mBinding;

    private PorductListModel.ListBean mProductData;

    private OptionsPickerView mCoupoonsPicker;//优惠券选择

    private List<CanUseCouponsModel> mCoupoonsModels = new ArrayList<>();

    private String mCode;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("code", code);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_product_details, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        hideAllNoTitle();

        setSubLeftImgState(true);

        setTopTitle("产品详情");

        if (getIntent() != null) {
            mProductData = getIntent().getParcelableExtra("productData");
            mCode = getIntent().getStringExtra("code");
        }

        initListener();

        setShowData(mProductData);

        getProductDetail();

    }


    /**
     * 事件
     */
    private void initListener() {
        mBinding.btnNext.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(this, false)) {
                return;
            }
            applyRequest();
        });

        mBinding.tvSelectCoupoons.setOnClickListener(v -> {
            if (!SPUtilHelpr.isLogin(this, false)) {
                return;
            }
            getCanUseCoupoons();
        });

        //选择优惠券 这里只做优惠券展示 真正使用优惠券是在 签约界面 SigningSureActivity
        mCoupoonsPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            CanUseCouponsModel cmodel = mCoupoonsModels.get(options1);
            if (cmodel != null) {
                if (!cmodel.isDefuit()) {
                    mBinding.tvSelectCoupoons.setText(MoneyUtils.showPrice(cmodel.getAmount()) + "元优惠卷");
                    mBinding.tvWillGetMoney.setText(MoneyUtils.showPrice(BigDecimalUtils.add(getWillMoney(mProductData), cmodel.getAmount())) + "元");//实际到账
                } else {
                    mBinding.tvSelectCoupoons.setText("选择优惠券");
                    mBinding.tvWillGetMoney.setText(MoneyUtils.showPrice(getWillMoney(mProductData)) + "元");//实际到账
                }
            }

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();

    }

    /**
     * 获取可用优惠券
     */
    private void getCanUseCoupoons() {
        if (mProductData == null || mProductData.getAmount() == null) return;
        Map<String, String> map = new HashMap<>();
        map.put("amount", mProductData.getAmount().intValue() + "");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getCanUseCouponsListData("623148", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<CanUseCouponsModel>(this) {

            @Override
            protected void onSuccess(List<CanUseCouponsModel> data, String SucMessage) {
                mCoupoonsModels = data;
                if (mCoupoonsPicker == null || mCoupoonsModels == null) return;

                if (mCoupoonsModels.size() == 0) {
                    showToast("暂无可用优惠券");
                    return;
                }
                CanUseCouponsModel model = new CanUseCouponsModel();
                model.setDefuit(true);
                mCoupoonsModels.add(0, model);
                mCoupoonsPicker.setPicker(mCoupoonsModels);
                mCoupoonsPicker.show();

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    public void getProductDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getProductDetail("623011", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<PorductListModel.ListBean>(ProductDetailsActivity.this) {
            @Override
            protected void onSuccess(PorductListModel.ListBean data, String SucMessage) {
                mProductData = data;
                setShowData(mProductData);
            }

            @Override
            protected void onFinish() {
                showContentView();
                disMissLoading();
            }
        });

    }


    /**
     * 设置要显示的数据
     */
    private void setShowData(PorductListModel.ListBean mData) {

        if (mData == null) {
            return;
        }

        mBinding.tvMoney.setText(MoneyUtils.showPrice(mData.getAmount()));
        mBinding.tvRepayMoney.setText(MoneyUtils.showPrice(mData.getAmount()) + "元");

        mBinding.tvKsCheckMoney.setText("快速信审费:" + MoneyUtils.showPrice(mData.getXsAmount()) + "元");//快速信审费
        mBinding.tvInterestMoney.setText("利息:" + MoneyUtils.showPrice(mData.getLxAmount()) + "元");
        mBinding.tvAccountManagerMoney.setText("账户管理费:" + MoneyUtils.showPrice(mData.getGlAmount()) + "元");
        mBinding.tvServiceMoney.setText("服务费:" + MoneyUtils.showPrice(mData.getFwAmount()) + "元");

        mBinding.tvWillGetMoney.setText(MoneyUtils.showPrice(getWillMoney(mProductData)) + "元");//实际到账

        mBinding.tvMoneyDay.setText(mData.getDuration() + "天");
    }

    /**
     * 计算实际到账
     *
     * @return
     */
    private BigDecimal getWillMoney(PorductListModel.ListBean mData) {
        if (mData == null) {
            return new BigDecimal(0);
        }

        BigDecimal deductMoney = BigDecimalUtils.add(mData.getXsAmount(), mData.getLxAmount());//审信费 + 利息

        BigDecimal deductMoney2 = BigDecimalUtils.add(mData.getGlAmount(), mData.getFwAmount());//管理费 + 加服务费

        BigDecimal deductMoneyAll = BigDecimalUtils.add(deductMoney, deductMoney2);//总共需要扣除金额

        BigDecimal money4 = BigDecimalUtils.subtract(mData.getAmount(), deductMoneyAll);    //总额 - 扣除费用 = 实际到账费用

        return money4;

    }


    /**
     * 额度申请
     */
    private void applyRequest() {
        if (mProductData == null) return;
        Map<String, String> map = new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("productCode", mProductData.getCode());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getProductSingState("623020", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();
//1 认证中 2待审核
        call.enqueue(new BaseResponseModelCallBack<ProductSingModel>(this) {
            @Override
            protected void onSuccess(ProductSingModel data, String SucMessage) {
                EventBus.getDefault().post(BORROWMONEYFRAGMENTREFRESH);//刷新产品列表数据
                if (TextUtils.equals(data.getStatus(), "1")) {
                    EventBusModel eventBusModel = new EventBusModel();
                    eventBusModel.setTag(EventTags.MAINCHANGESHOWINDEX);
                    CommonDialog commonDialog = new CommonDialog(ProductDetailsActivity.this).builder()
                            .setTitle("提示").setContentMsg("您的信息未认证，请先完成认证。")
                            .setPositiveBtn("确定", view -> {
                                eventBusModel.setEvInt(MainActivity.SHOWCERT); //显示认证界面
                                EventBus.getDefault().post(eventBusModel);
                                EventBus.getDefault().post(EventTags.AllFINISH);//结束上一个界面
                                finish();
                            });
                    commonDialog.show();

                } else if (TextUtils.equals(data.getStatus(), "2")) {
                    HumanReviewActivity.open(ProductDetailsActivity.this);
                    finish();
                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }
}
