package com.chengdai.ehealthproject.model.tabsurrounding.model;

/**
 * Created by 李先俊 on 2017/6/10.
 */

public class BannerModel {

    /*{
                "code": "DC201607211411347197",
                "name": "导航",
                "url": "http://sfklj.cn",
                "pic": "pic",
                "orderNo": "1",
                "status": "1",
                "type": "1",
                "toBelong": "1"
                "parentCode": "0",
                "siteCode": "GS201609131827116321",
            "remark": "备注"
            }*/

    private String code;
    private String name;
    private String url;
    private String pic;
    private String orderNo;
    private String status;
    private String type;
    private String toBelong;
    private String parentCode;
    private String siteCode;
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getToBelong() {
        return toBelong;
    }

    public void setToBelong(String toBelong) {
        this.toBelong = toBelong;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
