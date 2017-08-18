package com.cdkj.ylq.module.user.userinfo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.EventTags;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseRefreshFragment;
import com.cdkj.baselibrary.dialog.CommonDialog;
import com.cdkj.baselibrary.model.EventBusModel;
import com.cdkj.baselibrary.model.IsSuccessModes;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.cdkj.ylq.MainActivity;
import com.cdkj.ylq.R;
import com.cdkj.ylq.adapters.BorrowMoneyProductAdapter;
import com.cdkj.ylq.databinding.ActivityCouponsBinding;
import com.cdkj.ylq.databinding.LayoutProductFooterviewBinding;
import com.cdkj.ylq.model.CoupoonsModel;
import com.cdkj.ylq.model.PorductListModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.cdkj.ylq.module.certification.review.HumanReviewActivity;
import com.cdkj.ylq.module.product.ProductDetailsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.cdkj.baselibrary.appmanager.EventTags.BORROWMONEYFRAGMENTREFRESH;
import static com.cdkj.baselibrary.appmanager.EventTags.LOGINREFRESH;

/**
 * 优惠券列表
 * Created by 李先俊 on 2017/8/8.
 */

public class CouponsListFragment extends BaseRefreshFragment<CoupoonsModel.ListBean> {

    public static final int CANUSE = 1;//能使用
    public static final int CANUSET = 2;//不能使用

    private int requestState;


    /**
     * 获得fragment实例
     *
     * @return
     */
    public static CouponsListFragment getInstanse(int state) {
        CouponsListFragment fragment = new CouponsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments()!=null){
            requestState=getArguments().getInt("state");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void afterCreate(int pageIndex, int limit) {
        getListData(pageIndex, limit, true);
    }

    @Override
    protected boolean canLoadEmptyView() {
        if (requestState == CANUSE) {
            return false;
        } else if (requestState == CANUSET) {
            return true;
        }
        return true;
    }

    @Override
    public View getEmptyView() {
        ActivityCouponsBinding mFooterView = DataBindingUtil.inflate(mActivity.getLayoutInflater(), R.layout.activity_coupons, null, false);
        return mFooterView.getRoot();
    }

    @Override
    protected void getListData(int pageIndex, int limit, boolean canShowDialog) {
//0=可使用 1=已使用 2=已过期 12=已使用或已过期
        Map<String, String> map = new HashMap<>();
        map.put("limit", limit + "");
        map.put("start", pageIndex + "");
        map.put("userId", SPUtilHelpr.getUserId());

        if (requestState == CANUSE) {
            map.put("status", "0");
        } else if (requestState == CANUSET) {
            map.put("status", "12");
        } else {
            map.put("status", "0");
        }


        Call call = RetrofitUtils.createApi(MyApiServer.class).getCouponsListData("623147", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<CoupoonsModel>(mActivity) {
            @Override
            protected void onSuccess(CoupoonsModel data, String SucMessage) {
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
                loadError("您目前没有优惠券");
            }


            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    protected BaseQuickAdapter onCreateAdapter(List<CoupoonsModel.ListBean> mDataList) {
        return new BaseQuickAdapter<CoupoonsModel.ListBean, BaseViewHolder>(R.layout.item_coupons, mDataList) {
            @Override
            protected void convert(BaseViewHolder helper, CoupoonsModel.ListBean item) {
                if (item == null) return;
                helper.setText(R.id.tv_money, MoneyUtils.showPrice(item.getAmount()));
                helper.setText(R.id.tv_use_can, "限借款"+MoneyUtils.showPrice(item.getStartAmount())+"元及以上使用");
                helper.setText(R.id.tv_date,"截至日期"+ DateUtil.formatStringData(item.getInvalidDatetime(),DateUtil.DATE_YMD));

                if(CANUSE == requestState){
                    helper.setTextColor(R.id.tv_use_can, ContextCompat.getColor(mActivity,R.color.fontColor_hint));
                    helper.setTextColor(R.id.tv_date, ContextCompat.getColor(mActivity,R.color.fontColor_hint));
                }
        }
        };
    }

    @Override
    public String getEmptyInfo() {
        return "您目前没有优惠券";
    }

    @Override
    public int getEmptyImg() {
        return R.drawable.no_coupnos;
    }
}
