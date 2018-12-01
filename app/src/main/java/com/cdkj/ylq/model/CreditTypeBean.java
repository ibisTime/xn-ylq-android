package com.cdkj.ylq.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * @updateDts 2018/12/1
 */

public class CreditTypeBean {




    /**
     * status : 0
     */

    private String status;
    private int validDays;
    private BigDecimal creditScore;
    private ApplyBean apply;

    public BigDecimal getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(BigDecimal creditScore) {
        this.creditScore = creditScore;
    }

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApplyBean getApply() {
        return apply;
    }

    public void setApply(ApplyBean apply) {
        this.apply = apply;
    }

    public static class ApplyBean {
        /**
         * code : AP2018120117333020973044
         * applyUser : U2018120117144661954293
         * applyDatetime : Dec 1, 2018 5:33:30 PM
         * status : 3
         * creditScore : 0
         * approver : test
         * approveNote : 1
         * approveDatetime : Dec 1, 2018 5:38:55 PM
         * updater : test
         * updateDatetime : Dec 1, 2018 5:38:55 PM
         * remark : 已完成人工审核
         * companyCode : GS2018112119133810071833
         */

        private String code;
        private String applyUser;
        private String applyDatetime;
        @SerializedName("status")
        private String statusX;
        @SerializedName("creditScore")
        private int creditScoreX;
        private String approver;
        private String approveNote;
        private String approveDatetime;
        private String updater;
        private String updateDatetime;
        private String remark;
        private String companyCode;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getApplyUser() {
            return applyUser;
        }

        public void setApplyUser(String applyUser) {
            this.applyUser = applyUser;
        }

        public String getApplyDatetime() {
            return applyDatetime;
        }

        public void setApplyDatetime(String applyDatetime) {
            this.applyDatetime = applyDatetime;
        }

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public int getCreditScoreX() {
            return creditScoreX;
        }

        public void setCreditScoreX(int creditScoreX) {
            this.creditScoreX = creditScoreX;
        }

        public String getApprover() {
            return approver;
        }

        public void setApprover(String approver) {
            this.approver = approver;
        }

        public String getApproveNote() {
            return approveNote;
        }

        public void setApproveNote(String approveNote) {
            this.approveNote = approveNote;
        }

        public String getApproveDatetime() {
            return approveDatetime;
        }

        public void setApproveDatetime(String approveDatetime) {
            this.approveDatetime = approveDatetime;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public String getUpdateDatetime() {
            return updateDatetime;
        }

        public void setUpdateDatetime(String updateDatetime) {
            this.updateDatetime = updateDatetime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }
    }
}
