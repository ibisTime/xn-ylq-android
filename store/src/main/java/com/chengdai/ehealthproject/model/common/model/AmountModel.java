package com.chengdai.ehealthproject.model.common.model;

import java.math.BigDecimal;

/**
 * Created by 李先俊 on 2017/6/16.
 */

public class AmountModel {


    /**
     * accountNumber : A2016122410334393629
     * userId : U2016122116231392740
     * realName : 谢谢
     * type : C
     * status : 0
     * currency : CNY
     * amount : 0
     * frozenAmount : 0
     * md5 : f0ed31502f5d1f206753a5e8114c87e0
     * createDatetime : Dec 24, 2016 10:33:43 AM
     * systemCode : CD-CZH000001
     */

    private String accountNumber;
    private String userId;
    private String realName;
    private String type;
    private String status;
    private String currency;
    private BigDecimal amount;
    private BigDecimal frozenAmount;
    private String md5;
    private String createDatetime;
    private String systemCode;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
