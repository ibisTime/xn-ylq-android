package com.cdkj.ylq.module.certification;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.model.QiniuGetTokenModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.QiNiuUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityIdPicPutBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.qiniu.android.http.ResponseInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 身份证上传
 * Created by 李先俊 on 2017/9/4.
 */
public class IdInfoUpLoadCertActivity extends AbsBaseActivity {

    private ActivityIdPicPutBinding mBinding;

    //打开相机标识
    public final int PHOTOFLAG1 = 120;//正面
    public final int PHOTOFLAG2 = 121;//反面
    public final int PHOTOFLAG3 = 122;//手持

    //判断图片是否加载标识
    private boolean isLoadPic1;
    private boolean isLoadPic2;
    private boolean isLoadPic3;

    //图片本地地址
    public String mPicPath1;
    public String mPicPath2;
    public String mPicPath3;

    //图片七牛地址
    public String mPicQiURL1;
    public String mPicQiURL2;
    public String mPicQiURL3;

    private QiNiuUtil qiNiuUtil;

    private String mQiNiuToken;//七牛token

    private CerttificationInfoModel.InfoIdentifyPicBean mData;


    public static void open(Context context, CerttificationInfoModel.InfoIdentifyPicBean data, boolean isCheckCert) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, IdInfoUpLoadCertActivity.class);
        intent.putExtra("isCheckCert", isCheckCert);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_id_pic_put, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setTopTitle("身份认证");

        if (getIntent() != null) {

            mData=getIntent().getParcelableExtra("data");

            if (getIntent().getBooleanExtra("isCheckCert", false)) {
                mBinding.btnSubmit.setBackgroundResource(R.drawable.btn_no_click_gray);
                mBinding.btnSubmit.setEnabled(false);
            } else {
                mBinding.btnSubmit.setBackgroundResource(R.drawable.selector_login_btn);
                mBinding.btnSubmit.setEnabled(true);
            }
        }

        setSubLeftImgState(true);

        setShowData();

        qiNiuUtil = new QiNiuUtil(this);

        initListener();

    }

    private void setShowData() {
        if(mData==null) return;

        ImgUtils.loadActImg(this, MyConfig.IMGURL+mData.getIdentifyPic(),mBinding.imgIdcard);
        ImgUtils.loadActImg(this, MyConfig.IMGURL+mData.getIdentifyPicReverse(),mBinding.imgIdcardBack);
        ImgUtils.loadActImg(this, MyConfig.IMGURL+mData.getIdentifyPicHand(),mBinding.imgIdcardPeople);

    }


    private void initListener() {
        //身份证正面照
        mBinding.imgIdcard.setOnClickListener(v -> {
            ImageSelectActivity.launch(this, PHOTOFLAG1, ImageSelectActivity.SHOWPIC);
        });
        //身份证反面面照
        mBinding.imgIdcardBack.setOnClickListener(v -> {
            ImageSelectActivity.launch(this, PHOTOFLAG2, ImageSelectActivity.SHOWPIC);
        });
        //手持
        mBinding.imgIdcardPeople.setOnClickListener(v -> {
            ImageSelectActivity.launch(this, PHOTOFLAG3, ImageSelectActivity.SHOWPIC);
        });

        mBinding.btnSubmit.setOnClickListener(v -> {
            if (!isLoadPic1) {
                showToast("请添加身份证正面照");
                return;
            }
            if (!isLoadPic2) {
                showToast("请添加身份证反面照");
                return;
            }
            if (!isLoadPic3) {
                showToast("请添加手持正面照");
                return;
            }

            picSubmit();

        });

    }

    /**
     * 图片上传
     */
    private void picSubmit() {


        showLoadingDialog();

        qiNiuUtil.getQiniuToeknRequest().enqueue(new BaseResponseModelCallBack<QiniuGetTokenModel>(this) { //请求七牛token
            @Override
            protected void onSuccess(QiniuGetTokenModel mo, String SucMessage) {
                if (mo == null || TextUtils.isEmpty(mo.getUploadToken())) {
                    return;
                }
                mQiNiuToken = mo.getUploadToken();
                getPicUrl(1, mPicPath1);
            }

            @Override
            protected void onNull() {
                disMissLoading();
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
                disMissLoading();
            }

            @Override
            protected void onBuinessFailure(String code, String error) {
                disMissLoading();
            }

            @Override
            protected void onNoNet(String msg) {
                disMissLoading();
            }

            @Override
            protected void onFinish() {

            }
        });

    }

    /**
     * @param type 1 正面 2反面 3手持
     */
    public void getPicUrl(int type, String path) {
        //TODO 身份证图片上传是否需要压缩
        qiNiuUtil.uploadSingle(new QiNiuUtil.QiNiuCallBack() {
            @Override
            public void onSuccess(String key, ResponseInfo info, JSONObject res) {
                switch (type) {
                    case 1:
                        mPicQiURL1 = key;
                        getPicUrl(2, mPicPath2);
                        break;
                    case 2:
                        mPicQiURL2 = key;
                        getPicUrl(3, mPicPath3);
                        break;
                    case 3:
                        mPicQiURL3 = key;
                        picUrlUpLoadRequest();
                        break;
                }
            }

            @Override
            public void onFal(String info) {
                disMissLoading();

                switch (type) {
                    case 1:
                        showToast("身份证正面照上传错误,请重试");
                        break;
                    case 2:
                        showToast("身份证反面照上传错误,请重试");
                        break;
                    case 3:
                        showToast("身份证手持照上传错误,请重试");
                        break;
                }

            }

        }, path, mQiNiuToken);

    }

    /**
     * 把获取到的七牛url上传
     */
    private void picUrlUpLoadRequest() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("identifyPic", mPicQiURL1);
        map.put("identifyPicReverse", mPicQiURL2);
        map.put("identifyPicHand", mPicQiURL3);
        map.put("userId", SPUtilHelpr.getUserId());
        Call call = RetrofitUtils.getBaseAPiService().successRequest("623044", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    showToast("身份证图片上传成功");
                    finish();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        String path = data.getStringExtra(ImageSelectActivity.staticPath);
        if (requestCode == PHOTOFLAG1) {
            mPicPath1 = path;
            ImgUtils.loadActImgListener(IdInfoUpLoadCertActivity.this, path, mBinding.imgIdcard, new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    isLoadPic1 = true;
                    return false;
                }
            });


        } else if (requestCode == PHOTOFLAG2) {
            mPicPath2 = path;
            ImgUtils.loadActImgListener(IdInfoUpLoadCertActivity.this, path, mBinding.imgIdcardBack, new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    isLoadPic2 = true;
                    return false;
                }
            });

        } else if (requestCode == PHOTOFLAG3) {
            mPicPath3 = path;
            ImgUtils.loadActImgListener(IdInfoUpLoadCertActivity.this, path, mBinding.imgIdcardPeople, new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    isLoadPic3 = true;
                    return false;
                }
            });

        }
    }

}
