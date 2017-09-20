package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityShopAllOrderLookBinding;

/**
 * Created by 李先俊 on 2017/6/19.
 */

public class ShopAllOrderLookActivity  extends AbsStoreBaseActivity {


    private ActivityShopAllOrderLookBinding mBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopAllOrderLookActivity.class);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_all_order_look, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("商城订单");

        setSubLeftImgState(true);

        mBinding.tvJfOrder.setOnClickListener(v -> {
            ShopJfOrderStateLookActivity.open(this);
        });

        mBinding.tvOrder.setOnClickListener(v -> {
            ShopOrderStateLookActivity.open(this);
        });

    }
}
