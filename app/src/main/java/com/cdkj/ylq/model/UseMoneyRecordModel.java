package com.cdkj.ylq.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 李先俊 on 2017/8/17.
 */

public class UseMoneyRecordModel implements Parcelable {

    /**
     * list : [{"amount":1000000,"applyUser":"U201708081809018411896","code":"SIGN-2017081621233835285637","duration":7,"fkDatetime":"测试内容mne8","fwAmount":10000000000,"glAmount":15000000000,"hkDatetime":"测试内容e801","jxDatetime":"测试内容r4t2","lxAmount":16000000000,"payType":"测试内容sp5b","rate1":0.1,"rate2":0.2,"realHkAmount":85477,"realHkDatetime":1,"remark":"新申请借款","signDatetime":"Aug 16, 2017 9:23:38 PM","status":"0","totalAmount":13736,"updateDatetime":"Aug 16, 2017 9:23:38 PM","updater":"U201708081809018411896","xsAmount":26000000000,"yhAmount":0,"yqDays":0,"yqlxAmount":0}]
     * pageNO : 1
     * pageSize : 1
     * start : 0
     * totalCount : 2
     * totalPage : 2
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getFwAmount() {
            return fwAmount;
        }

        public void setFwAmount(BigDecimal fwAmount) {
            this.fwAmount = fwAmount;
        }

        public BigDecimal getGlAmount() {
            return glAmount;
        }

        public void setGlAmount(BigDecimal glAmount) {
            this.glAmount = glAmount;
        }

        public BigDecimal getLxAmount() {
            return lxAmount;
        }

        public void setLxAmount(BigDecimal lxAmount) {
            this.lxAmount = lxAmount;
        }

        public BigDecimal getRate1() {
            return rate1;
        }

        public void setRate1(BigDecimal rate1) {
            this.rate1 = rate1;
        }

        public BigDecimal getRate2() {
            return rate2;
        }

        public void setRate2(BigDecimal rate2) {
            this.rate2 = rate2;
        }

        public BigDecimal getRealHkAmount() {
            return realHkAmount;
        }

        public void setRealHkAmount(BigDecimal realHkAmount) {
            this.realHkAmount = realHkAmount;
        }

        public String getRealHkDatetime() {
            return realHkDatetime;
        }

        public void setRealHkDatetime(String realHkDatetime) {
            this.realHkDatetime = realHkDatetime;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public BigDecimal getXsAmount() {
            return xsAmount;
        }

        public void setXsAmount(BigDecimal xsAmount) {
            this.xsAmount = xsAmount;
        }

        public BigDecimal getYhAmount() {
            return yhAmount;
        }

        public void setYhAmount(BigDecimal yhAmount) {
            this.yhAmount = yhAmount;
        }

        public BigDecimal getYqlxAmount() {
            return yqlxAmount;
        }

        public void setYqlxAmount(BigDecimal yqlxAmount) {
            this.yqlxAmount = yqlxAmount;
        }

        /**
         * amount : 1000000
         * applyUser : U201708081809018411896
         * code : SIGN-2017081621233835285637
         * duration : 7
         * fkDatetime : 测试内容mne8
         * fwAmount : 10000000000
         * glAmount : 15000000000

         * hkDatetime : 测试内容e801
         * jxDatetime : 测试内容r4t2
         * lxAmount : 16000000000
         * payType : 测试内容sp5b
         * rate1 : 0.1
         * rate2 : 0.2
         * realHkAmount : 85477
         * realHkDatetime : 1
         * remark : 新申请借款
         * signDatetime : Aug 16, 2017 9:23:38 PM
         * status : 0
         * totalAmount : 13736
         * updateDatetime : Aug 16, 2017 9:23:38 PM
         * updater : U201708081809018411896
         * xsAmount : 26000000000
         * yhAmount : 0
         * yqDays : 0
         * yqlxAmount : 0
         */

        private BigDecimal amount;
        private String applyUser;
        private String code;
        private int duration;
        private String fkDatetime;
        private BigDecimal fwAmount;
        private BigDecimal glAmount;
        private String hkDatetime;
        private String jxDatetime;
        private BigDecimal lxAmount;
        private String payType;
        private BigDecimal rate1;
        private BigDecimal rate2;
        private BigDecimal realHkAmount;
        private String realHkDatetime;
        private String remark;
        private String signDatetime;
        private String status;
        private BigDecimal totalAmount;
        private String updateDatetime;
        private String updater;
        private String renewalCount;
        private String renewalStartDate;
        private String renewalEndDate;

        public String getRenewalStartDate() {
            return renewalStartDate;
        }

