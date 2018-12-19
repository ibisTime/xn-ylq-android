package com.cdkj.ylq.module.user.userinfo.usemoneyrecord;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.activitys.BackCardListActivity;
import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.base.AbsBaseActivity;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.appmanager.BusinessSings;
import com.cdkj.ylq.databinding.ActivityWaiteMoneyDetailsBinding;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.api.MyApiServer;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.utils.DateUtil.DATE_YMD;
import static com.cdkj.ylq.appmanager.BusinessSings.USEMONEYRECORD_0;
import static com.cdkj.ylq.appmanager.BusinessSings.USEMONEYRECORD_1;
import static com.cdkj.ylq.appmanager.BusinessSings.USEMONEYRECORD_2;
import static com.cdkj.ylq.appmanager.BusinessSings.USEMONEYRECORD_7;

/**
 * 借款待放款详情
 * Created by 李先俊 on 2017/8/9.
 */

public class WaiteMoneyDetailsActivity extends AbsBaseActivity {

    private ActivityWaiteMoneyDetailsBinding mBinding;


    private UseMoneyRecordModel.ListBean mData;
    private String mCode;

    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, UseMoneyRecordModel.ListBean state, String code) {
        if (context == null) {
            return;
        }

        Intent i = new Intent(context, WaiteMoneyDetailsActivity.class);
        i.putExtra("data", state);
        i.putExtra("code", code);
        context.startActivity(i);
    }


    @Override
    public View addMainView() {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_waite_money_details, null, false);

        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

        setSubLeftImgState(true);


        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");

            if (TextUtils.isEmpty(mCode)) {
                mData = getIntent().getParcelableExtra("data");
                setShowData();
            } else {
                getDataRequest();
            }

        }

        if (mData != null) {
            setTopTitle(getStateTitie(mData.getStatus()));
        } else {
            setTopTitle("借款详情");
        }

        initListener();

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


    private void initListener() {
        mBinding.btnSure.setOnClickListener(v -> {
            reUseRequest();
        });
    }


    /**
     * 重新申请
     */
    public void reUseRequest() {

        if (mData == null) return;

        Map<String, String> map = new HashMap<String, String>();
        map.put("code", mData.getCode());
        Call call = RetrofitUtils.getBaseAPiService().successRequest("623073", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(this) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                if (data.isSuccess()) {
                    showToast("申请成功，请等待审核");

                    refreshState1();

                    refreshState7();

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
     * 刷新待放款状态
     */
    private void refreshState1() {
        EventBusModel eventBusModel = new EventBusModel();
        eventBusModel.setTag(EventTags.USEMONEYRECORDFRAGMENTREFRESH);
        eventBusModel.setEvInfo(USEMONEYRECORD_1);
        EventBus.getDefault().post(eventBusModel);
    }

    /**
     * 刷新打款失败状态状态
     */
    private void refreshState7() {
        EventBusModel eventBusModel = new EventBusModel();
        eventBusModel.setTag(EventTags.USEMONEYRECORDFRAGMENTREFRESH);
        eventBusModel.setEvInfo(USEMONEYRECORD_7);
        EventBus.getDefault().post(eventBusModel);
    }


    private void setShowData() {

        if (mData == null) return;

        mBinding.tvMoney.setText(MoneyUtils.showPrice(mData.getTotalAmount()));
        mBinding.tvMoney2.setText(MoneyUtils.showPrice(mData.getTotalAmount()) + "元");

//        // 待还款不展示这 三个金额
//        if (TextUtils.equals(mData.getStatus(), USEMONEYRECORD_0)//待审核
//                || TextUtils.equals(mData.getStatus(), USEMONEYRECORD_2)//审核不通过
//                || TextUtils.equals(mData.getStatus(), USEMONEYRECORD_1)//待放款
//                || TextUtils.equals(mData.getStatus(), USEMONEYRECORD_7)) {
//            mBinding.flActualMoney.setVisibility(View.GONE);
//            mBinding.flLoanMoney.setVisibility(View.GONE);
//            mBinding.flPaidMoney.setVisibility(View.GONE);
//        } else {
//            mBinding.tvLoanMoney.setText(MoneyUtils.showPrice(mData.getBorrowAmount()) + "元");
//            mBinding.tvActualMoney.setText(MoneyUtils.showPrice(mData.getRealGetAmount()) + "元");
//            mBinding.tvPaidMoney.setText(MoneyUtils.showPrice(mData.getRealHkAmount()) + "元");
//        }

        mBinding.tvCode.setText(mData.getCode());
        mBinding.tvSignData.setText(DateUtil.formatStringData(mData.getSignDatetime(), DATE_YMD));

        mBinding.tvDay.setText(mData.getDuration() + "天");

        mBinding.imgState.setImageResource(getStateImg(mData.getStatus()));


        if (TextUtils.equals(mData.getStatus(), USEMONEYRECORD_2)) {
//            mBinding.tvState2.setText(mData.getApproveNote());
            //审核不通过  没有状态说明  通过短信通知的
            mBinding.rlState2.setVisibility(View.GONE);
        } else {
            mBinding.tvState2.setText(mData.getRemark());
        }

        if (TextUtils.equals(mData.getStatus(), USEMONEYRECORD_7)) { //打款失败时显示按钮 显示弹框
            mBinding.btnSure.setVisibility(View.VISIBLE);

            showDoubleWarnListen("打款失败,请核对银行卡信息", view -> {
                BackCardListActivity.open(this, false);
            });

        } else {
            mBinding.btnSure.setVisibility(View.GONE);
        }


        if (TextUtils.equals(mData.getStatus(), USEMONEYRECORD_0)          //待审核 //待放款
                || TextUtils.equals(mData.getStatus(), USEMONEYRECORD_1)
                ) {
            mBinding.layoutMoneyState.setVisibility(View.VISIBLE);
        } else {
            mBinding.layoutMoneyState.setVisibility(View.GONE);
        }

    }

    /**
     * 获取状态图片
     *
     * @param status
     * @return
     */
    private int getStateImg(String status) {

        switch (status) {
            case USEMONEYRECORD_0: //待审核
                return R.drawable.record_0;
            case USEMONEYRECORD_2://审核不通过
                return R.drawable.record_2;
            case USEMONEYRECORD_1://待放款
                return R.drawable.record_1;
            case USEMONEYRECORD_7://打款失败
                return R.drawable.record_7;
            case BusinessSings.USEMONEYRECORD_8://代扣中
                return R.drawable.record_1;
            default:
                mBinding.imgState.setVisibility(View.GONE);
                return R.drawable.default_pic;
        }
    }

    /**
     * 获取状态图片
     *
     * @param status
     * @return
     */
    private String getStateTitie(String status) {

        switch (status) {
            case USEMONEYRECORD_0: //待审核
                return "待审核详情";
            case USEMONEYRECORD_2://审核不通过
                return "审核失败";
            case USEMONEYRECORD_1://待放款
                return "待放款详情";
            case USEMONEYRECORD_7://打款失败
                return "打款失败";
            default:
                return "借款详情";
        }
    }
}
