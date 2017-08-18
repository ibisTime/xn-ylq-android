package com.cdkj.ylq.module.certification;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cdkj.baselibrary.MyConfig;
import com.cdkj.baselibrary.activitys.ImageSelectActivity;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.QiNiuUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityBasisInfoCertBinding;
import com.cdkj.ylq.databinding.ActivityIdCertBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.module.user.userinfo.PersonalActivity;
import com.cdkj.ylq.mpresenter.getUserCertificationInfoListener;
import com.cdkj.ylq.mpresenter.getUserCertificationPresenter;
import com.qiniu.android.http.ResponseInfo;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.EventTags.IDCARDCERTINFOREFRESH;

/**
 * 身份证认证
 * Created by 李先俊 on 2017/8/9.
 */

public class IdCardCertificationActivity extends AbsBaseActivity implements getUserCertificationInfoListener {

    private ActivityIdCertBinding mBinding;

    public static final int PHOTOFLAG = 120;

    private CerttificationInfoModel mCertData;

    private getUserCertificationPresenter mCertInfoGetPersenter;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, IdCardCertificationActivity.class));
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_id_cert, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        hideAllNoTitle();

        setSubLeftImgState(true);

        setTopTitle("身份认证");

        initListener();

        mCertInfoGetPersenter = new getUserCertificationPresenter(this);
        mCertInfoGetPersenter.getCertInfo();

    }

    //
    private void initListener() {
        //相片
        mBinding.fralayoutIdCard.setOnClickListener(v -> {
            ImageSelectActivity.launch(this, PHOTOFLAG);
        });
        //zm认证
        mBinding.fralayoutFaceCert.setOnClickListener(v -> {
            if (mCertData == null) return;
            ZMCertificationActivity.open(this, mCertData.getInfoIdentify());
        });
        //
        mBinding.butSubmit.setOnClickListener(v -> {

            if (mCertData == null){
                return;
            }

            if (!TextUtils.equals("1", mCertData.getInfoIdentifyPicFlag())) {
                showToast("请提交身份证");
                return;
            }
            if (!TextUtils.equals("1", mCertData.getInfoIdentifyFaceFlag())) {
                showToast("请进行人脸识别");
                return;
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", SPUtilHelpr.getUserId());
            Call call = RetrofitUtils.getBaseAPiService().successRequest("623049", StringUtils.getJsonToString(map));
            addCall(call);

            showLoadingDialog();

            call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
                @Override
                protected void onSuccess(IsSuccessModes data, String SucMessage) {
                    if(data.isSuccess()){
                        finish();
                    }else{
                        showToast("提交失败,请重试");
                    }
                }

                @Override
                protected void onFinish() {
                    disMissLoading();
                }
            });


        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == PHOTOFLAG) {
            String path = data.getStringExtra(ImageSelectActivity.staticPath);
            LogUtil.E("图片"+path);
            new QiNiuUtil(IdCardCertificationActivity.this).getQiniuURL(new QiNiuUtil.QiNiuCallBack() {
                @Override
                public void onSuccess(String key, ResponseInfo info, JSONObject res) {
                    LogUtil.E("图片success");
                    upLoadIdCard(key);
                }

                @Override
                public void onFal(String info) {
                    showToast(info);
                }
            }, path);

        }
    }

    /**
     * 上传身分证
     *
     * @param key
     */
    private void upLoadIdCard(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("identifyPic", key);
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623044", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {

                    showToast("身份证上传成功");

                    ImgUtils.loadActImg(IdCardCertificationActivity.this, MyConfig.IMGURL + key, mBinding.imgIdCard);

                    if (mCertInfoGetPersenter != null) {
                        mCertInfoGetPersenter.getCertInfo();
                    }
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }


    private void setShowDataState() {
        if (mCertData == null) return;

        if (mCertData.getInfoIdentifyPic() != null) {
            ImgUtils.loadActImg(IdCardCertificationActivity.this, MyConfig.IMGURL + mCertData.getInfoIdentifyPic().getIdentifyPic(), mBinding.imgIdCard);
        }

        if (TextUtils.equals("1", mCertData.getInfoIdentifyPicFlag())) {
            mBinding.tvIdcardState.setText("已认证");
            mBinding.tvIdcardState.setTextColor(ContextCompat.getColor(this, R.color.cert_state_ok));
            mBinding.imgState.setImageResource(R.drawable.cert_ok_2);

        } else {
            mBinding.tvIdcardState.setText("前往提交");
            mBinding.imgState.setImageResource(R.drawable.can_submit);
            mBinding.tvIdcardState.setTextColor(ContextCompat.getColor(this, R.color.cert_state_edit));
        }

        if (TextUtils.equals("1", mCertData.getInfoIdentifyFaceFlag())) {
            mBinding.tvFaceState.setText("已认证");
            mBinding.tvFaceState.setTextColor(ContextCompat.getColor(this, R.color.cert_state_ok));
            mBinding.imgFaceState.setImageResource(R.drawable.cert_ok_2);

        } else {
            mBinding.tvFaceState.setText("前往提交");
            mBinding.imgFaceState.setImageResource(R.drawable.can_submit);
            mBinding.tvFaceState.setTextColor(ContextCompat.getColor(this, R.color.cert_state_edit));
        }


    }


    @Override
    public void getInfoSuccess(CerttificationInfoModel userCertInfo, String msg) {

        showContentView();

        mCertData = userCertInfo;

        setShowDataState();

    }

    @Override
    public void getInfoFailed(String code, String msg) {
        showToast(msg);
    }

    @Override
    public void startGetInfo() {
        showLoadingDialog();
    }

    @Override
    public void endGetInfo() {
        disMissLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCertInfoGetPersenter != null) {
            mCertInfoGetPersenter.clear();
            mCertInfoGetPersenter = null;
        }
    }

    @Subscribe
    public void CertInfoRefresh(String tag) {

        if (TextUtils.equals(tag, IDCARDCERTINFOREFRESH)) {
            if (mCertInfoGetPersenter != null) {
                mCertInfoGetPersenter.getCertInfo();
            }
        }

    }

}
