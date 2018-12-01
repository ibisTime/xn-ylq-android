package com.cdkj.ylq.module.certification.all;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.dialog.UITipDialog;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.QiNiuHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityIdCardBinding;
import com.zqzn.idauth.sdk.DetectEngine;
import com.zqzn.idauth.sdk.ErrorCode;
import com.zqzn.idauth.sdk.FaceResultCallback;
import com.zqzn.idauth.sdk.IdResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class IdCardActivity extends AbsBaseActivity implements IdResultCallback, FaceResultCallback {

//    private static final int PHOTOFLAG1 = 1;
//    private static final int PHOTOFLAG2 = 2;
//    private static final int PHOTOFLAG3 = 3;
//    private String mQiNiuToken;
//    private QiNiuHelper qiNiuHelper;
    ActivityIdCardBinding mBinding;
    private String mPicQiURL1;
    private String mPicQiURL2;
    private String mPicQiURL3;
    private DetectEngine detectEngine;
    String app_key = "nJXnQp568zYcnBdPQxC7TANqakUUCjRZqZK8TrwGt7";
    String secret_key = "887DE27B914988C9CF7B2DEE15E3EDF8";

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, IdCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_id_card, null, false);
        detectEngine = new DetectEngine();
        setSubLeftImgState(true);
        setTopTitle("身份证认证");
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initListener();
    }

    private void initListener() {
        //身份证正面照
        mBinding.flJust.setOnClickListener(v -> {
            try {
                if (detectEngine.id_ocr(IdCardActivity.this, app_key, secret_key, IdCardActivity.this) != ErrorCode.SUCCESS.getCode())
                    Toast.makeText(getApplicationContext(), "接口调用失败", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
//            ImageSelectActivity.launch(this, PHOTOFLAG1, ImageSelectActivity.SHOWPIC, false);
        });
        //身份证反面面照
        mBinding.flBack.setOnClickListener(v -> {
            try {
                if (detectEngine.id_ocr(IdCardActivity.this, app_key, secret_key, IdCardActivity.this) != ErrorCode.SUCCESS.getCode())
                    Toast.makeText(getApplicationContext(), "接口调用失败", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
//            ImageSelectActivity.launch(this, PHOTOFLAG2, ImageSelectActivity.SHOWPIC, false);
        });
        //手持
        mBinding.flHold.setOnClickListener(v -> {
            try {
                if (detectEngine.face_liveness(IdCardActivity.this, app_key, secret_key, 1, IdCardActivity.this) != ErrorCode.SUCCESS.getCode())
                    Toast.makeText(getApplicationContext(), "接口调用失败", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
//            ImageSelectActivity.launch(this, PHOTOFLAG3, ImageSelectActivity.SHOWPIC, false);
        });

        mBinding.btnConfirm.setOnClickListener(v -> {
            if (check()) {
                picUrlUpLoadRequest();
            }
        });
    }

    private boolean check() {
        if (TextUtils.isEmpty(mPicQiURL1)) {
            ToastUtil.show(this, "请上传身份证照片");
            return false;
        }

        if (TextUtils.isEmpty(mPicQiURL2)) {
            ToastUtil.show(this, "请上传身份证照片");
            return false;
        }

        if (TextUtils.isEmpty(mPicQiURL3)) {
            ToastUtil.show(this, "请上传人脸照片");
            return false;
        }
//        if (TextUtils.isEmpty(mBinding.etName.getText().toString())) {
//            ToastUtil.show(this, "请填写姓名");
//            return false;
//        }
//
//        if (TextUtils.isEmpty(mBinding.etIdNo.getText().toString())) {
//            ToastUtil.show(this, "请填写身份证号");
//            return false;
//        }
        return true;
    }


    /**
     * 把获取到的七牛url上传
     */
    private void picUrlUpLoadRequest() {

        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("frontImage", mPicQiURL1);
        map.put("backImage", mPicQiURL2);
        map.put("faceImage", mPicQiURL3);
        map.put("userId", SPUtilHelpr.getUserId());
//        map.put("realName", mBinding.etName.getText().toString());
//        map.put("idNo", mBinding.etIdNo.getText().toString());
        Call call = RetrofitUtils.getBaseAPiService().successRequest("623044", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                showToast("身份证信息认证成功");
                finish();
//                if (data.isSuccess()) {
//                EventBus.getDefault().post(EventTags.IDCARDCERTINFOREFRESH);
//
//                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    /**
     * 身份证 信息返回回调
     *
     * @param idResult
     */
    @Override
    public void notifyResult(IdResult idResult) {
        Bitmap front_image = idResult.front_image;
        Bitmap back_image = idResult.back_image;
        ArrayList<Bitmap> listBitmap = new ArrayList<>();
        listBitmap.add(front_image);
        listBitmap.add(back_image);

        new QiNiuHelper(this).upLoadListPicBitmap(listBitmap, new QiNiuHelper.upLoadListImageListener() {
            @Override
            public void onChange(int index, String url) {
                switch (index) {
                    case 0:
                        mPicQiURL1 = url;
                        ImgUtils.loadActImg(IdCardActivity.this, SPUtilHelpr.getQiNiuUrl() + url, mBinding.ivIdCardJust);
                    case 1:
                        mPicQiURL2 = url;
                        ImgUtils.loadActImg(IdCardActivity.this, SPUtilHelpr.getQiNiuUrl() + url, mBinding.ivIdCardBack);
                        break;
                }
            }

            @Override
            public void onSuccess() {
                UITipDialog.showSuccess(IdCardActivity.this, "图片上传完成");
            }

            @Override
            public void onFal(String info) {
                UITipDialog.showSuccess(IdCardActivity.this, info);
            }

            @Override
            public void onError(String info) {
                UITipDialog.showSuccess(IdCardActivity.this, info);
            }
        });
    }

    /**
     * 脸部照片回调
     *
     * @param faceResult
     */
    @Override
    public void notifyResult(FaceResult faceResult) {
        Bitmap face_image = faceResult.face_image;
        new QiNiuHelper(this).uploadSingleBitmap(new QiNiuHelper.QiNiuCallBack() {
            @Override
            public void onSuccess(String key) {
                mPicQiURL3 = key;
                ImgUtils.loadActImg(IdCardActivity.this, SPUtilHelpr.getQiNiuUrl() + key, mBinding.ivIdCardHold);
            }

            @Override
            public void onFal(String info) {
                UITipDialog.showInfo(IdCardActivity.this, "上传失败请重新上传");

            }
        }, face_image);
    }


//========================下面代码是从相册中选择照片,已经改成为人脸识别===========================
//
//    /**
//     * @param type
//     */
//    public void getPicUrl(int type, String path) {
//        //TODO 身份证图片上传是否需要压缩
//        showLoadingDialog();
//
//        qiNiuHelper.uploadSinglePic(new QiNiuHelper.QiNiuCallBack() {
//            @Override
//            public void onSuccess(String key) {
//                disMissLoading();
//                switch (type) {
//                    case PHOTOFLAG1:
//                        mPicQiURL1 = key;
//                        ImgUtils.loadActImg(IdCardActivity.this, SPUtilHelpr.getQiNiuUrl() + key, mBinding.ivIdCardJust);
//                        break;
//                    case PHOTOFLAG2:
//                        mPicQiURL2 = key;
//                        ImgUtils.loadActImg(IdCardActivity.this, SPUtilHelpr.getQiNiuUrl() + key, mBinding.ivIdCardBack);
//                        break;
//                    case PHOTOFLAG3:
//                        mPicQiURL3 = key;
//                        ImgUtils.loadActImg(IdCardActivity.this, SPUtilHelpr.getQiNiuUrl() + key, mBinding.ivIdCardHold);
//                        break;
//                }
//            }
//
//            @Override
//            public void onFal(String info) {
//                disMissLoading();
//                switch (type) {
//                    case PHOTOFLAG1:
//                        showToast("身份证正面照上传错误,请重试");
//                        break;
//                    case PHOTOFLAG2:
//                        showToast("身份证反面照上传错误,请重试");
//                        break;
//                    case PHOTOFLAG3:
//                        showToast("身份证手持照上传错误,请重试");
//                        break;
//                }
//            }
//        }, path);
//    }
//
//
//    /**
//     * 获取七牛Token
//     *
//     * @param type
//     * @param path
//     */
//    public void getQiniuToken(int type, String path) {
//
//        if (!TextUtils.isEmpty(mQiNiuToken)) { //如果已经获取到token直接 上传图片
//            getPicUrl(type, path);
//            return;
//        }
//
//        Call call = qiNiuHelper.getQiniuToeknRequest();
//        addCall(call);
//        showLoadingDialog();
//        call.enqueue(new BaseResponseModelCallBack<QiniuGetTokenModel>(this) {
//            @Override
//            protected void onSuccess(QiniuGetTokenModel data, String SucMessage) {
//                if (data == null || TextUtils.isEmpty(data.getUploadToken())) {
//                    LogUtil.E("服务器连接失败,请重试");
//                    return;
//                }
//                mQiNiuToken = data.getUploadToken();
//                getPicUrl(type, path);
//            }
//
//            @Override
//            protected void onFinish() {
//                disMissLoading();
//            }
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK || data == null) {
//            return;
//        }
//
//        String path = data.getStringExtra(ImageSelectActivity.staticPath);
//
//        if (TextUtils.isEmpty(path)) {
//            switch (requestCode) {
//                case PHOTOFLAG1:
//                    showToast("身份证正面照上传错误,请重新拍摄");
//                    break;
//                case PHOTOFLAG2:
//                    showToast("身份证反面照上传错误,请重新拍摄");
//                    break;
//                case PHOTOFLAG3:
//                    showToast("身份证手持照上传错误,请重新拍摄");
//                    break;
//            }
//            return;
//        }
//        getQiniuToken(requestCode, path);
//    }
}
