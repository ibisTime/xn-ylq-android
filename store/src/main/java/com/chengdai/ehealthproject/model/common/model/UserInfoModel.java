package com.chengdai.ehealthproject.model.common.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李先俊 on 2017/6/16.
 */

public class UserInfoModel implements Parcelable {


    /**
     * userId : U201707061150126616311
     * loginName : 13626595294
     * nickname : 26616311
     * loginPwdStrength : 2
     * kind : f1
     * level : 0
     * userReferee : U201707052151229473790
     * userRefereeName : 15268501481
     * mobile : 13626595294
     * status : 0
     * createDatetime : Jul 6, 2017 11:50:12 AM
     * updater : U201707061150126616311
     * updateDatetime : Jul 6, 2017 11:50:12 AM
     * companyCode : CD-JKEG000011
     * userExt : {"userId":"U201707061150126616311","province":"浙江省","city":"杭州市","area":"余杭区","systemCode":"CD-JKEG000011","loginName":"13626595294","mobile":"13626595294","userReferee":"U201707052151229473790"}
     * identityFlag : 0
     * tradepwdFlag : 0
     * totalFollowNum : 0
     * totalFansNum : 0
     * referrer : {"userId":"U201707052151229473790","loginName":"15268501481","nickname":"小海哥","loginPwdStrength":"2","kind":"f1","level":"0","userReferee":"","mobile":"15268501481","status":"0","createDatetime":"Jul 5, 2017 9:51:22 PM","updater":"U201707052151229473790","updateDatetime":"Jul 5, 2017 9:51:22 PM","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","userExt":{"userId":"U201707052151229473790","photo":"ANDROID_1499262785560_195_260.jpg","province":"浙江省","city":"杭州市","area":"余杭区","systemCode":"CD-JKEG000011","loginName":"15268501481","mobile":"15268501481","userReferee":""},"totalFollowNum":0,"totalFansNum":0}
     */

    private String userId;
    private String loginName;
    private String nickname;
    private String loginPwdStrength;
    private String kind;
    private String level;
    private String userReferee;
    private String userRefereeName;
    private String mobile;
    private String status;
    private String createDatetime;
    private String updater;
    private String updateDatetime;
    private String companyCode;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getReferrerNum() {
        return referrerNum;
    }

    public void setReferrerNum(int referrerNum) {
        this.referrerNum = referrerNum;
    }

    private UserExtBean userExt;
    private String identityFlag;
    private String tradepwdFlag;
    private String totalFollowNum;
    private String totalFansNum;
    private String inviteCode;
    private int referrerNum=0;
    private ReferrerBean referrer;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getLoginPwdStrength() {
        return loginPwdStrength;
    }

