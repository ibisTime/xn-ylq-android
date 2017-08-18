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
import com.cdkj.ylq.databinding.ActivityBankcardInfoWriteBinding;
import com.cdkj.ylq.databinding.ActivityEmergencyInfoWriteBinding;
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
 * 银行信息认证(内容填写)
 * Created by 李先俊 on 2017/8/9.
 */

public class BankInfoCertificationWriteActivity extends AbsBaseActivity {

    private ActivityBankcardInfoWriteBinding mBinding;

    private CerttificationInfoModel.InfoBankcardBean mBankData;

    private List<KeyDataModel> mBanks = new ArrayList<>();//银行数据
    private String mBankCode;
    private OptionsPickerView mBankPicker;
    private CityPicker mCityPicker;//城市选择


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, CerttificationInfoModel.InfoBankcardBean bankData) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, BankInfoCertificationWriteActivity.class);
        intent.putExtra("bankData", bankData);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_bankcard_info_write, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        setTopTitle("银行卡信息");

        if (getIntent() != null) {
            mBankData = getIntent().getParcelableExtra("bankData");
        }

        getBankData();
        initPrikerView();
        initCityPicker();
        initListener();
        setShowData();
    }

    private void initListener() {
        mBinding.layoutBank.setOnClickListener(v -> {
            if (mBankPicker == null || mBanks == null) return;

            mBankPicker.setPicker(mBanks);
            mBankPicker.show();
        });

        mBinding.layoutCity.setOnClickListener(v -> {//城市选择
            if (mCityPicker == null) return;
            mCityPicker.show();
        });
        mBinding.btnSubmit.setOnClickListener(v -> {//提交
            dataSubmit();
        });

    }

    private void dataSubmit() {

        if (TextUtils.isEmpty(mBankCode) || TextUtils.isEmpty(mBinding.tvBank.getText().toString())) {
            showToast("请选择开户行");
            return;
        }
        if (TextUtils.isEmpty(mBinding.tvCity.getText().toString())) {
            showToast("请选择开户城市");
            return;
        }

        if (TextUtils.isEmpty(mBinding.editBankCardNumber.getText().toString())) {
            showToast("请填写电话");
            return;
        }
        if (TextUtils.isEmpty(mBinding.editBankCardNumber.getText().toString())) {
            showToast("请填写银行卡号");
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("cardNo", mBinding.editBankCardNumber.getText().toString());
        map.put("bank", mBankCode);
        map.put("mobile", mBinding.editPhoneNumber.getText().toString());
        map.put("privinceCity", mBinding.tvCity.getText().toString());
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623043", StringUtils.getJsonToString(map));
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

    private void initPrikerView() {
        mBankPicker = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            KeyDataModel model = mBanks.get(options1);
            if (model == null) return;
            mBankCode = model.getDkey();
            mBinding.tvBank.setText(model.getDvalue());

        }).setContentTextSize(16).setLineSpacingMultiplier(4).build();
    }

    private void setShowData() {

        if (mBankData == null) return;

        mBankCode = mBankData.getBank();


        mBinding.tvCity.setText(mBankData.getPrivinceCity());
        mBinding.editBankCardNumber.setText(mBankData.getCardNo());
        mBinding.editPhoneNumber.setText(mBankData.getMobile());

    }

    /**
     * 城市选择
     */
    private void initCityPicker() {
        mCityPicker = new CityPicker.Builder(BankInfoCertificationWriteActivity.this)
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
                mBinding.tvCity.setText(citySelected[0] + citySelected[1] + citySelected[3]);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 居住时长数据
     */
    public void getBankData() {
        Map<String, String> map = new HashMap<>();
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("parentKey", "bank");
        Call call = RetrofitUtils.createApi(MyApiServer.class).getKeyData("623907", StringUtils.getJsonToString(map));
        addCall(call);
        call.enqueue(new BaseResponseListCallBack<KeyDataModel>(this) {

            @Override
            protected void onSuccess(List<KeyDataModel> data, String SucMessage) {
                mBanks = data;
                checkBanks();
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    public void checkBanks() {
        if (mBanks == null) return;
        for (KeyDataModel kmodel : mBanks) {
            if (kmodel == null) continue;

            if (TextUtils.equals(kmodel.getDkey(), mBankCode)) {
                mBinding.tvBank.setText(kmodel.getDvalue());
                break;
            }

        }
    }


}
