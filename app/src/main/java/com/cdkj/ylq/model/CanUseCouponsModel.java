package com.cdkj.ylq.model;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.MoneyUtils;

import java.math.BigDecimal;

/**
 * Created by 李先俊 on 2017/8/16.
 */

public class CanUseCouponsModel implements IPickerViewData {


    /**
     * amount : 50000
     * getDatetime : Aug 16, 2017 3:09:09 PM
     * id : 1
     * invalidDatetime : Sep 15, 2017 12:00:00 AM
     * remark : 系统出错，回收优惠券
     * startAmount : 1000000
     * status : 0
     * type : 1
     * updateDatetime : Aug 16, 2017 3:13:48 PM
     * updater : ylq
     * userId : U201708081809018411896
     * validDays : 30
     */

    private BigDecimal amount;
    private String getDatetime;
    private String id;
    private String invalidDatetime;
    private String remark;
    private BigDecimal startAmount;
    private String status;
    private String type;
    private String updateDatetime;
    private String updater;
    private String userId;
    private int validDays;

    private boolean isDefuit;//判断是不是默认

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(BigDecimal startAmount) {
        this.startAmount = startAmount;
    }

    public String getGetDatetime() {
        return getDatetime;
    }

    public void setGetDatetime(String getDatetime) {
        this.getDatetime = getDatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvalidDatetime() {
        return invalidDatetime;
    }

    public void setInvalidDatetime(String invalidDatetime) {
        this.invalidDatetime = invalidDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }

    public boolean isDefuit() {
        return isDefuit;
    }

    public void setDefuit(boolean defuit) {
        isDefuit = defuit;
    }

    @Override
    public String getPickerViewText() {
        if(isDefuit){
            return "暂不使用优惠券";
        }
        return "减免" + MoneyUtils.showPrice(amount) + "元" + DateUtil.formatStringData(invalidDatetime, DateUtil.DATE_YMD)+"到期";
    }
}
