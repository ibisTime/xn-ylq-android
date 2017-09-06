package com.cdkj.ylq.module.user.userinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.base.BaseRefreshActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.model.MsgListModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 消息中心
 * Created by 李先俊 on 2017/8/9.
 */

public class MsgListActivity extends BaseRefreshActivity<MsgListModel.ListBean> {


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MsgListActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onInit(Bundle savedInstanceState, int pageIndex, int limit) {
        setSubLeftImgState(true);
        setTopTitle("消息");
        getListData(pageIndex, limit, true);

        if(mAdapter!=null){
           mAdapter.setOnItemClickListener((adapter, view, position) -> {
               MsgDetailsActivity.open(this,(MsgListModel.ListBean) adapter.getItem(position));
           });
        }
    }

    @Override
    protected void getListData(int page, int limit, boolean canShowDialog) {


        Map<String, String> map = new HashMap<>();
        map.put("channelType", "4");
        map.put("start", page + "");
        map.put("limit", limit + "");
        map.put("pushType", "41");
        map.put("toKind", "1");
        map.put("status", "1");
        map.put("fromSystemCode", MyConfig.SYSTEMCODE);
        map.put("toSystemCode", MyConfig.SYSTEMCODE);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getMsgList("804040", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<MsgListModel>(this) {
            @Override
            protected void onSuccess(MsgListModel data, String SucMessage) {
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
            protected void onNull() {
                loadError("您还没有消息");
            }

            @Override
            protected void onNoNet(String msg) {
                loadError(msg);
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });
    }

    @Override
    protected BaseQuickAdapter onCreateAdapter(List<MsgListModel.ListBean> mDataList) {
        return new BaseQuickAdapter<MsgListModel.ListBean, BaseViewHolder>(R.layout.item_msg, mDataList) {
            @Override
            protected void convert(BaseViewHolder helper, MsgListModel.ListBean item) {
                if (item == null) return;

                helper.setText(R.id.tv_content, item.getSmsContent());
                helper.setText(R.id.tv_title, item.getSmsTitle());
                helper.setText(R.id.tv_time, DateUtil.formatStringData(item.getCreateDatetime(), DateUtil.DATE_YMD));

            }
        };
    }

    @Override
    public String getEmptyInfo() {
        return "您还没有消息";
    }

    @Override
    public int getEmptyImg() {
        return 0;
    }


}
