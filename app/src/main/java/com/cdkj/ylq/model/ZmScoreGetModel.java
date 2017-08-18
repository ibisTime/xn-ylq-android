package com.cdkj.ylq.model;

/**
 * Created by 李先俊 on 2017/8/13.
 */

public class ZmScoreGetModel {

    /**
     * appId : 测试内容25wt
     * authorized : true
     * isMatched : false
     * param : 测试内容ry5t
     * signature : 测试内容kya4
     * zmScore : 681
     */

    private String appId;
    private boolean authorized;
    private boolean isMatched;
    private String param;
    private String signature;
    private String zmScore;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public boolean isIsMatched() {
        return isMatched;
    }

    public void setIsMatched(boolean isMatched) {
        this.isMatched = isMatched;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getZmScore() {
        return zmScore;
    }

    public void setZmScore(String zmScore) {
        this.zmScore = zmScore;
    }
}
