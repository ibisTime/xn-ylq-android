package com.cdkj.ylq.module.user.userinfo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseRefreshFragment;
import com.cdkj.baselibrary.model.IntroductionInfoModel;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.databinding.ActivityCouponsBinding;
import com.cdkj.ylq.model.CoupoonsModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 优惠券列表
 * Created by 李先俊 on 2017/8/8.
 */

public class CouponsListFragment extends BaseRefreshFragment<CoupoonsModel.ListBean> {

    public static final int CANUSE = 1;//能使用
    public static final int CANUSET = 2;//不能使用

    private int requestState;
    private ActivityCouponsBinding mTips;


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
        if (getArguments() != null) {
            requestState = getArguments().getInt("state");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void afterCreate(int pageIndex, int limit) {
        getListData(pageIndex, limit, true);
        if (requestState == CANUSE) {
            getKeyUrl();
        }
    }


    public void getKeyUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("ckey", "couponRule");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);

        Call call = RetrofitUtils.getBaseAPiService().getKeySystemInfo("805917", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<IntroductionInfoModel>(mActivity) {
            @Override
            protected void onSuccess(IntroductionInfoModel data, String SucMessage) {
                if (TextUtils.isEmpty(data.getCvalue())) {
                    if(mTips!=null){
                        mTips.layoutTips.setVisibility(View.GONE);
                    }
                }else{
                    if(mTips!=null){
                        mTips.layoutTips.setVisibility(View.VISIBLE);
                        mTips.tvTips.setText(data.getCvalue());
                    }
                }

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


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
        mTips = DataBindingUtil.inflate(mActivity.getLayoutInflater(), R.layout.activity_coupons, null, false);
        return mTips.getRoot();
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
                helper.setText(R.id.tv_use_can, "限借款" + MoneyUtils.showPrice(item.getStartAmount()) + "元及以上使用");
                helper.setText(R.id.tv_date, "截至日期" + DateUtil.formatStringData(item.getInvalidDatetime(), DateUtil.DATE_YMD));

                if (CANUSE == requestState) {
                    helper.setTextColor(R.id.tv_use_can, ContextCompat.getColor(mActivity, R.color.fontColor_hint));
                    helper.setTextColor(R.id.tv_date, ContextCompat.getColor(mActivity, R.color.fontColor_hint));
                }
            }
        };
    }

    @Override
    public String getEmptyInfo() {


        if (requestState == CANUSE) {

            return "您目前没有优惠券";
        } else if (requestState == CANUSET) {

            return "您目前没有不可使用的优惠券";
        } else {
            return "您目前没有优惠券";
        }

    }

    @Override
    public int getEmptyImg() {
        return R.drawable.no_coupnos;
    }
}
