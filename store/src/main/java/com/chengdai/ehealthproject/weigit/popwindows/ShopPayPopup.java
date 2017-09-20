package com.chengdai.ehealthproject.weigit.popwindows;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.databinding.PopupShopPayBinding;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopPayActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;


/**
 * 商城订单购买
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopPayPopup extends BasePopupWindow{

    private ShopListModel.ListBean mData;//产品书

    private PopupShopPayBinding mBinding;

    private int mPaySum;//购买数量

    private  int mQuantitySum;//当前选中产品库存数量

    private TagAdapter mTagAdapter;

    private ShopListModel.ListBean.ProductSpecsListBean mSelectProductData;//选中规格数据



    public void setmData(ShopListModel.ListBean mData) {
        this.mData = mData;

        if(mData!=null){

            mPaySum=0;

            mQuantitySum=0;

            ImgUtils.loadImgURL(mContext, MyConfigStore.IMGURL+mData.getSplitAdvPic(),mBinding.imgPhoto);

            if(mData.getProductSpecsList() != null && mData.getProductSpecsList().size() >0 && mData.getProductSpecsList().get(0) !=null){

                if(mData.getProductSpecsList().get(0).getQuantity()>0){
                    mPaySum=1;
                }

            mSelectProductData=mData.getProductSpecsList().get(0);

            mBinding.txtQuantity.setText("库存"+mData.getProductSpecsList().get(0).getQuantity());
            mBinding.txtPrice.setText(mContext.getString(R.string.price_sing)+ MoneyUtils.showPrice(mData.getProductSpecsList().get(0).getPrice1()));

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

    public ShopPayPopup(Activity context) {
        super(context);
    }


    @Override
    public View getPopupView() {
      mBinding=   DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.popup_shop_pay, null, false);
      return mBinding.getRoot();
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        setAnimotionStyle(R.style.popwin_anim_style);

        mBinding.txtBuy.setOnClickListener(v -> {

            if(mPaySum <=0){
                ToastUtil.show(mContext,"请选择购买数量");
                return;
            }

            if(!SPUtilHelpr.isLogin(mContext)){
                return;
            }

            if(mSelectProductData == null){
                return;
            }

           if(mData == null){
                return;
            }

            ShopPayActivity.open(mContext,mSelectProductData,mPaySum, MyConfigStore.IMGURL+mData.getSplitAdvPic());
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

                if(mData !=null && mData.getProductSpecsList()!=null && mData.getProductSpecsList().size()>x && mData.getProductSpecsList().get(x)!=null){

                    mSelectProductData=mData.getProductSpecsList().get(x);

                    mQuantitySum=mData.getProductSpecsList().get(x).getQuantity(); //重置产品库存

                    if(mQuantitySum>0){
                        mPaySum=1;
                    }else{
                        mPaySum=0;
                    }

                    mBinding.txtQuantity.setText("库存"+mData.getProductSpecsList().get(x).getQuantity());
                    mBinding.txtPrice.setText(mContext.getString(R.string.price_sing)+ MoneyUtils.showPrice(mData.getProductSpecsList().get(x).getPrice1()));

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
