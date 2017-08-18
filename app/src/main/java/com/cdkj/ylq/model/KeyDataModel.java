package com.cdkj.ylq.model;

import com.bigkoo.pickerview.model.IPickerViewData;

/**数据字典
 * Created by 李先俊 on 2017/8/13.
 */

public class KeyDataModel implements IPickerViewData {


    /**
     * id : 24
     * type : 1
     * parentKey : education
     * dkey : 6
     * dvalue : 博士
     * updater : admin
     * updateDatetime : Aug 12, 2017 5:34:05 PM
     * companyCode : CD-YLQ000014
     * systemCode : CD-YLQ000014
     */

    private int id;
    private String type;
    private String parentKey;
    private String dkey;
    private String dvalue;
    private String updater;
    private String updateDatetime;
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

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getDkey() {
        return dkey;
    }

    public void setDkey(String dkey) {
        this.dkey = dkey;
    }

    public String getDvalue() {
        return dvalue;
    }

    public void setDvalue(String dvalue) {
        this.dvalue = dvalue;
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

    @Override
    public String getPickerViewText() {
        return dvalue;
    }
}
