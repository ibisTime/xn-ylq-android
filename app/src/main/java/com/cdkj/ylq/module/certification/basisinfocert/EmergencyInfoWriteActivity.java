package com.cdkj.ylq.module.certification.basisinfocert;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityEmergencyInfoWriteBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.KeyDataModel;
import com.cdkj.ylq.module.api.MyApiServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * 紧急联系人信息认证(内容填写)
 * Created by 李先俊 on 2017/8/9.
 */

public class EmergencyInfoWriteActivity extends AbsBaseActivity {

    private ActivityEmergencyInfoWriteBinding mBinding;

    private CerttificationInfoModel.InfoContactBean mData;

    private List<KeyDataModel> mFamilys = new ArrayList<>();//亲属关系数据
    private String mFamilyCode;//亲属关系
    private OptionsPickerView mFamilysPicker;//亲属关系选择选择

    private List<KeyDataModel> mSocietys = new ArrayList<>();//社会关系数据
    private String mSocietysCode;//社会关系code
    private OptionsPickerView mSocietysPicker;//社会关系选择


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, CerttificationInfoModel.InfoContactBean data, boolean isCheckCert) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, EmergencyInfoWriteActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("isCheckCert", isCheckCert);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_emergency_info_write, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("紧急联系人信息");

        if (getIntent() != null) {
            mData = getIntent().getParcelableExtra("data");
        }

        initPrikerView();
        initListener();

        setShowData(mData);
        getAllData();
    }


    /**
     * 获取所有数据字典
     */
    private void getAllData() {
        showLoadingDialog();
        mSubscription.add(Observable.just("")
                .observeOn(Schedulers.io())
                .map(s -> {
                    mFamilys = getKeyDataValue("family_relation");
                    mSocietys = getKeyDataValue("society_relation");
                    return "";
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> disMissLoading())
                .subscribe(s -> {
                    checkFamilys();
                    checkSocietys();
                }, Throwable::printStackTrace));
    }


    /**
     * 获取数据字典数据
     */
    public List<KeyDataModel> getKeyDataValue(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("parentKey", key);
        Call call = RetrofitUtils.createApi(MyApiServer.class).getKeyData("623907", StringUtils.getJsonToString(map));
        addCall(call);

        try {
            BaseResponseListModel<KeyDataModel> de = (BaseResponseListModel<KeyDataModel>) call.execute().body();

            return de.getData();

        } catch (Exception e) {

        }

        return new ArrayList<>();
    }


    private void initListener() {
        mBinding.layoutFamilyRelation.setOnClickListener(v -> { //
            if (mFamilysPicker == null || mFamilys == null) return;
            mFamilysPicker.setPicker(mFamilys);
            mFamilysPicker.show();
        });

        mBinding.layoutSocietyRelation.setOnClickListener(v -> { //
            if (mSocietysPicker == null || mSocietys == null) return;
            mSocietysPicker.setPicker(mSocietys);
            mSocietysPicker.show();
        });

        mBinding.btnSubmit.setOnClickListener(v -> {
            dataSubmit();
        });
    }

    private void dataSubmit() {

        if (TextUtils.isEmpty(mFamilyCode) || TextUtils.isEmpty(mBinding.tvFamilyRelation.getText().toString())) {
            showToast("请选择亲属关系");
            return;
        }

        if (TextUtils.isEmpty(mBinding.editFamilyName.getText().toString())) {
            showToast("请填写亲属关系联系人姓名");
            return;
        }

        if (TextUtils.isEmpty(mBinding.editFamilyRelation.getText().toString())) {
            showToast("请填写亲属关系联系方式");
            return;
        }

        if (TextUtils.isEmpty(mSocietysCode) || TextUtils.isEmpty(mBinding.tvSocietyRelation.getText().toString())) {
            showToast("请选择社会关系");
            return;
        }

        if (TextUtils.isEmpty(mBinding.editSocietyName.getText().toString())) {
            showToast("请填写社会关系联系人姓名");
            return;
        }


        if (TextUtils.isEmpty(mBinding.editSocietyRelation.getText().toString())) {
            showToast("请填写社会关系联系方式");
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("familyMobile", mBinding.editFamilyRelation.getText().toString());
        map.put("familyRelation", mFamilyCode);
        map.put("societyMobile", mBinding.editSocietyRelation.getText().toString());
        map.put("societyRelation", mSocietysCode);
        map.put("familyName", mBinding.editFamilyName.getText().toString());
        map.put("societyName", mBinding.editSocietyName.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623042", StringUtils.getJsonToString(map));
        addCall(call);
        showLoadingDialog();
        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    showToast("提交成功");
                    finish();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }


    /**
     * 学历 婚姻选择
     */
    private void initPrikerView() {
        mFamilysPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            if (mFamilys.get(options1) == null) return;
            mFamilyCode = mFamilys.get(options1).getDkey();
            mBinding.tvFamilyRelation.setText(mFamilys.get(options1).getDvalue());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();

        mSocietysPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            if (mSocietys.get(0) == null) return;
            mSocietysCode = mSocietys.get(options1).getDkey();
            mBinding.tvSocietyRelation.setText(mSocietys.get(options1).getDvalue());
        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();

    }

    private void setShowData(CerttificationInfoModel.InfoContactBean data) {
        if (data == null) return;

        if (getIntent() != null) {
            if (getIntent().getBooleanExtra("isCheckCert", false)) {
                mBinding.btnSubmit.setBackgroundResource(R.drawable.btn_no_click_gray);
                mBinding.btnSubmit.setEnabled(false);
            } else {
                mBinding.btnSubmit.setBackgroundResource(R.drawable.selector_login_btn);
                mBinding.btnSubmit.setEnabled(true);
            }
        }


        mFamilyCode = data.getFamilyRelation();
        mSocietysCode = data.getSocietyRelation();
        mBinding.editFamilyRelation.setText(data.getFamilyMobile());
        mBinding.editSocietyRelation.setText(data.getSocietyMobile());
        mBinding.editFamilyName.setText(data.getFamilyName());
        mBinding.editSocietyName.setText(data.getSocietyName());

    }

    public void checkFamilys() {
        if (mFamilys == null) return;
        for (KeyDataModel kmodel : mFamilys) {
            if (kmodel == null) continue;

            if (TextUtils.equals(kmodel.getDkey(), mFamilyCode)) {
                mBinding.tvFamilyRelation.setText(kmodel.getDvalue());
                break;
            }

        }
    }


    public void checkSocietys() {
        if (mSocietys == null) return;
        for (KeyDataModel kmodel : mSocietys) {
            if (kmodel == null) continue;

            if (TextUtils.equals(kmodel.getDkey(), mSocietysCode)) {
                mBinding.tvSocietyRelation.setText(kmodel.getDvalue());
                break;
            }

        }
    }


}
