package com.cdkj.ylq.model;

import java.util.List;

/**
 * @updateDts 2018/11/27
 */

public class SelectZxBean {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * list : [{"code":"AP201811261714171549765","applyUser":"U2018112211391436485602","applyDatetime":"Nov 26, 2018 5:14:17 PM","status":"1","updater":"U2018112211391436485602","updateDatetime":"Nov 26, 2018 5:14:17 PM","remark":"新申请","companyCode":"GS2018112119133810071833","user":{"userId":"U2018112211391436485602","loginName":"18612131702","mobile":"18612131702","nickname":"36485602","photo":"IOS_1543227688006095_1242_2208.jpg","loginPwdStrength":"1","realName":"大飞哥","status":"0","province":"浙江省","city":"杭州市","area":"余杭区","address":"文一西路1818-2号15幢3层北航VR/AR创新研究院","createDatetime":"Nov 22, 2018 11:39:14 AM","isBlackList":"1","companyCode":"GS2018112119133810071833"}}]
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

    public static class ListBean {
        /**
         * code : AP201811261714171549765
         * applyUser : U2018112211391436485602
         * applyDatetime : Nov 26, 2018 5:14:17 PM
         * status : 1
         * updater : U2018112211391436485602
         * updateDatetime : Nov 26, 2018 5:14:17 PM
         * remark : 新申请
         * companyCode : GS2018112119133810071833
         * user : {"userId":"U2018112211391436485602","loginName":"18612131702","mobile":"18612131702","nickname":"36485602","photo":"IOS_1543227688006095_1242_2208.jpg","loginPwdStrength":"1","realName":"大飞哥","status":"0","province":"浙江省","city":"杭州市","area":"余杭区","address":"文一西路1818-2号15幢3层北航VR/AR创新研究院","createDatetime":"Nov 22, 2018 11:39:14 AM","isBlackList":"1","companyCode":"GS2018112119133810071833"}
         */

        private String code;
        private String applyUser;
        private String applyDatetime;
        private String status;
        private String updater;
        private String updateDatetime;
        private String remark;
        private String companyCode;
        private UserBean user;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getApplyUser() {
            return applyUser;
        }

        public void setApplyUser(String applyUser) {
            this.applyUser = applyUser;
        }

        public String getApplyDatetime() {
            return applyDatetime;
        }

        public void setApplyDatetime(String applyDatetime) {
            this.applyDatetime = applyDatetime;
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * userId : U2018112211391436485602
             * loginName : 18612131702
             * mobile : 18612131702
             * nickname : 36485602
             * photo : IOS_1543227688006095_1242_2208.jpg
             * loginPwdStrength : 1
             * realName : 大飞哥
             * status : 0
             * province : 浙江省
             * city : 杭州市
             * area : 余杭区
             * address : 文一西路1818-2号15幢3层北航VR/AR创新研究院
             * createDatetime : Nov 22, 2018 11:39:14 AM
             * isBlackList : 1
             * companyCode : GS2018112119133810071833
             */

            private String userId;
            private String loginName;
            private String mobile;
            private String nickname;
            private String photo;
            private String loginPwdStrength;
            private String realName;
            private String status;
            private String province;
            private String city;
            private String area;
            private String address;
            private String createDatetime;
            private String isBlackList;
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

            public String getCompanyCode() {
                return companyCode;
            }

            public void setCompanyCode(String companyCode) {
                this.companyCode = companyCode;
            }
        }
    }
}
