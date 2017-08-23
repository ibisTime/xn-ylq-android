package com.cdkj.ylq.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 李先俊 on 2017/8/22.
 */

public class ProductSingModel {

    /**
     * code : AP-2017082215364450838950
     * status : 1
     */

    private String code;
    private String status;//1 认证中 2待审核

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
