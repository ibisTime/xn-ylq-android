package com.chengdai.ehealthproject.model.tabsurrounding.model;

/**
 * Created by 李先俊 on 2017/6/12.
 */

public class DZUpdateModel {

    private String code;

    private boolean isDz;

    private int dzSum;

    public int getDzSum() {
        return dzSum;
    }

    public void setDzSum(int dzSum) {
        this.dzSum = dzSum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDz() {
        return isDz;
    }

    public void setDz(boolean dz) {
        isDz = dz;
    }
}
