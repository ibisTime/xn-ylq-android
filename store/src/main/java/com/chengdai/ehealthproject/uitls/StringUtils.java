package com.chengdai.ehealthproject.uitls;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 李先俊 on 2017/6/9.
 */

public class StringUtils {

    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[0-9]+$").matcher(input);
        return mer.find();
    }

    public static String getJsonToString(Object object){

        if(object==null){
            return "";
        }

        String jsonString=JSON.toJSONString(object);

        LogUtil.BIGLOG("JSON 转换__:        "+jsonString);

        return jsonString;
    }

    public static String doubleFormatMoney(double money){
        DecimalFormat df = new DecimalFormat("#######0.00");
        String showMoney = df.format((money));
        return showMoney/*.substring(0,showMoney.length()-1)*/;
    }

    public static String doubleFormatMoney3(double money){
        DecimalFormat df = new DecimalFormat("#######0.00");
        df.setRoundingMode(RoundingMode.CEILING);
        String showMoney = df.format((money));
        return showMoney/*.substring(0,showMoney.length()-1)*/;
    }

    public static Double doubleFormatMoney2(double money){
        DecimalFormat df = new DecimalFormat("#######0.00");
        String showMoney = df.format((money));
        return new BigDecimal(showMoney).doubleValue();
    }

    public static List<String> splitAsList(String s,String sp){

        List<String> strings=new ArrayList<>();

        if(!TextUtils.isEmpty(s)){
            strings= Arrays.asList( s.split(sp));
        }

        return strings;
    }
    /**
     *发起请求的金额计算
     * @return
     */
    public static String getRequestPrice(String price){
     return   new BigDecimal(doubleFormatMoney2(new BigDecimal(price).doubleValue()*1000)).intValue()+"";
    }
    /**
     *发起请求的金额计算
     * @return
     */
    public static String getRequestPrice(double price){
     return   new BigDecimal(doubleFormatMoney2(new BigDecimal(price).doubleValue()*1000)).intValue()+"";
    }


    /**
     * 切割获取广告图片
     * @param s
     * @return
     */
   public static List<String> splitBannerList(String s){
        return splitAsList(s,"\\|\\|");
    }

    public static String subString(String s, int start, int end) {

        try {
            if (s == null || s.length() <= 0 || end < start || end < 0 || start < 0) {

                return "";
            }
            return s.substring(start, end);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String subStringEnd(String s) {
        if(TextUtils.isEmpty(s) || s.length()<=1){
            return s;
        }
        return subString(s,s.length()-1,s.length());
    }

    //int前面补零
    public static String frontCompWithZoreString(Object sourceDate,int formatLength)
    {
        try {
            String newString = String.format("%0" + formatLength + "d", sourceDate);
            return  newString;
        }catch (Exception e)
        {
            return sourceDate.toString();
        }
    }

    public static String showPrice(BigDecimal big){

        if(big !=null){
            return doubleFormatMoney(((big.doubleValue())/1000));
        }
        return "0.00";
    }

    public static String showJF(BigDecimal big){

        return showPrice(big);

    /*    if(big !=null){
            return ((big.intValue())/1000)+"";
        }
        return "0";*/
    }

    /**
     * 显示积分乘规格
     * @param big
     * @param size
     * @return
     */
    public static String showJF(BigDecimal big,int size){

        return showPrice(big,size);

   /*     if(big !=null){
            BigDecimal bigDecimal=new BigDecimal(size);
            return (big.multiply(bigDecimal).intValue()/1000)+"";
        }

        return "0";*/

    }

    /**
     * 显示金钱乘规格
     * @param big
     * @param size
     * @return
     */
        public static String showPrice(BigDecimal big,int size){

        if(big !=null){
            BigDecimal bigDecimal=new BigDecimal(size);

            return  doubleFormatMoney((big.multiply(bigDecimal).doubleValue()/1000));
        }

        return "0";

    }

    public static String getOrderState(String state){

        if(TextUtils.isEmpty(state)){
            return "";
        }

        if (state.equals(MyConfig.ORDERTYPEWAITPAY)) { // 待支付
            return"待支付";
        } else if (state.equals(MyConfig.ORDERTYPEWAITFAHUO)) { // 已支付
            return"待发货";
        } else if (state.equals(MyConfig.ORDERTYPEWAITSHOUHUO)) { // 已发货
            return"去收货";
        } else if (state.equals(MyConfig.ORDERTYPEYISHOUHUO)) { // 已收货
            return"已收货";
        } else if (state.equals(MyConfig.ORDERTYPECANCELSHOP)) { // 用户取消
            return"已取消";
        } else if (state.equals(MyConfig.ORDERTYPECANCELUSER)) { // 商户取消
            return"已取消";
        } else if (state.equals(MyConfig.ORDERTYPEERROR)) {
            return"快递异常";
        }
        return "";
    }

    /**
     * 检查是否时待支付状态
     * @param state
     * @return
     */
    public static boolean canDoPay(String state){

        return MyConfig.ORDERTYPEWAITPAY.equals(state);
    }

    public static String getShowPriceSign(BigDecimal bigDecimal){
        return "￥"+showPrice(bigDecimal);
    }

   public static String getShowPriceSign(BigDecimal bigDecimal,int size){
        return "￥"+showPrice(bigDecimal,size);
    }

    /**
     * list装换为字符串
     *
     * @param list
     * @return
     */
    public static String ListToString(List<?> list,String sep1) {

        if (list == null || list.size() == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i).equals("")) {
                    continue;
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof List) {
                    sb.append(ListToString((List<?>) list.get(i),sep1));
                    if (i != list.size() - 1) {
                        sb.append(sep1);
                    }

                } /*else if (list.get(i) instanceof Map) {
                    sb.append(MapToString((Map<?, ?>) list.get(i)));
                    if (i != list.size() - 1) {
                        sb.append(sep1);
                    }

                }*/ else {
                    sb.append(list.get(i));
                    if (i != list.size() - 1) {
                        sb.append(sep1);
                    }

                }
            }
        }
        return sb.toString();
    }


    //判断email格式是否正确
    public static  boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }


    public static String getLogisticsCompany(String key){

            if(TextUtils.equals("ZJS",key)){

                return "宅急送";

            }else if(TextUtils.equals("TTKD",key)){
                return "天天快递";
            }else if(TextUtils.equals("SF",key)){
                return "顺丰快递";
            } else  if(TextUtils.equals("HTKY",key)){
                return "汇通快递";
            } else if(TextUtils.equals("YTO",key)){
                return "圆通快递";
            } else if(TextUtils.equals("ZTO",key)){
                return "中通快递";
            } else if(TextUtils.equals("STO",key)){
                return "申通快递";
            } else if(TextUtils.equals("EMS",key)){
                return "邮政EMS";
            }

            return "其它";
    }

    public static String getTjTypebyCode(Context context,String key){

        String[] tNames=context.getResources().getStringArray(R.array.tjtype);
        String[] tCodes=context.getResources().getStringArray(R.array.tj_type_code);

        if(tNames.length != tCodes.length){
            return "暂无";
        }

        int index=0;

          for(int i=0;i<tCodes.length;i++){
              if(TextUtils.equals(tCodes[i],key)){
                  index=i;
                  break;
              }
          }

         return tNames[index];
    }


    /**
     * Map转换String
     *
     * @param map :需要转换的Map
     * @return String转换后的字符串
     */
/*    public static String MapToString(Map<?, ?> map) {
        StringBuffer sb = new StringBuffer();
        // 遍历map
        for (Object obj : map.keySet()) {
            if (obj == null) {
                continue;
            }
            Object key = obj;
            Object value = map.get(key);
            if (value instanceof List<?>) {
                sb.append(key.toString() + SEP1 + ListToString((List<?>) value));
                sb.append(SEP2);
            } else if (value instanceof Map<?, ?>) {
                sb.append(key.toString() + SEP1
                        + MapToString((Map<?, ?>) value));
                sb.append(SEP2);
            } else {
                sb.append(key.toString() + SEP3 + value.toString());
                sb.append(SEP2);
            }
        }
        return  sb.toString();
    }*/

}
