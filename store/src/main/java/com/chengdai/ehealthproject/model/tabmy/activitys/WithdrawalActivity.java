package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityWithdrawalBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.tabmy.model.BankCardModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**提现
 * Created by 李先俊 on 2017/6/29.
 */

public class WithdrawalActivity extends AbsStoreBaseActivity {

    private ActivityWithdrawalBinding mBinding;

    private  BankCardModel mBankCardModel;//选择的银行卡数据

    private String mAmount;//余额

    private String mAccountNumber;


    /**
     *
     * @param context
     * @param amount//账户余额
     */
    public static void open(Context context, String amount,String accountNumber){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,WithdrawalActivity.class);
        intent.putExtra("amount",amount);
        intent.putExtra("amountnumber",accountNumber);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_withdrawal, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("提现");
        setSubLeftImgState(true);
        if(getIntent()!=null){
            mAmount=getIntent().getStringExtra("amount");
            mAccountNumber=getIntent().getStringExtra("amountnumber");
        }
        mBinding.tvNum.setText(getString(R.string.price_sing)+mAmount);
        initViews();
        getRechargeNum();
    }

    private void initViews() {

        mBinding.layoutBankCard.setOnClickListener(v -> {
            BackCardListActivity.open(this,true);
        });

        mBinding.txtPay.setOnClickListener(v -> {

            if(mBankCardModel==null){
                showToast("请选择银行卡");
                return;
            }


            if(TextUtils.isEmpty(mBinding.editPrice.getText().toString())){
                showToast("请输入提现金额");
                return;
            }

            Integer price=Integer.valueOf(mBinding.editPrice.getText().toString().trim());

            if(price<=0 ){
                showToast("提现金额不能小于0");
                return;
            }

            if(TextUtils.isEmpty(mBinding.editPayPwd.getText().toString())){
                showToast("请输入支付密码");
                return;
            }

            withdrawalRequest();


        });

    }

    /**
     *
     */
    private void getRechargeNum() {

        Map<String, String> map = new HashMap<>();

        map.put("key","CUSERQXBS");
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("companyCode", MyConfigStore.COMPANYCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().getRechargeNum("802027", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(null))
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s.getCvalue())) {
                        mBinding.editPrice.setHint("提现金额必须是"+s.getCvalue()+"的整数倍");
                    }
                }, Throwable::printStackTrace));

    }


    //提现请求
    private void withdrawalRequest() {

        Map<String,String> object=new HashMap<>();
        object.put("systemCode", MyConfigStore.SYSTEMCODE);
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("accountNumber", mAccountNumber);
//        object.put("amount",new BigDecimal(StringUtils.doubleFormatMoney2(new BigDecimal(mBinding.editPrice.getText().toString()).doubleValue()*1000)).intValue()+"");
        object.put("amount", MoneyUtils.getRequestPrice(mBinding.editPrice.getText().toString()));
        object.put("payCardNo", mBankCardModel.getBankcardNumber());
        // 开户行
        object.put("payCardInfo", mBankCardModel.getBankName());
        object.put("applyUser",SPUtilHelpr.getUserId());
        object.put("tradePwd", mBinding.editPayPwd.getText().toString());

       mSubscription.add( RetrofitUtils.getLoaderServer().withdrawalRequest("802750",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(s -> {
                    EventBusModel e=new EventBusModel();
                    e.setTag("MyAmountActivityFinish");//结束上一页
                    EventBus.getDefault().post(e);

                    finish();
                },Throwable::printStackTrace));


    }


    @Subscribe
    public void WithdrawalActivityEvent(BankCardModel bankCardModel){
        if(bankCardModel == null)return;
        mBankCardModel=bankCardModel;
        mBinding.txtBankCard.setText(bankCardModel.getBankcardNumber());

    }

}
