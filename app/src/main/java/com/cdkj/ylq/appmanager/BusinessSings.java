package com.cdkj.ylq.appmanager;

import android.text.TextUtils;

/**业务务代码标记
 * Created by 李先俊 on 2017/9/6.
 */

public class BusinessSings {

    //借款记录 //0 待审核 1待放款 2 审核不通过 3生效中 4已还款 5已逾期 6确认还账 7 打款失败
   public static final String USEMONEYRECORD_0="0";
   public static final String USEMONEYRECORD_1="1";
   public static final String USEMONEYRECORD_2="2";
   public static final String USEMONEYRECORD_3="3";
   public static final String USEMONEYRECORD_4="4";
   public static final String USEMONEYRECORD_5="5";
//   public static final String USEMONEYRECORD_6="6";
   public static final String USEMONEYRECORD_7="7";

    public  static String getStateRecordString(String status) {
        String str = "";
        if (TextUtils.isEmpty(status)) {
            return str;
        }

        switch (status) {
            case "0":
                str = "审核中";
                break;
            case "1":
                str = "审核通过";
                break;
            case "2":
                str = "审核不通过";
                break;
            case "3":
                str = "已放款";
                break;
            case "4":
                str = "已还款";
                break;
            case "5":
                str = "已逾期";
                break;
            case "7":
                str = "打款失败";
                break;
        }

        return str;
    }



}
