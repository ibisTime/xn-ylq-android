package com.cdkj.ylq.appmanager;

import android.app.Activity;
import android.text.TextUtils;

import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UsedMoneyDetailsActivity;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.UseingMoneyDetailsActivity;
import com.cdkj.ylq.module.user.userinfo.usemoneyrecord.WaiteMoneyDetailsActivity;

/**
 * 一些业务代码标记 逻辑判断
 * Created by 李先俊 on 2017/9/6.
 */

public class BusinessSings {

    //借款记录 //0 待审核 1待放款 2 审核不通过 3生效中 4已还款 5已逾期 6确认还账 7 打款失败
    public static final String USEMONEYRECORD_0 = "0";
    public static final String USEMONEYRECORD_1 = "1";
    public static final String USEMONEYRECORD_2 = "2";
    public static final String USEMONEYRECORD_3 = "3";
    public static final String USEMONEYRECORD_4 = "4";
    public static final String USEMONEYRECORD_5 = "5";
    //   public static final String USEMONEYRECORD_6="6";
    public static final String USEMONEYRECORD_7 = "7";

    //借款记录状态
    public static String getStateRecordString(String status) {
        String str = "";
        if (TextUtils.isEmpty(status)) {
            return str;
        }

        switch (status) {
            case USEMONEYRECORD_0:
                str = "审核中";
                break;
            case USEMONEYRECORD_1:
                str = "审核通过";
                break;
            case USEMONEYRECORD_2:
                str = "审核不通过";
                break;
            case USEMONEYRECORD_3:
                str = "已放款";
                break;
            case USEMONEYRECORD_4:
                str = "已还款";
                break;
            case USEMONEYRECORD_5:
                str = "已逾期";
                break;
            case USEMONEYRECORD_7:
                str = "打款失败";
                break;
        }

        return str;
    }


    //获取产品状态
    //("0", "可申请"),("1", "认证中"),("2", "人工审核中"),( "3", "已驳回"),("4", "已有额度"),("5", "等待放款中"),( "6", "生效中"),("7", "已逾期")
    public static String getProductState(String state) {

        if (TextUtils.isEmpty(state)) {
            return "";
        }

        String stateStr = "";
        ;
        switch (state) {
            case "0":
                stateStr = "立即申请";
                break;
            case "1":
                stateStr = "认证中";
                break;
            case "2":
                stateStr = "系统审核中";
                break;
            case "3":
                stateStr = "已驳回";
                break;
            case "4":
                stateStr = "审核通过 签约";
                break;
            case "5":
                stateStr = "款项正在路上";
                break;
            case "6":
                stateStr = "待还款";
                break;
            case "7":
                stateStr = "已逾期";
                break;
            case "11":
                stateStr = "打款失败";
                break;
        }

        return stateStr;
    }



    /**
     * 根据订单状态跳转相应界面
     *
     * @param activity
     * @param state
     */
    public static void startRecordActivity(Activity activity, UseMoneyRecordModel.ListBean state) {
        if (state == null || activity == null) {
            return;
        }
        if (TextUtils.equals(state.getStatus(), USEMONEYRECORD_0)//待审核
                || TextUtils.equals(state.getStatus(), USEMONEYRECORD_2)//审核不通过
                || TextUtils.equals(state.getStatus(), USEMONEYRECORD_1)//待放款
                || TextUtils.equals(state.getStatus(), USEMONEYRECORD_7)) {//打款失败
            WaiteMoneyDetailsActivity.open(activity, state, "");

        } else if (TextUtils.equals(state.getStatus(), USEMONEYRECORD_3)) {//生效中

            UseingMoneyDetailsActivity.open(activity, state, true, "");//

        } else if (TextUtils.equals(state.getStatus(), USEMONEYRECORD_4)) {//已还款
            UseingMoneyDetailsActivity.open(activity, state, false, "");//

        } else if (TextUtils.equals(state.getStatus(), USEMONEYRECORD_5)) {//已逾期

            UsedMoneyDetailsActivity.open(activity, state, ""); //

        }
    }


}
