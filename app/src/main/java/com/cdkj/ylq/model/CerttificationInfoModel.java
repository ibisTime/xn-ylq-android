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
        private String qq;
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

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
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
            dest.writeString(this.qq);
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
            this.qq = in.readString();
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
        }

        public InfoContactBean() {
        }

        protected InfoContactBean(Parcel in) {
            this.familyRelation = in.readString();
            this.familyMobile = in.readString();
            this.societyRelation = in.readString();
            this.societyMobile = in.readString();
        }

        public static final Parcelable.Creator<InfoContactBean> CREATOR = new Parcelable.Creator<InfoContactBean>() {
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

    public static class InfoIdentifyPicBean implements Parcelable {
        /**
         * identifyPic : ANDROID_1502618832189_470_470.jpg
         */

        private String identifyPic;

        public String getIdentifyPic() {
            return identifyPic;
        }

        public void setIdentifyPic(String identifyPic) {
            this.identifyPic = identifyPic;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.identifyPic);
        }

        public InfoIdentifyPicBean() {
        }

        protected InfoIdentifyPicBean(Parcel in) {
            this.identifyPic = in.readString();
        }

        public static final Parcelable.Creator<InfoIdentifyPicBean> CREATOR = new Parcelable.Creator<InfoIdentifyPicBean>() {
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
