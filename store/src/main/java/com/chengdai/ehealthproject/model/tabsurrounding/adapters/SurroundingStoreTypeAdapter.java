package com.chengdai.ehealthproject.model.tabsurrounding.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**周边商户分类
 * Created by 李先俊 on 2017/6/10.
 */

public class SurroundingStoreTypeAdapter extends CommonAdapter {


    public void setDatas(List<StoreTypeModel> datas) {
        if(datas!=null){
            this.mDatas = datas;
            notifyDataSetChanged();
        }
    }

    public SurroundingStoreTypeAdapter(Context context, List<StoreTypeModel> datas) {
        super(context, R.layout.item_surrounding_menu, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

        TextView tv=viewHolder.getView(R.id.tv_menu);
        ImageView img=viewHolder.getView(R.id.img_menu);

        StoreTypeModel model= (StoreTypeModel) item;
        if(model!=null){
            tv.setText(model.getName());
            ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+model.getPic(),img);
        }

    }
}
