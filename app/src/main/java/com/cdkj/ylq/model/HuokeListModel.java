package com.cdkj.ylq.model;

import java.util.List;

/**
 * Created by 李先俊 on 2017/8/18.
 */

public class HuokeListModel {

    /**
     * list : [{"address":"梦想小镇天使村","appOpenId":"544345353","area":"余杭区","birthday":"1989-08-02","city":"杭州市","companyCode":"CD-CZH000001","createDatetime":"Aug 3, 2017 5:33:54 PM","diploma":"0","divRate":"0.1","email":"leo.zheng@hichengdai.com","gender":"1","h5OpenId":"544345353","idKind":"1","idNo":"330281198908023312","introduce":"自我介绍","kind":"C","latitude":"120","level":"1","loginName":"15268501481","loginPwdStrength":"1","longitude":"120","mobile":"15268501481","nickname":"41317064","occupation":"1","pdf":"324.pdf","photo":"photo.jpg","province":"浙江省","realName":"真实姓名","remark":"新注册用户","roleCode":"SR3422","status":"0","systemCode":"CD-CZH000001","tradePwdStrength":"1","unionId":"544345353","updateDatetime":"Aug 3, 2017 5:33:54 PM","updater":"admin","userId":"U201708031733541317064","userReferee":"U2017080317335413170","workTime":"3"}]
     * pageNO : 1
     * pageSize : 10
     * start : 0
     * totalCount : 2
     * totalPage : 1
     */

    private int pageNO;
    private List<ListBean> list;

    public int getPageNO() {
        return pageNO;
    }

    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * address : 梦想小镇天使村
         * appOpenId : 544345353
         * area : 余杭区
         * birthday : 1989-08-02
         * city : 杭州市
         * companyCode : CD-CZH000001
         * createDatetime : Aug 3, 2017 5:33:54 PM
         * diploma : 0
         * divRate : 0.1
         * email : leo.zheng@hichengdai.com
         * gender : 1
         * h5OpenId : 544345353
         * idKind : 1
         * idNo : 330281198908023312
         * introduce : 自我介绍
         * kind : C
         * latitude : 120
         * level : 1
         * loginName : 15268501481
         * loginPwdStrength : 1
         * longitude : 120
         * mobile : 15268501481
         * nickname : 41317064
         * occupation : 1
         * pdf : 324.pdf
         * photo : photo.jpg
         * province : 浙江省
         * realName : 真实姓名
         * remark : 新注册用户
         * roleCode : SR3422
         * status : 0
         * systemCode : CD-CZH000001
         * tradePwdStrength : 1
         * unionId : 544345353
         * updateDatetime : Aug 3, 2017 5:33:54 PM
         * updater : admin
         * userId : U201708031733541317064
         * userReferee : U2017080317335413170
         * workTime : 3
         */

        private String address;
        private String appOpenId;
        private String area;
        private String birthday;
        private String city;
        private String companyCode;
        private String createDatetime;
        private String diploma;
        private String divRate;
        private String email;
        private String gender;
        private String h5OpenId;
        private String idKind;
        private String idNo;
        private String introduce;
        private String kind;
        private String latitude;
        private String level;
        private String loginName;
        private String loginPwdStrength;
        private String longitude;
        private String mobile;
        private String nickname;
        private String occupation;
        private String pdf;
        private String photo;
        private String province;
        private String realName;
        private String remark;
        private String roleCode;
        private String status;
        private String systemCode;
        private String tradePwdStrength;
        private String unionId;
        private String updateDatetime;
        private String updater;
        private String userId;
        private String userReferee;
        private String workTime;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAppOpenId() {
            return appOpenId;
        }

        public void setAppOpenId(String appOpenId) {
            this.appOpenId = appOpenId;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
        }

        public String getDiploma() {
            return diploma;
        }

        public void setDiploma(String diploma) {
            this.diploma = diploma;
        }

        public String getDivRate() {
            return divRate;
        }

        public void setDivRate(String divRate) {
            this.divRate = divRate;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getH5OpenId() {
            return h5OpenId;
        }

        public void setH5OpenId(String h5OpenId) {
            this.h5OpenId = h5OpenId;
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

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getLoginPwdStrength() {
            return loginPwdStrength;
        }

        public void setLoginPwdStrength(String loginPwdStrength) {
            this.loginPwdStrength = loginPwdStrength;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
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

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public String getTradePwdStrength() {
            return tradePwdStrength;
        }

        public void setTradePwdStrength(String tradePwdStrength) {
            this.tradePwdStrength = tradePwdStrength;
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }

        public String getUpdateDatetime() {
            return updateDatetime;
        }

        public void setUpdateDatetime(String updateDatetime) {
            this.updateDatetime = updateDatetime;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserReferee() {
            return userReferee;
        }

        public void setUserReferee(String userReferee) {
            this.userReferee = userReferee;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }
    }
}
