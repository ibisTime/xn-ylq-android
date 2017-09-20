package com.chengdai.ehealthproject.model.healthstore.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseFragment;
import com.chengdai.ehealthproject.databinding.FragmentShopCommodityBinding;
import com.chengdai.ehealthproject.model.common.model.activitys.WebViewActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.tabmy.fragments.HotelOrderRecordFragment;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopCommodityFragment extends BaseFragment {

    private FragmentShopCommodityBinding mBinding;

    private ShopListModel.ListBean mData;


    private  int mType;//用于判断是积分还是价格


    /**
     * 获得fragment实例
     * @return
     */
    public static ShopCommodityFragment getInstanse(ShopListModel.ListBean data,int type){
        ShopCommodityFragment fragment=new ShopCommodityFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("data",data);
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_shop_commodity, null, false);

        if(getArguments() != null){
            mData=getArguments().getParcelable("data");
            mType=getArguments().getInt("type");
        }

        if(mData != null){

            initBanner();

            initDataShow();
        }


        return mBinding.getRoot();
    }


    /**
     * 初始化显示数据
     */
    private void initDataShow() {

        mBinding.txtName.setText(mData.getName());

        mBinding.txtInfo.setText(mData.getSlogan());

        if(mData.getProductSpecsList()!=null && mData.getProductSpecsList().size()>0){
            if(mType== MyConfig.JFORDER){
                mBinding.txtPrice.setText(StringUtils.showPrice(mData.getProductSpecsList().get(0).getPrice1())+"  积分");
            }else{
                mBinding.txtPrice.setText(StringUtils.getShowPriceSign(mData.getProductSpecsList().get(0).getPrice1()));
            }
            mBinding.txtShichang.setText("市场参考价"+StringUtils.getShowPriceSign(mData.getProductSpecsList().get(0).getOriginalPrice()));

        }

    }

    /**
     * 初始化广告图片
     */
    private void initBanner() {

        List<String>   banaers= StringUtils.splitBannerList(mData.getPic());

        //设置图片集合
        mBinding.banner.setImages(banaers);
        mBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBinding.banner.setIndicatorGravity(BannerConfig.CENTER);

        mBinding.banner.setImageLoader(new GlideImageLoader());
        //banner设置方法全部调用完毕时最后调用
        mBinding.banner.start();
        mBinding.banner.startAutoPlay();
    }

    @Override
    public void onDestroy() {
        if(mBinding.banner!=null){
            mBinding.banner.stopAutoPlay();
        }
        super.onDestroy();
    }
}
