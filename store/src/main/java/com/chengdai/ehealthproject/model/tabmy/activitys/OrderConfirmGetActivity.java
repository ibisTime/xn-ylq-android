package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityGetOrdreBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**q确认收获并评价
 * Created by 李先俊 on 2017/6/16.
 */

public class OrderConfirmGetActivity extends AbsStoreBaseActivity {

    private ActivityGetOrdreBinding mBinding;

    private String mOrderCode;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String ordercode){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,OrderConfirmGetActivity.class);
        intent.putExtra("code",ordercode);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_get_ordre, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("确认收货");
        setSubLeftImgState(true);
        if(getIntent()!=null){
            mOrderCode=getIntent().getStringExtra("code");
        }

        mBinding.txtBtn.setOnClickListener(v -> {
            showDoubleWarnListen("确认收货？",view -> {
                confirmGetOrder();
            });
        });
    }

    public void confirmGetOrder(){

        Map<String,String> map=new HashMap<>();
        map.put("code",mOrderCode);
        map.put("updater",SPUtilHelpr.getUserId());
//        map.put("remark",mBinding.edit.getText().toString());
        mSubscription.add(RetrofitUtils.getLoaderServer().confirmGetOrder("808057", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(isSuccessModes -> isSuccessModes!=null && isSuccessModes.isSuccess())
                .subscribe(isSuccessModes -> {
                   showToast("确认收货成功");

                    EventBusModel eventBusModel=new EventBusModel();
                    eventBusModel.setTag("getShopOrderDetailsReqeustEvent_getOrder");
                    EventBus.getDefault().post(eventBusModel);

                    finish();
                },Throwable::printStackTrace));
    }

}
