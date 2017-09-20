package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySurroundingOrderStateBinding;
import com.chengdai.ehealthproject.model.dataadapters.TablayoutAdapter;
import com.chengdai.ehealthproject.model.healthstore.fragments.ShopOrderRecordFragment;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;

/**商城订单查看
 * Created by 李先俊 on 2017/6/15.
 */

public class ShopOrderStateLookActivity extends AbsStoreBaseActivity {

    private ActivitySurroundingOrderStateBinding mBinding;

    /*Tablayout 适配器*/
    private TablayoutAdapter tablayoutAdapter;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,ShopOrderStateLookActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_surrounding_order_state, null, false);

        setTopTitle("我的订单");

        setSubLeftImgState(true);

        initViewPager();

        addMainView(mBinding.getRoot());

    }

    private void initViewPager() {
        tablayoutAdapter=new TablayoutAdapter(getSupportFragmentManager());
        tablayoutAdapter.addFrag(ShopOrderRecordFragment.getInstanse(""), "全部");
        tablayoutAdapter.addFrag(ShopOrderRecordFragment.getInstanse(MyConfigStore.ORDERTYPEWAITPAY), "待付款");
        tablayoutAdapter.addFrag(ShopOrderRecordFragment.getInstanse(MyConfigStore.ORDERTYPEWAITFAHUO), "待发货");
        tablayoutAdapter.addFrag(ShopOrderRecordFragment.getInstanse(MyConfigStore.ORDERTYPEWAITSHOUHUO), "待收货");
        mBinding.viewpagerOrder.setOffscreenPageLimit(tablayoutAdapter.getCount());
        mBinding.viewpagerOrder.setAdapter(tablayoutAdapter);
        mBinding.tablayout.setupWithViewPager(mBinding.viewpagerOrder);        //viewpager和tablayout关联
    }
}
