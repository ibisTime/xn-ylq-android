package com.cdkj.baselibrary.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 金钱格式化
 * Created by 李先俊 on 2017/8/12.
 */

public class MoneyUtils {

    public static final String MONEYSING = "￥";

    /**
     * 发起请求的金额计算
     *
     * @return
     */
    public static String getRequestPrice(String price) {
        return new BigDecimal(doubleFormatMoney2(new BigDecimal(price).doubleValue() * 1000)).intValue() + "";
    }

    /**
     * 发起请求的金额计算
     *
     * @return
     */
    public static String getRequestPrice(double price) {
        return new BigDecimal(doubleFormatMoney2(new BigDecimal(price).doubleValue() * 1000)).intValue() + "";
    }

    public static String intFormatMoney(double money) {
        int a = (int) money;
        return a + ""/*.substring(0,showMoney.length()-1)*/;
    }

    /**
     * 金钱格式化
     *
     * @param money
     * @return
     */
    public static String doubleFormatMoney(double money) {
        DecimalFormat df = new DecimalFormat("#######0.00");
        df.setRoundingMode(RoundingMode.CEILING);
        String showMoney = df.format((money));
        return showMoney/*.substring(0,showMoney.length()-1)*/;
    }

    public static Double doubleFormatMoney2(double money) {
        DecimalFormat df = new DecimalFormat("#######0.00");
        df.setRoundingMode(RoundingMode.CEILING);
        String showMoney = df.format((money));
        return new BigDecimal(showMoney).doubleValue();
    }

    public static Double doubleFormatMoney3(BigDecimal money) {
        if (money != null) {
            DecimalFormat df = new DecimalFormat("#######0.00");
            df.setRoundingMode(RoundingMode.CEILING);
            String showMoney = df.format((money));
            return new BigDecimal(showMoney).doubleValue();
        }

        return 0.00;
    }

    public static String showPrice(BigDecimal big) {

        if (big != null) {
            return doubleFormatMoney(((big.doubleValue()) / 1000));
        }
        return "0.00";
    }

    /**
     * 返回金钱String  写单单位(元)
     * @param big
     * @return
     */
    public static String showPriceUnit(BigDecimal big) {

        if (big != null) {
            return doubleFormatMoney(((big.doubleValue()) / 1000))+"元";
        }
        return "0.00元";
    }

    public static String showPriceInt(BigDecimal big) {

        if (big != null) {

            return intFormatMoney(((big.doubleValue()) / 1000));
        }
        return "0";
    }

    /**
     * 显示金钱乘规格
     *
     * @param big
     * @param size
     * @return
     */
    public static String showPrice(BigDecimal big, int size) {

        if (big != null) {
            BigDecimal bigDecimal = new BigDecimal(size);

            return doubleFormatMoney((big.multiply(bigDecimal).doubleValue() / 1000));
        }

        return "0";

    }


    public static String getShowPriceSign(BigDecimal bigDecimal) {
        return MONEYSING + showPrice(bigDecimal);
    }

    public static String getShowPriceSign(BigDecimal bigDecimal, int size) {
        return MONEYSING + showPrice(bigDecimal, size);
    }


}
