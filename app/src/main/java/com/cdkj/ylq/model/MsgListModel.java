package com.cdkj.ylq.model;

import java.util.List;

/**
 * Created by 李先俊 on 2017/7/20.
 */

public class MsgListModel {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 2
     * totalPage : 1
     * list : [{"id":32,"fromSystemCode":"CD-CYC000009","channelType":"4","pushType":"41","toSystemCode":"CD-CYC000009","toKind":"2","smsType":"2","smsTitle":"试吃员消息1","smsContent":"试吃员消息1","status":"1","createDatetime":"Jul 20, 2017 8:17:06 PM","topushDatetime":"Jul 20, 2017 8:17:06 PM","pushedDatetime":"Jul 20, 2017 8:17:12 PM","updater":"admin","updateDatetime":"Jul 20, 2017 8:17:12 PM","remark":""},{"id":31,"fromSystemCode":"CD-CYC000009","channelType":"4","pushType":"41","toSystemCode":"CD-CYC000009","toKind":"2","smsType":"2","smsTitle":"试吃员消息2","smsContent":"试吃员消息2","status":"1","createDatetime":"Jul 20, 2017 8:16:50 PM","topushDatetime":"Jul 20, 2017 8:16:50 PM","pushedDatetime":"Jul 20, 2017 8:16:54 PM","updater":"admin","updateDatetime":"Jul 20, 2017 8:16:54 PM","remark":""}]
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> listX) {
        this.list = listX;
    }

    public static class ListBean {
        /**
         * id : 32
         * fromSystemCode : CD-CYC000009
         * channelType : 4
         * pushType : 41
         * toSystemCode : CD-CYC000009
         * toKind : 2
         * smsType : 2
         * smsTitle : 试吃员消息1
         * smsContent : 试吃员消息1
         * status : 1
         * createDatetime : Jul 20, 2017 8:17:06 PM
         * topushDatetime : Jul 20, 2017 8:17:06 PM
         * pushedDatetime : Jul 20, 2017 8:17:12 PM
         * updater : admin
         * updateDatetime : Jul 20, 2017 8:17:12 PM
         * remark :
         */

        private int id;
        private String fromSystemCode;
        private String channelType;
        private String pushType;
        private String toSystemCode;
        private String toKind;
        private String smsType;
        private String smsTitle;
        private String smsContent;
        private String status;
        private String createDatetime;
        private String topushDatetime;
        private String pushedDatetime;
        private String updater;
        private String updateDatetime;
        private String remark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFromSystemCode() {
            return fromSystemCode;
        }

        public void setFromSystemCode(String fromSystemCode) {
            this.fromSystemCode = fromSystemCode;
        }

        public String getChannelType() {
            return channelType;
        }

        public void setChannelType(String channelType) {
            this.channelType = channelType;
        }

        public String getPushType() {
            return pushType;
        }

        public void setPushType(String pushType) {
            this.pushType = pushType;
        }

        public String getToSystemCode() {
            return toSystemCode;
        }

        public void setToSystemCode(String toSystemCode) {
            this.toSystemCode = toSystemCode;
        }

        public String getToKind() {
            return toKind;
        }

        public void setToKind(String toKind) {
            this.toKind = toKind;
        }

        public String getSmsType() {
            return smsType;
        }

        public void setSmsType(String smsType) {
            this.smsType = smsType;
        }

        public String getSmsTitle() {
            return smsTitle;
        }

        public void setSmsTitle(String smsTitle) {
            this.smsTitle = smsTitle;
        }

        public String getSmsContent() {
            return smsContent;
        }

        public void setSmsContent(String smsContent) {
            this.smsContent = smsContent;
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

        public String getTopushDatetime() {
            return topushDatetime;
        }

        public void setTopushDatetime(String topushDatetime) {
            this.topushDatetime = topushDatetime;
        }

        public String getPushedDatetime() {
            return pushedDatetime;
        }

        public void setPushedDatetime(String pushedDatetime) {
            this.pushedDatetime = pushedDatetime;
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
    }
}
