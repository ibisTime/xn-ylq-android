package com.chengdai.ehealthproject.weigit.popwindows;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.databinding.PopupShopAddPayCarBinding;
import com.chengdai.ehealthproject.databinding.PopupShopPayBinding;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopPayActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.Map;


/**
 * 商城订单购买
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopAddPayCarPopup extends BasePopupWindow{

    private ShopListModel.ListBean mData;

    private PopupShopAddPayCarBinding mBinding;

    private int mPaySum;//购买数量

    private  int mQuantitySum;//当前选中产品库存数量

    private TagAdapter mTagAdapter;

    private ShopListModel.ListBean.ProductSpecsListBean mSelectProductData;


    public void setmData(ShopListModel.ListBean mData) {
        this.mData = mData;

        mPaySum=0;

        mQuantitySum=0;

        if(mData!=null){

            ImgUtils.loadImgIdforRound(mContext, MyConfig.IMGURL+mData.getSplitAdvPic(),mBinding.imgPhoto);

            if(mData.getProductSpecsList() != null && mData.getProductSpecsList().size() >0 && mData.getProductSpecsList().get(0) !=null){

                if(mData.getProductSpecsList().get(0).getQuantity()>0){
                    mPaySum=1;
                }

            mSelectProductData=mData.getProductSpecsList().get(0);

            mBinding.txtQuantity.setText("库存"+mData.getProductSpecsList().get(0).getQuantity());
            mBinding.txtPrice.setText(mContext.getString(R.string.price_sing)+ StringUtils.showPrice(mData.getProductSpecsList().get(0).getPrice1()));

             mQuantitySum=mData.getProductSpecsList().get(0).getQuantity(); //设置产品库存


             initTagViewState(mData);//设置规格显示状态
        }

        mBinding.txtNumber.setText(mPaySum+"");

      }

  }

    private void initTagViewState(final ShopListModel.ListBean mData) {

        mTagAdapter = new TagAdapter<ShopListModel.ListBean.ProductSpecsListBean>(mData.getProductSpecsList())
        {
            @Override
            public View getView(FlowLayout parent, int position, ShopListModel.ListBean.ProductSpecsListBean productSpecsListBean) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flowlayout_tv,
                        mBinding.flowlayout, false);
                tv.setText(productSpecsListBean.getName());
                return tv;
            }
        };

        mBinding.flowlayout.setAdapter(mTagAdapter);

        mTagAdapter.setSelectedList(0);//设置规格第一个默认选中
        mBinding.flowlayout.getChildAt(0).setClickable(true);
    }

    public ShopAddPayCarPopup(Activity context) {
        super(context);
    }


    @Override
    public View getPopupView() {
      mBinding=   DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.popup_shop_add_pay_car, null, false);
      return mBinding.getRoot();
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        setAnimotionStyle(R.style.popwin_anim_style);

        mBinding.txtBuy.setOnClickListener(v -> {

            if(mPaySum <=0){
                ToastUtil.show(mContext,"请选择数量");
                return;
            }

            if(!SPUtilHelpr.isLogin(mContext)){
                return;
            }

            dismiss();

            addPayCarRequest();//添加到购物车


//            ShopPayActivity.open(mContext,mSelectProductData,mPaySum);
        });

        mBinding.layoutCancel.setOnClickListener(v -> {
            dismiss();
        });

        mBinding.flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int x, FlowLayout parent) {

                for (int i = 0; i < parent.getChildCount(); i++) {         //单选效果
                    if (i != x) {
                        parent.getChildAt(i).setClickable(false);
                    } else {
                        parent.getChildAt(i).setClickable(true);
                    }
                }

                if(mData !=null &&mData.getProductSpecsList()!=null && mData.getProductSpecsList().size()>x && mData.getProductSpecsList().get(x)!=null){

                    mSelectProductData=mData.getProductSpecsList().get(x);

                    mQuantitySum=mData.getProductSpecsList().get(x).getQuantity(); //重置产品库存

                    if(mQuantitySum>0){
                        mPaySum=1;
                    }else{
                        mPaySum=0;
                    }

                    mBinding.txtQuantity.setText("库存"+mData.getProductSpecsList().get(x).getQuantity());
                    mBinding.txtPrice.setText(mContext.getString(R.string.price_sing)+ StringUtils.showPrice(mData.getProductSpecsList().get(x).getPrice1()));

                }else{
                    mSelectProductData =null;
                    mQuantitySum=0;
                    mPaySum=0;
                    mBinding.txtQuantity.setText("库存");
                    mBinding.txtPrice.setText(mContext.getString(R.string.price_sing));
                }
                mBinding.txtNumber.setText(mPaySum+"");
                return false;
            }
        });

        //购买数量减
        mBinding.txtSubtract.setOnClickListener(v -> {
            if(mPaySum > 0){
                mPaySum--;
                mBinding.txtNumber.setText(mPaySum+"");
            }
        });
  //购买数量加
     mBinding.txtAdd.setOnClickListener(v -> {
         if(mPaySum < mQuantitySum){
             mPaySum++;
             mBinding.txtNumber.setText(mPaySum+"");
         }
        });

    }

    /**
     * 添加到购物车
     */
    private void addPayCarRequest() {

        if(mSelectProductData == null){
            return;
        }

        Map<String,String> map=new HashMap<>();

        map.put("userId",SPUtilHelpr.getUserId());
        map.put("productSpecsCode",mSelectProductData.getCode());
        map.put("quantity",mPaySum+"");

        RetrofitUtils.getLoaderServer().ShopAddPayCar("808040",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mContext))
                .subscribe(codeModel -> {
                    if(codeModel!=null && !TextUtils.isEmpty(codeModel.getCode())){
                        dismiss();
                        ToastUtil.show(mContext,"添加到购物车成功");
                    }

                },Throwable::printStackTrace);

    }

    @Override
    public View getAnimaView() {
        return mBinding.popLayout;
    }

    @Override
    protected Animation getShowAnimation() {
//        return  AnimationUtils.loadAnimation(mContext, R.anim.pop_bottom_in);
        return  null;
    }

    @Override
    public Animation getExitAnimation() {
//        return AnimationUtils.loadAnimation(mContext, R.anim.pop_bottom_out);
        return  null;
    }


    @Override
    protected View getClickToDismissView() {
        return mBinding.dismissview;
    }

}
