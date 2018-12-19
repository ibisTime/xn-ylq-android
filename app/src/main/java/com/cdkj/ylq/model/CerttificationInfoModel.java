package com.cdkj.ylq.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李先俊 on 2017/8/12.
 */

public class CerttificationInfoModel  {

    /**
     * infoBasicFlag : 1
     * infoBasic : {"education":"1","marriage":"0","childrenNum":0,"provinceCity":"甘肃省白银市","address":"浙江省杭州市余杭区梦想小镇天使村","liveTime":"4","qq":"4143543654","email":"leo.zheng@hichengdai.com"}
     * infoOccupationFlag : 1
     * infoOccupation : {"occupation":"1","income":4,"company":"232323","provinceCity":"浙江-杭州","address":"梦想小镇天使村","phone":"0571-87686578"}
     * infoContactFlag : 1
     * infoContact : {"familyRelation":"1","familyMobile":"1537886898722","societyRelation":"2","societyMobile":"15378868987.."}
     * infoBankcardFlag : 1
     * infoBankcard : {"bank":"ABC","privinceCity":"浙江-杭州","mobile":"152637687656","cardNo":"6223 6575 3757 5888"}
     * infoAntifraudFlag : 0
     * infoZMCreditFlag : 0
     * infoIdentifyFlag : 0
     * infoIdentifyPicFlag : 1
     * infoIdentifyPic : {"identifyPic":"ANDROID_1502618832189_470_470.jpg"}
     * infoCarrierFlag : 1
     */

    private String infoBasicFlag;

    public String getInfoAddressBookFlag() {
        return infoAddressBookFlag;
    }

    public void setInfoAddressBookFlag(String infoAddressBookFlag) {
        this.infoAddressBookFlag = infoAddressBookFlag;
    }

    private String infoAddressBookFlag;
    private InfoBasicBean infoBasic;
    private String infoOccupationFlag;
    private InfoOccupationBean infoOccupation;
    private String infoContactFlag;
    private InfoContactBean infoContact;
    private String infoBankcardFlag;
    private InfoBankcardBean infoBankcard;
    private String infoAntifraudFlag;
    private String infoZMCreditFlag;
    private String infoIdentifyFlag;
    private String infoIdentifyPicFlag;
    private String infoIdentifyFaceFlag;
    private InfoIdentifyPicBean infoIdentifyPic;
    private String infoCarrierFlag;
    private InfoIdentifyBean infoIdentify;
    private InfoIdentifyBean infoIdentifyFace;
    private String infoZfbFlag;
    private String infoZqznFlag;
    private String infoPersonalFlag;
    private String locationFlag;
    private InfoZqznBean infoZqzn;

    public String getLocationFlag() {
        return locationFlag;
    }

    public void setLocationFlag(String locationFlag) {
        this.locationFlag = locationFlag;
    }

    public InfoZqznBean getInfoZqzn() {
        return infoZqzn;
    }

    public void setInfoZqzn(InfoZqznBean infoZqzn) {
        this.infoZqzn = infoZqzn;
    }

    public String getInfoPersonalFlag() {
        return infoPersonalFlag;
    }

    public void setInfoPersonalFlag(String infoPersonalFlag) {
        this.infoPersonalFlag = infoPersonalFlag;
    }

    public String getInfoZqznFlag() {
        return infoZqznFlag;
    }

    public void setInfoZqznFlag(String infoZqznFlag) {
        this.infoZqznFlag = infoZqznFlag;
    }

    public String getInfoZfbFlag() {
        return infoZfbFlag;
    }

    public void setInfoZfbFlag(String infoZfbFlag) {
        this.infoZfbFlag = infoZfbFlag;
    }

    public InfoIdentifyBean getInfoIdentifyFace() {
        return infoIdentifyFace;
    }

    public void setInfoIdentifyFace(InfoIdentifyBean infoIdentifyFace) {
        this.infoIdentifyFace = infoIdentifyFace;
    }

