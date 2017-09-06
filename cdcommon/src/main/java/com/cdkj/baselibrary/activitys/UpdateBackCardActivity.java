package com.cdkj.baselibrary.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.databinding.ActivityBindBankCardBinding;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.BankCardModel;
import com.cdkj.baselibrary.model.BankModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 删除、修改银行卡
 * Created by 李先俊 on 2017/6/29.
 */

public class UpdateBackCardActivity extends AbsBaseActivity {

    private ActivityBindBankCardBinding mBinding;

    private String[] mBankNames;
    private String[] mBankCodes;

    private String mSelectCardId;//选择的银行卡ID

    private BankCardModel mBankModel;

    /**
     * @param context
     */
    public static void open(Context context, BankCardModel data) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UpdateBackCardActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    @Override
    public View addMainView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_bind_bank_card, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        if (getIntent() != null) {
            mBankModel = getIntent().getParcelableExtra("data");
        }

        setTopTitle("修改银行卡");

        setShowData();

        setSubRightTitleAndClick("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDoubleWarnListen("您确定要删除该银行卡吗?", new CommonDialog.OnPositiveListener() {
                            @Override
                            public void onPositive(View view) {
                                deleteBank();
                            }
                        });
                    }
                }
        );

        setSubLeftImgState(true);

        //添加银行类型
        mBinding.txtBankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBankBrand();
            }
        });

        //添加银行卡
        mBinding.txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.editName.getText().toString())) {
                    showToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(mSelectCardId)) {
                    showToast("请选择银行");
                    return;
                }
                if (TextUtils.isEmpty(mBinding.edtCardId.getText().toString())) {
                    showToast("请输入卡号");
                    return;
                }

                updateBank("");
            }
        });

    }


    private void deleteBank() {
        if (mBankModel == null) return;
        Map<String, String> object = new HashMap<>();
        object.put("code", mBankModel.getCode());
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyConfig.SYSTEMCODE);
        Call call = RetrofitUtils.getBaseAPiService().successRequest("802011", StringUtils.getJsonToString(object));


        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {

            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    showToast("删除成功");
                    finish();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    //更新
    private void updateBank(String pwd) {
        if (mBankModel == null) return;
        Map<String, String> object = new HashMap<>();
        object.put("realName", mBinding.editName.getText().toString().trim());
        object.put("bankcardNumber", mBinding.edtCardId.getText().toString().trim());
        object.put("bankName", mBinding.txtBankName.getText().toString().trim());
        object.put("bankCode", mBankModel.getBankCode());
        object.put("code", mBankModel.getCode());
        object.put("status", "1");
//        object.put("tradePwd", pwd);
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("userId", SPUtilHelpr.getUserId());
        object.put("systemCode", MyConfig.SYSTEMCODE);

        Call call = RetrofitUtils.getBaseAPiService().successRequest("802012", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {

            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    showToast("修改成功");
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
     * 设置显示数据
     */
    private void setShowData() {

        if (mBankModel == null) {
            LogUtil.E("没有数据");
            return;
        }

        mBinding.txtBankName.setText(mBankModel.getBankName());
        mBinding.editName.setText(mBankModel.getRealName());
        mBinding.edtCardId.setText(mBankModel.getBankcardNumber());
        mSelectCardId = mBankModel.getBankCode();

    }


    /**
     * 获取银行卡渠道
     */
    private void getBankBrand() {
        Map object = new HashMap();
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("payType", "WAP");
        Call call = RetrofitUtils.getBaseAPiService().getBackModel("802116", StringUtils.getJsonToString(object));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<BankModel>(this) {
            @Override
            protected void onSuccess(List<BankModel> r, String SucMessage) {
                mBankNames = new String[r.size()];
                mBankCodes = new String[r.size()];

                int i = 0;

                for (BankModel b : r) {
                    mBankNames[i] = b.getBankName();
                    mBankCodes[i] = b.getBankCode();
                    i++;
                }
                if (mBankNames.length != 0 && mBankNames.length == mBankCodes.length) {
                    chooseBankCard();
                }
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }


    private void chooseBankCard() {
        new AlertDialog.Builder(this).setTitle("请选择银行").setSingleChoiceItems(
                mBankNames, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        txtBankCard.setText(list.get(which).getBankName());
                        mBinding.txtBankName.setText(mBankNames[which]);
                        mSelectCardId = mBankCodes[which];
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
    }


}
