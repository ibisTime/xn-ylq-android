package com.cdkj.ylq.model;

import java.math.BigDecimal;

/**额度
 * Created by 李先俊 on 2017/8/14.
 */

public class CanUseMoneyModel {

    private BigDecimal sxAmount;

    private int validDays;

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }
    //    private String validDatetime;

//    public String getValidDatetime() {
//        return validDatetime;
//    }
//
//    public void setValidDatetime(String validDatetime) {
//        this.validDatetime = validDatetime;
//    }

    public BigDecimal getSxAmount() {
        return sxAmount;
    }

    public void setSxAmount(BigDecimal sxAmount) {
        this.sxAmount = sxAmount;
    }
}