        public void setRenewalStartDate(String renewalStartDate) {
            this.renewalStartDate = renewalStartDate;
        }

        public String getRenewalEndDate() {
            return renewalEndDate;
        }

        public void setRenewalEndDate(String renewalEndDate) {
            this.renewalEndDate = renewalEndDate;
        }

        public String getRenewalCount() {
            return renewalCount;
        }

        public void setRenewalCount(String renewalCount) {
            this.renewalCount = renewalCount;
        }

        private BigDecimal xsAmount;
        private BigDecimal yhAmount;
        private int yqDays;
        private BigDecimal yqlxAmount;

        public String getApplyUser() {
            return applyUser;
        }

        public void setApplyUser(String applyUser) {
            this.applyUser = applyUser;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getFkDatetime() {
            return fkDatetime;
        }

        public void setFkDatetime(String fkDatetime) {
            this.fkDatetime = fkDatetime;
        }

        public String getHkDatetime() {
            return hkDatetime;
        }

        public void setHkDatetime(String hkDatetime) {
            this.hkDatetime = hkDatetime;
        }

        public String getJxDatetime() {
            return jxDatetime;
        }

        public void setJxDatetime(String jxDatetime) {
            this.jxDatetime = jxDatetime;
        }


        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSignDatetime() {
            return signDatetime;
        }

        public void setSignDatetime(String signDatetime) {
            this.signDatetime = signDatetime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public int getYqDays() {
            return yqDays;
        }

        public void setYqDays(int yqDays) {
            this.yqDays = yqDays;
        }

        public ListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSerializable(this.amount);
            dest.writeString(this.applyUser);
            dest.writeString(this.code);
            dest.writeInt(this.duration);
            dest.writeString(this.fkDatetime);
            dest.writeSerializable(this.fwAmount);
            dest.writeSerializable(this.glAmount);
            dest.writeString(this.hkDatetime);
            dest.writeString(this.jxDatetime);
            dest.writeSerializable(this.lxAmount);
            dest.writeString(this.payType);
            dest.writeSerializable(this.rate1);
            dest.writeSerializable(this.rate2);
            dest.writeSerializable(this.realHkAmount);
            dest.writeString(this.realHkDatetime);
            dest.writeString(this.remark);
            dest.writeString(this.signDatetime);
            dest.writeString(this.status);
            dest.writeSerializable(this.totalAmount);
            dest.writeString(this.updateDatetime);
            dest.writeString(this.updater);
            dest.writeString(this.renewalCount);
            dest.writeString(this.renewalStartDate);
            dest.writeString(this.renewalEndDate);
            dest.writeSerializable(this.xsAmount);
            dest.writeSerializable(this.yhAmount);
            dest.writeInt(this.yqDays);
            dest.writeSerializable(this.yqlxAmount);
        }

        protected ListBean(Parcel in) {
            this.amount = (BigDecimal) in.readSerializable();
            this.applyUser = in.readString();
            this.code = in.readString();
            this.duration = in.readInt();
            this.fkDatetime = in.readString();
            this.fwAmount = (BigDecimal) in.readSerializable();
            this.glAmount = (BigDecimal) in.readSerializable();
            this.hkDatetime = in.readString();
            this.jxDatetime = in.readString();
            this.lxAmount = (BigDecimal) in.readSerializable();
            this.payType = in.readString();
            this.rate1 = (BigDecimal) in.readSerializable();
            this.rate2 = (BigDecimal) in.readSerializable();
            this.realHkAmount = (BigDecimal) in.readSerializable();
            this.realHkDatetime = in.readString();
            this.remark = in.readString();
            this.signDatetime = in.readString();
            this.status = in.readString();
            this.totalAmount = (BigDecimal) in.readSerializable();
            this.updateDatetime = in.readString();
            this.updater = in.readString();
            this.renewalCount = in.readString();
            this.renewalStartDate = in.readString();
            this.renewalEndDate = in.readString();
            this.xsAmount = (BigDecimal) in.readSerializable();
            this.yhAmount = (BigDecimal) in.readSerializable();
            this.yqDays = in.readInt();
            this.yqlxAmount = (BigDecimal) in.readSerializable();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    public UseMoneyRecordModel() {
    }

    protected UseMoneyRecordModel(Parcel in) {
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Parcelable.Creator<UseMoneyRecordModel> CREATOR = new Parcelable.Creator<UseMoneyRecordModel>() {
        @Override
        public UseMoneyRecordModel createFromParcel(Parcel source) {
            return new UseMoneyRecordModel(source);
        }

        @Override
        public UseMoneyRecordModel[] newArray(int size) {
            return new UseMoneyRecordModel[size];
        }
    };
}
