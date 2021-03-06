package com.cdkj.ylq.module.borrowmoney;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseRefreshFragment;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.LogUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.adapters.BorrowMoneyProductAdapter;
import com.cdkj.ylq.appmanager.BusinessSings;
import com.cdkj.ylq.databinding.LayoutProductFooterviewBinding;
import com.cdkj.ylq.model.PorductListModel;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.certification.review.HumanReviewActivity;
import com.cdkj.ylq.module.product.ProductDetailsActivity;
import com.cdkj.ylq.module.user.userinfo.MsgListActivity;
import com.cdkj.ylq.module.user.userinfo.MyMaxMoneyActivity;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UsedMoneyDetailsActivity;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UseingMoneyDetailsActivity;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.WaiteMoneyDetailsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.EventTags.BORROWMONEYFRAGMENTREFRESH;
import static com.cdkj.baselibrary.appmanager.EventTags.LOGINREFRESH;

/**
 * 借款
 * Created by 李先俊 on 2017/8/8.
 */

public class BorrowMoneyFragment extends BaseRefreshFragment<PorductListModel.ListBean> {

    private boolean isFirstCreate;//是否第一次进入

    /**
     * 获得fragment实例
     *
     * @return
     */
    public static BorrowMoneyFragment getInstanse() {
        BorrowMoneyFragment fragment = new BorrowMoneyFragment();
        return fragment;
    }

    @Override
    protected boolean canLoadTopTitleView() {
        return true;
    }

