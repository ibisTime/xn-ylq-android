package com.chengdai.ehealthproject.model.healthstore.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthstore.models.ShopEvaluateModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopEvaluateAdapter extends CommonAdapter<ShopEvaluateModel> {


    public ShopEvaluateAdapter(Context context, List<ShopEvaluateModel> datas) {
        super(context, R.layout.item_evaluate, datas);
    }

    public void setData(List<ShopEvaluateModel> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }
    }

    public void addData(List<ShopEvaluateModel> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }
    @Override
    protected void convert(ViewHolder viewHolder, ShopEvaluateModel item, int position) {

        if(item == null){
            return;
        }

        ImageView imgPhoto = viewHolder.getView(R.id.img_photo);
        TextView txtName = viewHolder.getView(R.id.txt_name);
        ImageView imgEvalute = viewHolder.getView(R.id.img_evalute);
        TextView txtEvalute = viewHolder.getView(R.id.txt_evalute);


        if(item .getUser() != null){
            txtName.setText(item.getUser().getNickname());
            ImgUtils.loadImgIdforCircle(mContext, MyConfig.IMGURL+item.getUser().getPhoto(),imgPhoto);
            if(item.getType().equals("3")){
               imgEvalute.setImageResource(R.mipmap.evaluate_good);
                txtEvalute.setText("好评");

                 txtEvalute.setTextColor(ContextCompat.getColor(mContext,R.color.orange));
            }else if(item.getType().equals("B")){
                imgEvalute.setImageResource(R.mipmap.evaluate_general);
                txtEvalute.setText("中评");
                 txtEvalute.setTextColor(ContextCompat.getColor(mContext,R.color.fontColor_support));
            }else{
                 imgEvalute.setImageResource(R.mipmap.evaluate_bad);
                 txtEvalute.setText("差评");
                 txtEvalute.setTextColor(ContextCompat.getColor(mContext,R.color.gray));
            }

        }

    }


}
