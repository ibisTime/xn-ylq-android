package com.cdkj.ylq.module.renewal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.base.BaseRefreshActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.model.RenewalListModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by 李先俊 on 2017/9/6.
 */
public class RenewalListActivity extends BaseRefreshActivity<RenewalListModel.ListBean> {

    private String mCode;

    /**
     * @param context
     * @param code    订单code
     */
    public static void open(Context context, String code) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, RenewalListActivity.class);
        intent.putExtra("code", code);
        context.startActivity(intent);
    }


    @Override
    protected void onInit(Bundle savedInstanceState, int pageIndex, int limit) {
        if (getIntent() != null) {
            mCode = getIntent().getStringExtra("code");
        }

        setTopTitle("续期记录");

        setSubLeftImgState(true);

        if(mAdapter!=null){
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    RenewalDetailsActivity.open(this, (RenewalListModel.ListBean) adapter.getItem(position));
            });
        }


        onMRefresh(pageIndex,limit,true);
    }

    @Override
    protected void getListData(int pageIndex, int limit, boolean canShowDialog) {

        if (TextUtils.isEmpty(mCode)) {
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("borrowCode", mCode);
        map.put("start", pageIndex + "");
        map.put("limit", limit + "");
        Call call = RetrofitUtils.createApi(MyApiServer.class).getRenewalListData("623090", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<RenewalListModel>(this) {
            @Override
            protected void onSuccess(RenewalListModel data, String SucMessage) {
                setData(data.getList());
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    protected BaseQuickAdapter onCreateAdapter(List<RenewalListModel.ListBean> mDataList) {
        return new BaseQuickAdapter<RenewalListModel.ListBean, BaseViewHolder>(R.layout.item_renewal, mDataList) {
            @Override
            protected void convert(BaseViewHolder helper, RenewalListModel.ListBean item) {
                if (item == null) return;

                helper.setText(R.id.tv_money, MoneyUtils.showPrice(item.getTotalAmount())+"元");
                helper.setText(R.id.tv_date, DateUtil.formatStringData(item.getCreateDatetime(),DateUtil.DATE_YMD));
                helper.setText(R.id.tv_days, item.getStep()+" 天");

            }
        };
    }

    @Override
    public String getEmptyInfo() {
        return "暂无续期记录";
    }

    @Override
    public int getEmptyImg() {
        return 0;
    }
}