    @Override
    protected void afterCreate(int pageIndex, int limit) {
        setTopTitle(getString(R.string.app_name));
        setTopTitleViewBg(R.color.white);
        setTopTitleViewColor(R.color.activity_title_bg);
        setSubRightImgAndClick(R.drawable.msg, v -> {
            if (!SPUtilHelpr.isLogin(mActivity, false)) {
                return;
            }
            MsgListActivity.open(mActivity);
        });
        getListData(pageIndex, limit, true);
        isFirstCreate = true;
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                PorductListModel.ListBean data = (PorductListModel.ListBean) adapter.getItem(position);
                productItemClick(data);
            }
        });

    }

    /**
     * 点击 //("0", "可申请"),("1", "认证中"),("2", "人工审核中"),( "3", "已驳回"),("4", "已有额度"),("5", "等待放款中"),( "6", "生效中"),("7", "已逾期")
     *
     * @param data
     */
    private void productItemClick(PorductListModel.ListBean data) {


        if (data == null) {
            return;
        }

        if (TextUtils.equals("1", data.getIsLocked())) {//锁中状态

            CommonDialog commonDialog = new CommonDialog(mActivity).builder()
                    .setTitle("提示").setContentMsg("尊敬的用户,该款产品您还不能申请。")
                    .setPositiveBtn("确定", null);

            commonDialog.show();
            return;
        }

        if (TextUtils.equals(BusinessSings.PRODUCTSTATE_0, data.getUserProductStatus())) {      //产品详情
            ProductDetailsActivity.open(mActivity, data.getCode());
            return;
        }

        if (!SPUtilHelpr.isLogin(mActivity, false)) {
            LogUtil.E("点击阻拦 没有登录");
            return;
        }

        if (TextUtils.equals(BusinessSings.PRODUCTSTATE_1, data.getUserProductStatus())) {//认证中显示认证界面

            EventBusModel eventBusModel = new EventBusModel();
            eventBusModel.setTag(EventTags.MAINCHANGESHOWINDEX);
            eventBusModel.setEvInt(MainActivity.SHOWCERT);
            EventBus.getDefault().post(eventBusModel);

        } else if (TextUtils.equals(BusinessSings.PRODUCTSTATE_2, data.getUserProductStatus())) {//人工审核中
            HumanReviewActivity.open(mActivity);

        } else if (TextUtils.equals(BusinessSings.PRODUCTSTATE_3, data.getUserProductStatus())) {//已驳回
            ApplyFailureActivity.open(mActivity, data);

        } else if (TextUtils.equals(BusinessSings.PRODUCTSTATE_4, data.getUserProductStatus())) {//已有额度

            MyMaxMoneyActivity.open(mActivity);
        } else if (TextUtils.equals(BusinessSings.PRODUCTSTATE_5, data.getUserProductStatus())) { //等待放款中

            PutMoneyingActivity.open(mActivity, data.getBorrowCode());

        } else if (TextUtils.equals(BusinessSings.PRODUCTSTATE_6, data.getUserProductStatus())) { //生效中

            UseingMoneyDetailsActivity.open(mActivity, null, true, data.getBorrowCode());

        } else if (TextUtils.equals(BusinessSings.PRODUCTSTATE_7, data.getUserProductStatus())) { //逾期

            UsedMoneyDetailsActivity.open(mActivity, null, data.getBorrowCode());

        } else if (TextUtils.equals(BusinessSings.PRODUCTSTATE_11, data.getUserProductStatus())) {//打款失败
            WaiteMoneyDetailsActivity.open(mActivity, null, data.getBorrowCode());
        }
    }

    @Override
    protected void getListData(int pageIndex, int limit, boolean canShowDialog) {

        Map<String, String> map = new HashMap<>();

        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("uiLocation", "0");
        map.put("userId", SPUtilHelpr.getUserId());

        Call call = RetrofitUtils.createApi(MyApiServer.class).getProductListData("623012", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<PorductListModel>(mActivity) {
            @Override
            protected void onSuccess(PorductListModel data, String SucMessage) {
                setData(data.getList());
                setFooterView();
            }

            @Override
            protected void onBuinessFailure(String code, String error) {
                loadError(error);
            }

            @Override
            protected void onReqFailure(int errorCode, String errorMessage) {
                loadError(errorMessage);
            }

            @Override
            protected void onNoNet(String msg) {
                loadError(msg);
            }

            @Override
            protected void onNull() {
                loadError("暂无产品");
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    private void setFooterView() {

        if (mAdapter == null || mAdapter.getData() == null) {
            return;
        }

        if (mAdapter.getData().size() > 0 && mAdapter.getFooterLayoutCount() == 0 && mActivity != null) {
            LayoutProductFooterviewBinding mFooterView = DataBindingUtil.inflate(mActivity.getLayoutInflater(), R.layout.layout_product_footerview, null, false);
            mAdapter.addFooterView(mFooterView.getRoot());
        } else if (mAdapter.getData().size() == 0) {
            mAdapter.removeAllFooterView();
        }
    }

    @Override
    protected BaseQuickAdapter onCreateAdapter(List<PorductListModel.ListBean> mDataList) {
        return new BorrowMoneyProductAdapter(mDataList);
    }


    @Override
    public String getEmptyInfo() {
        return "暂无产品";
    }

    @Override
    public int getEmptyImg() {
        return R.drawable.no_coupnos;
    }

    @Override
    protected void lazyLoad() {
        if (mBinding != null) {
            onMRefresh(1, mLimit, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && mBinding != null && !isFirstCreate) {
            getListData(1, 10, false);
        }
        isFirstCreate = false;
    }

    @Subscribe
    public void BorrowMoneyEventBus(String tag) {
        if (TextUtils.equals(tag, LOGINREFRESH)) {//登录成功刷新数据
            onMRefresh(1, mLimit, false);
        } else if (TextUtils.equals(tag, BORROWMONEYFRAGMENTREFRESH)) {//刷新数据
            onMRefresh(1, mLimit, false);
        }
    }


    /**
     * 取消
     *
     * @param mo
     */
    @Subscribe
    public void CancleProductState(PorductListModel.ListBean mo) {
        if (mo == null) {
            return;
        }

        CommonDialog commonDialog = new CommonDialog(mActivity).builder()
                .setTitle("提示").setContentMsg("确定取消？")
                .setPositiveBtn("确定", view -> {

                    cancleDoRequest(mo.getCode());

                })
                .setNegativeBtn("取消", null, false);

        commonDialog.show();

    }

    /**
     * 取消操作
     *
     * @param code
     */
    private void cancleDoRequest(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("applyUser", SPUtilHelpr.getUserId());
        map.put("productCode", code);

        Call call = RetrofitUtils.getBaseAPiService().successRequest("623021", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IsSuccessModes>(mActivity) {
            @Override
            protected void onSuccess(IsSuccessModes data, String SucMessage) {
                onMRefresh(1, mLimit, false);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }


}
