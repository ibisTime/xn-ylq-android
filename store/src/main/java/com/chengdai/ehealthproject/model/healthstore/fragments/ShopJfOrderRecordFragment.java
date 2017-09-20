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

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseListFragment;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopPayConfirmActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopOrderDetailBean;
import com.chengdai.ehealthproject.model.tabmy.activitys.OrderConfirmGetActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.ShopOrderDetailsActivity;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/15.
 */

public class ShopJfOrderRecordFragment extends BaseListFragment<ShopOrderDetailBean>{

    private String mState;

    /**
     * 获得fragment实例
     * @return
     */
    public static ShopJfOrderRecordFragment getInstanse(String state){
        ShopJfOrderRecordFragment fragment=new ShopJfOrderRecordFragment();
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
        orderRecordRequest(mActivity,pageIndex);
    }

    @Override
    protected void onMLazyLoad(int pageIndex) {
      onMRefresh(pageIndex);
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
        return  R.layout.item_shop_order;
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

        txtTime.setText(DateUtil.formatStringData(item.getApplyDatetime(),DateUtil.DATE_YMD));

        txtBtn.setText(StringUtils.getOrderState(item.getStatus()));

        if(item.getProductOrderList() !=null && item.getProductOrderList().size()>0 && item.getProductOrderList().get(0) !=null
                && item.getProductOrderList().get(0).getProduct()!=null ){
            ImgUtils.loadFraImgId(ShopJfOrderRecordFragment.this, MyConfigStore.IMGURL+item.getProductOrderList().get(0).getProduct().getSplitAdvPic(),imgGood);

            txtPrice.setText(MoneyUtils.showPrice(item.getProductOrderList().get(0).getPrice1())+"  积分");
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
            }else if(TextUtils.equals(MyConfigStore.ORDERTYPEWAITSHOUHUO,item.getStatus())){  //待收获状态
                OrderConfirmGetActivity.open(mActivity,item.getCode());
            }
        });
    }

    @Override
    protected void onItemCilck(ShopOrderDetailBean shopOrderDetailBean, int position) {
        ShopOrderDetailsActivity.open(mActivity,shopOrderDetailBean, MyConfigStore.JFORDER);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getUserVisibleHint()){
            onMRefresh(mPageIndex);
        }
    }

    public void orderRecordRequest(Context context,int page){

        Map<String,String> object=new HashMap();

        object.put("applyUser", SPUtilHelpr.getUserId());
        object.put("start", page+"");
        object.put("limit", "10");
        object.put("status", mState+"");
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyConfigStore.SYSTEMCODE);
        object.put("type","2");//积分商城

        mSubscription.add(RetrofitUtils.getLoaderServer().ShopOrderList("808068",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .filter(data->data!=null)
                .subscribe(r->{
                  setData(r.getList());
                },Throwable::printStackTrace));

    }

}
