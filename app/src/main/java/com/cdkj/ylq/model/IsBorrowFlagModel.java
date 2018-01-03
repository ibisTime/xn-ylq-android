package com.cdkj.ylq.model;

/**
 * Created by 李先俊 on 2017/9/9.
 */

public class IsBorrowFlagModel {
    private String isBorrowFlag; //获取当前用户是否有借款 0=没有 1=有 接口623091 使用

    private String toApproveFlag; // 获取用户当前正在进行的申请记录 0=没有 1=有 是否正在审核中 接口623032使用

    public String getToApproveFlag() {
        return toApproveFlag;
    }

    public void setToApproveFlag(String toApproveFlag) {
        this.toApproveFlag = toApproveFlag;
    }

    public String getIsBorrowFlag() {
        return isBorrowFlag;
    }

    public void setIsBorrowFlag(String isBorrowFlag) {
        this.isBorrowFlag = isBorrowFlag;
    }
}
