package com.chengdai.ehealthproject.weigit.appmanager;

/**
 * Created by 李先俊 on 2017/6/9.
 */

public class MyConfigStore {

    public final static String COMPANYCODE="CD-JKEG000011";
    public final static String SYSTEMCODE="CD-JKEG000011";
    public final static String IMGURL="http://or4e1nykg.bkt.clouddn.com/";
    // 无损压缩
    public static String IMGURLCOMPRESS = "?imageslim";
//    public final static String HOTELTYPE="FL2017061219492431865712";
    //TODO 民宿判断方法是否需要修改  现在用 getLevel=2为名宿
    public final static String HOTELTYPE="mingsu";

    /*1待支付 2 已支付待发货 3 已发货待收货 4 已收货 91用户取消 92 商户取消 93 快递异常*/
    public final static String ORDERTYPEWAITPAY="1";
    public final static String ORDERTYPEWAITFAHUO="2";
    public final static String ORDERTYPEWAITSHOUHUO="3";
    public final static String ORDERTYPEYISHOUHUO="4";
    public final static String ORDERTYPECANCELUSER="91";
    public final static String ORDERTYPECANCELSHOP="92";
    public final static String ORDERTYPEERROR="93";


    public final static String GENDERMAN="1";
    public final static String GENDERWOMAN="0";


    public static final int JFORDER=1;//积分订单
    public static final int PRICEORDER=2;//普通订单

    public final static String LEVEL_NOT_VIP="0";//不是
    public final static String LEVEL_VIP="1";


}