    public InfoIdentifyBean getInfoIdentify() {
        return infoIdentify;
    }

    public void setInfoIdentify(InfoIdentifyBean infoIdentify) {
        this.infoIdentify = infoIdentify;
    }

    /*"infoIdentify": {

            "realName": "李先俊",
            "idNo": "522321199509111655"
        }*/

    public static class InfoIdentifyBean implements Parcelable {
        private String realName;
        private String idNo;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.realName);
            dest.writeString(this.idNo);
        }

        public InfoIdentifyBean() {
        }

        protected InfoIdentifyBean(Parcel in) {
            this.realName = in.readString();
            this.idNo = in.readString();
        }

        public static final Parcelable.Creator<InfoIdentifyBean> CREATOR = new Parcelable.Creator<InfoIdentifyBean>() {
            @Override
            public InfoIdentifyBean createFromParcel(Parcel source) {
                return new InfoIdentifyBean(source);
            }

            @Override
            public InfoIdentifyBean[] newArray(int size) {
                return new InfoIdentifyBean[size];
            }
        };
    }

    public String getInfoIdentifyFaceFlag() {
        return infoIdentifyFaceFlag;
    }

    public void setInfoIdentifyFaceFlag(String infoIdentifyFaceFlag) {
        this.infoIdentifyFaceFlag = infoIdentifyFaceFlag;
    }

    public String getInfoBasicFlag() {
        return infoBasicFlag;
    }

    public void setInfoBasicFlag(String infoBasicFlag) {
        this.infoBasicFlag = infoBasicFlag;
    }

    public InfoBasicBean getInfoBasic() {
        return infoBasic;
    }

    public void setInfoBasic(InfoBasicBean infoBasic) {
        this.infoBasic = infoBasic;
    }

    public String getInfoOccupationFlag() {
        return infoOccupationFlag;
    }

    public void setInfoOccupationFlag(String infoOccupationFlag) {
        this.infoOccupationFlag = infoOccupationFlag;
    }

    public InfoOccupationBean getInfoOccupation() {
        return infoOccupation;
    }

    public void setInfoOccupation(InfoOccupationBean infoOccupation) {
        this.infoOccupation = infoOccupation;
    }

    public String getInfoContactFlag() {
        return infoContactFlag;
    }

    public void setInfoContactFlag(String infoContactFlag) {
        this.infoContactFlag = infoContactFlag;
    }

    public InfoContactBean getInfoContact() {
        return infoContact;
    }

    public void setInfoContact(InfoContactBean infoContact) {
        this.infoContact = infoContact;
    }

    public String getInfoBankcardFlag() {
        return infoBankcardFlag;
    }

    public void setInfoBankcardFlag(String infoBankcardFlag) {
        this.infoBankcardFlag = infoBankcardFlag;
    }

    public InfoBankcardBean getInfoBankcard() {
        return infoBankcard;
    }

    public void setInfoBankcard(InfoBankcardBean infoBankcard) {
        this.infoBankcard = infoBankcard;
    }

    public String getInfoAntifraudFlag() {
        return infoAntifraudFlag;
    }

    public void setInfoAntifraudFlag(String infoAntifraudFlag) {
        this.infoAntifraudFlag = infoAntifraudFlag;
    }

    public String getInfoZMCreditFlag() {
        return infoZMCreditFlag;
    }

    public void setInfoZMCreditFlag(String infoZMCreditFlag) {
        this.infoZMCreditFlag = infoZMCreditFlag;
    }

    public String getInfoIdentifyFlag() {
        return infoIdentifyFlag;
    }

    public void setInfoIdentifyFlag(String infoIdentifyFlag) {
        this.infoIdentifyFlag = infoIdentifyFlag;
    }

    public String getInfoIdentifyPicFlag() {
        return infoIdentifyPicFlag;
    }

    public void setInfoIdentifyPicFlag(String infoIdentifyPicFlag) {
        this.infoIdentifyPicFlag = infoIdentifyPicFlag;
    }

    public InfoIdentifyPicBean getInfoIdentifyPic() {
        return infoIdentifyPic;
    }

    public void setInfoIdentifyPic(InfoIdentifyPicBean infoIdentifyPic) {
        this.infoIdentifyPic = infoIdentifyPic;
    }

    public String getInfoCarrierFlag() {
        return infoCarrierFlag;
    }

    public void setInfoCarrierFlag(String infoCarrierFlag) {
        this.infoCarrierFlag = infoCarrierFlag;
    }

    public static class InfoBasicBean implements Parcelable {
        /**
         * education : 1
         * marriage : 0
         * childrenNum : 0
         * provinceCity : 甘肃省白银市
         * address : 浙江省杭州市余杭区梦想小镇天使村
         * liveTime : 4
         * qq : 4143543654
         * email : leo.zheng@hichengdai.com
         */

        private String education;
        private String marriage;
        private int childrenNum;
        private String provinceCity;
        private String address;
        private String liveTime;
        private String wechat;
        private String email;

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getMarriage() {
            return marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage;
        }

        public int getChildrenNum() {
            return childrenNum;
        }

        public void setChildrenNum(int childrenNum) {
            this.childrenNum = childrenNum;
        }

        public String getProvinceCity() {
            return provinceCity;
        }

        public void setProvinceCity(String provinceCity) {
            this.provinceCity = provinceCity;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLiveTime() {
            return liveTime;
        }

        public void setLiveTime(String liveTime) {
            this.liveTime = liveTime;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.education);
            dest.writeString(this.marriage);
            dest.writeInt(this.childrenNum);
            dest.writeString(this.provinceCity);
            dest.writeString(this.address);
            dest.writeString(this.liveTime);
            dest.writeString(this.wechat);
            dest.writeString(this.email);
        }

        public InfoBasicBean() {
        }

        protected InfoBasicBean(Parcel in) {
            this.education = in.readString();
            this.marriage = in.readString();
            this.childrenNum = in.readInt();
            this.provinceCity = in.readString();
            this.address = in.readString();
            this.liveTime = in.readString();
            this.wechat = in.readString();
            this.email = in.readString();
        }

        public static final Parcelable.Creator<InfoBasicBean> CREATOR = new Parcelable.Creator<InfoBasicBean>() {
            @Override
            public InfoBasicBean createFromParcel(Parcel source) {
                return new InfoBasicBean(source);
            }

            @Override
            public InfoBasicBean[] newArray(int size) {
                return new InfoBasicBean[size];
            }
        };
    }

    public static class InfoOccupationBean implements Parcelable {
        /**
         * occupation : 1
         * income : 4
         * company : 232323
         * provinceCity : 浙江-杭州
         * address : 梦想小镇天使村
         * phone : 0571-87686578
         */

        private String occupation;
        private int income;
        private String company;
        private String provinceCity;
        private String address;
        private String phone;

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getProvinceCity() {
            return provinceCity;
        }

        public void setProvinceCity(String provinceCity) {
            this.provinceCity = provinceCity;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.occupation);
            dest.writeInt(this.income);
            dest.writeString(this.company);
            dest.writeString(this.provinceCity);
            dest.writeString(this.address);
            dest.writeString(this.phone);
        }

        public InfoOccupationBean() {
        }

        protected InfoOccupationBean(Parcel in) {
            this.occupation = in.readString();
            this.income = in.readInt();
            this.company = in.readString();
            this.provinceCity = in.readString();
            this.address = in.readString();
            this.phone = in.readString();
        }

        public static final Parcelable.Creator<InfoOccupationBean> CREATOR = new Parcelable.Creator<InfoOccupationBean>() {
            @Override
            public InfoOccupationBean createFromParcel(Parcel source) {
                return new InfoOccupationBean(source);
            }

            @Override
            public InfoOccupationBean[] newArray(int size) {
                return new InfoOccupationBean[size];
            }
        };
    }

    public static class InfoContactBean implements Parcelable {
        /**
         * familyRelation : 1
         * familyMobile : 1537886898722
         * societyRelation : 2
         * societyMobile : 15378868987..
         */

        private String familyRelation;
        private String familyMobile;
        private String societyRelation;
        private String societyMobile;
        private String familyName;
        private String societyName;

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getSocietyName() {
            return societyName;
        }

        public void setSocietyName(String societyName) {
            this.societyName = societyName;
        }

        public String getFamilyRelation() {
            return familyRelation;
        }

        public void setFamilyRelation(String familyRelation) {
            this.familyRelation = familyRelation;
        }

        public String getFamilyMobile() {
            return familyMobile;
        }

        public void setFamilyMobile(String familyMobile) {
            this.familyMobile = familyMobile;
        }

        public String getSocietyRelation() {
            return societyRelation;
        }

        public void setSocietyRelation(String societyRelation) {
            this.societyRelation = societyRelation;
        }

        public String getSocietyMobile() {
            return societyMobile;
        }

        public void setSocietyMobile(String societyMobile) {
            this.societyMobile = societyMobile;
        }

        public InfoContactBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.familyRelation);
            dest.writeString(this.familyMobile);
            dest.writeString(this.societyRelation);
            dest.writeString(this.societyMobile);
            dest.writeString(this.familyName);
            dest.writeString(this.societyName);
        }

        protected InfoContactBean(Parcel in) {
            this.familyRelation = in.readString();
            this.familyMobile = in.readString();
            this.societyRelation = in.readString();
            this.societyMobile = in.readString();
            this.familyName = in.readString();
            this.societyName = in.readString();
        }

        public static final Creator<InfoContactBean> CREATOR = new Creator<InfoContactBean>() {
            @Override
            public InfoContactBean createFromParcel(Parcel source) {
                return new InfoContactBean(source);
            }

            @Override
            public InfoContactBean[] newArray(int size) {
                return new InfoContactBean[size];
            }
        };
    }

    public static class InfoBankcardBean implements Parcelable {
        /**
         * bank : ABC
         * privinceCity : 浙江-杭州
         * mobile : 152637687656
         * cardNo : 6223 6575 3757 5888
         */

        private String bank;
        private String privinceCity;
        private String mobile;
        private String cardNo;

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getPrivinceCity() {
            return privinceCity;
        }

        public void setPrivinceCity(String privinceCity) {
            this.privinceCity = privinceCity;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.bank);
            dest.writeString(this.privinceCity);
            dest.writeString(this.mobile);
            dest.writeString(this.cardNo);
        }

        public InfoBankcardBean() {
        }

        protected InfoBankcardBean(Parcel in) {
            this.bank = in.readString();
            this.privinceCity = in.readString();
            this.mobile = in.readString();
            this.cardNo = in.readString();
        }

        public static final Parcelable.Creator<InfoBankcardBean> CREATOR = new Parcelable.Creator<InfoBankcardBean>() {
            @Override
            public InfoBankcardBean createFromParcel(Parcel source) {
                return new InfoBankcardBean(source);
            }

            @Override
            public InfoBankcardBean[] newArray(int size) {
                return new InfoBankcardBean[size];
            }
        };
    }

    public static class InfoZqznBean {
        /**
         * zqznInfoFront : {"idNo":"412723199001014298","address":"河南省商水县舒庄乡汾河村七组","gender":"男","race":"汉","name":"邵建飞","birth":"1990.01.01"}
         * zqznInfoBack : {"issuedBy":"商水县公安局","validDate":"2014.08.04-2024.08.04"}
         * zqznInfoRealAuth : {"verifyStatus":1,"reason":""}
         */

        private ZqznInfoFrontBean zqznInfoFront;
        private ZqznInfoBackBean zqznInfoBack;
        private ZqznInfoRealAuthBean zqznInfoRealAuth;

        public ZqznInfoFrontBean getZqznInfoFront() {
            return zqznInfoFront;
        }

        public void setZqznInfoFront(ZqznInfoFrontBean zqznInfoFront) {
            this.zqznInfoFront = zqznInfoFront;
        }

        public ZqznInfoBackBean getZqznInfoBack() {
            return zqznInfoBack;
        }

        public void setZqznInfoBack(ZqznInfoBackBean zqznInfoBack) {
            this.zqznInfoBack = zqznInfoBack;
        }

        public ZqznInfoRealAuthBean getZqznInfoRealAuth() {
            return zqznInfoRealAuth;
        }

        public void setZqznInfoRealAuth(ZqznInfoRealAuthBean zqznInfoRealAuth) {
            this.zqznInfoRealAuth = zqznInfoRealAuth;
        }

        public static class ZqznInfoFrontBean {
            /**
             * idNo : 412723199001014298
             * address : 河南省商水县舒庄乡汾河村七组
             * gender : 男
             * race : 汉
             * name : 邵建飞
             * birth : 1990.01.01
             */

            private String idNo;
            private String address;
            private String gender;
            private String race;
            private String name;
            private String birth;

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getRace() {
                return race;
            }

            public void setRace(String race) {
                this.race = race;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBirth() {
                return birth;
            }

            public void setBirth(String birth) {
                this.birth = birth;
            }
        }

        public static class ZqznInfoBackBean {
            /**
             * issuedBy : 商水县公安局
             * validDate : 2014.08.04-2024.08.04
             */

            private String issuedBy;
            private String validDate;

            public String getIssuedBy() {
                return issuedBy;
            }

            public void setIssuedBy(String issuedBy) {
                this.issuedBy = issuedBy;
            }

            public String getValidDate() {
                return validDate;
            }

            public void setValidDate(String validDate) {
                this.validDate = validDate;
            }
        }

        public static class ZqznInfoRealAuthBean {
            /**
             * verifyStatus : 1
             * reason :
             */

            private int verifyStatus;
            private String reason;

            public int getVerifyStatus() {
                return verifyStatus;
            }

            public void setVerifyStatus(int verifyStatus) {
                this.verifyStatus = verifyStatus;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }
        }
    }

    public static class InfoIdentifyPicBean implements Parcelable {
        /**
         * identifyPic : ANDROID_1502618832189_470_470.jpg
         */

        private String identifyPic;
        private String identifyPicReverse;
        private String identifyPicHand;

        public String getIdentifyPicReverse() {
            return identifyPicReverse;
        }

        public void setIdentifyPicReverse(String identifyPicReverse) {
            this.identifyPicReverse = identifyPicReverse;
        }

        public String getIdentifyPicHand() {
            return identifyPicHand;
        }

        public void setIdentifyPicHand(String identifyPicHand) {
            this.identifyPicHand = identifyPicHand;
        }

        public String getIdentifyPic() {
            return identifyPic;
        }

        public void setIdentifyPic(String identifyPic) {
            this.identifyPic = identifyPic;
        }

        public InfoIdentifyPicBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.identifyPic);
            dest.writeString(this.identifyPicReverse);
            dest.writeString(this.identifyPicHand);
        }

        protected InfoIdentifyPicBean(Parcel in) {
            this.identifyPic = in.readString();
            this.identifyPicReverse = in.readString();
            this.identifyPicHand = in.readString();
        }



        public static final Creator<InfoIdentifyPicBean> CREATOR = new Creator<InfoIdentifyPicBean>() {
            @Override
            public InfoIdentifyPicBean createFromParcel(Parcel source) {
                return new InfoIdentifyPicBean(source);
            }

            @Override
            public InfoIdentifyPicBean[] newArray(int size) {
                return new InfoIdentifyPicBean[size];
            }
        };
    }


}
