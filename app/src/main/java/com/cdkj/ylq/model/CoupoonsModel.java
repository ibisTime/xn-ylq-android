package com.cdkj.ylq.model;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.cdkj.baselibrary.utils.MoneyUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 李先俊 on 2017/8/16.
 */

public class CoupoonsModel {

    /**
     * list : [{"amount":50000,"getDatetime":"Aug 16, 2017 3:09:09 PM","id":1,"invalidDatetime":"Sep 15, 2017 12:00:00 AM","remark":"系统出错，回收优惠券","startAmount":1000000,"status":"3","type":"1 0=获客优惠券 1=借还优惠券","updateDatetime":"Aug 16, 2017 3:13:48 PM","updater":"ylq","userId":"U201708081809018411896","validDays":30}]
     * pageNO : 1
     * pageSize : 10
     * start : 0
     * totalCount : 1
     * totalPage : 1
     */

    private List<ListBean> list;

    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean  implements IPickerViewData {
        /**
         * amount : 50000
         * getDatetime : Aug 16, 2017 3:09:09 PM
         * id : 1
         * invalidDatetime : Sep 15, 2017 12:00:00 AM
         * remark : 系统出错，回收优惠券
         * startAmount : 1000000
         * status : 3
         * type : 1 0=获客优惠券 1=借还优惠券
         * updateDatetime : Aug 16, 2017 3:13:48 PM
         * updater : ylq
         * userId : U201708081809018411896
         * validDays : 30
         */

        private BigDecimal amount;
        private String getDatetime;
        private int id;
        private String invalidDatetime;
        private String remark;
        private BigDecimal startAmount;
        private String status;
        private String type;
        private String updateDatetime;
        private String updater;
        private String userId;
        private int validDays;


        public String getGetDatetime() {
            return getDatetime;
        }

        public void setGetDatetime(String getDatetime) {
            this.getDatetime = getDatetime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInvalidDatetime() {
            return invalidDatetime;
        }

        public void setInvalidDatetime(String invalidDatetime) {
            this.invalidDatetime = invalidDatetime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getStartAmount() {
            return startAmount;
        }

        public void setStartAmount(BigDecimal startAmount) {
            this.startAmount = startAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public int getValidDays() {
            return validDays;
        }

        public void setValidDays(int validDays) {
            this.validDays = validDays;
        }
        @Override
        public String getPickerViewText() {
            return MoneyUtils.MONEYSING+ MoneyUtils.showPrice(amount)+ "限借款"+MoneyUtils.showPrice(startAmount)+"元及以上使用";
        }
    }
}
