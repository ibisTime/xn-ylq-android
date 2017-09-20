package com.chengdai.ehealthproject.model.common.model.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityAddressSelectBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.adapters.AddressAdapter;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**收货地址选择
 * Created by 李先俊 on 2017/6/17.
 */

public class AddressSelectActivity extends AbsStoreBaseActivity {

    private ActivityAddressSelectBinding mBinding;

    private AddressAdapter mAdapter;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,AddressSelectActivity.class);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_address_select, null, false);

        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);

        setTopTitle("选择地址");

        mAdapter=new AddressAdapter(this,new ArrayList<>());
        mBinding.listAddress.setAdapter(mAdapter);


        getAddressRequst();

        mBinding.txtAdd.setOnClickListener(v -> {

            if(mAdapter.getCount()==0){
                AddAddressActivity.open(this,true,null);
            }else{
                AddAddressActivity.open(this,false,null);
            }
        });



    }

    /**
     * 请求收获地址
     */

    private void getAddressRequst(){

        Map<String,String> map=new HashMap<>();

        map.put("userId",SPUtilHelpr.getUserId());
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().GetAddress("805165", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .filter(data-> data!=null)
                .subscribe(data ->{
                    mAdapter.setData(data);

                },Throwable::printStackTrace));

    }


    //AddressAdapter AddAddressActivity 刷新数据
    @Subscribe
    public  void getAddressRequestEvent(EventBusModel model){
        if(model != null && "AddressSelectRefesh" .equals(model.getTag())){
            getAddressRequst();
        }
    }


}
