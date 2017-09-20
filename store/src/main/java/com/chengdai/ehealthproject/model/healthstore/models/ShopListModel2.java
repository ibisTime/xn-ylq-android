package com.chengdai.ehealthproject.model.healthstore.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopListModel2  {


    /**
     * code : CP2017071115334251868705
     * storeCode : SJ2017071011184306249129
     * kind : 1
     * category : FL2017061611260999346746
     * type : FL2017061612200043893900
     * name : 东北长白山人参
     * slogan : 货好，便宜，多买实惠
     * advPic : 人参1_1499758391095.jpg
     * pic : 人参1_1499758397656.jpg
     * description : <p><img src="http://or4e1nykg.bkt.clouddn.com/人参1_1499758404963.jpg" style="max-width:100%;"><br></p><p><br></p>
     * location : 2
     * orderNo : 77
     * status : 3
     * updater : jkeg
     * updateDatetime : Jul 11, 2017 5:33:21 PM
     * boughtCount : 0
     * companyCode : CD-JKEG000011
     * systemCode : CD-JKEG000011
     */

    private String code;
    private String storeCode;
    private String kind;
    private String category;
    private String type;
    private String name;
    private String slogan;
    private String advPic;
    private String pic;
    private String description;
    private String location;
    private int orderNo;
    private String status;
    private String updater;
    private String updateDatetime;
    private int boughtCount;
    private String companyCode;
    private String systemCode;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAdvPic() {
        return advPic;
    }

    public String getSplitAdvPic() {

        List<String> s= StringUtils.splitAsList(advPic,"\\|\\|");
        if(s .size()>1)
        {
            return s.get(0);
        }

        return advPic;
    }



    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
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

    public int getBoughtCount() {
        return boughtCount;
    }

    public void setBoughtCount(int boughtCount) {
        this.boughtCount = boughtCount;
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
