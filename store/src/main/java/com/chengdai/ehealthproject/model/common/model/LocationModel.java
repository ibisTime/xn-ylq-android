package com.chengdai.ehealthproject.model.common.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李先俊 on 2017/6/13.
 */

public class LocationModel implements Parcelable {

    private String countryName;//国家名

    private String provinceName;//省份

    private String cityName;//城市

    private String areaName;//区域名

    public LocationModel() {
    }

    private String  latitude;//经度
    private String  longitud;//纬度

    public LocationModel(String countryName, String provinceName, String cityName, String areaName, String latitude, String longitud) {
        this.countryName = countryName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.latitude = latitude;
        this.longitud = longitud;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.countryName);
        dest.writeString(this.provinceName);
        dest.writeString(this.cityName);
        dest.writeString(this.areaName);
        dest.writeString(this.latitude);
        dest.writeString(this.longitud);
    }

    protected LocationModel(Parcel in) {
        this.countryName = in.readString();
        this.provinceName = in.readString();
        this.cityName = in.readString();
        this.areaName = in.readString();
        this.latitude = in.readString();
        this.longitud = in.readString();
    }

    public static final Parcelable.Creator<LocationModel> CREATOR = new Parcelable.Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel source) {
            return new LocationModel(source);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };
}
