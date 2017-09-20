package com.chengdai.ehealthproject.model.healthstore.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseListFragment;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopPayConfirmActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopOrderDetailBean;
import com.chengdai.ehealthproject.model.tabmy.activitys.OrderConfirmGetActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.ShopOrderDetailsActivity;
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

public class ShopOrderRecordFragment extends BaseListFragment<ShopOrderDetailBean> {

    private String mState;

    /**
     * 获得fragment实例
     * @return
     */
    public static ShopOrderRecordFragment getInstanse(String state){
        ShopOrderRecordFragment fragment=new ShopOrderRecordFragment();
        Bundle bundle=new Bundle();
        bundle.putString("state",state);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(getArguments()!=null){
            mState=getArguments().getString("state","");
        }

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    protected void onMInitRefresh(int pageIndex) {
        orderRecordRequest(mActivity);
    }

    @Override
    protected void onMLazyLoad(int pageIndex) {
        orderRecordRequest(null);
    }

    @Override
    protected void onMRefresh(int pageIndex) {
        orderRecordRequest(null);
    }

    @Override
    protected void onMLoadMore(int pageIndex) {
        orderRecordRequest(null);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_shop_order;
    }

    @Override
    protected void onBind(ViewHolder viewHolder, ShopOrderDetailBean item, int position) {

        TextView txtOrderId=viewHolder.getView(R.id.txt_orderId);
        TextView txtTime=viewHolder.getView(R.id.txt_time);
        TextView txtBtn=viewHolder.getView(R.id.txt_btn);
        TextView txtPrice=viewHolder.getView(R.id.txt_price);
        ImageView imgGood=viewHolder.getView(R.id.img_good);
        TextView tvNumber=viewHolder.getView(R.id.txt_number);
        TextView tvName=viewHolder.getView(R.id.txt_name);


        txtOrderId.setText(item.getCode());

        txtTime.setText(DateUtil.format(new Date(item.getApplyDatetime()),DateUtil.DATE_YMD));

        txtBtn.setText(StringUtils.getOrderState(item.getStatus()));



        if(item.getProductOrderList() !=null && item.getProductOrderList().size()>0 && item.getProductOrderList().get(0) !=null
                && item.getProductOrderList().get(0).getProduct()!=null ){
            ImgUtils.loadImgURL(mActivity, MyConfig.IMGURL+item.getProductOrderList().get(0).getProduct().getAdvPic(),imgGood);

            txtPrice.setText(StringUtils.getShowPriceSign(item.getProductOrderList().get(0).getPrice1()));

            tvNumber.setText("X" + item.getProductOrderList().get(0).getQuantity());

            tvName.setText(item.getProductOrderList().get(0).getProduct().getName());

        }

        txtBtn.setOnClickListener(v -> {

            if (StringUtils.canDoPay(item.getStatus())){//待支付状态
                ShopListModel.ListBean.ProductSpecsListBean data=new ShopListModel.ListBean.ProductSpecsListBean();

                if(item.getProductOrderList() !=null && item.getProductOrderList().size()>0 && item.getProductOrderList().get(0) !=null) {
                    data.setPrice1(item.getProductOrderList().get(0).getPrice1());
                    data.setmBuyNum(item.getProductOrderList().get(0).getQuantity());

                    ShopPayConfirmActivity.open(mActivity,data,item.getCode(),false);
                }
            }else if(TextUtils.equals(MyConfig.ORDERTYPEWAITSHOUHUO,item.getStatus())){  //待收获状态
                OrderConfirmGetActivity.open(mActivity,item.getCode());
            }
        });

    }

    @Override
    protected void onItemCilck(ShopOrderDetailBean shopOrderDetailBean, int position) {
        ShopOrderDetailsActivity.open(mActivity,shopOrderDetailBean,MyConfig.PRICEORDER);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getUserVisibleHint()){
          onMRefresh(mPageIndex);
        }
    }



    public void orderRecordRequest(Context context){

        Map<String,String> object=new HashMap();

        object.put("applyUser", SPUtilHelpr.getUserId());
        object.put("start", mPageIndex+"");
        object.put("limit", "10");
        object.put("status", mState+"");
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyConfig.SYSTEMCODE);
        object.put("type","1");//普通商城


        mSubscription.add(RetrofitUtils.getLoaderServer().ShopOrderList("808068",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .filter(data->data!=null)
                .subscribe(r->{
                    setData(r.getList());
                },Throwable::printStackTrace));


/*        mSubscription.add(RetrofitUtils.getLoaderServer().OrderRecordRequest("808245", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(r -> {



                },Throwable::printStackTrace));*/

    }

}
