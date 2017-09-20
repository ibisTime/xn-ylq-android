package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityShopOrderDetailsBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopPayConfirmActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopOrderDetailBean;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**订单详情查看
 * Created by 李先俊 on 2017/6/15.
 */

public class ShopOrderDetailsActivity extends AbsStoreBaseActivity {

    private ActivityShopOrderDetailsBinding mBinding;

    private  ShopOrderDetailBean mData;

    private  int mType;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, ShopOrderDetailBean data,int type){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopOrderDetailsActivity.class);

        intent.putExtra("data",data);

        intent.putExtra("type",type);

        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_order_details, null, false);

        if(getIntent()!=null){

            mData= getIntent().getParcelableExtra("data");
            mType= getIntent().getIntExtra("type", MyConfigStore.JFORDER);

        }

        setTopTitle("订单详情");

        setSubLeftImgState(true);

        addMainView(mBinding.getRoot());

        setShowDataStae();

//        getShopOrderDetailsReqeust(this);

    }

    private void setShowDataStae() {

        if(mData == null){
            return;
        }

        mBinding.txtOrderId.setText(mData.getCode());

        mBinding.txtTime.setText(DateUtil.format(new Date(mData.getApplyDatetime()),DateUtil.DATE_YYMMddHHmm));

        mBinding.txtStatus.setText(StringUtils.getOrderState(mData.getStatus()));


        if(mData.getProductOrderList() !=null && mData.getProductOrderList().size()>0 && mData.getProductOrderList().get(0) !=null
                && mData.getProductOrderList().get(0).getProduct()!=null ){
            ImgUtils.loadImgURL(this, MyConfigStore.IMGURL+mData.getProductOrderList().get(0).getProduct().getAdvPic(),mBinding.imgGood);


           if(mType == MyConfigStore.JFORDER){
               mBinding.txtPrice.setText(MoneyUtils.showPrice(mData.getProductOrderList().get(0).getPrice1())+"  积分");
           }else{
               mBinding.txtPrice.setText(MoneyUtils.getShowPriceSign(mData.getProductOrderList().get(0).getPrice1()));
           }

            mBinding.txtNumber.setText("X" + mData.getProductOrderList().get(0).getQuantity());

            mBinding.txtName.setText(mData.getProductOrderList().get(0).getProduct().getName());

            mBinding.txtPhone.setText(mData.getReceiver()+" "+mData.getReMobile());

            mBinding.txtAddress.setText(mData.getReAddress());

            mBinding.txtGuige.setText(mData.getProductOrderList().get(0).getProductSpecsName());

        }

        if(TextUtils.equals(MyConfigStore.ORDERTYPEWAITSHOUHUO,mData.getStatus())){//待收货状态
            mBinding.linLogistics.setVisibility(View.VISIBLE);
            mBinding.txtBtn.setVisibility(View.VISIBLE);
            mBinding.txtBtn.setText("确认收货");
            mBinding.txtLogisticsCode.setText(mData.getLogisticsCode());

            mBinding.txtLogisticsCompany.setText(StringUtils.getLogisticsCompany(mData.getLogisticsCompany()));

            mBinding.txtBtn.setOnClickListener(v -> {
                if(mData==null) return;
                OrderConfirmGetActivity.open(this,mData.getCode());
            });

        }else if(StringUtils.canDoPay(mData.getStatus())){//待支付状态
            mBinding.txtCancel.setVisibility(View.VISIBLE);
            mBinding.txtBtn.setVisibility(View.VISIBLE);

            mBinding.txtBtn.setOnClickListener(v -> {
                ShopListModel.ListBean.ProductSpecsListBean data1=new ShopListModel.ListBean.ProductSpecsListBean();

                if(mData.getProductOrderList() !=null && mData.getProductOrderList().size()>0 && mData.getProductOrderList().get(0) !=null) {
                    data1.setPrice1(mData.getProductOrderList().get(0).getPrice1());
                    data1.setmBuyNum(mData.getProductOrderList().get(0).getQuantity());
                    ShopPayConfirmActivity.open(this,data1,mData.getCode(),false);
                }
            });

           mBinding.txtCancel.setOnClickListener(v -> {
               showDoubleWarnListen("确定取消订单？",view -> {
                   cancelOrderRequest();
               });
            });

        }else{
            mBinding.txtCancel.setVisibility(View.GONE);
            mBinding.txtBtn.setVisibility(View.GONE);
            mBinding.linLogistics.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
//       getShopOrderDetailsReqeust(null);
    }

    //ShopPayConfirmActivity 支付成功刷新
    @Subscribe
  public void  getShopOrderDetailsReqeustEvent(EventBusModel b){
        if(b ==null){
            return;
        }

        if("getShopOrderDetailsReqeustEvent" .equals(b.getTag())){//支付状态刷新
            mBinding.txtBtn.setVisibility(View.GONE);
            mBinding.txtCancel.setVisibility(View.GONE);
            mBinding.txtStatus.setText("已支付");
        }else if("getShopOrderDetailsReqeustEvent_getOrder" .equals(b.getTag())){//收货成功刷新
            mBinding.txtBtn.setVisibility(View.GONE);
            mBinding.txtCancel.setVisibility(View.GONE);
            mBinding.txtStatus.setText("已收货");
        }

    }

    /**
     * 获取订单详情
     * @param c
     */
    private  void getShopOrderDetailsReqeust(Context c){

        if(mData == null){
            return;
        }

        Map object=new HashMap();

        object.put("code", mData.getCode());
        object.put("token", SPUtilHelpr.getUserToken());

      mSubscription.add( RetrofitUtils.getLoaderServer().ShopOrderDetails("808066",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(c))
                .subscribe(shopOrderModel -> {

                    if(shopOrderModel.getList() != null && shopOrderModel.getList().size()>0){
                        mData=shopOrderModel.getList().get(0);

                        setShowDataStae();
                    }

                },Throwable::printStackTrace));

    }

    /**
     * 取消订单
     */
    private void cancelOrderRequest(){
        if(mData == null){
            return;
        }
        Map object=new HashMap();
        object.put("code", mData.getCode());
        object.put("userId", SPUtilHelpr.getUserId());
        object.put("remark", "用户取消订单");
        object.put("token", SPUtilHelpr.getUserToken());


        mSubscription.add( RetrofitUtils.getLoaderServer().ShopOrderCancel("808053",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(shopOrderModel -> {

                    if(shopOrderModel != null && shopOrderModel.isSuccess()){
                        showToast("取消订单成功");
                        mBinding.txtBtn.setVisibility(View.GONE);
                        mBinding.txtCancel.setVisibility(View.GONE);
                        mBinding.txtStatus.setText("已取消");
                        getShopOrderDetailsReqeust(null);
                    }

                },Throwable::printStackTrace));


    }


}
