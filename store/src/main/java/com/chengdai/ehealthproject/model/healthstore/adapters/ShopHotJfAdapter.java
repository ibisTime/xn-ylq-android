package com.chengdai.ehealthproject.model.healthstore.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthstore.models.PayCarListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/19.
 */

public class ShopHotJfAdapter extends CommonAdapter<ShopListModel.ListBean> {

    public ShopHotJfAdapter(Context context, List<ShopListModel.ListBean> datas) {
        super(context, R.layout.item_hot_jf, datas);
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

        ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+item.getSplitAdvPic(),img_jf_hot);

        tv_name_jf.setText(item.getName());

        tv_slogan_jf.setText(item.getSlogan());

        if(item.getProductSpecsList() !=null && item.getProductSpecsList().get(0)!=null){
            tv_jf_number.setText(StringUtils.showJF(item.getProductSpecsList().get(0).getPrice1()));
            tv_price.setText("市场参考价:"+StringUtils.getShowPriceSign(item.getProductSpecsList().get(0).getOriginalPrice()));
        }


    }

}
