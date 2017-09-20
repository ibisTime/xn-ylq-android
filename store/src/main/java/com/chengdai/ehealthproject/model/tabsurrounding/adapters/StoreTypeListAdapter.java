package com.chengdai.ehealthproject.model.tabsurrounding.adapters;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.tabsurrounding.model.DZUpdateModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/12.
 */

public class StoreTypeListAdapter extends CommonAdapter {

    private  boolean isEvent;

    public StoreTypeListAdapter(Context context, List<StoreListModel.ListBean> datas,boolean isEvent) {
        super(context, R.layout.itme_fragment_surrounding, datas);
        this.isEvent=isEvent;
    }

    public void setData(List<StoreListModel.ListBean> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }

    }

    public void addData(List<StoreListModel.ListBean> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void setDzInfo(DZUpdateModel dzInfo){

        if(dzInfo==null){
            return;
        }

        for(int i=0;i<mDatas.size();i++){
            StoreListModel.ListBean listBean= (StoreListModel.ListBean) mDatas.get(i);

            if(listBean==null) continue;

            if(dzInfo.getCode().equals(listBean.getCode())){
                listBean.setDZ(dzInfo.isDz());
                listBean.setTotalDzNum(dzInfo.getDzSum());
            }
        }

        notifyDataSetChanged();

    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

        StoreListModel.ListBean storeListModel = (StoreListModel.ListBean) item;


        ImageView  imgTitle=viewHolder.getView(R.id.img_shop_title);
        TextView   tvTitle=viewHolder.getView(R.id.tv_store_title);
        TextView   tvSlogan=viewHolder.getView(R.id.tv_slogan);
        TextView   tvAddress=viewHolder.getView(R.id.tv_address);
        TextView   tvDZSUM=viewHolder.getView(R.id.tv_dz_sum);
        ImageView   imgIsDz=viewHolder.getView(R.id.img_isDZ);
        FrameLayout frameLayout_dz=viewHolder.getView(R.id.frameLayout_dz);

        frameLayout_dz.setOnClickListener(v -> {

            if(!SPUtilHelpr.isLogin(mContext)){

                return;
            }

            Map map=new HashMap();
            map.put("storeCode", storeListModel.getCode());
            map.put("type","1");
            map.put("userId", SPUtilHelpr.getUserId());

            RetrofitUtils.getLoaderServer().DZRequest("808240", StringUtils.getJsonToString(map))

                    .compose(RxTransformerHelper.applySchedulerResult(mContext))

                    .filter(isSuccessModes -> isSuccessModes!=null)

                    .subscribe(baseResponseModel -> {

                        if(storeListModel!=null && baseResponseModel.isSuccess()){
                            if(storeListModel.isDZ()){
                                ImgUtils.loadImgId(mContext,R.mipmap.good_hand_un,imgIsDz);
                                storeListModel.setTotalDzNum(storeListModel.getTotalDzNum()-1);
                            }else if(! storeListModel.isDZ()){
                                ImgUtils.loadImgId(mContext,R.mipmap.good_hand_,imgIsDz);
                                storeListModel.setTotalDzNum(storeListModel.getTotalDzNum()+1);
                            }
                            tvDZSUM.setText(storeListModel.getTotalDzNum()+"");
                            storeListModel.setDZ(!storeListModel.isDZ());

                            if(isEvent){
                                DZUpdateModel dz=new DZUpdateModel();

                                dz.setCode(storeListModel.getCode());
                                dz.setDz(storeListModel.isDZ());
                                dz.setDzSum(storeListModel.getTotalDzNum());

                                EventBus.getDefault().post(dz);
                            }

                        }


                    });

        });

        if(storeListModel != null){

            ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+ storeListModel.getSplitAdvPic(),imgTitle);

            tvTitle.setText(storeListModel.getName());

            tvTitle.setText(storeListModel.getName());

            tvSlogan.setText(storeListModel.getSlogan());

            tvAddress.setText(storeListModel.getAddress());

            tvDZSUM.setText(storeListModel.getTotalDzNum()+"");

            if(storeListModel.isDZ()){
                ImgUtils.loadImgId(mContext,R.mipmap.good_hand_,imgIsDz);
            }else{
                ImgUtils.loadImgId(mContext,R.mipmap.good_hand_un,imgIsDz);
            }

        }

    }

}
