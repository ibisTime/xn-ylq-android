package com.chengdai.ehealthproject.model.healthstore.models;

import android.os.Parcel;
import android.os.Parcelable;

/**积分商城图片
 * Created by 李先俊 on 2017/6/19.
 */

public class JfPicModel implements Parcelable {

    /**
     * id : 15
     * ckey : jfPic
     * cvalue : 入口图
     * note : th (3)_1497779245408.jpg
     * updater : jkeg
     * updateDatetime : Jun 18, 2017 5:46:19 PM
     * companyCode : CD-JKEG000011
     * systemCode : CD-JKEG000011
     */

    private int id;
    private String ckey;
    private String cvalue;
    private String note;
    private String updater;
    private String updateDatetime;
    private String companyCode;
    private String systemCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public String getCvalue() {
        return cvalue;
    }

    public void setCvalue(String cvalue) {
        this.cvalue = cvalue;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.ckey);
        dest.writeString(this.cvalue);
        dest.writeString(this.note);
        dest.writeString(this.updater);
        dest.writeString(this.updateDatetime);
        dest.writeString(this.companyCode);
        dest.writeString(this.systemCode);
    }

    public JfPicModel() {
    }

    protected JfPicModel(Parcel in) {
        this.id = in.readInt();
        this.ckey = in.readString();
        this.cvalue = in.readString();
        this.note = in.readString();
        this.updater = in.readString();
        this.updateDatetime = in.readString();
        this.companyCode = in.readString();
        this.systemCode = in.readString();
    }

    public static final Parcelable.Creator<JfPicModel> CREATOR = new Parcelable.Creator<JfPicModel>() {
        @Override
        public JfPicModel createFromParcel(Parcel source) {
            return new JfPicModel(source);
        }

        @Override
        public JfPicModel[] newArray(int size) {
            return new JfPicModel[size];
        }
    };
}
