package com.cdkj.ylq.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品列表
 * Created by 李先俊 on 2017/8/11.
 */

public class PorductListModel implements Parcelable {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 4
     * totalPage : 1
     * list : [{"code":"CP2017081115474451560213","name":"借贷产品A","slogan":"极速放款","level":"1","amount":1000000,"duration":7,"yqRate1":0.1,"yqRate2":0.2,"lxRate":16000,"xsRate":26000,"glRate":15000,"fwRate":10000,"status":"1","uiLocation":"0","uiOrder":0,"uiColor":"#0cb8ae","updater":"admin","updateDatetime":"Aug 11, 2017 5:03:12 PM","remark":"新上产品update","userProductStatus":"0","isLocked":"0"},{"code":"CP2017081322402533299923","name":"借贷产品B","slogan":"极速放款","level":"2","amount":2000000,"duration":7,"yqRate1":0.1,"yqRate2":0.1,"lxRate":50000,"xsRate":8000,"glRate":10000,"fwRate":15000,"status":"1","uiLocation":"0","uiOrder":2,"uiColor":"#fba72e","updater":"ylq","updateDatetime":"Aug 13, 2017 10:40:38 PM","userProductStatus":"0","isLocked":"1"},{"code":"CP2017081322420874944608","name":"借贷产品C","slogan":"急速放款","level":"3","amount":4000000,"duration":7,"yqRate1":0.1,"yqRate2":0.1,"lxRate":100000,"xsRate":14000,"glRate":30000,"fwRate":50000,"status":"1","uiLocation":"0","uiOrder":3,"uiColor":"#f16254","updater":"ylq","updateDatetime":"Aug 13, 2017 10:42:18 PM","userProductStatus":"0","isLocked":"1"},{"code":"CP2017081322425687468229","name":"借贷产品D","slogan":"急速放款","level":"4","amount":5000000,"duration":7,"yqRate1":0.1,"yqRate2":0.1,"lxRate":400000,"xsRate":15000,"glRate":10000,"fwRate":50000,"status":"1","uiLocation":"0","uiOrder":4,"uiColor":"#28a6e6","updater":"ylq","updateDatetime":"Aug 13, 2017 10:43:10 PM","userProductStatus":"0","isLocked":"1"}]
     */

    private int pageNO;
    private int start;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    public int getPageNO() {
        return pageNO;
    }

    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * code : CP2017081115474451560213
         * name : 借贷产品A
         * slogan : 极速放款
         * level : 1
         * amount : 1000000
         * duration : 7
         * yqRate1 : 0.1
         * yqRate2 : 0.2
         * lxRate : 16000
         * xsRate : 26000
         * glRate : 15000
         * fwRate : 10000
         * status : 1
         * uiLocation : 0
         * uiOrder : 0
         * uiColor : #0cb8ae
         * updater : admin
         * updateDatetime : Aug 11, 2017 5:03:12 PM
         * remark : 新上产品update
         * userProductStatus : 0
         * isLocked : 0
         */

        private String code;
        private String name;
        private String slogan;
        private String level;
        private BigDecimal amount;
        private BigDecimal fwAmount;
        private BigDecimal glAmount;
        private BigDecimal lxAmount;
        private BigDecimal xsAmount;
        private int duration;

        private int hkDays;//还款时间（0表示当天还款）

        public int getHkDays() {
            return hkDays;
        }

