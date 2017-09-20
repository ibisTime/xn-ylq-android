package com.chengdai.ehealthproject.weigit.appmanager;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.chengdai.ehealthproject.uitls.qiniu.QiNiuUtil;
import com.zzhoujay.richtext.RichText;


/**第三方管理
 * Created by 李先俊 on 2017/6/20.
 */

public class AppOhterManager {

    public static  void  getQiniuURL(Context cotext, String data, QiNiuUtil.QiNiuCallBack callBack){
        new QiNiuUtil(cotext).getQiniuURL(callBack,data);
    }

    public static void showRichText(Activity context, TextView tv, String str){
        RichText.from(str).imageClick((imageUrls, position) -> {
//            ImagePrviewActivity2.open(context,imageUrls,position);
        }).into(tv);
    }
}
