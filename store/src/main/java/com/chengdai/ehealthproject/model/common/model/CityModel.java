package com.chengdai.ehealthproject.model.common.model;

import android.text.TextUtils;

/**
 * Created by 李先俊 on 2017/6/16.
 */

public class CityModel {

    private String name;
    private String pinyin;
    private String firstChar;

    public CityModel() {}

    public CityModel(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {

        return pinyin;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
