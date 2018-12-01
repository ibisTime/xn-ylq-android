package com.cdkj.ylq.module.user.userinfo.usemoneyrecord;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityUseingMoneyBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.pay.AlsoMoneyTabActivity;
import com.cdkj.ylq.module.pay.RenewalMoneyTabActivity;
import com.cdkj.ylq.module.renewal.RenewalListActivity;
import com.cdkj.ylq.module.stages.StagesActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.utils.DateUtil.DATE_YMD;

/**
 * 借款生效中详情 (已还款显示 fra_state_note 其他隐藏 ) （借款记录）
 * Created by 李先俊 on 2017/8/9.
 */
//TODO 借款记录详情 生效中已还款是否需要分开
//TODO 状态图标由生效中改为待还款 名字record_3
public class UseingMoneyDetailsActivity extends AbsBaseActivity {

    private ActivityUseingMoneyBinding mBinding;

    private UseMoneyRecordModel.ListBean mData;

    private String mCode;

    private boolean mState;//true  生效中  false //已经还款


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, UseMoneyRecordModel.ListBean data, boolean isUseing, String code) {
        if (context == null) {
            return;
        }

        Intent i = new Intent(context, UseingMoneyDetailsActivity.class);
        i.putExtra("data", data);
        i.putExtra("code", code);
        i.putExtra("state", isUseing);
        context.startActivity(i);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_useing_money, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);

        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");
            mState = getIntent().getBooleanExtra("state", false);

            if (TextUtils.isEmpty(mCode)) { //如果code为空的话获取传递过来的数据
                mData = (UseMoneyRecordModel.ListBean) getIntent().getSerializableExtra("data");
                setShowData();
            } else { //如果code不为空的话用code获取详情数据
                getDataRequest();
            }
        }

        initListener();
    }

    private void setShowData() {

        if (mData == null) return;

        if (mState) { //生效中
            setTopTitle("待还款详情");

            mBinding.fraStateNote.setVisibility(View.GONE);
            mBinding.imgState.setImageResource(R.drawable.record_3);
            mBinding.layoutStateBtn.setVisibility(View.VISIBLE);

        } else {
            mBinding.fraStateNote.setVisibility(View.VISIBLE);
            mBinding.imgState.setImageResource(R.drawable.record_4);
            mBinding.layoutStateBtn.setVisibility(View.GONE);
            setTopTitle("已还款详情");
        }

        mBinding.tvMoney.setText(MoneyUtils.showPrice(mData.getAmount()));
        mBinding.tvMoney2.setText(MoneyUtils.showPrice(mData.getAmount()) + "元");
        mBinding.tvCode.setText(mData.getCode());
        mBinding.tvSignData.setText(DateUtil.formatStringData(mData.getSignDatetime(), DATE_YMD));
        mBinding.tvDay.setText(mData.getDuration() + "天");
        mBinding.tvDakuan.setText(DateUtil.formatStringData(mData.getFkDatetime(), DATE_YMD));
        mBinding.tvJixi.setText(DateUtil.formatStringData(mData.getJxDatetime(), DATE_YMD));
        mBinding.tvHuankuan.setText(DateUtil.formatStringData(mData.getHkDatetime(), DATE_YMD));
        mBinding.tvKuaisu.setText(MoneyUtils.showPrice(mData.getXsAmount()) + "元");
        mBinding.tvGuanli.setText(MoneyUtils.showPrice(mData.getGlAmount()) + "元");
        mBinding.tvLixi.setText(MoneyUtils.showPrice(mData.getLxAmount()) + "元");
        mBinding.tvJianmian.setText(MoneyUtils.showPrice(mData.getYhAmount()) + "元");
        mBinding.tvDaoqi.setText(MoneyUtils.showPrice(mData.getTotalAmount()) + "元");
        mBinding.tvService.setText(MoneyUtils.showPrice(mData.getFwAmount()) + "元");
        mBinding.tvXuqiNum.setText(mData.getRenewalCount() + "");

        if (TextUtils.equals("0", mData.getIsStage())) {
            //不是分期
            mBinding.llFenqi.setVisibility(View.GONE);
        } else {
            //分期
            mBinding.llFenqi.setVisibility(View.VISIBLE);
            UseMoneyRecordModel.ListBean.Info info = mData.getInfo();

            mBinding.tvFenqi.setText(info.getStageCount() + "/" + mData.getStageCount());//设置当前是第几期
            mBinding.tvStartTime.setText(DateUtil.formatStringData(info.getStartTime(), DateUtil.DATE_YMD));
            mBinding.tvEndTime.setText(DateUtil.formatStringData(info.getEndTime(), DateUtil.DATE_YMD));
            mBinding.tvTodayMoney.setText(MoneyUtils.showPrice(info.getAmount()));

            mBinding.fraFenqi.setOnClickListener(v -> {
                //跳转到分期列表
                ArrayList<UseMoneyRecordModel.ListBean.StageListBean> stageList = mData.getStageList();
                StagesActivity.open(this, stageList);
            });
        }

    }


    private void initListener() {

        //还款
        mBinding.btnPayMoney.setOnClickListener(v -> {
            if (mData == null) return;

            if (TextUtils.equals("1", mData.getIsStage())) {
                //分期还款
                AlsoMoneyTabActivity.open(this, mData.getInfo());

            } else {
                AlsoMoneyTabActivity.open(this, mData.getCode(), MoneyUtils.showPrice(mData.getTotalAmount()));
            }
        });
        //续期
        mBinding.btnRenewal.setOnClickListener(v -> {
            if (mData == null) return;
            RenewalMoneyTabActivity.open(this, mData);
        });

        mBinding.fraXuqi.setOnClickListener(v -> {
            if (mData == null) return;
            RenewalListActivity.open(this, mData.getCode());
        });

        //借款编号  不需要借款合同了去掉点击事件
//        mBinding.layoutCode.setOnClickListener(v -> {
//            if (TextUtils.isEmpty(mBinding.tvCode.getText().toString())) {
//                return;
//            }
//            ContractShowActivity.open(this, mBinding.tvCode.getText().toString());
//        });

    }


    public void getDataRequest() {

        Map<String, String> map = new HashMap<>();
        map.put("code", mCode);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getUseMoneyData("623086", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UseMoneyRecordModel.ListBean>(this) {
            @Override
            protected void onSuccess(UseMoneyRecordModel.ListBean data, String SucMessage) {
                mData = data;
                setShowData();
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }
}
