package com.chengdai.ehealthproject.model.tabmy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseListFragment;
import com.chengdai.ehealthproject.model.tabmy.activitys.HotelOrderDetailsActivity;
import com.chengdai.ehealthproject.model.tabmy.model.HotelOrderRecordModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/15.
 */

public class HotelOrderRecordFragment extends BaseListFragment<HotelOrderRecordModel.ListBean> {

    /**
     * 获得fragment实例
     * @return
     */
    public static HotelOrderRecordFragment getInstanse(){
        HotelOrderRecordFragment fragment=new HotelOrderRecordFragment();
        Bundle bundle=new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onMInitRefresh(int pageIndex) {
        orderRecordRequest(mActivity,pageIndex);
    }

    @Override
    protected void onMLazyLoad(int pageIndex) {

    }


    @Override
    protected void onMRefresh(int pageIndex) {
        orderRecordRequest(null,pageIndex);
    }

    @Override
    protected void onMLoadMore(int pageIndex) {
        orderRecordRequest(null,pageIndex);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_hotelorder_list;
    }

    @Override
    protected void onBind(ViewHolder viewHolder, HotelOrderRecordModel.ListBean item, int position) {


        TextView tvCode=viewHolder.getView(R.id.tv_code);
        TextView tvDate=viewHolder.getView(R.id.tv_date);
        TextView tv_hotel_size=viewHolder.getView(R.id.tv_hotel_size);
        TextView tv_hotel_data=viewHolder.getView(R.id.tv_hotel_data);
        TextView tv_hotel_info=viewHolder.getView(R.id.tv_hotel_info);
        TextView tv_sure_pay=viewHolder.getView(R.id.tv_sure_pay);
        ImageView img_hotel_info=viewHolder.getView(R.id.img_hotel_info);

        viewHolder.setOnClickListener(R.id.lin_root,v -> {
            HotelOrderDetailsActivity.open(mActivity,item);
        });

        tvCode.setText(item.getCode());

        if(!TextUtils.isEmpty(item.getPayDatetime())){
            tvDate.setText(DateUtil.format( new Date(item.getPayDatetime()), DateUtil.DATE_YMD));
        }


   /*     if("0".equals(item.getStatus())){
            tv_sure_pay.setVisibility(View.VISIBLE);
        }else{
            tv_sure_pay.setVisibility(View.GONE);
        }
*/
        if(item.getProduct() !=null){
            tv_hotel_size.setText(item.getProduct().getName());
            tv_hotel_info.setText(item.getProduct().getSlogan());
            ImgUtils.loadImgURL(mActivity, MyConfig.IMGURL+item.getProduct().getSplitAdvPic(),img_hotel_info);

            if(!TextUtils.isEmpty(item.getStartDate()) && !TextUtils.isEmpty(item.getEndDate())){
                tv_hotel_data.setText(
                        "入住:"+DateUtil.format( new Date(item.getStartDate()),"MM月dd日")
                                +"离开:"+DateUtil.format( new Date(item.getEndDate()),"MM月dd日")
                                +"  "+(DateUtil.getDatesBetweenTwoDate(new Date(item.getStartDate()),new Date(item.getEndDate())).size()-1)+"晚"
                );
            }
        }

    }


    public void orderRecordRequest(Context context,int page){

        Map<String,String> map=new HashMap();

        map.put("applyUser",SPUtilHelpr.getUserId());
        map.put("start",page+"");
        map.put("limit","10");
//        map.put("status","1");
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().HotelOrderRecordRequest("808468", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .filter(data->data!=null)
                .subscribe(r -> {
                     setData(r.getList());
                },Throwable::printStackTrace));


    }

}
