package com.chengdai.ehealthproject.model.healthstore.models;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/18.
 */

public class ShopOrderModel {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 2
     * totalCount : 6
     * totalPage : 3
     * list : [{"code":"IN201611171344066694","type":"1","receiver":"郑海清","reMobile":"18767101909","reAddress":"浙江省杭州市余杭区","receiptType":"","receiptTitle":"","applyUser":"U000000001","applyNote":"","applyDatetime":"Nov 17, 2016 1:44:06 PM","amount":10000,"yunfei":0,"payAmount":10000,"payDatetime":"Nov 17, 2016 1:44:06 PM","status":"3","updater":"admin","updateDatetime":"Nov 17, 2016 2:58:07 PM","remark":"发货录入","logisticsCode":"IN201252015222425","logisticsCompany":"1","deliverer":"admin","deliveryDatetime":"Sep 8, 2016 2:00:00 PM","pdf":"物流单","systemCode":"GS00001","totalAmount":10000,"productOrderList":[{"code":"IM201611171344067091","orderCode":"IN201611171344066694","productCode":"MP201611161913526307","quantity":1000,"salePrice":10,"productName":"胶囊咖啡A","advPic":"aa","isComment":"1"}]}]
     */

    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ShopOrderDetailBean> list;

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

    public List<ShopOrderDetailBean> getList() {
        return list;
    }

    public void setList(List<ShopOrderDetailBean> list) {
        this.list = list;
    }

}
