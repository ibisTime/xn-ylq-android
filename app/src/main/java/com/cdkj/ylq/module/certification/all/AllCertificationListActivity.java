package com.cdkj.ylq.module.certification.all;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityAllCertificationListBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
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

        setSubLeftImgState(true);
        setTopTitle("认证");
        if (mCertInfoPresenter == null) {
            mCertInfoPresenter = new GetUserCertificationPresenter(this);
        }
//        mCertInfoPresenter.getCertInfo(true);
    }

    /**
     * 设置布局  显示的文字颜色状态
     *
     * @param type
     * @param text
     * @param tv
     */
    private void setViewData(String type, String text, TextView tv) {
        tv.setText(text);
        if (TextUtils.equals("1", type)) {
            tv.setTextColor(ContextCompat.getColor(this, R.color.cert_state_ok));
        } else if (TextUtils.equals("2", type)) {
            tv.setTextColor(ContextCompat.getColor(this, R.color.guoqi));
        } else if (TextUtils.equals("3", type)) {
            tv.setTextColor(ContextCompat.getColor(this, R.color.guoqi));
        } else {
            tv.setTextColor(ContextCompat.getColor(this, R.color.txt_gray));
        }
    }

    private void initListener() {
        if (mCertData == null) {
            return;
        }
        mBinding.llBasic.setOnClickListener(v -> {
            if (TextUtils.equals("0", mCertData.getInfoPersonalFlag())) {
                BasisInfoCertificationActivity.open(this);
            }
        });

        mBinding.llIdCard.setOnClickListener(v -> {
            //身份认证  IdCardActivity.open(this);
            if (TextUtils.equals("0", mCertData.getInfoZqznFlag())) {
                try {
                    if (detectEngine.id_ocr(AllCertificationListActivity.this, app_key, secret_key, AllCertificationListActivity.this) != ErrorCode.SUCCESS.getCode())
                        Toast.makeText(getApplicationContext(), "接口调用失败", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        mBinding.llMailList.setOnClickListener(v -> {
            AddressBookCertActivity.open(this, false);
        });
        //支付宝
        mBinding.llZfb.setOnClickListener(v -> {
//            AliActivity.open(this);
            if (TextUtils.equals("0", mCertData.getInfoZfbFlag())) {
                MxParam mxParam = new MxParam();
                mxParam.setUserId(SPUtilHelpr.getUserId());
                mxParam.setApiKey(moxieApiKey);
                mxParam.setThemeColor("#0cb8ae");
                mxParam.setBannerBgColor("#0cb8ae");
                mxParam.setBannerTxtColor("#ffffff");
                mxParam.setTaskType(MxParam.PARAM_TASK_ALIPAY);
                MoxieSDK.getInstance().start(this, mxParam, null);
            }
        });
        //通讯录
        mBinding.llTxl.setOnClickListener(v -> {
            if (TextUtils.equals("0", mCertData.getInfoAddressBookFlag())) {
                AddressBookCertActivity.open(this, TextUtils.equals("1", mCertData.getInfoAddressBookFlag()));
            }
        });
        //运营商
        mBinding.llOperator.setOnClickListener(v -> {
            if (TextUtils.equals("0", mCertData.getInfoCarrierFlag())) {
                //判断是否已经实名认证过了
                if (TextUtils.equals("1", mCertData.getInfoZqznFlag())) {
                    //已经实名认证//携带数据自动填充信息
                    startOperatorRZ(true);
                } else {
                    //直接去认证
                    startOperatorRZ(false);
                }
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
        loginParam.put("idcard", zqznInfoFront.getIdNo());  // 身份证
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
        setViewData("1", "已认证", mBinding.tvPhoneType);
        setViewData("1", "已认证", mBinding.tvLocationType);

        if (TextUtils.equals("1", mCertData.getInfoZqznFlag())) { //身份认证
            setViewData("1", "已认证", mBinding.tvIdCardType);
        } else if (TextUtils.equals("2", mCertData.getInfoZqznFlag())) {
            setViewData("2", "已过期", mBinding.tvIdCardType);
        } else if (TextUtils.equals("3", mCertData.getInfoZqznFlag())) {
            setViewData("3", "认证中", mBinding.tvIdCardType);
        } else {
            setViewData("", "未认证", mBinding.tvIdCardType);
        }

        if (TextUtils.equals("1", mCertData.getInfoPersonalFlag())) { //基本信息
            setViewData("1", "已认证", mBinding.tvBasicType);

        } else if (TextUtils.equals("2", mCertData.getInfoPersonalFlag())) {
            setViewData("2", "已过期", mBinding.tvBasicType);
        } else if (TextUtils.equals("3", mCertData.getInfoPersonalFlag())) {
            setViewData("3", "认证中", mBinding.tvBasicType);
        } else {
            setViewData("", "未认证", mBinding.tvBasicType);
        }

        if (TextUtils.equals("1", mCertData.getInfoCarrierFlag())) { //运营商
            setViewData("1", "已认证", mBinding.tvOperatorType);

        } else if (TextUtils.equals("2", mCertData.getInfoIdentifyFlag())) {
            setViewData("2", "已过期", mBinding.tvOperatorType);
        } else if (TextUtils.equals("3", mCertData.getInfoIdentifyFlag())) {
            setViewData("3", "认证中", mBinding.tvOperatorType);
        } else {
            setViewData("", "未认证", mBinding.tvOperatorType);
        }

        if (TextUtils.equals("1", mCertData.getInfoZfbFlag())) { //支付宝
            setViewData("1", "已认证", mBinding.tvZfbType);

        } else if (TextUtils.equals("2", mCertData.getInfoIdentifyFlag())) {
            setViewData("2", "已过期", mBinding.tvZfbType);
        } else if (TextUtils.equals("3", mCertData.getInfoIdentifyFlag())) {
            setViewData("3", "认证中", mBinding.tvZfbType);
        } else {
            setViewData("", "未认证", mBinding.tvZfbType);
        }

        if (TextUtils.equals("1", mCertData.getInfoAddressBookFlag())) { //通讯录
            setViewData("1", "已认证", mBinding.tvTxlType);

        } else if (TextUtils.equals("2", mCertData.getInfoAddressBookFlag())) {
            setViewData("2", "已过期", mBinding.tvTxlType);
        } else if (TextUtils.equals("3", mCertData.getInfoAddressBookFlag())) {
            setViewData("3", "认证中", mBinding.tvTxlType);
        } else {
            setViewData("", "未认证", mBinding.tvTxlType);
        }

        if (TextUtils.equals("1", mCertData.getInfoCarrierFlag())) { //运营商
            setViewData("1", "已认证", mBinding.tvOperatorType);

        } else if (TextUtils.equals("2", mCertData.getInfoCarrierFlag())) {
            setViewData("2", "已过期", mBinding.tvOperatorType);
        } else if (TextUtils.equals("3", mCertData.getInfoCarrierFlag())) {
            setViewData("3", "认证中", mBinding.tvOperatorType);
        } else {
            setViewData("", "未认证", mBinding.tvOperatorType);
        }

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
     * 脸部照片
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

        if (idResult.result_code == ErrorCode.SUCCESS.getCode()) {
            Bitmap front_image = idResult.front_image;
            Bitmap back_image = idResult.back_image;
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

    @Override
    protected void onResume() {
        super.onResume();
        mCertInfoPresenter.getCertInfo(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post("刷新界面数据");
    }
}
