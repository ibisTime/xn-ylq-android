package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityStoreDetailsBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.model.DZUpdateModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreDetailsModel;
import com.chengdai.ehealthproject.uitls.AppUtils;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.PermissionHelper;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.chengdai.ehealthproject.weigit.appmanager.AppOhterManager;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**商户详情
 * Created by 李先俊 on 2017/6/12.
 */

public class StoredetailsActivity extends AbsStoreBaseActivity {

    private ActivityStoreDetailsBinding mBinding;

    private String storeCode;

    private StoreDetailsModel mStoreDetailsModel;

    private List<String> imgs;

    private PermissionHelper permissionHelper;
    /**
     * 打开当前页面
     * @param context
     * @param storeCode 商店编号
     */
    public static void open(Context context,String storeCode){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,StoredetailsActivity.class);

        intent.putExtra("storeCode",storeCode);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_store_details, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.store_details));
        permissionHelper = new PermissionHelper(this);
        if(getIntent() != null) storeCode=getIntent().getStringExtra("storeCode");

        setSubLeftImgState(true);

        getStoreDetailsRequest(storeCode);

        mBinding.btnPay.setOnClickListener(v -> {
            if(!SPUtilHelpr.isLogin(this)){
                return;
            }
            if(mStoreDetailsModel!=null){
                SurroundingPayActivity.open(this,mStoreDetailsModel.getRate1(),mStoreDetailsModel.getCode());
            }
        });


        mBinding.imgDz.setOnClickListener(v -> {

            if(!SPUtilHelpr.isLogin(this)){

                return;
            }

            Map map=new HashMap();
            map.put("storeCode",storeCode);
            map.put("type","1");
            map.put("userId", SPUtilHelpr.getUserId());

            //点赞和取消点赞
            RetrofitUtils.getLoaderServer().DZRequest("808240", StringUtils.getJsonToString(map))
                    .compose(RxTransformerHelper.applySchedulerResult(this))
                    .filter(isSuccessModes -> isSuccessModes!=null)
                    .subscribe(baseResponseModel -> {
                        if(mStoreDetailsModel!=null && baseResponseModel.isSuccess()){
                            if(mStoreDetailsModel.isDZ()){
                                ImgUtils.loadImgId(this,R.mipmap.good_hand_un,mBinding.imgDz);
                                mStoreDetailsModel.setTotalDzNum(mStoreDetailsModel.getTotalDzNum()-1);
                            }else if(! mStoreDetailsModel.isDZ()){
                                ImgUtils.loadImgId(this,R.mipmap.good_hand_,mBinding.imgDz);
                                mStoreDetailsModel.setTotalDzNum(mStoreDetailsModel.getTotalDzNum()+1);
                            }
                            mBinding.tvDzsum.setText(mStoreDetailsModel.getTotalDzNum()+"");
                            mStoreDetailsModel.setDZ(!mStoreDetailsModel.isDZ());


                            DZUpdateModel dz=new DZUpdateModel();

                            dz.setCode(mStoreDetailsModel.getCode());
                            dz.setDz(mStoreDetailsModel.isDZ());
                            dz.setDzSum(mStoreDetailsModel.getTotalDzNum());

                            EventBus.getDefault().post(dz); //SurroundingFragment 点赞效果刷新
                        }

                    },Throwable::printStackTrace);


        });

        mBinding.tvAddress.setOnClickListener(v -> {
            if(mStoreDetailsModel==null) return;
        });


        mBinding.layoutCallPhone.setOnClickListener(v -> {

            permissionHelper.requestPermissions(new PermissionHelper.PermissionListener() {
                @Override
                public void doAfterGrand(String... permission) {
                    AppUtils.callPhone(StoredetailsActivity.this,mBinding.tvPhoneNumber.getText().toString());
                }

                @Override
                public void doAfterDenied(String... permission) {
                    ToastUtil.show(StoredetailsActivity.this,"请授予拨打手机号权限");
                }
            }, Manifest.permission.CALL_PHONE);

        });

    }

    //权限处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    /**
     * 获取商户详情
     */
    public void getStoreDetailsRequest(String code){
        Map map=new HashMap();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("code",code);

       mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreDetails("808218", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(this))

               .filter(storeDetailsModel -> storeDetailsModel!=null)

                .subscribe(storeListModel -> {

                    mStoreDetailsModel=storeListModel;

                    setShowData(storeListModel);

                    if(!"2".equals(mStoreDetailsModel.getStatus())){
                        showWarnListen("店铺不处于上架状态，请联系商家",view -> {
                            finish();
                        });
                    }


                },Throwable::printStackTrace));
    }

    private void setShowData(StoreDetailsModel storeListModel) {
        imgs= StringUtils.splitAsList(storeListModel.getPic(),"\\|\\|");

        //设置图片集合
        mBinding.bannerStoreDetail.setImages(imgs);
        mBinding.bannerStoreDetail.setImageLoader(new GlideImageLoader());
        //banner设置方法全部调用完毕时最后调用
        mBinding.bannerStoreDetail.start();
        mBinding.bannerStoreDetail.startAutoPlay();


        mBinding.tvStoreName.setText(storeListModel.getName());
        mBinding.tvDescription.setText(storeListModel.getSlogan());

        mBinding.tvAddress.setText(storeListModel.getAddress());

        mBinding.tvPhoneNumber.setText(storeListModel.getBookMobile());

        mBinding.tvDzsum.setText(storeListModel.getTotalDzNum()+"");

        if(!TextUtils.isEmpty(storeListModel.getDescription())){
            AppOhterManager.showRichText(this,mBinding.tvTxtdescription,storeListModel.getDescription());
        }


        if(storeListModel.isDZ()){
            ImgUtils.loadImgId(this, R.mipmap.good_hand_,mBinding.imgDz);
        }else{
            ImgUtils.loadImgId(this,R.mipmap.good_hand_un,mBinding.imgDz);
        }
    }


    @Override
    protected void onDestroy() {
        mBinding.bannerStoreDetail.stopAutoPlay();
        super.onDestroy();
    }
}
