package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityJfPayConfirmBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**积分订单确认
 * Created by 李先俊 on 2017/6/12.
 */

public class ShopPayJfConfirmActivity extends AbsStoreBaseActivity {

    private ActivityJfPayConfirmBinding mBinding;

    private String  mStoreCode;

    private boolean isStartMain=true;

    private  ShopListModel.ListBean.ProductSpecsListBean mData;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,ShopListModel.ListBean.ProductSpecsListBean data,String orderCode,boolean isStartMain){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopPayJfConfirmActivity.class);
        intent.putExtra("orderCode",orderCode);
        intent.putExtra("data",data);
        intent.putExtra("isStartMain",isStartMain);//是否打开主页
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_jf_pay_confirm, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_pay));

        setSubLeftImgState(true);

        if(getIntent()!=null){
            mStoreCode=getIntent().getStringExtra("orderCode");
            mData=getIntent().getParcelableExtra("data");
            isStartMain=getIntent().getBooleanExtra("isStartMain",true);
        }

        mBinding.tvJifen.setText(MoneyUtils.showPrice(mData.getPrice1()));

        initViews();

    }




    private void initViews() {

        mBinding.txtPay.setOnClickListener(v -> {
            if(SPUtilHelpr.isLogin(this)){
              payRequest(1);
            }
        });

    }

    private void payRequest(int PayType) {

    if(!SPUtilHelpr.isLogin(this)){
      return;
  }

        Map object=new HashMap();

        ArrayList codeList=new ArrayList();
        codeList.add(mStoreCode);

        object.put("codeList", codeList);
        object.put("payType", PayType);
        object.put("token", SPUtilHelpr.getUserToken());

        yuPay(object);//余额支付

    }

    /**
     * 余额支付
     * @param object
     */
    private void yuPay(Map object) {
        mSubscription.add(RetrofitUtils.getLoaderServer().ShopOrderPay("808052", StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                        if(data != null && data.isSuccess()){
                            payState();
                        }else{
                            showToast("支付失败");
                        }

                },Throwable::printStackTrace));
    }

    /**
     * 支付成功状态
     */
    private void payState() {
        showToast("支付成功");
        if (isStartMain){
            EventBusModel eventBusModel=new EventBusModel();
            eventBusModel.setTag("AllFINISH");
            EventBus.getDefault().post(eventBusModel); //结束掉所有界面
//            MainActivity.open(this,4);
        }

        finish();
    }

}