        public void setHkDays(int hkDays) {
            this.hkDays = hkDays;
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

        public BigDecimal getXsAmount() {
            return xsAmount;
        }

        public void setXsAmount(BigDecimal xsAmount) {
            this.xsAmount = xsAmount;
        }

        private BigDecimal yqRate1;
        private BigDecimal yqRate2;
        private BigDecimal lxRate;
        private BigDecimal xsRate;
        private BigDecimal glRate;
        private BigDecimal fwRate;
        private String status;
        private String uiLocation;
        private int uiOrder;
        private String uiColor;
        private String color;
        private String updater;
        private String updateDatetime;
        private String remark;
        private String userProductStatus;
        private String isLocked;
        private String borrowCode;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getBorrowCode() {
            return borrowCode;
        }

        public void setBorrowCode(String borrowCode) {
            this.borrowCode = borrowCode;
        }

        public String getApproveNote() {
            return approveNote;
        }

        public void setApproveNote(String approveNote) {
            this.approveNote = approveNote;
        }

        private String approveNote;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getLevel() {
            return level;
        }


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUiLocation() {
            return uiLocation;
        }

        public void setUiLocation(String uiLocation) {
            this.uiLocation = uiLocation;
        }

        public int getUiOrder() {
            return uiOrder;
        }

        public void setUiOrder(int uiOrder) {
            this.uiOrder = uiOrder;
        }

        public String getUiColor() {
            return uiColor;
        }

        public void setUiColor(String uiColor) {
            this.uiColor = uiColor;
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

        public String getUserProductStatus() {
            return userProductStatus;
        }

        public void setUserProductStatus(String userProductStatus) {
            this.userProductStatus = userProductStatus;
        }

        public String getIsLocked() {
            return isLocked;
        }

        public void setIsLocked(String isLocked) {
            this.isLocked = isLocked;
        }

        public ListBean() {
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public BigDecimal getYqRate1() {
            return yqRate1;
        }

        public void setYqRate1(BigDecimal yqRate1) {
            this.yqRate1 = yqRate1;
        }

        public BigDecimal getYqRate2() {
            return yqRate2;
        }

        public void setYqRate2(BigDecimal yqRate2) {
            this.yqRate2 = yqRate2;
        }

        public BigDecimal getLxRate() {
            return lxRate;
        }

        public void setLxRate(BigDecimal lxRate) {
            this.lxRate = lxRate;
        }

        public BigDecimal getXsRate() {
            return xsRate;
        }

        public void setXsRate(BigDecimal xsRate) {
            this.xsRate = xsRate;
        }

        public BigDecimal getGlRate() {
            return glRate;
        }

        public void setGlRate(BigDecimal glRate) {
            this.glRate = glRate;
        }

        public BigDecimal getFwRate() {
            return fwRate;
        }

        public void setFwRate(BigDecimal fwRate) {
            this.fwRate = fwRate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.name);
            dest.writeString(this.slogan);
            dest.writeString(this.level);
            dest.writeSerializable(this.amount);
            dest.writeSerializable(this.fwAmount);
            dest.writeSerializable(this.glAmount);
            dest.writeSerializable(this.lxAmount);
            dest.writeSerializable(this.xsAmount);
            dest.writeInt(this.duration);
            dest.writeInt(this.hkDays);
            dest.writeSerializable(this.yqRate1);
            dest.writeSerializable(this.yqRate2);
            dest.writeSerializable(this.lxRate);
            dest.writeSerializable(this.xsRate);
            dest.writeSerializable(this.glRate);
            dest.writeSerializable(this.fwRate);
            dest.writeString(this.status);
            dest.writeString(this.uiLocation);
            dest.writeInt(this.uiOrder);
            dest.writeString(this.uiColor);
            dest.writeString(this.updater);
            dest.writeString(this.updateDatetime);
            dest.writeString(this.remark);
            dest.writeString(this.userProductStatus);
            dest.writeString(this.isLocked);
            dest.writeString(this.borrowCode);
            dest.writeString(this.approveNote);
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.name = in.readString();
            this.slogan = in.readString();
            this.level = in.readString();
            this.amount = (BigDecimal) in.readSerializable();
            this.fwAmount = (BigDecimal) in.readSerializable();
            this.glAmount = (BigDecimal) in.readSerializable();
            this.lxAmount = (BigDecimal) in.readSerializable();
            this.xsAmount = (BigDecimal) in.readSerializable();
            this.duration = in.readInt();
            this.hkDays = in.readInt();
            this.yqRate1 = (BigDecimal) in.readSerializable();
            this.yqRate2 = (BigDecimal) in.readSerializable();
            this.lxRate = (BigDecimal) in.readSerializable();
            this.xsRate = (BigDecimal) in.readSerializable();
            this.glRate = (BigDecimal) in.readSerializable();
            this.fwRate = (BigDecimal) in.readSerializable();
            this.status = in.readString();
            this.uiLocation = in.readString();
            this.uiOrder = in.readInt();
            this.uiColor = in.readString();
            this.updater = in.readString();
            this.updateDatetime = in.readString();
            this.remark = in.readString();
            this.userProductStatus = in.readString();
            this.isLocked = in.readString();
            this.borrowCode = in.readString();
            this.approveNote = in.readString();
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
        dest.writeInt(this.pageNO);
        dest.writeInt(this.start);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.totalCount);
        dest.writeInt(this.totalPage);
        dest.writeTypedList(this.list);
    }

    public PorductListModel() {
    }

    protected PorductListModel(Parcel in) {
        this.pageNO = in.readInt();
        this.start = in.readInt();
        this.pageSize = in.readInt();
        this.totalCount = in.readInt();
        this.totalPage = in.readInt();
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Parcelable.Creator<PorductListModel> CREATOR = new Parcelable.Creator<PorductListModel>() {
        @Override
        public PorductListModel createFromParcel(Parcel source) {
            return new PorductListModel(source);
        }

        @Override
        public PorductListModel[] newArray(int size) {
            return new PorductListModel[size];
        }
    };
}
