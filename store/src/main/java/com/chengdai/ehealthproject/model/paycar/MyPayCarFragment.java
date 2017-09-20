package com.chengdai.ehealthproject.model.paycar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.baselibrary.utils.ToastUtil;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.databinding.ActivityPayCarSelectBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopCarPayActivity;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopPayCarListAdApter;
import com.chengdai.ehealthproject.model.healthstore.models.PayCarListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore.IMGURL;

/**
 * 我的购物车辆
 * Created by 李先俊 on 2017/9/20.
 */

public class MyPayCarFragment extends BaseLazyFragment {
    private ActivityPayCarSelectBinding mBinding;

    private int mPageStart = 1;//分页索引
    private ShopPayCarListAdApter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.activity_pay_car_select, null, false);
        initViews();
        return mBinding.getRoot();
    }


    private void initViews() {

        mBinding.springview.setType(SpringView.Type.FOLLOW);
        mBinding.springview.setGive(SpringView.Give.TOP);
        mBinding.springview.setHeader(new DefaultHeader(mActivity));
        mBinding.springview.setFooter(new DefaultFooter(mActivity));


        mBinding.springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart = 1;
                getCarListRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getCarListRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }
        });

        mAdapter = new ShopPayCarListAdApter(mActivity, new ArrayList<>());
        mBinding.listAddress.setAdapter(mAdapter);

        mBinding.listAddress.setOnItemClickListener((parent, view, position, id) -> {
            mAdapter.setmSelectPosition(position);
            mBinding.txtDiscountMoney.setText(mAdapter.getSelectPriceShowString());

        });

        mBinding.txtPay.setOnClickListener(v -> {

            if (mAdapter.getCount() == 0) {
                ToastUtil.show(mActivity, "请选择结算订单");
                return;
            }

            PayCarListModel.ListBean selectData = mAdapter.getmSelectPositionItem();
            if (selectData == null || selectData.getProductSpecs() == null || selectData.getProduct() == null) {
                ToastUtil.show(mActivity, "请选择结算订单");
                return;
            }
            ShopListModel.ListBean.ProductSpecsListBean bean = new ShopListModel.ListBean.ProductSpecsListBean();
            bean.setCode(selectData.getCode());
            bean.setPrice1(selectData.getProductSpecs().getPrice1());
            bean.setName(selectData.getProduct().getName());

            ShopCarPayActivity.open(mActivity, bean, selectData.getQuantity(), IMGURL + selectData.getProduct().getSplitAdvPic());

        });
    }


    private void getCarListRequest(Context context) {

        if (!SPUtilHelpr.isLoginNoStart()) {
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("start", mPageStart + "");
        map.put("limit", "10");

        mSubscription.add(RetrofitUtils.getLoaderServer().GetPayCarList("808045", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(data -> {

                    if (mPageStart == 1) {
                        if (data != null && data.getList() != null) {
                            mAdapter.setData(data.getList());
                        }
                        showEmptyState();

                    } else {
                        if (data != null && data.getList() != null && data.getList().size() > 0) {
                            mAdapter.addData(data.getList());
                        } else if (mPageStart > 1) {
                            mPageStart--;
                        }
                    }

                    mBinding.txtDiscountMoney.setText(mAdapter.getSelectPriceShowString());
                }, Throwable::printStackTrace));

    }

    /**
     *设置空数据显示状态
     */
    private void showEmptyState() {
        if (mAdapter.getCount() <= 0) {
            mBinding.springview.setVisibility(View.GONE);
            mBinding.linMoneySum.setVisibility(View.GONE);
            mBinding.tvCarEmpty.setVisibility(View.VISIBLE);
        }else{
            mBinding.springview.setVisibility(View.VISIBLE);
            mBinding.linMoneySum.setVisibility(View.VISIBLE);
            mBinding.tvCarEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && mBinding != null) {
            getCarListRequest(mActivity);
        }
    }

    @Override
    protected void lazyLoad() {
        getCarListRequest(null);
    }

    @Override
    protected void onInvisible() {

    }

    //ShopPayCarListAdapter  刷新数据
    @Subscribe
    public void CarRefrshRequestEvent(EventBusModel model) {
        if (model != null && "ShopPayCarSelectActivityFresh".equals(model.getTag()) && mAdapter != null) {
            showEmptyState();
            mBinding.txtDiscountMoney.setText(mAdapter.getSelectPriceShowString());
        }
    }

}
