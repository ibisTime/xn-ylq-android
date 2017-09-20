package com.chengdai.ehealthproject.model.healthmanager.adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李先俊 on 2017/6/26.
 */

public class HealthTaskListMdoel implements Parcelable {

    /**
     * code : HT20170617501402242
     * logo : bmi_1498101133818.png
     * name : 吃早餐
     * summary : 早餐是大脑活动的能量之源
     * cjNum : 0
     * orderNo : 1
     * remark :
     */

    private String code;
    private String logo;
    private String name;
    private String summary;
    private int cjNum;
    private int orderNo;
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getCjNum() {
        return cjNum;
    }

    public void setCjNum(int cjNum) {
        this.cjNum = cjNum;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.logo);
        dest.writeString(this.name);
        dest.writeString(this.summary);
        dest.writeInt(this.cjNum);
        dest.writeInt(this.orderNo);
        dest.writeString(this.remark);
    }

    public HealthTaskListMdoel() {
    }

    protected HealthTaskListMdoel(Parcel in) {
        this.code = in.readString();
        this.logo = in.readString();
        this.name = in.readString();
        this.summary = in.readString();
        this.cjNum = in.readInt();
        this.orderNo = in.readInt();
        this.remark = in.readString();
    }

    public static final Parcelable.Creator<HealthTaskListMdoel> CREATOR = new Parcelable.Creator<HealthTaskListMdoel>() {
        @Override
        public HealthTaskListMdoel createFromParcel(Parcel source) {
            return new HealthTaskListMdoel(source);
        }

        @Override
        public HealthTaskListMdoel[] newArray(int size) {
            return new HealthTaskListMdoel[size];
        }
    };
}
