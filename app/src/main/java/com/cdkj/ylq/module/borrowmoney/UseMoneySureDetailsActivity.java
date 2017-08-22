package com.cdkj.ylq.module.borrowmoney;

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
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.BigDecimalUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityProductDetailsBinding;
import com.cdkj.ylq.model.CanUseCouponsModel;
import com.cdkj.ylq.model.PorductListModel;
import com.cdkj.ylq.module.api.MyApiServer;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 产品详情 (签约之前)
 * Created by 李先俊 on 2017/8/9.
 */

public class UseMoneySureDetailsActivity extends AbsBaseActivity {

    private ActivityProductDetailsBinding mBinding;

    private PorductListModel.ListBean mProductData;

    private OptionsPickerView mCoupoonsPicker;//优惠券选择

    private List<CanUseCouponsModel> mCoupoonsModels = new ArrayList<>();

    private String mCouponId;//优惠券ID

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, PorductListModel.ListBean productData) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, UseMoneySureDetailsActivity.class);
        intent.putExtra("productData", productData);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_product_details, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("额度使用");

        if (getIntent() != null) {
            mProductData = getIntent().getParcelableExtra("productData");
        }

        mBinding.btnNext.setText("使用额度");

        initListener();

        setShowData(mProductData);

    }


    /**
     * 事件
     */
    private void initListener() {
        mBinding.btnNext.setOnClickListener(v -> {
            SigningSureActivity.open(this, mProductData, mCouponId,  mBinding.tvWillGetMoney.getText().toString());
        });

        mBinding.tvSelectCoupoons.setOnClickListener(v -> {
            getCanUseCoupoons();
        });

        mCoupoonsPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            CanUseCouponsModel cmodel = mCoupoonsModels.get(options1);
            if (cmodel != null) {
                if (!cmodel.isDefuit()) {
                    mBinding.tvSelectCoupoons.setText(MoneyUtils.showPrice(cmodel.getAmount())+"元优惠卷");
                    mCouponId = cmodel.getId();
                    mBinding.tvWillGetMoney.setText(MoneyUtils.showPrice(BigDecimalUtils.add(getWillMoney(mProductData), cmodel.getAmount())) + "元");//实际到账
                } else {
                    mBinding.tvSelectCoupoons.setText("选择优惠券");
                    mCouponId = "";
                    mBinding.tvWillGetMoney.setText(MoneyUtils.showPrice(getWillMoney(mProductData)) + "元");//实际到账
                }
            }


        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();

    }

    /**
     * 获取可用优惠券
     */
    private void getCanUseCoupoons() {
        if (mProductData == null || mProductData.getAmount()==null) return;
        Map<String, String> map = new HashMap<>();
        map.put("amount", mProductData.getAmount().intValue()+"");
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

    @Subscribe
    public void EventMoth(String tag) {
        if (TextUtils.equals(tag, EventTags.USEMONEYSUREFINISH)) {
            finish();
        }
    }


}
