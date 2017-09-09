package com.cdkj.ylq.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 李先俊 on 2017/9/7.
 */

public class RenewalListModel {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * list : [{"code":"XQ2017090719473523538941","applyUser":"U201709041542245654574","borrowCode":"JZB2017090719282939365090","step":7,"cycle":1,"startDate":"Sep 15, 2017 12:00:00 AM","endDate":"Sep 21, 2017 11:59:59 PM","yqAmount":0,"xsAmount":105000,"glAmount":105000,"fwAmount":105000,"lxAmount":70000,"totalAmount":385000,"payDatetime":"Sep 7, 2017 7:48:05 PM","payType":"4","payCode":"线下","payGroup":"PG2017090719473523567281","createDatetime":"Sep 7, 2017 7:47:35 PM","status":"1","curNo":2}]
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * code : XQ2017090719473523538941
         * applyUser : U201709041542245654574
         * borrowCode : JZB2017090719282939365090
         * step : 7
         * cycle : 1
         * startDate : Sep 15, 2017 12:00:00 AM
         * endDate : Sep 21, 2017 11:59:59 PM
         * yqAmount : 0
         * xsAmount : 105000
         * glAmount : 105000
         * fwAmount : 105000
         * lxAmount : 70000
         * totalAmount : 385000
         * payDatetime : Sep 7, 2017 7:48:05 PM
         * payType : 4
         * payCode : 线下
         * payGroup : PG2017090719473523567281
         * createDatetime : Sep 7, 2017 7:47:35 PM
         * status : 1
         * curNo : 2
         */

        private String code;
        private String applyUser;
        private String borrowCode;
        private int step;
        private int cycle;
        private String startDate;
        private String endDate;
        private BigDecimal yqAmount;
        private BigDecimal xsAmount;
        private BigDecimal glAmount;
        private BigDecimal fwAmount;
        private BigDecimal lxAmount;
        private BigDecimal totalAmount;
        private String payDatetime;
        private String payType;
        private String payCode;
        private String payGroup;
        private String createDatetime;
        private String status;
        private int curNo;

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

        public String getBorrowCode() {
            return borrowCode;
        }

        public void setBorrowCode(String borrowCode) {
            this.borrowCode = borrowCode;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public BigDecimal getYqAmount() {
            return yqAmount;
        }

        public void setYqAmount(BigDecimal yqAmount) {
            this.yqAmount = yqAmount;
        }

        public BigDecimal getXsAmount() {
            return xsAmount;
        }

        public void setXsAmount(BigDecimal xsAmount) {
            this.xsAmount = xsAmount;
        }

        public BigDecimal getGlAmount() {
            return glAmount;
        }

        public void setGlAmount(BigDecimal glAmount) {
            this.glAmount = glAmount;
        }

        public BigDecimal getFwAmount() {
            return fwAmount;
        }

        public void setFwAmount(BigDecimal fwAmount) {
            this.fwAmount = fwAmount;
        }

        public BigDecimal getLxAmount() {
            return lxAmount;
        }

        public void setLxAmount(BigDecimal lxAmount) {
            this.lxAmount = lxAmount;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getPayDatetime() {
            return payDatetime;
        }

        public void setPayDatetime(String payDatetime) {
            this.payDatetime = payDatetime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayCode() {
            return payCode;
        }

        public void setPayCode(String payCode) {
            this.payCode = payCode;
        }

        public String getPayGroup() {
            return payGroup;
        }

        public void setPayGroup(String payGroup) {
            this.payGroup = payGroup;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCurNo() {
            return curNo;
        }

        public void setCurNo(int curNo) {
            this.curNo = curNo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.applyUser);
            dest.writeString(this.borrowCode);
            dest.writeInt(this.step);
            dest.writeInt(this.cycle);
            dest.writeString(this.startDate);
            dest.writeString(this.endDate);
            dest.writeSerializable(this.yqAmount);
            dest.writeSerializable(this.xsAmount);
            dest.writeSerializable(this.glAmount);
            dest.writeSerializable(this.fwAmount);
            dest.writeSerializable(this.lxAmount);
            dest.writeSerializable(this.totalAmount);
            dest.writeString(this.payDatetime);
            dest.writeString(this.payType);
            dest.writeString(this.payCode);
            dest.writeString(this.payGroup);
            dest.writeString(this.createDatetime);
            dest.writeString(this.status);
            dest.writeInt(this.curNo);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.applyUser = in.readString();
            this.borrowCode = in.readString();
            this.step = in.readInt();
            this.cycle = in.readInt();
            this.startDate = in.readString();
            this.endDate = in.readString();
            this.yqAmount = (BigDecimal) in.readSerializable();
            this.xsAmount = (BigDecimal) in.readSerializable();
            this.glAmount = (BigDecimal) in.readSerializable();
            this.fwAmount = (BigDecimal) in.readSerializable();
            this.lxAmount = (BigDecimal) in.readSerializable();
            this.totalAmount = (BigDecimal) in.readSerializable();
            this.payDatetime = in.readString();
            this.payType = in.readString();
            this.payCode = in.readString();
            this.payGroup = in.readString();
            this.createDatetime = in.readString();
            this.status = in.readString();
            this.curNo = in.readInt();
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }
}
