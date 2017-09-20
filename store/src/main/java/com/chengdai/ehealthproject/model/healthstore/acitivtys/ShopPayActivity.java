package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityShopPayBinding;
import com.chengdai.ehealthproject.model.common.model.activitys.AddressSelectActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.healthstore.models.getOrderAddressModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**商城支付
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopPayActivity extends AbsStoreBaseActivity {

    private ActivityShopPayBinding mBinding;

    private ShopListModel.ListBean.ProductSpecsListBean mSelectProductData;

    private List<getOrderAddressModel> mAddressList ;//是收货地址

    private int mBuyNum=0;//购买数量

    private String imgUrl;//产品缩略图片

    /**
     * 打开当前页面
     * @param context  price code
     */
    public static void open(Context context, ShopListModel.ListBean.ProductSpecsListBean data,int buyNum,String imgUrl){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopPayActivity.class);
        intent.putExtra("data",data);
        intent.putExtra("buynum",buyNum);
        intent.putExtra("imgUrl",imgUrl);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_pay, null, false);

        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);

        setTopTitle(getString(R.string.txt_pay));

        if(getIntent() != null){
            mSelectProductData=getIntent().getParcelableExtra("data");
            mBuyNum = getIntent().getIntExtra("buynum",0);
            imgUrl = getIntent().getStringExtra("imgUrl");
        }

        initViews();

        setShowData();


        mBinding.txtBuy.setOnClickListener(v -> {
            if(mAddressList == null || mAddressList.size()==0){
                showToast("请先选择收货地址");
                return;
            }
            if(mBuyNum == 0){
                showToast("至少选择一个数量");
                return;
            }

          buyRequest();

        });

    }

    private void setShowData() {
        if(mSelectProductData!=null){

            mBinding.txtPriceSingle.setText(StringUtils.getShowPriceSign(mSelectProductData.getPrice1()));

            mBinding.txtName.setText(mSelectProductData.getName());

            mBinding.txtNumber.setText("X"+mBuyNum);

            mBinding.textMoney.setText(StringUtils.getShowPriceSign(mSelectProductData.getPrice1(),mBuyNum));
        }

        ImgUtils.loadImgURL(this,imgUrl,mBinding.imgGood);
    }

    private void buyRequest() {

        /* "productCode": "CP201703271142204949",
    "toUser": "SYS_USER_CAIGO",
    "quantity": "1",
    "pojo": {
        "receiver": "郑海清",
        "reMobile": "15158110100",
        "reAddress": "浙江省杭州市余杭区梦想小镇天使村",
        "applyUser": "U2017032717333217771",
        "applyNote": "下单测试",
        "receiptType": "",
        "receiptTitle": "",
        "companyCode": "CD-CCG000007",
        "systemCode": "CD-CCG000007"
    }*/

        if(mSelectProductData == null){
            return;
        }

        Map object=new HashMap<>();

        Map<String,String> pojo=new HashMap<>();

        pojo.put("receiver", mAddressList.get(0).getAddressee());
        pojo.put("reMobile", mAddressList.get(0).getMobile());
        pojo.put("reAddress", mAddressList.get(0).getProvince() + " " + mAddressList.get(0).getCity() + " " + mAddressList.get(0).getDistrict() + " " + mAddressList.get(0).getDetailAddress());
        pojo.put("applyUser", SPUtilHelpr.getUserId());
        pojo.put("applyNote", mBinding.edtEnjoin.getText().toString().trim());
        pojo.put("companyCode", MyConfig.COMPANYCODE);
        pojo.put("systemCode", MyConfig.SYSTEMCODE);
        pojo.put("token", SPUtilHelpr.getUserToken());

        object.put("productSpecsCode", mSelectProductData.getCode());
        object.put("quantity", mBuyNum+"");
        object.put("pojo", pojo);



       mSubscription.add( RetrofitUtils.getLoaderServer().ShopOrderCerate("808050",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(codeModel -> {
                   if(!TextUtils.isEmpty(codeModel)){
                       showToast("下单成功");
                       mSelectProductData.setmBuyNum(mBuyNum);
                       ShopPayConfirmActivity.open(this,mSelectProductData,codeModel,true);
                   }

                },Throwable::printStackTrace));

    }


    @Override
    protected void onResume() {
        super.onResume();
        getAddressRequst();
    }

    private void initViews() {


        mBinding.layoutAdd.setOnClickListener(v -> {
            AddressSelectActivity.open(this);
        });

        mBinding.layoutAddress.setOnClickListener(v -> {
            AddressSelectActivity.open(this);
        });
    }


    /**
     * 请求收获地址
     */
   private void getAddressRequst(){

       Map<String,String> map=new HashMap<>();

       map.put("userId",SPUtilHelpr.getUserId());
       map.put("isDefault", "1");
       map.put("token", SPUtilHelpr.getUserToken());

       mSubscription.add(RetrofitUtils.getLoaderServer().GetAddress("805165", StringUtils.getJsonToString(map))
               .compose(RxTransformerListHelper.applySchedulerResult(this))
               .subscribe(data ->{
                   mAddressList=data;
                   setAddressState(data);

               },Throwable::printStackTrace));

   }



    /**
     * 设置地址显示状态 有地址是显示默认地址  无地址时显示添加按钮
     */
    private void setAddressState(List<getOrderAddressModel> addressList) {
        if (addressList == null || addressList.size() == 0) {
            mBinding.layoutAddress.setVisibility(View.GONE);
            mBinding. layoutNoAddress.setVisibility(View.VISIBLE);
        } else {
            mBinding.layoutAddress.setVisibility(View.VISIBLE);
            mBinding.layoutNoAddress.setVisibility(View.GONE);

            mBinding. txtConsignee.setText(addressList.get(0).getAddressee());
            mBinding. txtPhone.setText(addressList.get(0).getMobile());
            mBinding. txtAddress.setText("收货地址：" + addressList.get(0).getProvince() + " " + addressList.get(0).getCity() + " " + addressList.get(0).getDistrict() + "" + addressList.get(0).getDetailAddress());
        }
    }

    //AddressAdapter 刷新数据
    @Subscribe
    public  void getAddressRequestEvent(getOrderAddressModel model){
        if(model != null ){

            mAddressList = null;

            mBinding.layoutAddress.setVisibility(View.VISIBLE);
            mBinding.layoutNoAddress.setVisibility(View.GONE);

            mBinding. txtConsignee.setText(model.getAddressee());
            mBinding. txtPhone.setText(model.getMobile());
            mBinding. txtAddress.setText("收货地址：" + model.getProvince() + " " + model.getCity() + " " + model.getDistrict() + "" + model.getDetailAddress());

 /*           mBinding. txtConsignee.setText("");
            mBinding. txtPhone.setText("");
            mBinding. txtAddress.setText("");*/

        }
    }

}
