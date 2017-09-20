package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHotelroomDetailsBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelListModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreDetailsModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.chengdai.ehealthproject.weigit.appmanager.AppOhterManager;


import java.util.List;

/**商户详情
 * Created by 李先俊 on 2017/6/12.
 */

public class HotelRoomDetailsActivity extends AbsStoreBaseActivity {

    private ActivityHotelroomDetailsBinding mBinding;

    private StoreDetailsModel mStoreDetailsModel;

    private List<String> imgs;

    private  HotelListModel.ListBean model;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, HotelListModel.ListBean model){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HotelRoomDetailsActivity.class);

        intent.putExtra("model",model);


        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hotelroom_details, null, false);

        addMainView(mBinding.getRoot());


        if(getIntent() != null){
            model=getIntent().getParcelableExtra("model");
        }

        setSubLeftImgState(true);


        setShowData(model);

//        getStoreDetailsRequest(storeCode);

        initViews();


    }

    private void initViews() {
        mBinding.btnPay.setOnClickListener(v -> {
            if(mStoreDetailsModel!=null)
                SurroundingPayActivity.open(this,mStoreDetailsModel.getRate1(),mStoreDetailsModel.getCode());
        });

    }


    /**
     * 获取商户详情
     */
    public void getStoreDetailsRequest(String code){
/*        Map map=new HashMap();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("code",code);

        mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreDetails("808218", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(this))

                .filter(storeDetailsModel -> storeDetailsModel!=null)

                .subscribe(storeListModel -> {

                    mStoreDetailsModel=storeListModel;

                    setShowData(storeListModel);

                },Throwable::printStackTrace));*/
    }

    private void setShowData(HotelListModel.ListBean storeListModel) {

        if(storeListModel == null){
            setTopTitle("房间详情");
            return;
        }

        setTopTitle(storeListModel.getName());
        imgs= StringUtils.splitAsList(storeListModel.getPic(),"\\|\\|");

        //设置图片集合
        mBinding.bannerStoreDetail.setImages(imgs);
        mBinding.bannerStoreDetail.setImageLoader(new GlideImageLoader());
        //banner设置方法全部调用完毕时最后调用
        mBinding.bannerStoreDetail.start();
        mBinding.bannerStoreDetail.startAutoPlay();


        mBinding.tvStoreName.setText(storeListModel.getName());
        mBinding.tvDescription.setText(storeListModel.getSlogan());

//        mBinding.tvAddress.setText(storeListModel.getAddress());
//        mBinding.tvPhoneNumber.setText(storeListModel.getBookMobile());

        if(!TextUtils.isEmpty(storeListModel.getDescription())){
            AppOhterManager.showRichText(this,mBinding.tvTxtdescription,storeListModel.getDescription());
        }

        mBinding.tvPrice.setText(getString(R.string.price_sing)+StringUtils.showPrice(storeListModel.getPrice()));

    }


    @Override
    protected void onDestroy() {
        mBinding.bannerStoreDetail.stopAutoPlay();
        super.onDestroy();
    }
}
