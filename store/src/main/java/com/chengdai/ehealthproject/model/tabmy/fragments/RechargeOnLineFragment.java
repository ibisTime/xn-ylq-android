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
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.ActivityRechargeBinding;
import com.chengdai.ehealthproject.model.tabmy.activitys.RechargeTabActivity;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.payutils.PayUtil;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**线上充值
 * Created by 李先俊 on 2017/8/29.
 */

public class RechargeOnLineFragment extends BaseLazyFragment {
    private ActivityRechargeBinding mBinding;
    private int mPayType;//支付方式
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.activity_recharge, null, false);


        initPayTypeSelectState();

        //限制金额数据
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

        mBinding.txtPay.setOnClickListener(v -> {

            if(TextUtils.isEmpty(mBinding.edtPrice.getText().toString())){
                ToastUtil.show(mActivity,"请输入充值金额");
                return;
            }

            if(new BigDecimal(mBinding.edtPrice.getText().toString()).doubleValue()<=0){
                ToastUtil.show(mActivity,"金额必须大于0");
                return;
            }

            if(mPayType==PayUtil.ALIPAY){  //支付宝充值
                rechargeAliRequest();
            }else{
                rechargeWXRequest();//微信充值
            }
        });

        return mBinding.getRoot();
    }



    /**
     * 微信充值请求
     */
    private void rechargeWXRequest() {
        Map<String,String> map=new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("channelType","36");//36微信app支付 30支付宝支付
        map.put("amount", StringUtils.getRequestPrice(mBinding.edtPrice.getText().toString()));
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().wxPayRequest("802710",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mActivity))
                .subscribe(model -> {
                    PayUtil.callWXPay(mActivity,model, RechargeTabActivity.CALLPAYTAG);
                },Throwable::printStackTrace));

    }

    /**
     * 支付宝   充值请求
     */
    private void rechargeAliRequest() {
        Map<String,String> map=new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("channelType","30");//36微信app支付 30支付宝支付
        map.put("amount",StringUtils.getRequestPrice(mBinding.edtPrice.getText().toString()));
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().rechargeRequest("802710",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mActivity))
                .subscribe(model -> {
                    PayUtil.callAlipay(mActivity,model.getSignOrder(),RechargeTabActivity.CALLPAYTAG);

                },Throwable::printStackTrace));

    }

    /**
     * 初始化支付方式选择状态
     */
    private void initPayTypeSelectState() {
        ImgUtils.loadImgId(mActivity,R.mipmap.pay_select,mBinding.imgWeixin);
        ImgUtils.loadImgId(mActivity,R.mipmap.un_select,mBinding.imgZhifubao);

        mPayType=PayUtil.WEIXINPAY;

        mBinding.linWeipay.setOnClickListener(v -> {
            mPayType=PayUtil.WEIXINPAY;
            ImgUtils.loadImgId(mActivity,R.mipmap.pay_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(mActivity,R.mipmap.un_select,mBinding.imgZhifubao);
        });

        mBinding.linZhifubao.setOnClickListener(v -> {
            mPayType=PayUtil.ALIPAY;
            ImgUtils.loadImgId(mActivity,R.mipmap.un_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(mActivity,R.mipmap.pay_select,mBinding.imgZhifubao);
        });

    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }





}
