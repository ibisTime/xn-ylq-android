package com.chengdai.ehealthproject.model.tabsurrounding.model;

import android.os.Parcel;
import android.os.Parcelable;

/**保存酒店订单支付数据
 * Created by 李先俊 on 2017/6/14.
 */

public class HotelOrderPayModel implements Parcelable {

private String orderCode;//订单号

    private String useName;
    private String usePhoee;
    private String usePs;

    HotelListModel.ListBean mHotelModel;

    private String startShowDate;
    private String endShowDate;

    private String days;

    public String getStartShowDate() {
        return startShowDate;
    }

    public void setStartShowDate(String startShowDate) {
        this.startShowDate = startShowDate;
    }

    public String getEndShowDate() {
        return endShowDate;
    }

    public void setEndShowDate(String endShowDate) {
        this.endShowDate = endShowDate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getUsePhoee() {
        return usePhoee;
    }

    public void setUsePhoee(String usePhoee) {
        this.usePhoee = usePhoee;
    }

    public String getUsePs() {
        return usePs;
    }

    public void setUsePs(String usePs) {
        this.usePs = usePs;
    }

    public HotelListModel.ListBean getmHotelModel() {
        return mHotelModel;
    }

    public void setmHotelModel(HotelListModel.ListBean mHotelModel) {
        this.mHotelModel = mHotelModel;
    }

    public HotelOrderPayModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderCode);
        dest.writeString(this.useName);
        dest.writeString(this.usePhoee);
        dest.writeString(this.usePs);
        dest.writeParcelable(this.mHotelModel, flags);
        dest.writeString(this.startShowDate);
        dest.writeString(this.endShowDate);
        dest.writeString(this.days);
    }

    protected HotelOrderPayModel(Parcel in) {
        this.orderCode = in.readString();
        this.useName = in.readString();
        this.usePhoee = in.readString();
        this.usePs = in.readString();
        this.mHotelModel = in.readParcelable(HotelListModel.ListBean.class.getClassLoader());
        this.startShowDate = in.readString();
        this.endShowDate = in.readString();
        this.days = in.readString();
    }

    public static final Creator<HotelOrderPayModel> CREATOR = new Creator<HotelOrderPayModel>() {
        @Override
        public HotelOrderPayModel createFromParcel(Parcel source) {
            return new HotelOrderPayModel(source);
        }

        @Override
        public HotelOrderPayModel[] newArray(int size) {
            return new HotelOrderPayModel[size];
        }
    };
}
