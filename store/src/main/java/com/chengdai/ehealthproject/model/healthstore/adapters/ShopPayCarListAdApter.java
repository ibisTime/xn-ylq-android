package com.chengdai.ehealthproject.model.healthstore.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdkj.baselibrary.dialog.CommonDialog;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthstore.models.PayCarListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/19.
 */

public class ShopPayCarListAdApter extends CommonAdapter<PayCarListModel.ListBean> {

    private int mSelectPosition=0;

    public ShopPayCarListAdApter(Context context,  List<PayCarListModel.ListBean> datas) {
        super(context, R.layout.item_pay_car, datas);
    }

    public PayCarListModel.ListBean getmSelectPositionItem() {

        if(mSelectPosition <0 || mSelectPosition>=mDatas.size()){
            return null;
        }

        return mDatas.get(mSelectPosition);
    }

    public String getSelectPriceShowString(){

        PayCarListModel.ListBean data=   getmSelectPositionItem();
        if(data !=null && data.getProductSpecs()!=null){
            return StringUtils.getShowPriceSign(data.getProductSpecs().getPrice1(),data.getQuantity())  ;
        }

        return "0";
    }


    public void setmSelectPosition(int mSelectPosition) {
        this.mSelectPosition = mSelectPosition;
        notifyDataSetChanged();
    }

    public void setData(List<PayCarListModel.ListBean> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }
    }

    public void addData(List<PayCarListModel.ListBean> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }


    @Override
    protected void convert(ViewHolder viewHolder, PayCarListModel.ListBean item, int position) {

        if(item == null){
            return;
        }

        TextView txt_name=viewHolder.getView(R.id.txt_name);
        TextView txt_price=viewHolder.getView(R.id.txt_price);
        TextView txt_subtract=viewHolder.getView(R.id.txt_subtract);
        TextView txt_add=viewHolder.getView(R.id.txt_add);
        TextView txt_number=viewHolder.getView(R.id.txt_number);
        TextView tv_num_info=viewHolder.getView(R.id.tv_num_info);
        ImageView img_good=viewHolder.getView(R.id.img_good);
        ImageView img_select=viewHolder.getView(R.id.img_select);
        LinearLayout layout_delete=viewHolder.getView(R.id.layout_delete);

        setShowData(item, position, txt_name, txt_price, txt_number, img_good, img_select,tv_num_info);


        txt_subtract.setOnClickListener(v -> {
            if(item.getQuantity()>1){

                int quantity=item.getQuantity();

                quantity--;

                editCarpayInfo(item,quantity);
            }
        });

        txt_add.setOnClickListener(v -> {
            int quantity=item.getQuantity();
            quantity++;
            editCarpayInfo(item,quantity);
        });

        layout_delete.setOnClickListener(v -> {

            CommonDialog commonDialog = new CommonDialog(mContext).builder()
                    .setTitle("提示").setContentMsg("是否确认删除该订单?")
                    .setPositiveBtn("确定", view -> {
                        deleteRequest(position,item);
                    })
                    .setNegativeBtn("取消", null, false);

            commonDialog.show();

        });


    }

    /**
     *
     */
    private void editCarpayInfo(PayCarListModel.ListBean item,int quantity) {

        Map map=new HashMap();
        map.put("code",item.getCode());
        map.put("quantity",quantity+"");

        RetrofitUtils.getLoaderServer().DeletePayCar("808042",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mContext))
                .subscribe(isSuccessModes -> {
                    if(isSuccessModes!=null && isSuccessModes.isSuccess()){
                        item.setQuantity(quantity);
                        notifyDataSetChanged();
                        EventBusModel model=new EventBusModel();
                        model.setTag("ShopPayCarSelectActivityFresh");
                        EventBus.getDefault().post(model);
                    }
                },Throwable::printStackTrace);


    }

    /**
     * 删除订单请求
     * @param position
     * @param item
     */
    private void deleteRequest(int position,PayCarListModel.ListBean item) {

        List<String> codeList=new ArrayList<String>();

        codeList.add(item.getCode());

        Map map=new HashMap();
        map.put("cartCodeList",codeList);
        RetrofitUtils.getLoaderServer().DeletePayCar("808041",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mContext))
                .subscribe(isSuccessModes -> {
                    if(isSuccessModes!=null && isSuccessModes.isSuccess()){

                        if(position == mSelectPosition){
                            mSelectPosition=0;
                        }

                        mDatas.remove(position);
                        notifyDataSetChanged();
                        EventBusModel model=new EventBusModel();
                        model.setTag("ShopPayCarSelectActivityFresh");
                        EventBus.getDefault().post(model);

                    }
                },Throwable::printStackTrace);

    }

    /**
     * 设置显示数据
     * @param item
     * @param position
     * @param txt_name
     * @param txt_price
     * @param txt_number
     * @param img_good
     * @param img_select
     */
    private void setShowData(PayCarListModel.ListBean item, int position, TextView txt_name, TextView txt_price, TextView txt_number, ImageView img_good, ImageView img_select,TextView tv_num_info) {
        txt_name.setText(item.getProduct().getName());

        txt_number.setText(item.getQuantity()+"");

        tv_num_info.setText(item.getProductSpecs().getName()+" x "+item.getQuantity());

        if(mSelectPosition == position){
            ImgUtils.loadImgId(mContext, R.mipmap.pay_select,img_select);
        }else{
            ImgUtils.loadImgId(mContext,R.mipmap.un_select,img_select);
        }


        if(item.getProductSpecs() != null){
            txt_price.setText(StringUtils.getShowPriceSign(item.getProductSpecs().getPrice1()));

        }
        if(item.getProduct() != null){
           ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+item.getProduct().getSplitAdvPic(),img_good);
          }
    }

}
