package com.cdkj.ylq.module.renewal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.base.BaseRefreshActivity;
import com.cdkj.baselibrary.nets.BaseResponseModelCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by 李先俊 on 2017/9/6.
 */
//TODO 续期列表
public class RenewalListActivity extends BaseRefreshActivity {

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
        Call call = RetrofitUtils.getBaseAPiService().stringRequest("623090", StringUtils.getJsonToString(map));

        addCall(call);

        if (canShowDialog) showLoadingDialog();

        call.enqueue(new BaseResponseModelCallBack<String>(this) {
            @Override
            protected void onSuccess(String data, String SucMessage) {

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });

    }

    @Override
    protected BaseQuickAdapter onCreateAdapter(List mDataList) {
        return null;
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
