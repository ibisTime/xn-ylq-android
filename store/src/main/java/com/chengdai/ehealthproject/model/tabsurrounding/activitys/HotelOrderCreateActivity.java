package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHotelOrderCreateBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelListModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelOrderCreateModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelOrderPayModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**酒店订单创建
 * Created by 李先俊 on 2017/6/14.
 */

public class HotelOrderCreateActivity extends AbsStoreBaseActivity {

  private ActivityHotelOrderCreateBinding mBinding;

    private HotelListModel.ListBean mHotelModel;

    private  HotelOrderCreateModel mHotelOrderCreateModel;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, HotelOrderCreateModel hotelOrderCreateModel){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HotelOrderCreateActivity.class);
        intent.putExtra("hotelModel",hotelOrderCreateModel);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hotel_order_create, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("确认订单");

        if(getIntent()!=null){
            mHotelOrderCreateModel = getIntent().getParcelableExtra("hotelModel");
            mHotelModel = mHotelOrderCreateModel.getHotelInfoModel();
        }

        setSubLeftImgState(true);

        setShowData();

        mBinding.txtPay.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mBinding.editHotelCheckName.getText().toString())){
                showToast("请填写入住人姓名");
                return;
            }
            if(TextUtils.isEmpty(mBinding.editHotelCheckPhone.getText().toString())){
                showToast("请填写手机号");
                return;
            }

            createOrderRequest();

        });




    }

    /**
     *确认订单
     */
    private void createOrderRequest() {

        if(!SPUtilHelpr.isLogin(this)){

            return;
        }


        Map<String,String> map=new HashMap();

        map.put("productCode",mHotelModel.getCode());
        map.put("startDate",mHotelOrderCreateModel.getStartData());
        map.put("endDate",mHotelOrderCreateModel.getEndDate());
        map.put("reName",mBinding.editHotelCheckName.getText().toString());
        map.put("reMobile",mBinding.editHotelCheckPhone.getText().toString());
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("applyNote",mBinding.editHotelSp.getText().toString());

       mSubscription.add( RetrofitUtils.getLoaderServer().HotelOrderCreate("808450", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(this))

                .subscribe(codeModel -> {

                    if(codeModel!=null && !TextUtils.isEmpty(codeModel.getCode())){

                        HotelOrderPayModel payModel=new HotelOrderPayModel();
                        if(mHotelOrderCreateModel!=null) {
                            payModel.setDays(mHotelOrderCreateModel.getDays()+"");
                            payModel.setStartShowDate(mHotelOrderCreateModel.getStartShowData());
                            payModel.setEndShowDate(mHotelOrderCreateModel.getEndShowDate());
                        }
                        payModel.setOrderCode(codeModel.getCode());
                        payModel.setmHotelModel(mHotelModel);

                        payModel.setUseName(mBinding.editHotelCheckName.getText().toString());
                        payModel.setUsePhoee(mBinding.editHotelCheckPhone.getText().toString());
                        payModel.setUsePs(mBinding.editHotelSp.getText().toString());


                        HotelOrderPayActivity.open(this,payModel);

                        return;
                    }

                    showToast("确认失败");

                },Throwable::printStackTrace));

    }

    private void setShowData() {

        if(mHotelModel !=null ){
            ImgUtils.loadImgURL(this, MyConfig.IMGURL+mHotelModel.getSplitAdvPic(),mBinding.imgHotelInfo);

            mBinding.tvHotelSize.setText(mHotelModel.getName());
            mBinding.tvHotelInfo.setText(mHotelModel.getSlogan());
            mBinding.tvHotelNum.setText("1间");
            mBinding.txtDiscountMoney.setText(StringUtils.showPrice(mHotelModel.getPrice().multiply(new BigDecimal(mHotelOrderCreateModel.getDays())))+"");

            mHotelModel.setPrice(mHotelModel.getPrice().multiply(new BigDecimal(mHotelOrderCreateModel.getDays())));

        }

        if(mHotelOrderCreateModel!=null) {
            mBinding.tvHotelName.setText(mHotelOrderCreateModel.getHotelName());
            mBinding.tvHotelAddress.setText(mHotelOrderCreateModel.getHotenAddress());
            mBinding.tvHotelData.setText("入住 : "+mHotelOrderCreateModel.getStartShowData() +"  离店 : "+mHotelOrderCreateModel.getEndShowDate() +"  "+ mHotelOrderCreateModel.getDays()+"晚");
        }


    }
}
