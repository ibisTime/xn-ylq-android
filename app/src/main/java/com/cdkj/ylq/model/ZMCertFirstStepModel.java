package com.cdkj.ylq.model;

/**芝麻认证第一步
 * Created by 李先俊 on 2017/7/26.
 */

public class ZMCertFirstStepModel {

    /**
     * bizNo : ZM201707263000000808000467800746
     * isSuccess : false
     * url : https://zmopenapi.zmxy.com.cn/openapi.do?sign=Wfj%2BJAl6U%2F8
     */

    private String bizNo;
    private boolean isSuccess;
    private String url;
    private String merchantId;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
