package com.chengdai.ehealthproject.model.healthstore.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;


import java.util.List;

/**
 * Created by 李先俊 on 2017/6/12.
 */

public class ShopTypeListAdapter extends CommonAdapter<ShopListModel.ListBean> {


    public ShopTypeListAdapter(Context context, List<ShopListModel.ListBean> datas) {
        super(context, R.layout.item_shop_list, datas);
    }

    public void setData(List<ShopListModel.ListBean> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }
    }

    public void addData(List<ShopListModel.ListBean> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }


    @Override
    protected void convert(ViewHolder viewHolder, ShopListModel.ListBean item, int position) {

        ImageView  imgTitle=viewHolder.getView(R.id.img_shop_title);
        TextView   tvTitle=viewHolder.getView(R.id.tv_store_title);
        TextView   tvSlogan=viewHolder.getView(R.id.tv_slogan);
        TextView   tvPrice=viewHolder.getView(R.id.tv_price);
        TextView   tv_price_reference=viewHolder.getView(R.id.tv_price_reference);

        if(item != null){

            ImgUtils.loadImgURL(mContext, MyConfigStore.IMGURL+ item.getSplitAdvPic(),imgTitle);

            tvTitle.setText(item.getName());

            tvTitle.setText(item.getName());

            tvSlogan.setText(item.getSlogan());

            if(item.getProductSpecsList()!=null && item.getProductSpecsList().size()>0){
                tvPrice.setText(mContext.getString(R.string.price_sing)+ MoneyUtils.showPrice(item.getProductSpecsList().get(0).getPrice1()));
                tv_price_reference.setText("市场参考价:"+mContext.getString(R.string.price_sing)+MoneyUtils.showPrice(item.getProductSpecsList().get(0).getOriginalPrice()));
            }


//            tvPrice.setText(item.getP);

        }

    }
}
