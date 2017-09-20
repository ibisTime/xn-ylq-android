package com.chengdai.ehealthproject.model.tabsurrounding.adapters;

import android.content.Context;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**周边商户分类选择左
 * Created by 李先俊 on 2017/6/10.
 */

public class SurroundingMenuLeftAdapter extends CommonAdapter {

    private String typeCode;


    public void setTypeName(String typeCode) {
        this.typeCode = typeCode;
        notifyDataSetChanged();
    }

    public void setDatas(List<StoreTypeModel> datas) {
        if(datas!=null){
            this.mDatas = datas;
            notifyDataSetChanged();
        }

    }

    public SurroundingMenuLeftAdapter(Context context, int layoutId, List<StoreTypeModel> datas, String typeCode) {
        super(context, layoutId, datas);
        this.typeCode=typeCode;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

        TextView tv=viewHolder.getView(R.id.tv_txt);
        StoreTypeModel model= (StoreTypeModel) item;

        if(model!=null ){
            if(typeCode.equals(model.getCode())){
                tv.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }else{
                tv.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            }
            tv.setText(model.getName());
        }

    }
}
