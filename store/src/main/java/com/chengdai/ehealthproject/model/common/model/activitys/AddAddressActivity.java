package com.chengdai.ehealthproject.model.common.model.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityAddAddressBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthstore.models.getOrderAddressModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**添加收货地址
 * Created by 李先俊 on 2017/6/17.
 */

public class AddAddressActivity extends AbsStoreBaseActivity {

    private ActivityAddAddressBinding mBinding;
    private String mProvince;
    private String mCity;
    private String mDistrict;

    private boolean isDefault;

    private getOrderAddressModel mShowData;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, boolean isDefault, getOrderAddressModel data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,AddAddressActivity.class);

        intent.putExtra("isDefault",isDefault);
        intent.putExtra("data",data);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_add_address, null, false);

        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);

        setTopTitle("添加地址");

        if(getIntent() !=null) {
            isDefault=getIntent().getBooleanExtra("isDefault",true);
            mShowData=getIntent().getParcelableExtra("data");
        }


        setShowData();

        getLocationInfo();

        initViews();


    }

    /**
     * 设置传递过来的数据
     */
    private void setShowData() {

        if(mShowData == null){
            return;
        }

        mProvince=mShowData.getProvince();
        mCity=mShowData.getCity();
        mDistrict=mShowData.getDistrict();
        mBinding.txtAddress.setText(mShowData.getProvince()+" "+mShowData.getCity()+" "+mShowData.getDistrict());
        mBinding.edtName.setText(mShowData.getAddressee());
        mBinding.edtPhone.setText(mShowData.getMobile());
        mBinding.edtDetailed.setText(mShowData.getDetailAddress());


    }

    private void initViews() {

        mBinding.txtAddress.setText(mProvince +" "+mCity+"  "+mDistrict);

        mBinding.txtAddress.setOnClickListener(v -> {
            cityPicker();
        });

        mBinding.txtConfirm.setOnClickListener(v -> {

            if(TextUtils.isEmpty(mBinding.edtName.getText().toString())){
                showToast("请输入姓名");
                return;
            }

            if(TextUtils.isEmpty(mBinding.edtPhone.getText().toString())){
                showToast("请输入手机号");
                return;
            }

            if(TextUtils.isEmpty(mBinding.txtAddress.getText().toString())){
                showToast("请选择省市区");
                return;
            }

          if(TextUtils.isEmpty(mBinding.edtDetailed.getText().toString())){
                showToast("请输入详细地址");
                return;
            }

            if(mShowData != null){  //编辑地址
                editAddressRequest();
            }else{                     //新增地址
                addAressRequest();
            }
        });
    }


    private  void editAddressRequest(){
        Map<String,String> object=new HashMap<>();
        object.put("code", mShowData.getCode());
        object.put("userId", SPUtilHelpr.getUserId());
        object.put("addressee",mBinding.edtName.getText().toString().trim());
        object.put("mobile", mBinding.edtPhone.getText().toString().trim());
        object.put("province", mProvince);
        object.put("city", mCity);
        object.put("district", mDistrict);
        object.put("detailAddress", mBinding.edtDetailed.getText().toString().trim());
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyConfigStore.SYSTEMCODE);

      mSubscription.add(  RetrofitUtils.getLoaderServer().editAddress("805162", StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(isSuccessModes -> {
                    if(isSuccessModes!=null && isSuccessModes.isSuccess())
                    {
                        showToast("编辑成功");
                        EventBusModel model=new EventBusModel();
                        model.setTag("AddressSelectRefesh");   //AddressSelectActivity
                        EventBus.getDefault().post(model);

                        finish();
                    }

                },Throwable::printStackTrace));


    }


    /**
     * 添加地址请求
     */
    private void addAressRequest() {

        Map<String,String> object=new HashMap<>();

        if (isDefault) {

            object.put("isDefault", "1");
        } else {
            object.put("isDefault", "0");
        }
        object.put("userId",SPUtilHelpr.getUserId());
        object.put("addressee",mBinding.edtName.getText().toString().trim());
        object.put("mobile", mBinding.edtPhone.getText().toString().trim());
        object.put("province", mProvince);
        object.put("city", mCity);
        object.put("district", mDistrict);
        object.put("detailAddress", mBinding.edtDetailed.getText().toString().trim());
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyConfigStore.SYSTEMCODE);

       mSubscription.add( RetrofitUtils.getLoaderServer().AddAddress("805160", StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
               .filter(codeModel -> codeModel!=null && !TextUtils.isEmpty(codeModel.getCode()))
                .subscribe(codeModel -> {
                    showToast("添加成功");

                    EventBusModel model=new EventBusModel();
                    model.setTag("AddressSelectRefesh");   //AddressSelectActivity
                    EventBus.getDefault().post(model);

                    finish();

                },Throwable::printStackTrace));

    }



    private void cityPicker(){

        CityPicker cityPicker = new CityPicker.Builder(AddAddressActivity.this)
                .textSize(18)
                .titleBackgroundColor("#ffffff")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#FE4332")
                .cancelTextColor("#FE4332")
                .province(mProvince)
                .city(mCity)
                .district(mDistrict)
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                mProvince = citySelected[0];
                //城市
                mCity = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                mDistrict = citySelected[2];
                //邮编
                String code = citySelected[3];

                mBinding.txtAddress.setText(mProvince+" "+ mCity +" "+ mDistrict);
            }

            @Override
            public void onCancel() {

            }
        });
    }


    public void getLocationInfo() {

/*        LocationModel mLocationModel=  SPUtilHelpr.getLocationInfo();

        if(mLocationModel != null){
            mProvince =mLocationModel.getProvinceName();

            mCity =mLocationModel.getCityName();

            mDistrict =mLocationModel.getAreaName();
        }*/

        if(!TextUtils.isEmpty(mProvince) && !TextUtils.isEmpty(mCity) && !TextUtils.isEmpty(mDistrict)){

        }else{
            mProvince="北京市";
            mCity="北京市";
            mDistrict="昌平区";
        }


    }
}
