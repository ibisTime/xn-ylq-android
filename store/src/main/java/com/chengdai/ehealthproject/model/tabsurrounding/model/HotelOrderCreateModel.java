package com.chengdai.ehealthproject.model.tabsurrounding.model;

import android.os.Parcel;
import android.os.Parcelable;

/**用于储存酒店民宿一些订单信息
 * Created by 李先俊 on 2017/6/14.
 */

public class HotelOrderCreateModel implements Parcelable {


    private String startData;
    private String endDate;

    private String startShowData;
    private String endShowDate;

    private  int days;

    private String hotenAddress;

    private String hotelName;//酒店名称

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    private  HotelListModel.ListBean  hotelInfoModel;

    public String getStartShowData() {
        return startShowData;
    }

    public void setStartShowData(String startShowData) {
        this.startShowData = startShowData;
    }

    public String getEndShowDate() {
        return endShowDate;
    }

    public void setEndShowDate(String endShowDate) {
        this.endShowDate = endShowDate;
    }

    public HotelOrderCreateModel(String startData, String endDate, int days, String hotenAddress, HotelListModel.ListBean hotelInfoModel) {
        this.startShowData = startData;
        this.endShowDate = endDate;
        this.days = days;
        this.hotenAddress = hotenAddress;
        this.hotelInfoModel = hotelInfoModel;
    }

    public HotelListModel.ListBean getHotelInfoModel() {

        return hotelInfoModel;
    }

    public void setHotelInfoModel(HotelListModel.ListBean hotelInfoModel) {
        this.hotelInfoModel = hotelInfoModel;
    }

    public String getStartData() {
        return startData;
    }

    public void setStartData(String startData) {
        this.startData = startData;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getHotenAddress() {
        return hotenAddress;
    }

    public void setHotenAddress(String hotenAddress) {
        this.hotenAddress = hotenAddress;
    }


    public HotelOrderCreateModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startData);
        dest.writeString(this.endDate);
        dest.writeString(this.startShowData);
        dest.writeString(this.endShowDate);
        dest.writeInt(this.days);
        dest.writeString(this.hotenAddress);
        dest.writeString(this.hotelName);
        dest.writeParcelable(this.hotelInfoModel, flags);
    }

    protected HotelOrderCreateModel(Parcel in) {
        this.startData = in.readString();
        this.endDate = in.readString();
        this.startShowData = in.readString();
        this.endShowDate = in.readString();
        this.days = in.readInt();
        this.hotenAddress = in.readString();
        this.hotelName = in.readString();
        this.hotelInfoModel = in.readParcelable(HotelListModel.ListBean.class.getClassLoader());
    }

    public static final Creator<HotelOrderCreateModel> CREATOR = new Creator<HotelOrderCreateModel>() {
        @Override
        public HotelOrderCreateModel createFromParcel(Parcel source) {
            return new HotelOrderCreateModel(source);
        }

        @Override
        public HotelOrderCreateModel[] newArray(int size) {
            return new HotelOrderCreateModel[size];
        }
    };
}
