package com.chengdai.ehealthproject.model.healthmanager.adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李先俊 on 2017/6/23.
 */

public class FoodHotListData implements Parcelable {


    /**
     * code : KL20170617002360824
     * type : A
     * name : 111
     * calorie : 222
     * orderNo : 333
     * remark :
     */

    private String code;
    private String type;
    private String name;
    private String calorie;
    private int orderNo;
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
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
        dest.writeString(this.type);
        dest.writeString(this.name);
        dest.writeString(this.calorie);
        dest.writeInt(this.orderNo);
        dest.writeString(this.remark);
    }

    public FoodHotListData() {
    }

    protected FoodHotListData(Parcel in) {
        this.code = in.readString();
        this.type = in.readString();
        this.name = in.readString();
        this.calorie = in.readString();
        this.orderNo = in.readInt();
        this.remark = in.readString();
    }

    public static final Parcelable.Creator<FoodHotListData> CREATOR = new Parcelable.Creator<FoodHotListData>() {
        @Override
        public FoodHotListData createFromParcel(Parcel source) {
            return new FoodHotListData(source);
        }

        @Override
        public FoodHotListData[] newArray(int size) {
            return new FoodHotListData[size];
        }
    };
}
