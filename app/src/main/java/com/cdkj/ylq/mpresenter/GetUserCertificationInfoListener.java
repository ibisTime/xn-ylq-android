package com.cdkj.ylq.mpresenter;

/* 获取认证信息接口
 * Created by 李先俊 on 2017/8/12.
 */

import com.cdkj.baselibrary.model.UserLoginModel;
import com.cdkj.ylq.model.CerttificationInfoModel;

public interface GetUserCertificationInfoListener {

    void getInfoSuccess(CerttificationInfoModel userCertInfo, String msg);    //成功

    void getInfoFailed(String code, String msg);   //失败

    void startGetInfo(boolean showDialog);   //开始登录

    void endGetInfo(boolean showDialog);   //结束登录

}
