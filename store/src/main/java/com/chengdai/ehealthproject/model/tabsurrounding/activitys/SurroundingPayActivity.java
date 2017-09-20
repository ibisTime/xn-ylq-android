package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityPayStoreBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.pay.PaySucceedInfo;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.uitls.payutils.PayUtil;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 周边支付
 * Created by 李先俊 on 2017/6/12.
 */

public class SurroundingPayActivity extends AbsStoreBaseActivity {

    private ActivityPayStoreBinding mBinding;

    private Double rate;//折扣

    private String mStoreCode;

    private Double mDiscountMoney = 0.0;

    private int mPayType = 1;

    private static final String CALLPAYTAG = "SurroundingPayConfirmAcitivty";

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, Double rate, String storeCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, SurroundingPayActivity.class);
        intent.putExtra("rate", rate);
        intent.putExtra("storeCode", storeCode);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pay_store, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_pay));

        setSubLeftImgState(true);

        if (getIntent() != null) {
            mStoreCode = getIntent().getStringExtra("storeCode");
            rate = getIntent().getDoubleExtra("rate", 0);
        }

        initViews();
        getAmountRequest();
        initPayTypeSelectState();

    }

    /**
     * 初始化支付方式选择状态
     */
    private void initPayTypeSelectState() {

        ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.pay_select, mBinding.imgBalace);
        ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.un_select, mBinding.imgWeixin);
        ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.un_select, mBinding.imgZhifubao);

        mBinding.linBalace.setOnClickListener(v -> {
            mPayType = 1;
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.pay_select, mBinding.imgBalace);
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.un_select, mBinding.imgWeixin);
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.un_select, mBinding.imgZhifubao);
        });
        mBinding.linWeipay.setOnClickListener(v -> {
            mPayType = 2;
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.un_select, mBinding.imgBalace);
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.pay_select, mBinding.imgWeixin);
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.un_select, mBinding.imgZhifubao);
        });

        mBinding.linZhifubao.setOnClickListener(v -> {
            mPayType = 3;
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.un_select, mBinding.imgBalace);
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.un_select, mBinding.imgWeixin);
            ImgUtils.loadImgId(SurroundingPayActivity.this, R.mipmap.pay_select, mBinding.imgZhifubao);
        });


    }


    private void initViews() {

        mBinding.edtPrice.setFilters(new InputFilter[]{new InputFilter() {
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

        mBinding.tvRate.setText((int) (rate * 10) + "折");

        mBinding.txtPay.setOnClickListener(v -> {
            if (SPUtilHelpr.isLogin(this)) {

                if (TextUtils.isEmpty(mBinding.edtPrice.getText().toString())) {
                    showToast("请输入消费金额");
                    return;
                }

                if (new BigDecimal(mBinding.edtPrice.getText().toString()).doubleValue() <= 0) {
                    showToast("金额必须大于0.01");
                    return;
                }

                payRequest(mPayType);
            }
        });

        RxTextView.textChanges(mBinding.edtPrice).subscribe(charSequence -> {
            if (!TextUtils.isEmpty(charSequence.toString())) {

                mDiscountMoney = (Double.valueOf(charSequence.toString()));

                mBinding.txtDiscountMoney.setText(StringUtils.doubleFormatMoney3(mDiscountMoney * rate) + "");

            } else {
                mDiscountMoney = 0.00;
                mBinding.txtDiscountMoney.setText("0.00");
            }

        });
    }

    private void payRequest(int PayType) {
/*// 用户编号（必填）
    private String userId;

    // 商家编号（必填）
    private String storeCode;

    // 消费金额（必填）
    private String amount;

    // 支付类型（必填） 1-余额支付  2-微信APP支付 3-支付宝APP支付
    private String payType;*/

        if (!SPUtilHelpr.isLogin(this)) {
            return;
        }

        Map map = new HashMap();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("storeCode", mStoreCode);
        map.put("amount", StringUtils.getRequestPrice(mDiscountMoney));
        map.put("payType", PayType);

        switch (PayType) {
            case 1:
                yuPay(map);//余额支付
                break;
            case 2:
                wxPay(map);//微信APP支付
                break;
            case 3://支付宝支付
                aliPay(map);
                break;
        }

    }

    /**
     * 获取余额请求
     */
    private void getAmountRequest() {

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("currency", "CNY");
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().getAmount("802503", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(null))
                .filter(r -> r != null && r.size() > 0 && r.get(0) != null)
                .subscribe(r -> {
                    mBinding.txtBalaceEnd.setText("(余额：" + getString(R.string.price_sing) + StringUtils.showPrice(r.get(0).getAmount()) + ")");
                }, Throwable::printStackTrace));
    }

    private void wxPay(Map map) {
        mSubscription.add(RetrofitUtils.getLoaderServer().wxPayRequest("808271", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                    PayUtil.callWXPay(SurroundingPayActivity.this, data, CALLPAYTAG);
                }, Throwable::printStackTrace));

    }

    /**
     * 余额支付
     *
     * @param map
     */
    private void yuPay(Map map) {

        mSubscription.add(RetrofitUtils.getLoaderServer().SurroundingPay("808271", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                    if (TextUtils.isEmpty(data)) {
                        showToast("支付失败");
                    } else {
                        payState();
                    }

                }, Throwable::printStackTrace));

    }

    /**
     * 支付宝支付
     *
     * @param map
     */
    private void aliPay(Map map) {

        mSubscription.add(RetrofitUtils.getLoaderServer().SurroundingAliPay("808271", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(data -> data != null)
                .subscribe(data -> {
                    PayUtil.callAlipay(this, data.getSignOrder(), CALLPAYTAG);
                }, Throwable::printStackTrace));

    }


    /**
     * 支付回调
     *
     * @param mo
     */
    @Subscribe
    public void AliPayState(PaySucceedInfo mo) {
        if (mo == null || !TextUtils.equals(mo.getTag(), CALLPAYTAG)) {
            return;
        }

        if (mo.getCallType() == PayUtil.ALIPAY && mo.isPaySucceed()) {
            payState();
        } else if (mo.getCallType() == PayUtil.WEIXINPAY && mo.isPaySucceed()) {
            payState();
        }
    }

    /**
     * 支付成功状态
     */
    private void payState() {
        showToast("支付成功");
        EventBusModel eventBusModel = new EventBusModel();
        eventBusModel.setTag("AllFINISH");
        EventBus.getDefault().post(eventBusModel); //结束掉所有界面
//        MainActivity.open(this,3);//显示周边

        finish();
    }


}
