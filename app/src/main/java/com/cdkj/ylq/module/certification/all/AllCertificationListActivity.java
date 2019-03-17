package com.cdkj.ylq.module.certification.all;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.interfaces.LocationCallBackListener;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.model.LocationModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.GaoDeLocation;
import com.cdkj.baselibrary.utils.LocationHelper;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.PermissionHelper;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityAllCertificationListBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.SuccessModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.certification.AddressBookCertActivity;
import com.cdkj.ylq.module.certification.basisinfocert.BasisInfoCertificationActivity;
import com.cdkj.ylq.mpresenter.GetUserCertificationInfoListener;
import com.cdkj.ylq.mpresenter.GetUserCertificationPresenter;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.model.MxLoginCustom;
import com.moxie.client.model.MxParam;
import com.zqzn.idauth.sdk.DetectEngine;
import com.zqzn.idauth.sdk.ErrorCode;
import com.zqzn.idauth.sdk.FaceResultCallback;
import com.zqzn.idauth.sdk.IdResultCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 认证界面 认证列表
 */
public class AllCertificationListActivity extends AbsBaseActivity implements GetUserCertificationInfoListener, IdResultCallback, FaceResultCallback {

    private ActivityAllCertificationListBinding mBinding;
    private GetUserCertificationPresenter mCertInfoPresenter;
    private CerttificationInfoModel mCertData;

