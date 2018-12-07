package com.cdkj.ylq.module.certification.basisinfocert;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityJobInfoWriteBinding;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.KeyDataModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * 职业信息认证(内容填写)
 * Created by 李先俊 on 2017/8/9.
 */
public class JobInfoCertificationWriteActivity extends AbsBaseActivity {
    private ActivityJobInfoWriteBinding mBinding;
    CerttificationInfoModel.InfoOccupationBean mJobdata;

    private List<KeyDataModel> mJobs = new ArrayList<>();//职业数据
    private String mJobCode;//职业code
    private OptionsPickerView mJobsPicker;//职业选择


    private List<KeyDataModel> mInMoneys = new ArrayList<>();//收入数据
    private String mInMoneyCode;//收入code
    private OptionsPickerView mInMoneyPicker;//收入选择

    private CityPicker mCityPicker;//城市选择

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, CerttificationInfoModel.InfoOccupationBean data, boolean isCheckCert) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, JobInfoCertificationWriteActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("isCheckCert", isCheckCert);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_job_info_write, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setSubLeftImgState(true);
        setTopTitle("职业信息");
        if (getIntent() != null) {
            mJobdata = getIntent().getParcelableExtra("data");
        }

        initPrikerView();
        initCityPicker();
        initListener();

        setShowData(mJobdata);
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
                    mInMoneys = getKeyDataValue("income");
                    mJobs = getKeyDataValue("occupation");
                    return "";
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> disMissLoading())
                .subscribe(s -> {
                    checkInmoneys();
                    checkJobs();
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


    /**
     *
     */
    private void initListener() {

        mBinding.layoutJobSelect.setOnClickListener(v -> {
            if (mJobsPicker == null || mJobs == null) return;
            mJobsPicker.setPicker(mJobs);
            mJobsPicker.show();
        });
        mBinding.layoutInmoney.setOnClickListener(v -> {
            if (mInMoneyPicker == null || mInMoneys == null) return;
            mInMoneyPicker.setPicker(mInMoneys);
            mInMoneyPicker.show();
        });
        mBinding.layoutCity.setOnClickListener(v -> {
            if (mCityPicker == null) return;
            mCityPicker.show();
        });
        mBinding.btnSubmit.setOnClickListener(v -> {
            dataSubmit();
        });
    }

    /**
     * 数据提交
     */
    private void dataSubmit() {

        if (TextUtils.isEmpty(mJobCode) || TextUtils.isEmpty(mBinding.tvJob.getText().toString())) {
            showToast("请选择职业");
            return;
        }
        if (TextUtils.isEmpty(mInMoneyCode) || TextUtils.isEmpty(mBinding.tvInmoney.getText().toString())) {
            showToast("请选择月收入");
            return;
        }
        if (TextUtils.isEmpty(mBinding.editCompanyName.getText().toString())) {
            showToast("请输入单位名称");
            return;
        }
        if (TextUtils.isEmpty(mBinding.tvCity.getText().toString())) {
            showToast("请选择城市");
            return;
        }
        if (TextUtils.isEmpty(mBinding.editAddressJob.getText().toString())) {
            showToast("请输入单位地址");
            return;
        }
//        if (TextUtils.isEmpty(mBinding.editPhoneNumberJob.getText().toString())) {
//            showToast("请输入单位电话");
//            return;
//        }
        Map<String, String> map = new HashMap<>();
        map.put("address", mBinding.editAddressJob.getText().toString());
        map.put("company", mBinding.editCompanyName.getText().toString());
        map.put("income", mInMoneyCode);
        map.put("occupation", mJobCode);
        map.put("phone", mBinding.editPhoneNumberJob.getText().toString());
        map.put("provinceCity", mBinding.tvCity.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623041", StringUtils.getJsonToString(map));
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

    private void setShowData(CerttificationInfoModel.InfoOccupationBean mJobdata) {
        if (mJobdata == null) return;

        if (getIntent() != null) {
            if (getIntent().getBooleanExtra("isCheckCert", false)) {
                mBinding.btnSubmit.setBackgroundResource(R.drawable.btn_no_click_gray);
                mBinding.btnSubmit.setEnabled(false);
            } else {
                mBinding.btnSubmit.setBackgroundResource(R.drawable.selector_login_btn);
                mBinding.btnSubmit.setEnabled(true);
            }
        }

        mInMoneyCode = mJobdata.getIncome() + "";
        mJobCode = mJobdata.getOccupation();

        mBinding.editPhoneNumberJob.setText(mJobdata.getPhone());
        mBinding.editAddressJob.setText(mJobdata.getAddress());
        mBinding.tvCity.setText(mJobdata.getProvinceCity());
        mBinding.editCompanyName.setText(mJobdata.getCompany());
    }


    /**
     * 城市选择
     */
    private void initCityPicker() {
        mCityPicker = new CityPicker.Builder(JobInfoCertificationWriteActivity.this)
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
                mBinding.tvCity.setText(citySelected[0] + " " + citySelected[1] + " " + citySelected[2]);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 学历 婚姻选择
     */
    private void initPrikerView() {
        mJobsPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            if (mJobs.get(options1) == null) return;

            mJobCode = mJobs.get(options1).getDkey();
            mBinding.tvJob.setText(mJobs.get(options1).getDvalue());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();

        mInMoneyPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            if (mInMoneys.get(options1) == null) return;

            mInMoneyCode = mInMoneys.get(options1).getDkey();
            mBinding.tvInmoney.setText(mInMoneys.get(options1).getDvalue());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();
    }

    public void checkJobs() {
        if (mJobs == null) return;
        for (KeyDataModel kmodel : mJobs) {
            if (kmodel == null) continue;

            if (TextUtils.equals(kmodel.getDkey(), mJobCode)) {
                mBinding.tvJob.setText(kmodel.getDvalue());
                break;
            }

        }
    }

    public void checkInmoneys() {
        if (mInMoneys == null) return;
        for (KeyDataModel kmodel : mInMoneys) {
            if (kmodel == null) continue;

            if (TextUtils.equals(kmodel.getDkey(), mInMoneyCode)) {
                mBinding.tvInmoney.setText(kmodel.getDvalue());
                break;
            }

        }
    }


}
