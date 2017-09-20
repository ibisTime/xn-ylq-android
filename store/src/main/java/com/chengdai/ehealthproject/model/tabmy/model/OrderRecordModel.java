package com.chengdai.ehealthproject.model.tabmy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/15.
 */

public class OrderRecordModel implements Parcelable {

    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 9
     * totalPage : 1
     * list : [{"code":"XF2017061310504941253792","userId":"U201706091555347931953","storeCode":"SJ2017061219110399944422","price":770,"backAmount":0,"createDatetime":"Jun 13, 2017 10:50:49 AM","status":"1","payType":"1","payAmount1":0,"payAmount3":0,"payDatetime":"Jun 13, 2017 10:50:49 AM","remark":"云币余额支付-O2O消费","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","store":{"code":"SJ2017061219110399944422","name":"店铺啊","level":"1","category":"FL2017060915535389718625","type":"FL2017061017281129736403","slogan":"111","advPic":"th (1)_1497265884457.jpg","pic":"th (2)_1497265890764.jpg","description":"222","province":"浙江省","city":"杭州市","area":"余杭区","address":"222","longitude":"119.998089","latitude":"30.38812","bookMobile":"18611112222","smsMobile":"18611112222","uiLocation":"1","uiOrder":"1","userReferee":"U201706121841587774403","rate1":1,"rate2":0,"rate3":0,"isDefault":"1","status":"2","updater":"libo","updateDatetime":"Jun 12, 2017 7:28:16 PM","owner":"U201706121911038741558","contractNo":"ZHS-2017061219110399918558","totalRmbNum":0,"totalJfNum":0,"totalDzNum":1,"totalScNum":0,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"},"user":{"userId":"U201706091555347931953","kind":"f1","loginName":"13765051712","nickname":"47931953","mobile":"13765051712","identityFlag":"0"},"storeUser":{"userId":"U201706121911038741558","kind":"f2","loginName":"18611112222","nickname":"38741558","mobile":"18611112222","identityFlag":"0","userReferee":"U201706121841587774403","divRate":0}}]
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
         * code : XF2017061310504941253792
         * userId : U201706091555347931953
         * storeCode : SJ2017061219110399944422
         * price : 770
         * backAmount : 0
         * createDatetime : Jun 13, 2017 10:50:49 AM
         * status : 1
         * payType : 1
         * payAmount1 : 0
         * payAmount3 : 0
         * payDatetime : Jun 13, 2017 10:50:49 AM
         * remark : 云币余额支付-O2O消费
         * companyCode : CD-JKEG000011
         * systemCode : CD-JKEG000011
         * store : {"code":"SJ2017061219110399944422","name":"店铺啊","level":"1","category":"FL2017060915535389718625","type":"FL2017061017281129736403","slogan":"111","advPic":"th (1)_1497265884457.jpg","pic":"th (2)_1497265890764.jpg","description":"222","province":"浙江省","city":"杭州市","area":"余杭区","address":"222","longitude":"119.998089","latitude":"30.38812","bookMobile":"18611112222","smsMobile":"18611112222","uiLocation":"1","uiOrder":"1","userReferee":"U201706121841587774403","rate1":1,"rate2":0,"rate3":0,"isDefault":"1","status":"2","updater":"libo","updateDatetime":"Jun 12, 2017 7:28:16 PM","owner":"U201706121911038741558","contractNo":"ZHS-2017061219110399918558","totalRmbNum":0,"totalJfNum":0,"totalDzNum":1,"totalScNum":0,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}
         * user : {"userId":"U201706091555347931953","kind":"f1","loginName":"13765051712","nickname":"47931953","mobile":"13765051712","identityFlag":"0"}
         * storeUser : {"userId":"U201706121911038741558","kind":"f2","loginName":"18611112222","nickname":"38741558","mobile":"18611112222","identityFlag":"0","userReferee":"U201706121841587774403","divRate":0}
         */

        private String code;
        private String userId;
        private String storeCode;
        private BigDecimal price;
        private int backAmount;
        private String createDatetime;
        private String status;
        private String payType;
        private int payAmount1;
        private int payAmount3;
        private String payDatetime;
        private String remark;
        private String companyCode;
        private String systemCode;
        private StoreBean store;
        private UserBean user;
        private StoreUserBean storeUser;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public int getBackAmount() {
            return backAmount;
        }

        public void setBackAmount(int backAmount) {
            this.backAmount = backAmount;
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

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public int getPayAmount1() {
            return payAmount1;
        }

        public void setPayAmount1(int payAmount1) {
            this.payAmount1 = payAmount1;
        }

        public int getPayAmount3() {
            return payAmount3;
        }

        public void setPayAmount3(int payAmount3) {
            this.payAmount3 = payAmount3;
        }

        public String getPayDatetime() {
            return payDatetime;
        }

        public void setPayDatetime(String payDatetime) {
            this.payDatetime = payDatetime;
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

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public StoreBean getStore() {
            return store;
        }

        public void setStore(StoreBean store) {
            this.store = store;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public StoreUserBean getStoreUser() {
            return storeUser;
        }

        public void setStoreUser(StoreUserBean storeUser) {
            this.storeUser = storeUser;
        }

        public static class StoreBean implements Parcelable {
            /**
             * code : SJ2017061219110399944422
             * name : 店铺啊
             * level : 1
             * category : FL2017060915535389718625
             * type : FL2017061017281129736403
             * slogan : 111
             * advPic : th (1)_1497265884457.jpg
             * pic : th (2)_1497265890764.jpg
             * description : 222
             * province : 浙江省
             * city : 杭州市
             * area : 余杭区
             * address : 222
             * longitude : 119.998089
             * latitude : 30.38812
             * bookMobile : 18611112222
             * smsMobile : 18611112222
             * uiLocation : 1
             * uiOrder : 1
             * userReferee : U201706121841587774403
             * rate1 : 1
             * rate2 : 0
             * rate3 : 0
             * isDefault : 1
             * status : 2
             * updater : libo
             * updateDatetime : Jun 12, 2017 7:28:16 PM
             * owner : U201706121911038741558
             * contractNo : ZHS-2017061219110399918558
             * totalRmbNum : 0
             * totalJfNum : 0
             * totalDzNum : 1
             * totalScNum : 0
             * companyCode : CD-JKEG000011
             * systemCode : CD-JKEG000011
             */

            private String code;
            private String name;
            private String level;
            private String category;
            private String type;
            private String slogan;
            private String advPic;
            private String pic;
            private String description;
            private String province;
            private String city;
            private String area;
            private String address;
            private String longitude;
            private String latitude;
            private String bookMobile;
            private String smsMobile;
            private String uiLocation;
            private String uiOrder;
            private String userReferee;
            private int rate1;
            private int rate2;
            private int rate3;
            private String isDefault;
            private String status;
            private String updater;
            private String updateDatetime;
            private String owner;
            private String contractNo;
            private int totalRmbNum;
            private int totalJfNum;
            private int totalDzNum;
            private int totalScNum;
            private String companyCode;
            private String systemCode;

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

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
            }

            public String getAdvPic() {
                return advPic;
            }

             public String getSplitAdvPic() {

                 List<String> s= StringUtils.splitAsList(advPic,"\\|\\|");
                 if(s .size()>1)
                 {
                     return s.get(0);
                 }

                return advPic;
            }

            public void setAdvPic(String advPic) {
                this.advPic = advPic;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getBookMobile() {
                return bookMobile;
            }

            public void setBookMobile(String bookMobile) {
                this.bookMobile = bookMobile;
            }

            public String getSmsMobile() {
                return smsMobile;
            }

            public void setSmsMobile(String smsMobile) {
                this.smsMobile = smsMobile;
            }

            public String getUiLocation() {
                return uiLocation;
            }

            public void setUiLocation(String uiLocation) {
                this.uiLocation = uiLocation;
            }

            public String getUiOrder() {
                return uiOrder;
            }

            public void setUiOrder(String uiOrder) {
                this.uiOrder = uiOrder;
            }

            public String getUserReferee() {
                return userReferee;
            }

            public void setUserReferee(String userReferee) {
                this.userReferee = userReferee;
            }

            public int getRate1() {
                return rate1;
            }

            public void setRate1(int rate1) {
                this.rate1 = rate1;
            }

            public int getRate2() {
                return rate2;
            }

            public void setRate2(int rate2) {
                this.rate2 = rate2;
            }

            public int getRate3() {
                return rate3;
            }

            public void setRate3(int rate3) {
                this.rate3 = rate3;
            }

            public String getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(String isDefault) {
                this.isDefault = isDefault;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getContractNo() {
                return contractNo;
            }

            public void setContractNo(String contractNo) {
                this.contractNo = contractNo;
            }

            public int getTotalRmbNum() {
                return totalRmbNum;
            }

            public void setTotalRmbNum(int totalRmbNum) {
                this.totalRmbNum = totalRmbNum;
            }

            public int getTotalJfNum() {
                return totalJfNum;
            }

            public void setTotalJfNum(int totalJfNum) {
                this.totalJfNum = totalJfNum;
            }

            public int getTotalDzNum() {
                return totalDzNum;
            }

            public void setTotalDzNum(int totalDzNum) {
                this.totalDzNum = totalDzNum;
            }

            public int getTotalScNum() {
                return totalScNum;
            }

            public void setTotalScNum(int totalScNum) {
                this.totalScNum = totalScNum;
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
                dest.writeString(this.code);
                dest.writeString(this.name);
                dest.writeString(this.level);
                dest.writeString(this.category);
                dest.writeString(this.type);
                dest.writeString(this.slogan);
                dest.writeString(this.advPic);
                dest.writeString(this.pic);
                dest.writeString(this.description);
                dest.writeString(this.province);
                dest.writeString(this.city);
                dest.writeString(this.area);
                dest.writeString(this.address);
                dest.writeString(this.longitude);
                dest.writeString(this.latitude);
                dest.writeString(this.bookMobile);
                dest.writeString(this.smsMobile);
                dest.writeString(this.uiLocation);
                dest.writeString(this.uiOrder);
                dest.writeString(this.userReferee);
                dest.writeInt(this.rate1);
                dest.writeInt(this.rate2);
                dest.writeInt(this.rate3);
                dest.writeString(this.isDefault);
                dest.writeString(this.status);
                dest.writeString(this.updater);
                dest.writeString(this.updateDatetime);
                dest.writeString(this.owner);
                dest.writeString(this.contractNo);
                dest.writeInt(this.totalRmbNum);
                dest.writeInt(this.totalJfNum);
                dest.writeInt(this.totalDzNum);
                dest.writeInt(this.totalScNum);
                dest.writeString(this.companyCode);
                dest.writeString(this.systemCode);
            }

            public StoreBean() {
            }

            protected StoreBean(Parcel in) {
                this.code = in.readString();
                this.name = in.readString();
                this.level = in.readString();
                this.category = in.readString();
                this.type = in.readString();
                this.slogan = in.readString();
                this.advPic = in.readString();
                this.pic = in.readString();
                this.description = in.readString();
                this.province = in.readString();
                this.city = in.readString();
                this.area = in.readString();
                this.address = in.readString();
                this.longitude = in.readString();
                this.latitude = in.readString();
                this.bookMobile = in.readString();
                this.smsMobile = in.readString();
                this.uiLocation = in.readString();
                this.uiOrder = in.readString();
                this.userReferee = in.readString();
                this.rate1 = in.readInt();
                this.rate2 = in.readInt();
                this.rate3 = in.readInt();
                this.isDefault = in.readString();
                this.status = in.readString();
                this.updater = in.readString();
                this.updateDatetime = in.readString();
                this.owner = in.readString();
                this.contractNo = in.readString();
                this.totalRmbNum = in.readInt();
                this.totalJfNum = in.readInt();
                this.totalDzNum = in.readInt();
                this.totalScNum = in.readInt();
                this.companyCode = in.readString();
                this.systemCode = in.readString();
            }

            public static final Creator<StoreBean> CREATOR = new Creator<StoreBean>() {
                @Override
                public StoreBean createFromParcel(Parcel source) {
                    return new StoreBean(source);
                }

                @Override
                public StoreBean[] newArray(int size) {
                    return new StoreBean[size];
                }
            };
        }

        public static class UserBean implements Parcelable {
            /**
             * userId : U201706091555347931953
             * kind : f1
             * loginName : 13765051712
             * nickname : 47931953
             * mobile : 13765051712
             * identityFlag : 0
             */

            private String userId;
            private String kind;
            private String loginName;
            private String nickname;
            private String mobile;
            private String identityFlag;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getKind() {
                return kind;
            }

            public void setKind(String kind) {
                this.kind = kind;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIdentityFlag() {
                return identityFlag;
            }

            public void setIdentityFlag(String identityFlag) {
                this.identityFlag = identityFlag;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.userId);
                dest.writeString(this.kind);
                dest.writeString(this.loginName);
                dest.writeString(this.nickname);
                dest.writeString(this.mobile);
                dest.writeString(this.identityFlag);
            }

            public UserBean() {
            }

            protected UserBean(Parcel in) {
                this.userId = in.readString();
                this.kind = in.readString();
                this.loginName = in.readString();
                this.nickname = in.readString();
                this.mobile = in.readString();
                this.identityFlag = in.readString();
            }

            public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
                @Override
                public UserBean createFromParcel(Parcel source) {
                    return new UserBean(source);
                }

                @Override
                public UserBean[] newArray(int size) {
                    return new UserBean[size];
                }
            };
        }

        public static class StoreUserBean implements Parcelable {
            /**
             * userId : U201706121911038741558
             * kind : f2
             * loginName : 18611112222
             * nickname : 38741558
             * mobile : 18611112222
             * identityFlag : 0
             * userReferee : U201706121841587774403
             * divRate : 0
             */

            private String userId;
            private String kind;
            private String loginName;
            private String nickname;
            private String mobile;
            private String identityFlag;
            private String userReferee;
            private int divRate;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getKind() {
                return kind;
            }

            public void setKind(String kind) {
                this.kind = kind;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIdentityFlag() {
                return identityFlag;
            }

            public void setIdentityFlag(String identityFlag) {
                this.identityFlag = identityFlag;
            }

            public String getUserReferee() {
                return userReferee;
            }

            public void setUserReferee(String userReferee) {
                this.userReferee = userReferee;
            }

            public int getDivRate() {
                return divRate;
            }

            public void setDivRate(int divRate) {
                this.divRate = divRate;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.userId);
                dest.writeString(this.kind);
                dest.writeString(this.loginName);
                dest.writeString(this.nickname);
                dest.writeString(this.mobile);
                dest.writeString(this.identityFlag);
                dest.writeString(this.userReferee);
                dest.writeInt(this.divRate);
            }

            public StoreUserBean() {
            }

            protected StoreUserBean(Parcel in) {
                this.userId = in.readString();
                this.kind = in.readString();
                this.loginName = in.readString();
                this.nickname = in.readString();
                this.mobile = in.readString();
                this.identityFlag = in.readString();
                this.userReferee = in.readString();
                this.divRate = in.readInt();
            }

            public static final Creator<StoreUserBean> CREATOR = new Creator<StoreUserBean>() {
                @Override
                public StoreUserBean createFromParcel(Parcel source) {
                    return new StoreUserBean(source);
                }

                @Override
                public StoreUserBean[] newArray(int size) {
                    return new StoreUserBean[size];
                }
            };
        }

        public ListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.userId);
            dest.writeString(this.storeCode);
            dest.writeSerializable(this.price);
            dest.writeInt(this.backAmount);
            dest.writeString(this.createDatetime);
            dest.writeString(this.status);
            dest.writeString(this.payType);
            dest.writeInt(this.payAmount1);
            dest.writeInt(this.payAmount3);
            dest.writeString(this.payDatetime);
            dest.writeString(this.remark);
            dest.writeString(this.companyCode);
            dest.writeString(this.systemCode);
            dest.writeParcelable(this.store, flags);
            dest.writeParcelable(this.user, flags);
            dest.writeParcelable(this.storeUser, flags);
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.userId = in.readString();
            this.storeCode = in.readString();
            this.price = (BigDecimal) in.readSerializable();
            this.backAmount = in.readInt();
            this.createDatetime = in.readString();
            this.status = in.readString();
            this.payType = in.readString();
            this.payAmount1 = in.readInt();
            this.payAmount3 = in.readInt();
            this.payDatetime = in.readString();
            this.remark = in.readString();
            this.companyCode = in.readString();
            this.systemCode = in.readString();
            this.store = in.readParcelable(StoreBean.class.getClassLoader());
            this.user = in.readParcelable(UserBean.class.getClassLoader());
            this.storeUser = in.readParcelable(StoreUserBean.class.getClassLoader());
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

    public OrderRecordModel() {
    }

    protected OrderRecordModel(Parcel in) {
        this.pageNO = in.readInt();
        this.start = in.readInt();
        this.pageSize = in.readInt();
        this.totalCount = in.readInt();
        this.totalPage = in.readInt();
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Parcelable.Creator<OrderRecordModel> CREATOR = new Parcelable.Creator<OrderRecordModel>() {
        @Override
        public OrderRecordModel createFromParcel(Parcel source) {
            return new OrderRecordModel(source);
        }

        @Override
        public OrderRecordModel[] newArray(int size) {
            return new OrderRecordModel[size];
        }
    };
}
