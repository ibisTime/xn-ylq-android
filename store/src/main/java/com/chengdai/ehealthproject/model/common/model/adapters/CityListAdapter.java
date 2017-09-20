package com.chengdai.ehealthproject.model.common.model.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.common.model.CityModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreListModel;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-12-13.
 */

public class CityListAdapter extends CommonAdapter<CityModel>{

    public void setData(List<CityModel> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }

    }

    public void addData(List<CityModel> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public CityListAdapter(Context context, List<CityModel> datas) {
        super(context, R.layout.item_cityselect, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, CityModel item, int position) {

        TextView tvName=viewHolder.getView(R.id.tv_city_name);
        TextView tvNameChar=viewHolder.getView(R.id.tv_city_name_char);


        if(position>0 && item.getFirstChar() .equals(mDatas.get(position-1).getFirstChar())){
            tvNameChar.setVisibility(View.GONE);
        }else{
            tvNameChar.setVisibility(View.VISIBLE);
        }

        tvName.setText(item.getName());
        tvNameChar.setText(item.getFirstChar());

    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {

            CityModel model= (CityModel) mDatas.get(i);

            if(model==null)
            {
                continue;
            }

            String sortStr = model.getFirstChar();

            if(!TextUtils.isEmpty(sortStr))
            {
                char firstChar = sortStr.toUpperCase().charAt(0);

                if (firstChar == section) {
                    return i;
                }
            }

        }
        return -1;
    }


}
