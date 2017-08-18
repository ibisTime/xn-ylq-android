package com.cdkj.ylq.module.user.userinfo.usemoneyrecord;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseRefreshFragment;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 借款记录列表
 * Created by 李先俊 on 2017/8/8.
 */

public class UseMoneyRecordFragment extends BaseRefreshFragment<UseMoneyRecordModel.ListBean> {


    private int requestState;

    private boolean isFirstCreate;//是否第一次创建

    /**
     * 获得fragment实例
     *
     * @return
     */
    public static UseMoneyRecordFragment getInstanse(int state) {
        UseMoneyRecordFragment fragment = new UseMoneyRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            requestState = getArguments().getInt("state");
        }
        isFirstCreate = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void afterCreate(int pageIndex, int limit) {
        mBinding.refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        if (getUserVisibleHint()) {
            isFirstCreate = false;
            getListData(pageIndex, limit, true);
        }

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            UseMoneyRecordModel.ListBean state = (UseMoneyRecordModel.ListBean) mAdapter.getItem(position);
            if (state == null) {
                return;
            }

            if (TextUtils.equals(state.getStatus(), "0")) {
                WaiteMoneyDetailsActivity.open(mActivity, state);//待放款 详情
            } else if (TextUtils.equals(state.getStatus(), "1")) {//放款中详情
                UseingMoneyDetailsActivity.open(mActivity, state,"");
            } else if (TextUtils.equals(state.getStatus(), "2")) {//逾期详情
                UsedMoneyDetailsActivity.open(mActivity,state,"");

            } else if (TextUtils.equals(state.getStatus(), "3")) {//已还款详情
                UseingMoneyDetailsActivity.open(mActivity, state,"");
            }


        });
    }

    @Override
    protected void getListData(int pageIndex, int limit, boolean canShowDialog) {
//0=可使用 1=已使用 2=已过期 12=已使用或已过期
        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("status", requestState + "");

        Call call = RetrofitUtils.createApi(MyApiServer.class).getRecordList("623087", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<UseMoneyRecordModel>(mActivity) {
            @Override
            protected void onSuccess(UseMoneyRecordModel data, String SucMessage) {
                setData(data.getList());
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
                loadError(errorMessage);
            }

            @Override
            protected void onBuinessFailure(String code, String error) {
                loadError(error);
            }

            @Override
            protected void onNoNet(String msg) {
                loadError(msg);
            }

            @Override
            protected void onNull() {
                loadError("您还没有借款记录");
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    protected BaseQuickAdapter onCreateAdapter(List<UseMoneyRecordModel.ListBean> mDataList) {
        return new BaseQuickAdapter<UseMoneyRecordModel.ListBean, BaseViewHolder>(R.layout.item_use_money_record, mDataList) {
            @Override
            protected void convert(BaseViewHolder helper, UseMoneyRecordModel.ListBean item) {
                if (item == null) return;

                helper.setText(R.id.tv_money, MoneyUtils.showPrice(item.getAmount()));
                helper.setText(R.id.tv_days, item.getDuration() + "天");
                helper.setText(R.id.tv_state, getStateString(item.getStatus()));
                helper.setText(R.id.tv_code, "订单编号 " + item.getCode());

            }
        };
    }

    //@mock=1 ("0","等待放款中"),("1","生效中"),("2","已逾期"),("3", "已还款"),("4","已取消");
    public String getStateString(String status) {
        String str = "";
        if (TextUtils.isEmpty(status)) {
            return str;
        }

        switch (status) {
            case "0":
                str = "待放款";
                break;
            case "1":
                str = "生效中";
                break;
            case "2":
                str = "已逾期";
                break;
            case "3":
                str = "已还款";
                break;
            case "4":
                str = "已取消";
                break;
        }

        return str;
    }

    @Override
    public String getEmptyInfo() {
        return "您还没有借款记录";
    }

    @Override
    public int getEmptyImg() {
        return R.drawable.no_coupnos;
    }

    @Override
    protected void lazyLoad() {
        if (isFirstCreate) {
            getListData(mPageIndex, mLimit, true);
            isFirstCreate = false;
        }

    }
}