    public void setLoginPwdStrength(String loginPwdStrength) {
        this.loginPwdStrength = loginPwdStrength;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUserReferee() {
        return userReferee;
    }

    public void setUserReferee(String userReferee) {
        this.userReferee = userReferee;
    }

    public String getUserRefereeName() {
        return userRefereeName;
    }

    public void setUserRefereeName(String userRefereeName) {
        this.userRefereeName = userRefereeName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
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

    public UserExtBean getUserExt() {
        return userExt;
    }

    public void setUserExt(UserExtBean userExt) {
        this.userExt = userExt;
    }

    public String getIdentityFlag() {
        return identityFlag;
    }

    public void setIdentityFlag(String identityFlag) {
        this.identityFlag = identityFlag;
    }

    public String getTradepwdFlag() {
        return tradepwdFlag;
    }

    public void setTradepwdFlag(String tradepwdFlag) {
        this.tradepwdFlag = tradepwdFlag;
    }

    public String getTotalFollowNum() {
        return totalFollowNum;
    }

    public void setTotalFollowNum(String totalFollowNum) {
        this.totalFollowNum = totalFollowNum;
    }

    public String getTotalFansNum() {
        return totalFansNum;
    }

    public void setTotalFansNum(String totalFansNum) {
        this.totalFansNum = totalFansNum;
    }

    public ReferrerBean getReferrer() {
        return referrer;
    }

    public void setReferrer(ReferrerBean referrer) {
        this.referrer = referrer;
    }

    public static class UserExtBean implements Parcelable {
        /**
         * userId : U201707061150126616311
         * province : 浙江省
         * city : 杭州市
         * area : 余杭区
         * systemCode : CD-JKEG000011
         * loginName : 13626595294
         * mobile : 13626595294
         * userReferee : U201707052151229473790
         */

        private String userId;
        private String province;
        private String city;
        private String area;
        private String systemCode;
        private String loginName;
        private String mobile;
        private String userReferee;

        private String birthday;
        private String gender;
        private String email;
        private String introduce;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        private String photo;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getUserId() {

            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserReferee() {
            return userReferee;
        }

        public void setUserReferee(String userReferee) {
            this.userReferee = userReferee;
        }

        public UserExtBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userId);
            dest.writeString(this.province);
            dest.writeString(this.city);
            dest.writeString(this.area);
            dest.writeString(this.systemCode);
            dest.writeString(this.loginName);
            dest.writeString(this.mobile);
            dest.writeString(this.userReferee);
            dest.writeString(this.birthday);
            dest.writeString(this.gender);
            dest.writeString(this.email);
            dest.writeString(this.introduce);
            dest.writeString(this.photo);
        }

        protected UserExtBean(Parcel in) {
            this.userId = in.readString();
            this.province = in.readString();
            this.city = in.readString();
            this.area = in.readString();
            this.systemCode = in.readString();
            this.loginName = in.readString();
            this.mobile = in.readString();
            this.userReferee = in.readString();
            this.birthday = in.readString();
            this.gender = in.readString();
            this.email = in.readString();
            this.introduce = in.readString();
            this.photo = in.readString();
        }

        public static final Creator<UserExtBean> CREATOR = new Creator<UserExtBean>() {
            @Override
            public UserExtBean createFromParcel(Parcel source) {
                return new UserExtBean(source);
            }

            @Override
            public UserExtBean[] newArray(int size) {
                return new UserExtBean[size];
            }
        };
    }

    public static class ReferrerBean implements Parcelable {
        /**
         * userId : U201707052151229473790
         * loginName : 15268501481
         * nickname : 小海哥
         * loginPwdStrength : 2
         * kind : f1
         * level : 0
         * userReferee :
         * mobile : 15268501481
         * status : 0
         * createDatetime : Jul 5, 2017 9:51:22 PM
         * updater : U201707052151229473790
         * updateDatetime : Jul 5, 2017 9:51:22 PM
         * companyCode : CD-JKEG000011
         * systemCode : CD-JKEG000011
         * userExt : {"userId":"U201707052151229473790","photo":"ANDROID_1499262785560_195_260.jpg","province":"浙江省","city":"杭州市","area":"余杭区","systemCode":"CD-JKEG000011","loginName":"15268501481","mobile":"15268501481","userReferee":""}
         * totalFollowNum : 0
         * totalFansNum : 0
         */

        private String userId;
        private String loginName;
        private String nickname;
        private String loginPwdStrength;
        private String kind;
        private String level;
        private String userReferee;
        private String mobile;
        private String status;
        private String createDatetime;
        private String updater;
        private String updateDatetime;
        private String companyCode;
        private String systemCode;
        private UserExtBeanX userExt;
        private int totalFollowNum;
        private int totalFansNum;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getLoginPwdStrength() {
            return loginPwdStrength;
        }

        public void setLoginPwdStrength(String loginPwdStrength) {
            this.loginPwdStrength = loginPwdStrength;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getUserReferee() {
            return userReferee;
        }

        public void setUserReferee(String userReferee) {
            this.userReferee = userReferee;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
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

        public UserExtBeanX getUserExt() {
            return userExt;
        }

        public void setUserExt(UserExtBeanX userExt) {
            this.userExt = userExt;
        }

        public int getTotalFollowNum() {
            return totalFollowNum;
        }

        public void setTotalFollowNum(int totalFollowNum) {
            this.totalFollowNum = totalFollowNum;
        }

        public int getTotalFansNum() {
            return totalFansNum;
        }

        public void setTotalFansNum(int totalFansNum) {
            this.totalFansNum = totalFansNum;
        }

        public static class UserExtBeanX implements Parcelable {
            /**
             * userId : U201707052151229473790
             * photo : ANDROID_1499262785560_195_260.jpg
             * province : 浙江省
             * city : 杭州市
             * area : 余杭区
             * systemCode : CD-JKEG000011
             * loginName : 15268501481
             * mobile : 15268501481
             * userReferee :
             */

            private String userId;
            private String photo;
            private String province;
            private String city;
            private String area;
            private String systemCode;
            private String loginName;
            private String mobile;
            private String userReferee;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
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

            public String getSystemCode() {
                return systemCode;
            }

            public void setSystemCode(String systemCode) {
                this.systemCode = systemCode;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getUserReferee() {
                return userReferee;
            }

            public void setUserReferee(String userReferee) {
                this.userReferee = userReferee;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.userId);
                dest.writeString(this.photo);
                dest.writeString(this.province);
                dest.writeString(this.city);
                dest.writeString(this.area);
                dest.writeString(this.systemCode);
                dest.writeString(this.loginName);
                dest.writeString(this.mobile);
                dest.writeString(this.userReferee);
            }

            public UserExtBeanX() {
            }

            protected UserExtBeanX(Parcel in) {
                this.userId = in.readString();
                this.photo = in.readString();
                this.province = in.readString();
                this.city = in.readString();
                this.area = in.readString();
                this.systemCode = in.readString();
                this.loginName = in.readString();
                this.mobile = in.readString();
                this.userReferee = in.readString();
            }

            public static final Creator<UserExtBeanX> CREATOR = new Creator<UserExtBeanX>() {
                @Override
                public UserExtBeanX createFromParcel(Parcel source) {
                    return new UserExtBeanX(source);
                }

                @Override
                public UserExtBeanX[] newArray(int size) {
                    return new UserExtBeanX[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userId);
            dest.writeString(this.loginName);
            dest.writeString(this.nickname);
            dest.writeString(this.loginPwdStrength);
            dest.writeString(this.kind);
            dest.writeString(this.level);
            dest.writeString(this.userReferee);
            dest.writeString(this.mobile);
            dest.writeString(this.status);
            dest.writeString(this.createDatetime);
            dest.writeString(this.updater);
            dest.writeString(this.updateDatetime);
            dest.writeString(this.companyCode);
            dest.writeString(this.systemCode);
            dest.writeParcelable(this.userExt, flags);
            dest.writeInt(this.totalFollowNum);
            dest.writeInt(this.totalFansNum);
        }

        public ReferrerBean() {
        }

        protected ReferrerBean(Parcel in) {
            this.userId = in.readString();
            this.loginName = in.readString();
            this.nickname = in.readString();
            this.loginPwdStrength = in.readString();
            this.kind = in.readString();
            this.level = in.readString();
            this.userReferee = in.readString();
            this.mobile = in.readString();
            this.status = in.readString();
            this.createDatetime = in.readString();
            this.updater = in.readString();
            this.updateDatetime = in.readString();
            this.companyCode = in.readString();
            this.systemCode = in.readString();
            this.userExt = in.readParcelable(UserExtBeanX.class.getClassLoader());
            this.totalFollowNum = in.readInt();
            this.totalFansNum = in.readInt();
        }

        public static final Creator<ReferrerBean> CREATOR = new Creator<ReferrerBean>() {
            @Override
            public ReferrerBean createFromParcel(Parcel source) {
                return new ReferrerBean(source);
            }

            @Override
            public ReferrerBean[] newArray(int size) {
                return new ReferrerBean[size];
            }
        };
    }

    public UserInfoModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.loginName);
        dest.writeString(this.nickname);
        dest.writeString(this.loginPwdStrength);
        dest.writeString(this.kind);
        dest.writeString(this.level);
        dest.writeString(this.userReferee);
        dest.writeString(this.userRefereeName);
        dest.writeString(this.mobile);
        dest.writeString(this.status);
        dest.writeString(this.createDatetime);
        dest.writeString(this.updater);
        dest.writeString(this.updateDatetime);
        dest.writeString(this.companyCode);
        dest.writeParcelable(this.userExt, flags);
        dest.writeString(this.identityFlag);
        dest.writeString(this.tradepwdFlag);
        dest.writeString(this.totalFollowNum);
        dest.writeString(this.totalFansNum);
        dest.writeString(this.inviteCode);
        dest.writeInt(this.referrerNum);
        dest.writeParcelable(this.referrer, flags);
    }

    protected UserInfoModel(Parcel in) {
        this.userId = in.readString();
        this.loginName = in.readString();
        this.nickname = in.readString();
        this.loginPwdStrength = in.readString();
        this.kind = in.readString();
        this.level = in.readString();
        this.userReferee = in.readString();
        this.userRefereeName = in.readString();
        this.mobile = in.readString();
        this.status = in.readString();
        this.createDatetime = in.readString();
        this.updater = in.readString();
        this.updateDatetime = in.readString();
        this.companyCode = in.readString();
        this.userExt = in.readParcelable(UserExtBean.class.getClassLoader());
        this.identityFlag = in.readString();
        this.tradepwdFlag = in.readString();
        this.totalFollowNum = in.readString();
        this.totalFansNum = in.readString();
        this.inviteCode = in.readString();
        this.referrerNum = in.readInt();
        this.referrer = in.readParcelable(ReferrerBean.class.getClassLoader());
    }

    public static final Creator<UserInfoModel> CREATOR = new Creator<UserInfoModel>() {
        @Override
        public UserInfoModel createFromParcel(Parcel source) {
            return new UserInfoModel(source);
        }

        @Override
        public UserInfoModel[] newArray(int size) {
            return new UserInfoModel[size];
        }
    };
}