    private final String app_key = "nJXnQp568zYcnBdPQxC7TANqakUUCjRZqZK8TrwGt7";
    private final String secret_key = "887DE27B914988C9CF7B2DEE15E3EDF8";
    private final String moxieApiKey = "079bae4e7fd041b9a1d986e16e75e3e5";
    private DetectEngine detectEngine;
    private String faceImage;
    private String backImage;
    private String frontImage;
    private LocationHelper mapLocation;
    private PermissionHelper mHelper;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AllCertificationListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_all_certification_list, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        detectEngine = new DetectEngine();
        mHelper = new PermissionHelper(this);
        setSubLeftImgState(true);
        setTopTitle("认证");
        if (mCertInfoPresenter == null) {
            mCertInfoPresenter = new GetUserCertificationPresenter(this);
        }
//        mCertInfoPresenter.getCertInfo(true);
    }

    /**
     * 设置布局  显示的文字颜色状态
     * 0 未认证
     * 1 已认证
     * 3 认证中
     *
     * @param type
     * @param tv
     */
    private void setViewData(String type, TextView tv) {

        if (TextUtils.equals("1", type)) {
            tv.setText("已认证");
            tv.setTextColor(ContextCompat.getColor(this, R.color.cert_state_ok));
        } else if (TextUtils.equals("4", type)) {
            tv.setText("认证失败");
            tv.setTextColor(ContextCompat.getColor(this, R.color.guoqi));
        } else if (TextUtils.equals("3", type)) {
            tv.setText("认证中");
            tv.setTextColor(ContextCompat.getColor(this, R.color.guoqi));
        } else {
            tv.setText("未认证");
            tv.setTextColor(ContextCompat.getColor(this, R.color.txt_gray));
        }
    }

    private void initListener() {
        if (mCertData == null) {
            return;
        }

        /**
         * 定位
         */
        mBinding.llLocation.setOnClickListener(v -> {
            if (TextUtils.equals("0", mCertData.getLocationFlag()) || TextUtils.equals("4", mCertData.getLocationFlag())) {
                sendLocation();
            }
        });

        //基本信息
        mBinding.llBasic.setOnClickListener(v -> {
            if (TextUtils.equals("0", mCertData.getInfoPersonalFlag()) || TextUtils.equals("4", mCertData.getInfoPersonalFlag())) {
                BasisInfoCertificationActivity.open(this);
            }
        });
        //身份认证
        mBinding.llIdCard.setOnClickListener(v -> {
            //第一次启动,某些手机会出现黑屏,先进行权限申请再进入
            permissionRequest();
        });

        //支付宝
        mBinding.llZfb.setOnClickListener(v -> {
//            AliActivity.open(this);
            if (TextUtils.equals("0", mCertData.getInfoZfbFlag()) || TextUtils.equals("4", mCertData.getInfoZfbFlag())) {

                checkMoXiew("INFO_ZHIFUBAO", new MoxieLisener() {
                    @Override
                    public void OnSuccer() {
                        MxParam mxParam = new MxParam();
                        mxParam.setUserId(SPUtilHelpr.getUserId());
                        mxParam.setApiKey(moxieApiKey);
                        mxParam.setThemeColor("#0cb8ae");
                        mxParam.setBannerBgColor("#0cb8ae");
                        mxParam.setBannerTxtColor("#ffffff");
                        mxParam.setTaskType(MxParam.PARAM_TASK_ALIPAY);
                        MoxieSDK.getInstance().start(AllCertificationListActivity.this, mxParam, null);
                    }

                    @Override
                    public void OnError(String msg) {
                        UITipDialog.showFall(AllCertificationListActivity.this, msg);
                    }
                });
            }
        });

        //通讯录
        mBinding.llTxl.setOnClickListener(v -> {
            if (TextUtils.equals("0", mCertData.getInfoAddressBookFlag()) || TextUtils.equals("4", mCertData.getInfoAddressBookFlag())) {
                AddressBookCertActivity.open(this, TextUtils.equals("1", mCertData.getInfoAddressBookFlag()));
            }
        });
        //运营商
        mBinding.llOperator.setOnClickListener(v -> {
            if (TextUtils.equals("0", mCertData.getInfoCarrierFlag()) || TextUtils.equals("4", mCertData.getInfoCarrierFlag())) {
                checkMoXiew("INFO_CARRIER", new MoxieLisener() {
                    @Override
                    public void OnSuccer() {
                        //判断是否已经实名认证过了
                        if (TextUtils.equals("1", mCertData.getInfoZqznFlag())) {
                            //已经实名认证//携带数据自动填充信息
                            startOperatorRZ(true);
                        } else {
                            //直接去认证
                            startOperatorRZ(false);
                        }
                    }

                    @Override
                    public void OnError(String msg) {
                        UITipDialog.showFall(AllCertificationListActivity.this, msg);
                    }
                });
            }
//            OperatorActivity.open(this);
        });
    }

    private void startOperatorRZ(boolean isRZ) {
        MxParam mxParam = new MxParam();
        mxParam.setUserId(SPUtilHelpr.getUserId());
        mxParam.setApiKey(moxieApiKey);
        mxParam.setThemeColor("#0cb8ae");//设置SDK 主色调
        mxParam.setBannerBgColor("#0cb8ae");
        mxParam.setBannerTxtColor("#ffffff");
        mxParam.setTaskType(MxParam.PARAM_TASK_CARRIER);
        //没有实名认证就直接去认证
        if (!isRZ) {
            MoxieSDK.getInstance().start(this, mxParam, null);
            return;
        }
        //已经实名认证过了 弹窗提醒需不需要将信息携带过去
        CerttificationInfoModel.InfoZqznBean infoZqzn = mCertData.getInfoZqzn();
        CerttificationInfoModel.InfoZqznBean.ZqznInfoFrontBean zqznInfoFront = infoZqzn.getZqznInfoFront();
        //如果返回信息为空的话也直接去 去认证不携带信息
        if (infoZqzn == null || zqznInfoFront == null) {
            MoxieSDK.getInstance().start(this, mxParam, null);
            return;
        }
        MxLoginCustom loginCustom = new MxLoginCustom();
        Map<String, Object> loginParam = new HashMap<>();
        loginParam.put("phone", SPUtilHelpr.getUserPhoneNum());          // 手机号
        loginParam.put("name", zqznInfoFront.getName());               // 姓名
        loginParam.put("idcard", zqznInfoFront.getIdNo());              // 身份证
        loginCustom.setLoginParams(loginParam);
        mxParam.setLoginCustom(loginCustom);
        MoxieSDK.getInstance().start(this, mxParam, null);

//        CommonDialog commonDialog = new CommonDialog(this).builder()
//                .setTitle("请确认个人信息")
//                .setCancelable(true)//返回键可取消
//                .setCanceledOnTouchOutside(true)//点击空白可取消
//                .setContentMsg("姓名: " + zqznInfoFront.getName() + "\n\n" +
//                        "手机号: " + SPUtilHelpr.getUserPhoneNum() + "\n\n" +
//                        "身份证号码: " + zqznInfoFront.getIdNo())
//                .setPositiveBtn("是", view -> {
//                    MxLoginCustom loginCustom = new MxLoginCustom();
//                    Map<String, Object> loginParam = new HashMap<>();
//                    loginParam.put("phone", SPUtilHelpr.getUserPhoneNum());          // 手机号
//                    loginParam.put("name", zqznInfoFront.getName());               // 姓名
//                    loginParam.put("idcard", zqznInfoFront.getIdNo());  // 身份证
//                    loginCustom.setLoginParams(loginParam);
//                    mxParam.setLoginCustom(loginCustom);
//                    MoxieSDK.getInstance().start(this, mxParam, null);
//                }).setNegativeBtn("不是", view -> {
//                    MoxieSDK.getInstance().start(this, mxParam, null);
//                }, false);
//
//        commonDialog.show();
    }

    //认证结果的四个回调
    @Override
    public void getInfoSuccess(CerttificationInfoModel userCertInfo, String msg) {
        mCertData = userCertInfo;
        if (mCertData == null) {
            return;
        }
        initListener();

        setViewData("1", mBinding.tvPhoneType);

        setViewData(mCertData.getLocationFlag(), mBinding.tvLocationType);//定位认证

        setViewData(mCertData.getInfoZqznFlag(), mBinding.tvIdCardType);//身份认证

        setViewData(mCertData.getInfoPersonalFlag(), mBinding.tvBasicType); //基本信息

        setViewData(mCertData.getInfoCarrierFlag(), mBinding.tvOperatorType);//运营商

        setViewData(mCertData.getInfoZfbFlag(), mBinding.tvZfbType);//支付宝

        setViewData(mCertData.getInfoAddressBookFlag(), mBinding.tvTxlType); //通讯录

        setViewData(mCertData.getInfoCarrierFlag(), mBinding.tvOperatorType);//运营商
    }

    @Override
    public void getInfoFailed(String code, String msg) {
        ToastUtil.show(this, msg);
    }

    @Override
    public void startGetInfo(boolean showDialog) {
        if (showDialog) showLoadingDialog();
    }

    @Override
    public void endGetInfo(boolean showDialog) {
        if (showDialog) disMissLoading();
    }

    /**
     * 脸部照片回调
     *
     * @param faceResult
     */
    @Override
    public void notifyResult(FaceResult faceResult) {
        if (faceResult.result_code == ErrorCode.SUCCESS.getCode()) {
            upLoadfaceImage(faceResult.face_image);
        } else {
            ToastUtil.show(this, "获取人脸照片失败请重新验证");
        }

    }

    /**
     * 身份证照片
     *
     * @param idResult
     */
    @Override
    public void notifyResult(IdResult idResult) {
        if (idResult == null) {
            return;
        }

        if (idResult.result_code == ErrorCode.SUCCESS.getCode()) {
            Bitmap front_image = idResult.front_image;
            Bitmap back_image = idResult.back_image;
            Log.e("pppppp", "notifyResult: 正面照片" + front_image + "反面照片" + back_image);
            if (front_image == null || back_image == null) {
                UITipDialog.showFall(this, "获取照片失败,请重新尝试");
                return;
            }
            ArrayList<Bitmap> listBitmap = new ArrayList<>();
            listBitmap.add(front_image);
            listBitmap.add(back_image);
            upLoadIdCard(listBitmap);
        } else {
            ToastUtil.show(this, "获取人脸照片失败请重新验证");
        }

    }

    public void upLoadfaceImage(Bitmap face_image) {
        new QiNiuHelper(this).uploadSingleBitmap(new QiNiuHelper.QiNiuCallBack() {
            @Override
            public void onSuccess(String key) {
                faceImage = key;
                //提交
                if (check()) {
                    picUrlUpLoadRequest();
                } else {
                    ToastUtil.show(AllCertificationListActivity.this, "信息上传失败请重试");
                }
//                ImgUtils.loadActImg(AllCertificationListActivity.this, SPUtilHelpr.getQiNiuUrl() + key, mBinding.ivIdCardHold);
            }

            @Override
            public void onFal(String info) {
                UITipDialog.showInfo(AllCertificationListActivity.this, "上传失败请重新上传");
            }
        }, face_image);

    }

    private boolean check() {
        if (TextUtils.isEmpty(frontImage) || TextUtils.isEmpty(backImage) || TextUtils.isEmpty(faceImage)) {
            return false;
        }
        return true;
    }

    public void upLoadIdCard(ArrayList<Bitmap> listBitmap) {
        showLoadingDialog();
        new QiNiuHelper(this).upLoadListPicBitmap(listBitmap, new QiNiuHelper.upLoadListImageListener() {
            @Override
            public void onChange(int index, String url) {
                switch (index) {
                    case 0:
                        frontImage = url;
                    case 1:
                        backImage = url;
                        break;
                }
            }

            @Override
            public void onSuccess() {
//                UITipDialog.showSuccess(AllCertificationListActivity.this, "图片上传完成");
                //完成跳转 人脸界面
                disMissLoading();
                try {
                    if (detectEngine.face_liveness(AllCertificationListActivity.this, app_key, secret_key, 1, AllCertificationListActivity.this) != ErrorCode.SUCCESS.getCode())
                        Toast.makeText(getApplicationContext(), "接口调用失败", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFal(String info) {
                disMissLoading();
                UITipDialog.showSuccess(AllCertificationListActivity.this, info);
            }

            @Override
            public void onError(String info) {
                disMissLoading();
                UITipDialog.showSuccess(AllCertificationListActivity.this, info);
            }
        });
    }

    /**
     * 把获取到的七牛url上传
     */
    private void picUrlUpLoadRequest() {

        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("frontImage", frontImage);
        map.put("backImage", backImage);
        map.put("faceImage", faceImage);
        map.put("userId", SPUtilHelpr.getUserId());
//        map.put("realName", mBinding.etName.getText().toString());
//        map.put("idNo", mBinding.etIdNo.getText().toString());
        Call call = RetrofitUtils.getBaseAPiService().successRequest("623044", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                showToast("身份证信息认证成功");
                //刷新界面信息
                mCertInfoPresenter.getCertInfo(true);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    /**
     * 检查魔蝎账户是否有钱,有钱就继续认证,没钱就不能取认证,当账号没钱的时候 可能调取sdk会失败
     * <p>
     * 认证标志（INFO_ZQZN 智取职能人脸识别， INFO_CARRIER 魔蝎运营商，INFO_ZHIFUBAO 魔蝎支付宝认证）
     */
    private void checkMoXiew(String key, MoxieLisener moxieLisener) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("key", key);

        showLoadingDialog();
        Call<BaseResponseModel<SuccessModel>> infoMoney = RetrofitUtils.createApi(MyApiServer.class).getInfoMoney("623061", StringUtils.getJsonToString(map));
        infoMoney.enqueue(new BaseResponseModelCallBack<SuccessModel>(this) {
            @Override
            protected void onSuccess(SuccessModel data, String SucMessage) {
                moxieLisener.OnSuccer();
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
//                super.onReqFailure(errorCode, errorMessage);
                moxieLisener.OnError(errorMessage);
            }

            @Override
            protected void onBuinessFailure(String code, String error) {
//                super.onBuinessFailure(code, error);
                moxieLisener.OnError(error);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    /**
     * 定位
     */
    private void sendLocation() {
        showLoadingDialog();
        //                mLocationModel = locationModel;
//                mBinding.tvLocation.setText(mLocationModel.getProvince() + mLocationModel.getCity() + mLocationModel.getDistrict());
        mapLocation = new LocationHelper(this, new GaoDeLocation(), new LocationCallBackListener() {
            @Override
            public void locationSuccessful(LocationModel locationModel) {
                disMissLoading();
                //定位成功  调取接口保存信息
                submitLocationData(locationModel);
            }

            @Override
            public void locationFailure(String msg) {
                UITipDialog.showFall(AllCertificationListActivity.this, "定位失败,请授予权限,并检查定位模式是否已开启!");
                disMissLoading();
            }

            @Override
            public void noPermissions() {
                disMissLoading();
            }
        });
        mapLocation.startLocation();//开始定位
    }

    /**
     * 提交定位信息
     */
    private void submitLocationData(LocationModel locationModel) {

        Map map = new HashMap();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("province", locationModel.getProvince());
        map.put("city", locationModel.getCity());
        map.put("area", locationModel.getDistrict());
        String address = null;
        if (!TextUtils.isEmpty(locationModel.getStreet())) {
            address = locationModel.getStreet();
        }
        if (!TextUtils.isEmpty(locationModel.getStreetNum())) {
            address += locationModel.getStreetNum();
        }
        map.put("address", address);
        showLoadingDialog();
        Call<BaseResponseModel<SuccessModel>> baseResponseModelCall = RetrofitUtils.createApi(MyApiServer.class).submitLocationData("623062", StringUtils.getJsonToString(map));
        baseResponseModelCall.enqueue(new BaseResponseModelCallBack<SuccessModel>(this) {
            @Override
            protected void onSuccess(SuccessModel data, String SucMessage) {
                //提交成功刷新数据状态
                mCertInfoPresenter.getCertInfo(true);

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    /**
     * 权限申请
     */
    private void permissionRequest() {
        LogUtil.E("权限申请");
        mHelper.requestPermissions(new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {

                //身份认证  IdCardActivity.open(this);
                if (TextUtils.equals("0", mCertData.getInfoZqznFlag()) || TextUtils.equals("4", mCertData.getInfoZqznFlag())) {
                    checkMoXiew("INFO_ZQZN", new MoxieLisener() {
                        @Override
                        public void OnSuccer() {
                            try {
                                if (detectEngine.id_ocr(AllCertificationListActivity.this, app_key, secret_key, AllCertificationListActivity.this) != ErrorCode.SUCCESS.getCode())
                                    Toast.makeText(getApplicationContext(), "接口调用失败", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void OnError(String msg) {
                            UITipDialog.showFall(AllCertificationListActivity.this, msg);
                        }
                    });
                }
            }

            @Override
            public void doAfterDenied(String... permission) {
                disMissLoading();
                showToast("请授予权限");
            }
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mapLocation != null) {
            mapLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        if (mHelper != null) {
            mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCertInfoPresenter.getCertInfo(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //刷新的是上个界面的数据,就是认证的状态
        EventBus.getDefault().post("刷新界面数据");
    }


}

interface MoxieLisener {
    void OnSuccer();

    void OnError(String msg);
}
