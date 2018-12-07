package com.cdkj.ylq.model;

/**
 * @updateDts 2018/12/7
 */
public class CerttificationInfoListBean {

    /**
     * userId : U201812071605344226479
     * userInfo : {"userId":"U201812071605344226479","loginName":"14444444444","mobile":"14444444444","nickname":"44226479","photo":"ANDROID_1544170715779_432_576.jpg","loginPwdStrength":"1","idKind":"1","idNo":"412723199001014298","realName":"邵建飞","status":"0","province":"浙江省","city":"杭州市","area":"余杭区","address":"中心路1818-2号","createDatetime":"Dec 7, 2018 4:05:34 PM","isBlackList":"0","isWhiteList":"0","isCoupon":"0","companyCode":"GSModelCode"}
     * infoBasicFlag : 0
     * infoOccupationFlag : 0
     * infoContactFlag : 0
     * infoCarrierFlag : 0
     * infoAddressBookFlag : 0
     * infoZfbFlag : 0
     * infoZqznFlag : 1
     * infoZqzn : {"zqznInfoFront":{"idNo":"412723199001014298","address":"河南省商水县舒庄乡汾河村七组","gender":"男","race":"汉","name":"邵建飞","birth":"1990.01.01"},"zqznInfoBack":{"issuedBy":"商水县公安局","validDate":"2014.08.04-2024.08.04"},"zqznInfoRealAuth":{"verifyStatus":1,"reason":""}}
     * infoPersonalFlag : 0
     */

    private String userId;
    private UserInfoBean userInfo;
    private String infoBasicFlag;
    private String infoOccupationFlag;
    private String infoContactFlag;
    private String infoCarrierFlag;
    private String infoAddressBookFlag;
    private String infoZfbFlag;
    private String infoZqznFlag;
    private InfoZqznBean infoZqzn;
    private String infoPersonalFlag;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getInfoBasicFlag() {
        return infoBasicFlag;
    }

    public void setInfoBasicFlag(String infoBasicFlag) {
        this.infoBasicFlag = infoBasicFlag;
    }

    public String getInfoOccupationFlag() {
        return infoOccupationFlag;
    }

    public void setInfoOccupationFlag(String infoOccupationFlag) {
        this.infoOccupationFlag = infoOccupationFlag;
    }

    public String getInfoContactFlag() {
        return infoContactFlag;
    }

    public void setInfoContactFlag(String infoContactFlag) {
        this.infoContactFlag = infoContactFlag;
    }

    public String getInfoCarrierFlag() {
        return infoCarrierFlag;
    }

    public void setInfoCarrierFlag(String infoCarrierFlag) {
        this.infoCarrierFlag = infoCarrierFlag;
    }

    public String getInfoAddressBookFlag() {
        return infoAddressBookFlag;
    }

    public void setInfoAddressBookFlag(String infoAddressBookFlag) {
        this.infoAddressBookFlag = infoAddressBookFlag;
    }

    public String getInfoZfbFlag() {
        return infoZfbFlag;
    }

    public void setInfoZfbFlag(String infoZfbFlag) {
        this.infoZfbFlag = infoZfbFlag;
    }

    public String getInfoZqznFlag() {
        return infoZqznFlag;
    }

    public void setInfoZqznFlag(String infoZqznFlag) {
        this.infoZqznFlag = infoZqznFlag;
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

    public static class UserInfoBean {
        /**
         * userId : U201812071605344226479
         * loginName : 14444444444
         * mobile : 14444444444
         * nickname : 44226479
         * photo : ANDROID_1544170715779_432_576.jpg
         * loginPwdStrength : 1
         * idKind : 1
         * idNo : 412723199001014298
         * realName : 邵建飞
         * status : 0
         * province : 浙江省
         * city : 杭州市
         * area : 余杭区
         * address : 中心路1818-2号
         * createDatetime : Dec 7, 2018 4:05:34 PM
         * isBlackList : 0
         * isWhiteList : 0
         * isCoupon : 0
         * companyCode : GSModelCode
         */

        private String userId;
        private String loginName;
        private String mobile;
        private String nickname;
        private String photo;
        private String loginPwdStrength;
        private String idKind;
        private String idNo;
        private String realName;
        private String status;
        private String province;
        private String city;
        private String area;
        private String address;
        private String createDatetime;
        private String isBlackList;
        private String isWhiteList;
        private String isCoupon;
        private String companyCode;

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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getLoginPwdStrength() {
            return loginPwdStrength;
        }

        public void setLoginPwdStrength(String loginPwdStrength) {
            this.loginPwdStrength = loginPwdStrength;
        }

        public String getIdKind() {
            return idKind;
        }

        public void setIdKind(String idKind) {
            this.idKind = idKind;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
        }

        public String getIsBlackList() {
            return isBlackList;
        }

        public void setIsBlackList(String isBlackList) {
            this.isBlackList = isBlackList;
        }

        public String getIsWhiteList() {
            return isWhiteList;
        }

        public void setIsWhiteList(String isWhiteList) {
            this.isWhiteList = isWhiteList;
        }

        public String getIsCoupon() {
            return isCoupon;
        }

        public void setIsCoupon(String isCoupon) {
            this.isCoupon = isCoupon;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }
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
}
