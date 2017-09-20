package com.chengdai.ehealthproject.model.healthmanager.model;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/28.
 */

public class JfGuideListModel {

    /**
     * pageNO : 1
     * start : 0
     * pageSize : 50
     * totalCount : 2
     * totalPage : 1
     * list : [{"id":34,"type":"1","ckey":"recRegAddJf","cvalue":"20","note":"邀请好友","updater":"admin","updateDatetime":"Jun 27, 2017 3:28:57 PM","remark":"推荐好友注册送积分","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"},{"id":33,"type":"1","ckey":"regAddJf","cvalue":"20","note":"新用户注册","updater":"admin","updateDatetime":"Jun 27, 2017 3:28:57 PM","remark":"新会员注册送积分","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}]
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 34
         * type : 1
         * ckey : recRegAddJf
         * cvalue : 20
         * note : 邀请好友
         * updater : admin
         * updateDatetime : Jun 27, 2017 3:28:57 PM
         * remark : 推荐好友注册送积分
         * companyCode : CD-JKEG000011
         * systemCode : CD-JKEG000011
         */

        private int id;
        private String type;
        private String ckey;
        private String cvalue;
        private String note;
        private String updater;
        private String updateDatetime;
        private String remark;
        private String companyCode;
        private String systemCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }
}
