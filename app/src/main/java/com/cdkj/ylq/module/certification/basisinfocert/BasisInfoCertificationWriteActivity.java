package com.cdkj.ylq.module.certification.basisinfocert;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityBasisInfoWriteBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.KeyDataModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 基础信息认证(内容填写)
 * Created by 李先俊 on 2017/8/9.
 */

public class BasisInfoCertificationWriteActivity extends AbsBaseActivity {

    private ActivityBasisInfoWriteBinding mBinding;
    private CerttificationInfoModel.InfoBasicBean mInfoData;

    private List<KeyDataModel> mEducations = new ArrayList<>();//学历数据
    private String mEducationCode;//学历code
    private OptionsPickerView mEducationsPicker;//学历选择

    private List<KeyDataModel> mMarriages = new ArrayList<>();//婚姻数据
    private String mMarriagesCode;//婚姻code
    private OptionsPickerView mMarriagesPicker;//婚姻选择

    private CityPicker mCityPicker;//城市选择

    private List<KeyDataModel> mLiveDays = new ArrayList<>();//居住时长数据
    private String mLiveDaysCode;
    private OptionsPickerView mLiveDaysPicker;


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, CerttificationInfoModel.InfoBasicBean data) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, BasisInfoCertificationWriteActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);

    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_basis_info_write, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("基本信息");

        if (getIntent() != null) {
            mInfoData = getIntent().getParcelableExtra("data");
        }

        showData(mInfoData);
        getEducationData();
        getMarriagesData();
        getLivesData();
        initPrikerView();
        initCityPicker();
        initListener();
    }

    private void initListener() {
        mBinding.layoutStudySing.setOnClickListener(v -> {//学历选择
            if (mEducationsPicker == null || mEducations == null) return;

            mEducationsPicker.setPicker(mEducations);
            mEducationsPicker.show();
        });
        mBinding.layoutMarriage.setOnClickListener(v -> { //婚姻选择
            if (mMarriages == null || mMarriages == null) return;
            mMarriagesPicker.setPicker(mMarriages);
            mMarriagesPicker.show();
        });

        mBinding.layoutLiveDays.setOnClickListener(v -> { //居住时长
            if (mLiveDays == null || mLiveDaysPicker == null) return;
            mLiveDaysPicker.setPicker(mLiveDays);
            mLiveDaysPicker.show();
        });
        mBinding.layoutCity.setOnClickListener(v -> {//城市选择
            if (mCityPicker == null) return;
            mCityPicker.show();
        });
        mBinding.btnSubmit.setOnClickListener(v -> {//提交
            writeDataSubmit();
        });
    }

    /**
     * 填写数据提交
     */
    private void writeDataSubmit() {

        if (TextUtils.isEmpty(mEducationCode) || TextUtils.isEmpty(mBinding.tvStudySing.getText().toString())) {
            showToast("请选择学历");
            return;
        }
        if (TextUtils.isEmpty(mMarriagesCode) || TextUtils.isEmpty(mBinding.tvMarriage.getText().toString())) {
            showToast("请选择婚姻状况");
            return;
        }

        if (TextUtils.isEmpty(mBinding.editChildNum.getText().toString())) {
            showToast("请填写子女个数");
            return;
        }

        if (TextUtils.isEmpty(mBinding.editAddress.getText().toString())) {
            showToast("请填写详细地址");
            return;
        }
        if (TextUtils.isEmpty(mLiveDaysCode) || TextUtils.isEmpty(mBinding.tvLiveDays.getText().toString())) {
            showToast("请选择居住时长");
            return;
        }
        if (TextUtils.isEmpty(mBinding.editQq.getText().toString())) {
            showToast("请填写QQ");
            return;
        }
        if (TextUtils.isEmpty(mBinding.editEmail.getText().toString())) {
            showToast("请填写email");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("address", mBinding.editAddress.getText().toString());
        map.put("childrenNum", mBinding.editChildNum.getText().toString());
        map.put("education", mEducationCode);
        map.put("email", mBinding.editEmail.getText().toString());
        map.put("liveTime", mLiveDaysCode);
        map.put("marriage", mMarriagesCode);
        map.put("provinceCity", mBinding.tvCity.getText().toString());
        map.put("qq", mBinding.editQq.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623040", StringUtils.getJsonToString(map));
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
        mEducationsPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            KeyDataModel keyDataModel = mEducations.get(options1);
            if (keyDataModel == null) return;

            mEducationCode = keyDataModel.getDkey();
            mBinding.tvStudySing.setText(keyDataModel.getDvalue());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();

        mMarriagesPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            KeyDataModel keyDataModel = mMarriages.get(options1);
            if (keyDataModel == null) return;

            mMarriagesCode = keyDataModel.getDkey();
            mBinding.tvMarriage.setText(keyDataModel.getDvalue());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();

        mLiveDaysPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            KeyDataModel keyDataModel = mLiveDays.get(options1);
            if (keyDataModel == null) return;

            mLiveDaysCode = keyDataModel.getDkey();
            mBinding.tvLiveDays.setText(keyDataModel.getDvalue());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();

    }

    /**
     * 城市选择
     */
    private void initCityPicker() {
        mCityPicker = new CityPicker.Builder(BasisInfoCertificationWriteActivity.this)
                .textSize(18)
                .titleBackgroundColor("#ffffff")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#3DA3FF")
                .cancelTextColor("#3DA3FF")
                .province("北京市")
                .city("北京市")
                .district("昌平区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();


        //监听方法，获取选择结果
        mCityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                mBinding.tvCity.setText(citySelected[0] +" "+ citySelected[1] +" "+ citySelected[3]);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 显示数据
     *
     * @param mInfoData
     */
    private void showData(CerttificationInfoModel.InfoBasicBean mInfoData) {

        if (mInfoData == null) return;


        mEducationCode = mInfoData.getEducation();
        mMarriagesCode = mInfoData.getMarriage();
        mLiveDaysCode = mInfoData.getLiveTime();

        mBinding.editChildNum.setText(mInfoData.getChildrenNum() + "");
        mBinding.tvCity.setText(mInfoData.getProvinceCity());
        mBinding.editAddress.setText(mInfoData.getAddress());
        mBinding.editQq.setText(mInfoData.getQq());
        mBinding.editEmail.setText(mInfoData.getEmail());
    }

    /**
     * 获取学历数据
     */
    public void getEducationData() {
        Map<String, String> map = new HashMap<>();
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("parentKey", "education");
        Call call = RetrofitUtils.createApi(MyApiServer.class).getKeyData("623907", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseListCallBack<KeyDataModel>(this) {

            @Override
            protected void onSuccess(List<KeyDataModel> data, String SucMessage) {
                mEducations = data;
                checkEducation();
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    //获取
    public void checkEducation() {
        if (mEducations == null) return;
        for (KeyDataModel kmodel : mEducations) {
            if (kmodel == null) continue;

            if (TextUtils.equals(kmodel.getDkey(), mEducationCode)) {
                mBinding.tvStudySing.setText(kmodel.getDvalue());
                break;
            }

        }
    }

    /**
     * 获取婚姻数据
     */
    public void getMarriagesData() {
        Map<String, String> map = new HashMap<>();
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("parentKey", "marriage");
        Call call = RetrofitUtils.createApi(MyApiServer.class).getKeyData("623907", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseListCallBack<KeyDataModel>(this) {

            @Override
            protected void onSuccess(List<KeyDataModel> data, String SucMessage) {
                mMarriages = data;
                checkMarriage();
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    public void checkMarriage() {
        if (mMarriages == null) return;
        for (KeyDataModel kmodel : mMarriages) {
            if (kmodel == null) continue;

            if (TextUtils.equals(kmodel.getDkey(), mMarriagesCode)) {
                mBinding.tvMarriage.setText(kmodel.getDvalue());
                break;
            }

        }
    }

    /**
     * 居住时长数据
     */
    public void getLivesData() {
        Map<String, String> map = new HashMap<>();
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("parentKey", "live_time");
        Call call = RetrofitUtils.createApi(MyApiServer.class).getKeyData("623907", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseListCallBack<KeyDataModel>(this) {

            @Override
            protected void onSuccess(List<KeyDataModel> data, String SucMessage) {
                mLiveDays = data;
                checkLives();
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    public void checkLives() {
        if (mLiveDays == null) return;
        for (KeyDataModel kmodel : mLiveDays) {
            if (kmodel == null) continue;

            if (TextUtils.equals(kmodel.getDkey(), mLiveDaysCode)) {
                mBinding.tvLiveDays.setText(kmodel.getDvalue());
                break;
            }

        }
    }


}
