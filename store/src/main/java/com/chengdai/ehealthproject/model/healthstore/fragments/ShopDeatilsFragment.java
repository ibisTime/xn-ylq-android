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
import com.chengdai.ehealthproject.databinding.FragmentShopTabDetailsBinding;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.chengdai.ehealthproject.weigit.appmanager.AppOhterManager;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopDeatilsFragment extends BaseFragment {

    private FragmentShopTabDetailsBinding mBinding;

    private ShopListModel.ListBean mData;

    /**
     * 获得fragment实例
     * @return
     */
    public static ShopDeatilsFragment getInstanse(ShopListModel.ListBean data){
        ShopDeatilsFragment fragment=new ShopDeatilsFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("data",data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_shop_tab_details, null, false);

        if(getArguments() != null){
            mData=getArguments().getParcelable("data");
        }

        if(mData != null && !TextUtils.isEmpty(mData.getDescription())){
            AppOhterManager.showRichText(mActivity,mBinding.tvDetails,mData.getDescription());
        }

        return mBinding.getRoot();
    }

}
