package com.cdkj.ylq.module.user.userinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.appmanager.SPUtilHelpr;
import com.cdkj.baselibrary.base.BaseRefreshActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.model.HuokeListModel;
import com.cdkj.ylq.module.api.MyApiServer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 获客人列表
 * Created by 李先俊 on 2017/7/18.
 */

public class HuokeListActivity extends BaseRefreshActivity<HuokeListModel.ListBean> {


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, HuokeListActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onInit(Bundle savedInstanceState, int pageIndex, int limit) {

        setSubLeftImgState(true);

        setTopTitle("推荐历史");

        getListData(pageIndex, limit, true);

    }

    @Override
    protected void getListData(int pageIndex, int limit, boolean canShowDialog) {

        Map<String, String> map = new HashMap<>();
        map.put("start", pageIndex + "");
        map.put("limit", limit + "");
        map.put("userReferee", SPUtilHelpr.getUserId());
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

        Call call = RetrofitUtils.createApi(MyApiServer.class).getHuokeListData("805120", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<HuokeListModel>(this) {
            @Override
            protected void onSuccess(HuokeListModel data, String SucMessage) {
                setData(data.getList());
            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    protected BaseQuickAdapter onCreateAdapter(List<HuokeListModel.ListBean> mDataList) {
        return new BaseQuickAdapter<HuokeListModel.ListBean, BaseViewHolder>(R.layout.item_friend, mDataList) {
            @Override
            protected void convert(BaseViewHolder helper, HuokeListModel.ListBean item) {
                if (item == null) return;

                ImgUtils.loadActLogo(HuokeListActivity.this, SPUtilHelpr.getQiNiuUrl() + item.getPhoto(), helper.getView(R.id.img_photo));

                helper.setText(R.id.tv_name, item.getMobile());
                helper.setText(R.id.tv_time, DateUtil.formatStringData(item.getCreateDatetime(), DateUtil.DATE_YMD));

            }
        };

    }

    @Override
    public String getEmptyInfo() {
        return "您还没有推荐历史";
    }

    @Override
    public int getEmptyImg() {
        return R.drawable.no_coupnos;
    }

}
