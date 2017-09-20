package com.chengdai.ehealthproject.model.healthstore.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopEvaluateModel implements Parcelable {


    /**
     * code : HD201704051233004431
     * type : 1
     * actionUser : U2017032820273497120
     * actionDatetime : Apr 5, 2017 12:33:00 PM
     * storeCode : SJ201703311944099424
     * systemCode : CD-CCG000007
     * user : {"userId":"U2017032820273497120","loginName":"18868824532","nickname":"73497120","mobile":"18868824534","identityFlag":"0"}
     */

    private String code;
    private String type;
    private String actionUser;
    private String actionDatetime;
    private String storeCode;
    private String systemCode;
    private UserBean user;

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

    public String getActionUser() {
        return actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    public String getActionDatetime() {
        return actionDatetime;
    }

    public void setActionDatetime(String actionDatetime) {
        this.actionDatetime = actionDatetime;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Parcelable {
        private String userId;
        private String loginName;
        private String nickname;
        private String mobile;
        private String identityFlag;
        private String photo;
        private String userReferee;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getUserReferee() {
            return userReferee;
        }

        public void setUserReferee(String userReferee) {
            this.userReferee = userReferee;
        }

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

        public UserBean() {
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
            dest.writeString(this.mobile);
            dest.writeString(this.identityFlag);
            dest.writeString(this.photo);
            dest.writeString(this.userReferee);
        }

        protected UserBean(Parcel in) {
            this.userId = in.readString();
            this.loginName = in.readString();
            this.nickname = in.readString();
            this.mobile = in.readString();
            this.identityFlag = in.readString();
            this.photo = in.readString();
            this.userReferee = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.type);
        dest.writeString(this.actionUser);
        dest.writeString(this.actionDatetime);
        dest.writeString(this.storeCode);
        dest.writeString(this.systemCode);
        dest.writeParcelable(this.user, flags);
    }

    public ShopEvaluateModel() {
    }

    protected ShopEvaluateModel(Parcel in) {
        this.code = in.readString();
        this.type = in.readString();
        this.actionUser = in.readString();
        this.actionDatetime = in.readString();
        this.storeCode = in.readString();
        this.systemCode = in.readString();
        this.user = in.readParcelable(UserBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ShopEvaluateModel> CREATOR = new Parcelable.Creator<ShopEvaluateModel>() {
        @Override
        public ShopEvaluateModel createFromParcel(Parcel source) {
            return new ShopEvaluateModel(source);
        }

        @Override
        public ShopEvaluateModel[] newArray(int size) {
            return new ShopEvaluateModel[size];
        }
    };
}
