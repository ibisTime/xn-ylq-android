package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.model.pay.PaySucceedInfo;
import com.cdkj.baselibrary.utils.payutils.PayUtil;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySurroundingOrderStateBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.dataadapters.TablayoutAdapter;
import com.chengdai.ehealthproject.model.tabmy.fragments.RechargeInLineFragment;
import com.chengdai.ehealthproject.model.tabmy.fragments.RechargeOnLineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 酒店订单查看
 * Created by 李先俊 on 2017/6/15.
 */

public class RechargeTabActivity extends AbsStoreBaseActivity {

    private ActivitySurroundingOrderStateBinding mBinding;

    /*Tablayout 适配器*/
    private TablayoutAdapter tablayoutAdapter;

    public   static final String CALLPAYTAG="RechargeActivityPay";

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, RechargeTabActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_surrounding_order_state, null, false);

        setTopTitle("充值");

        setSubLeftImgState(true);

        initViewPager();

        addMainView(mBinding.getRoot());

    }

    private void initViewPager() {
        tablayoutAdapter = new TablayoutAdapter(getSupportFragmentManager());
        tablayoutAdapter.addFrag(new RechargeOnLineFragment(), "线上充值");
        tablayoutAdapter.addFrag(new RechargeInLineFragment(), "线下充值");
        mBinding.viewpagerOrder.setAdapter(tablayoutAdapter);
        mBinding.tablayout.setupWithViewPager(mBinding.viewpagerOrder);        //viewpager和tablayout关联
        mBinding.viewpagerOrder.setOffscreenPageLimit(tablayoutAdapter.getCount());
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

    /**
     * 支付回调
     * @param mo
     */
    @Subscribe
    public void PayStateEvent2(String mo){
        if(TextUtils.equals(mo,CALLPAYTAG)){

            showToast("提交成功,请耐心等待审核");

            EventBusModel e=new EventBusModel();
            e.setTag("MyAmountActivityFinish");//结束上一页
            EventBus.getDefault().post(e);

            finish();
        }

    }

}
