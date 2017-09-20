package com.chengdai.ehealthproject.model.common.model.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.activitys.AddAddressActivity;
import com.chengdai.ehealthproject.model.healthstore.models.getOrderAddressModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class AddressAdapter extends CommonAdapter<getOrderAddressModel> {

    public AddressAdapter(Context context, List<getOrderAddressModel> datas) {
        super(context, R.layout.item_address, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, getOrderAddressModel item, int position) {

        if(item == null){
            return;
        }

        TextView txtName=viewHolder.getView(R.id.txt_name);
        TextView txtPhone=viewHolder.getView(R.id.txt_phone);
        TextView txtAddress=viewHolder.getView(R.id.txt_address);
        ImageView imgChoose=viewHolder.getView(R.id.img_choose);
        LinearLayout layoutEdit=viewHolder.getView(R.id.layout_edit);
        LinearLayout layoutDelete=viewHolder.getView(R.id.layout_delete);



        if ("1".equals(item.getIsDefault())) {
           imgChoose.setBackgroundResource(R.mipmap.address_choose);
        } else {
           imgChoose.setBackgroundResource(R.mipmap.address_unchoose);
        }

       txtName.setText(item.getAddressee());
       txtPhone.setText(item.getMobile());
       txtAddress.setText(item.getProvince() + " " + item.getCity() + " " + item.getDistrict() + "" + item.getDetailAddress());


        imgChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("0".equals(item.getIsDefault())) {
                    setDefaultAddressRequest(item);
                }
            }
        });

        //编辑
        layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAddressActivity.open(mContext,false,item);
            }
        });

        //删除
        layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePosition(item.getCode());
            }
            //添加

        });



    }

    /**
     * 删除
     */
    private void deletePosition(String po) {
        CommonDialog commonDialog = new CommonDialog(mContext).builder()
                .setTitle("提示").setContentMsg("是否确认删除该收货地址?")
                .setPositiveBtn("确定", view -> {
                    deleteRequest(po);
                })
                .setNegativeBtn("取消", null, false);

        commonDialog.show();
    }

    /**
     * 删除请求
     */
    private void deleteRequest(String code) {

        Map<String,String> object=new HashMap<>();

        object.put("code", code);
        object.put("token",SPUtilHelpr.getUserToken());
        object.put("systemCode", MyConfigStore.SYSTEMCODE);


        RetrofitUtils.getLoaderServer().AddressDelete("805161", StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(mContext))
                .subscribe(isSuccessModes -> {

                    if(isSuccessModes!=null && isSuccessModes.isSuccess()){
                        EventBusModel model=new EventBusModel();
                        model.setTag("AddressSelectRefesh");   //
                        EventBus.getDefault().post(model);
                    }

                },Throwable::printStackTrace);


    }

    public void setData(List<getOrderAddressModel> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }

    }

    public void addData(List<getOrderAddressModel> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置默认地址请求
     */
    public void setDefaultAddressRequest(getOrderAddressModel code) {

        Map<String ,String > map=new HashMap<>();

        map.put("code", code.getCode());
        map.put("token", SPUtilHelpr.getUserToken());
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("systemCode", MyConfigStore.SYSTEMCODE);


        RetrofitUtils.getLoaderServer().SetDefultAddress("805163", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(null))
                .filter(isSuccessModes -> isSuccessModes ) //设置默认地址成功时
                .subscribe(datas -> {

                    ToastUtil.show(mContext,"选择地址成功");

                    EventBusModel model=new EventBusModel();
                    model.setTag("AddressSelectRefesh");   //
                    EventBus.getDefault().post(model);
                    EventBus.getDefault().post(code); //设置支付页面默认地址
                });

/*        RetrofitUtils.getLoaderServer().SetDefultAddress("805163", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mContext))
                .filter(isSuccessModes -> isSuccessModes ) //设置默认地址成功时
                .map(isSuccessModes -> {
                    Map<String,String> map2=new HashMap<>();
                    map2.put("userId",SPUtilHelpr.getUserId());
                    map2.put("token", SPUtilHelpr.getUserToken());
                    return map2;
                })
                .flatMap(requestData ->  RetrofitUtils.getLoaderServer().GetAddress("805165", StringUtils.getJsonToString(requestData)))
                .compose(RxTransformerListHelper.applySchedulerResult(null))
                .subscribe(datas -> {
                    mDatas=datas;
                    notifyDataSetChanged();

                });*/
    }



}
