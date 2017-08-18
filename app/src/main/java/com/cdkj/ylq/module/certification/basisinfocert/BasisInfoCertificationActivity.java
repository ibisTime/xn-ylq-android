package com.cdkj.ylq.module.certification.basisinfocert;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.PermissionHelper;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.SystemUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityBasisInfoCertBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.mpresenter.getUserCertificationInfoListener;
import com.cdkj.ylq.mpresenter.getUserCertificationPresenter;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * 基础信息认证
 * Created by 李先俊 on 2017/8/9.
 */

public class BasisInfoCertificationActivity extends AbsBaseActivity implements getUserCertificationInfoListener {

    private ActivityBasisInfoCertBinding mBinding;

    private CerttificationInfoModel mCertData;

    private getUserCertificationPresenter mPresenter; //获取认证信息
    private PermissionHelper mHelper;

    private boolean mCanGetIemi;//

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BasisInfoCertificationActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_basis_info_cert, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("基本信息");

        mPresenter = new getUserCertificationPresenter(this);

        initListeneer();

        mHelper = new PermissionHelper(this);

        mHelper.requestPermissions("请授予读取手机状态权限！",
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        mCanGetIemi = true;
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                        mCanGetIemi = false;
                    }
                }, Manifest.permission.READ_PHONE_STATE);

    }

    //权限处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) mPresenter.getCertInfo();
    }

    /**
     * 设置认证状态显示
     *
     * @param mCertData
     */
    private void setShowState(CerttificationInfoModel mCertData) {
        if (mCertData == null) {
            return;
        }

        if (TextUtils.equals("1", mCertData.getInfoBasicFlag())) {
            mBinding.imgBasisInfo.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals("1", mCertData.getInfoOccupationFlag())) {
            mBinding.imgJobInfo.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals("1", mCertData.getInfoContactFlag())) {
            mBinding.imgEmergencyInfo.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals("1", mCertData.getInfoBankcardFlag())) {
            mBinding.imgBancardInfo.setVisibility(View.VISIBLE);
        }

    }


    private void initListeneer() {
        //基本信息填写
        mBinding.layoutBasisInfo.setOnClickListener(v -> {
            if (mCertData == null) return;
            BasisInfoCertificationWriteActivity.open(this, mCertData.getInfoBasic());
        });
        //职业信息填写
        mBinding.layoutJobInfo.setOnClickListener(v -> {
            if (mCertData == null) return;
            JobInfoCertificationWriteActivity.open(this, mCertData.getInfoOccupation());
        });

        //紧急联系人信息填写
        mBinding.layoutEmergencyInfo.setOnClickListener(v -> {
            if (mCertData == null) return;
            EmergencyInfoWriteActivity.open(this, mCertData.getInfoContact());
        });
        //银行卡信息填写
        mBinding.layoutBankCardInfo.setOnClickListener(v -> {
            if (mCertData == null) return;
            BankInfoCertificationWriteActivity.open(this, mCertData.getInfoBankcard());
        });
        //反欺诈认证 all提交
        mBinding.btnSure.setOnClickListener(v -> {

            if (mCertData == null) return;

            if (!TextUtils.equals("1", mCertData.getInfoBasicFlag())) {
                showToast("请提交基本信息");
                return;
            }

            if (!TextUtils.equals("1", mCertData.getInfoOccupationFlag())) {
                showToast("请提交职业信息");
                return;
            }

            if (!TextUtils.equals("1", mCertData.getInfoContactFlag())) {
                showToast("请提交紧急联系人信息");
                return;
            }
            if (!TextUtils.equals("1", mCertData.getInfoBankcardFlag())) {
                showToast("请认证银行卡信息信息");
                return;
            }

            allSubmitRequest();

        });
    }

    private void allSubmitRequest() {
        Map<String, String> map = new HashMap<>();
        map.put("ip", SystemUtils.getIPAddress(true));
        map.put("mac", "");
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("wifimac", SystemUtils.getMacAddress(this));
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

        if (mCanGetIemi) {
            map.put("iemi", SystemUtils.getIMEI(this));
        }

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623052", StringUtils.getJsonToString(map));

        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
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
    public void getInfoSuccess(CerttificationInfoModel userCertInfo, String msg) {
        mCertData = userCertInfo;
        setShowState(userCertInfo);
    }

    @Override
    public void getInfoFailed(String code, String msg) {
        ToastUtil.show(this, msg);
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
        if (mPresenter != null) {
            mPresenter.clear();
        }
    }
}
