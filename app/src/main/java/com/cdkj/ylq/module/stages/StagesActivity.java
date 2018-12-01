package com.cdkj.ylq.module.stages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdkj.baselibrary.base.BaseRefreshActivity;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class StagesActivity extends BaseRefreshActivity {
    private ArrayList<UseMoneyRecordModel.ListBean.StageListBean> stageListBeanList;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

//    }

    /**
     * 打开当前界面
     *
     * @param context
     */
    public static void open(Context context, ArrayList<UseMoneyRecordModel.ListBean.StageListBean> stageListBean) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, StagesActivity.class);
        intent.putExtra("stageListBean", stageListBean);
        context.startActivity(intent);
    }

    @Override
    protected void onInit(Bundle savedInstanceState, int pageIndex, int limit) {

        setTopTitle("分期列表");
        setSubLeftImgState(true);
        init();

        mBinding.refreshLayout.setEnableLoadmore(false);
        mBinding.refreshLayout.setEnableRefresh(false);
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                //跳转到  分期详情
                UseMoneyRecordModel.ListBean.StageListBean item = (UseMoneyRecordModel.ListBean.StageListBean) adapter.getItem(position);
                StagesDetailsActivity.open(this, item);
            });
        }
    }

    private void init() {
        if (getIntent() != null) {
            stageListBeanList = (ArrayList<UseMoneyRecordModel.ListBean.StageListBean>) getIntent().getSerializableExtra("stageListBean");
        }
        getListData(1, 10, false);
    }

    @Override
    protected void getListData(int pageIndex, int limit, boolean canShowDialog) {

        if (stageListBeanList != null || stageListBeanList.size() != 0) {
            setData(stageListBeanList);
        }

    }

    @Override
    protected BaseQuickAdapter onCreateAdapter(List mDataList) {
        return new BaseQuickAdapter<UseMoneyRecordModel.ListBean.StageListBean, BaseViewHolder>(R.layout.item_stages, mDataList) {
            @Override
            protected void convert(BaseViewHolder holder, final UseMoneyRecordModel.ListBean.StageListBean bean) {
                holder.setText(R.id.tv_date, bean.getDate());
                holder.setText(R.id.tv_interest, "包含利息" + MoneyUtils.showPrice(bean.getLxAmount()));
                holder.setText(R.id.tv_money, MoneyUtils.showPrice(bean.getAmount()));
                holder.setText(R.id.tv_remark, bean.getRemark());
            }
        };
    }

    @Override
    public String getEmptyInfo() {
        return "暂无分期";
    }

    @Override
    public int getEmptyImg() {
        return 0;
    }


}
