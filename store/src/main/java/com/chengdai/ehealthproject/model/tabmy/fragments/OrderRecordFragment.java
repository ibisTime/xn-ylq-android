package com.chengdai.ehealthproject.model.tabmy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseListFragment;
import com.chengdai.ehealthproject.model.tabmy.activitys.OrderDetailsActivity;
import com.chengdai.ehealthproject.model.tabmy.model.OrderRecordModel;
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

public class OrderRecordFragment extends BaseListFragment<OrderRecordModel.ListBean> {

    /**
     * 获得fragment实例
     * @return
     */
    public static OrderRecordFragment getInstanse(){
        OrderRecordFragment fragment=new OrderRecordFragment();
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
//        orderRecordRequest(null,pageIndex);
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
        return R.layout.item_order_list;
    }


    @Override
    protected void onBind(ViewHolder holder, OrderRecordModel.ListBean item, int position) {
        TextView tvCode=holder.getView(R.id.tv_code);
        TextView tvDate=holder.getView(R.id.tv_date);
        TextView tvName=holder.getView(R.id.tv_name);
        TextView tvPrice=holder.getView(R.id.tv_price);
        ImageView imgTitle=holder.getView(R.id.img_title);

        holder.setOnClickListener(R.id.lin_root,v -> {
            OrderDetailsActivity.open(mActivity,item);
        });

        if(!TextUtils.isEmpty(item.getCreateDatetime())){
            tvDate.setText(DateUtil.format( new Date(item.getCreateDatetime()), DateUtil.DATE_YMD));
        }

        tvPrice.setText(mActivity.getString(R.string.price_sing)+ StringUtils.showPrice(item.getPrice()));

        tvCode.setText(item.getCode());

        if(item.getStore() !=null ){
            tvName.setText(item.getStore().getName());
            ImgUtils.loadImgURL(mActivity, MyConfig.IMGURL+item.getStore().getSplitAdvPic(),imgTitle);
        }
    }


    public void orderRecordRequest(Context context,int page){

        Map<String,String> map=new HashMap();
        map.put("userId",SPUtilHelpr.getUserId());
        map.put("start",page+"");
        map.put("limit","10");
        map.put("status","1");
        mSubscription.add(RetrofitUtils.getLoaderServer().OrderRecordRequest("808245", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .filter(data->data!=null)
                .subscribe(r -> {
                  setData(r.getList());
                },Throwable::printStackTrace));

    }

}
