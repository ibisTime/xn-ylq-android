package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityRechargeBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.pay.PaySucceedInfo;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.payutils.PayUtil;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**充值
 * Created by 李先俊 on 2017/6/29.
 */

public class RechargeActivity extends AbsStoreBaseActivity {

    private ActivityRechargeBinding mBinding;

    private int mPayType;//支付方式

    private  static final String CALLPAYTAG="RechargeActivityPay";

    /**
     *
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,RechargeActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_recharge, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("充值");
        setSubLeftImgState(true);

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
                showToast("请输入充值金额");
                return;
            }

            if(new BigDecimal(mBinding.edtPrice.getText().toString()).doubleValue()<=0){
                showToast("金额必须大于0");
                return;
            }

            if(mPayType==PayUtil.ALIPAY){  //支付宝充值
                rechargeAliRequest();
            }else{
                rechargeWXRequest();//微信充值
            }
        });
    }

    /**
     * 微信充值请求
     */
    private void rechargeWXRequest() {
        Map<String,String> map=new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("channelType","36");//36微信app支付 30支付宝支付
        map.put("amount",StringUtils.getRequestPrice(mBinding.edtPrice.getText().toString()));
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().wxPayRequest("802710",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(model -> {
                    PayUtil.callWXPay(this,model,CALLPAYTAG);
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
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(model -> {
                    PayUtil.callAlipay(this,model.getSignOrder(),CALLPAYTAG);

                },Throwable::printStackTrace));

    }

    /**
     * 初始化支付方式选择状态
     */
    private void initPayTypeSelectState() {
        ImgUtils.loadImgId(RechargeActivity.this,R.mipmap.pay_select,mBinding.imgWeixin);
        ImgUtils.loadImgId(RechargeActivity.this,R.mipmap.un_select,mBinding.imgZhifubao);

        mPayType=PayUtil.WEIXINPAY;

        mBinding.linWeipay.setOnClickListener(v -> {
            mPayType=PayUtil.WEIXINPAY;
            ImgUtils.loadImgId(RechargeActivity.this,R.mipmap.pay_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(RechargeActivity.this,R.mipmap.un_select,mBinding.imgZhifubao);
        });

        mBinding.linZhifubao.setOnClickListener(v -> {
            mPayType=PayUtil.ALIPAY;
            ImgUtils.loadImgId(RechargeActivity.this,R.mipmap.un_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(RechargeActivity.this,R.mipmap.pay_select,mBinding.imgZhifubao);
        });

    }

    /**
     * 支付回调
     * @param mo
     */
    @Subscribe
    public void PayStateEvent(PaySucceedInfo mo){
        if(mo == null || !TextUtils.equals(mo.getTag(),CALLPAYTAG)){
            return;
        }

        if(mo.getCallType() == PayUtil.ALIPAY && mo.isPaySucceed()){   //支付宝支付
            showToast("充值成功");

            EventBusModel e=new EventBusModel();
            e.setTag("MyAmountActivityFinish");//结束上一页
            EventBus.getDefault().post(e);

            finish();
        }else if(mo.getCallType() == PayUtil.WEIXINPAY && mo.isPaySucceed()){//微信支付

            showToast("充值成功");

            EventBusModel e=new EventBusModel();
            e.setTag("MyAmountActivityFinish");//结束上一页
            EventBus.getDefault().post(e);

            finish();
        }
    }

}
