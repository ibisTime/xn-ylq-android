package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityShopJfdetailsBinding;
import com.chengdai.ehealthproject.model.dataadapters.ViewPagerAdapter;
import com.chengdai.ehealthproject.model.healthstore.fragments.ShopCommodityFragment;
import com.chengdai.ehealthproject.model.healthstore.fragments.ShopDeatilsFragment;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopJfDetailsActivity extends AbsStoreBaseActivity {

    private ActivityShopJfdetailsBinding mBinding;

    private ShopListModel.ListBean mData;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, ShopListModel.ListBean data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopJfDetailsActivity.class);
        intent.putExtra("data",data);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_jfdetails, null, false);
        hintTitleView();
        addMainView(mBinding.getRoot());

        if(getIntent() != null) mData=getIntent().getParcelableExtra("data");

        initViews();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
  /*      if(shopPayPopup !=null){
            shopPayPopup=null;
        }
        if(addPayCarPopup !=null){
            addPayCarPopup=null;
        }*/
    }

    /**
     * 初始化View
     */
    private void initViews() {
        //返回
        mBinding.layoutBack.setOnClickListener(v -> {
            finish();
        });
       //商品
        mBinding.txtCommodity.setOnClickListener(v -> {
            setTopTabTitleShowState(1);
            mBinding.viewpager.setCurrentItem(0);

        });
       //详情
        mBinding.txtDetail.setOnClickListener(v -> {
            setTopTabTitleShowState(2);
            mBinding.viewpager.setCurrentItem(1);
        });
        //评价
        mBinding.txtEvaluate.setOnClickListener(v -> {
            setTopTabTitleShowState(3);
            mBinding.viewpager.setCurrentItem(2);
        });


        //打开购物车

        mBinding.layoutPayCar.setOnClickListener(v -> {

            if(!SPUtilHelpr.isLogin(this)){
                return;
            }

            ShopPayCarSelectActivity.open(this);
        });

        mBinding.tvChange.setOnClickListener(v -> {
            if(mData!=null && mData.getProductSpecsList()!=null && mData.getProductSpecsList().size()>0){

                if(!SPUtilHelpr.isLogin(this)){
                    return;
                }

                ShopPayJfActivity.open(this,mData.getProductSpecsList().get(0),1,MyConfig.IMGURL+mData.getSplitAdvPic());
            }
        });

        initViewPager();
    }


    /**
     * 初始化ViewPgaer
     */
    private void initViewPager() {

        List<Fragment> fragments=new ArrayList<>();
        fragments.add(ShopCommodityFragment.getInstanse(mData, MyConfig.JFORDER));
        fragments.add(ShopDeatilsFragment.getInstanse(mData));
//        fragments.add(ShopEvaluateFragment.getInstanse(mData));
        mBinding.viewpager.setOffscreenPageLimit(3);
        mBinding.viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments));


        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               setTopTabTitleShowState(position+1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置顶部tab状态
     *
     */
    private void setTopTabTitleShowState(int state) {

        switch (state){
            case 1:
                mBinding.txtDetail.setTextColor(ContextCompat.getColor(this, R.color.white));
                mBinding.txtEvaluate.setTextColor(ContextCompat.getColor(this,R.color.white));
                mBinding.txtCommodity.setTextColor(ContextCompat.getColor(this,R.color.orange));

                mBinding.lineCommodity.setVisibility(View.VISIBLE);
                mBinding.lineDetail.setVisibility(View.INVISIBLE);
                mBinding.lineEvaluate.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mBinding.txtCommodity.setTextColor(ContextCompat.getColor(this,R.color.white));
                mBinding.txtEvaluate.setTextColor(ContextCompat.getColor(this,R.color.white));
                mBinding.txtDetail.setTextColor(ContextCompat.getColor(this,R.color.orange));

                mBinding.lineCommodity.setVisibility(View.INVISIBLE);
                mBinding.lineDetail.setVisibility(View.VISIBLE);
                mBinding.lineEvaluate.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mBinding.txtCommodity.setTextColor(ContextCompat.getColor(this,R.color.white));
                mBinding.txtDetail.setTextColor(ContextCompat.getColor(this,R.color.white));
                mBinding.txtEvaluate.setTextColor(ContextCompat.getColor(this,R.color.orange));

                mBinding.lineCommodity.setVisibility(View.INVISIBLE);
                mBinding.lineDetail.setVisibility(View.INVISIBLE);
                mBinding.lineEvaluate.setVisibility(View.VISIBLE);

                break;
        }

    }
}
