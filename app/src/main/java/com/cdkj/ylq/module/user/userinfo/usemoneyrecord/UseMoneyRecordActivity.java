package com.cdkj.ylq.module.user.userinfo.usemoneyrecord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cdkj.baselibrary.appmanager.MyConfig;
import com.cdkj.baselibrary.activitys.CommonTablayoutActivity;
import com.cdkj.baselibrary.nets.BaseResponseListCallBack;
import com.cdkj.baselibrary.nets.RetrofitUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.cdkj.ylq.model.KeyDataModel;
import com.cdkj.ylq.module.api.MyApiServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 借款记录
 * Created by 李先俊 on 2017/8/16.
 */

public class UseMoneyRecordActivity extends CommonTablayoutActivity {


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, UseMoneyRecordActivity.class));
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setSubLeftImgState(true);
        setTopTitle("借款记录");
//        getStateKeyData();
        super.afterCreate(savedInstanceState);

    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(UseMoneyRecordFragment.getInstanse(0));
        mFragments.add(UseMoneyRecordFragment.getInstanse(1));
        mFragments.add(UseMoneyRecordFragment.getInstanse(3));
        mFragments.add(UseMoneyRecordFragment.getInstanse(2));


        return mFragments;
    }

    @Override
    public List<String> getFragmentTitles() {
        List<String> mTitles = new ArrayList<>();

        mTitles.add("待放款"); // 0
        mTitles.add("生效中"); //1
        mTitles.add("已还款");//3
        mTitles.add("已逾期"); ///2

        return mTitles;
    }

    /**
     * 获取数据状态key
     */
    public void getStateKeyData() {
        Map<String, String> map = new HashMap<>();
        map.put("parentKey", "borrow_status");
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);
        Call call = RetrofitUtils.createApi(MyApiServer.class).getKeyData("623907", StringUtils.getJsonToString(map));

        addCall(call);

        showLoadingDialog();

        call.enqueue(new BaseResponseListCallBack<KeyDataModel>(this) {
            @Override
            protected void onSuccess(List<KeyDataModel> data, String SucMessage) {

            }

            @Override
            protected void onFinish() {
                disMissLoading();
            }
        });


    }

}
