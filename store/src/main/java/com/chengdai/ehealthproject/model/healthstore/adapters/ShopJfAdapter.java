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
 * Created by 李先俊 on 2017/6/19.
 */

public class ShopJfAdapter extends CommonAdapter<ShopListModel.ListBean> {

    public ShopJfAdapter(Context context, List<ShopListModel.ListBean> datas) {
        super(context, R.layout.item_jf_change, datas);
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

        if(item == null){
            return;
        }

        ImageView img_jf_hot=viewHolder.getView(R.id.img_jf_hot);
        TextView tv_name_jf=viewHolder.getView(R.id.tv_name_jf);
        TextView tv_slogan_jf=viewHolder.getView(R.id.tv_slogan_jf);
        TextView tv_jf_number=viewHolder.getView(R.id.tv_jf_number);
        TextView tv_price=viewHolder.getView(R.id.tv_price);

        ImgUtils.loadImgURL(mContext, MyConfigStore.IMGURL+item.getSplitAdvPic(),img_jf_hot);

        tv_name_jf.setText(item.getName());

        tv_slogan_jf.setText(item.getSlogan());

        if(item.getProductSpecsList() !=null && item.getProductSpecsList().get(0)!=null){
            tv_jf_number.setText(MoneyUtils.showPrice(item.getProductSpecsList().get(0).getPrice1()));
            tv_price.setText(MoneyUtils.getShowPriceSign(item.getProductSpecsList().get(0).getOriginalPrice()));
        }


    }

}
