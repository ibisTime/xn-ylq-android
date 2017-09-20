package com.chengdai.ehealthproject.uitls;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cdkj.baselibrary.utils.LogUtil;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;

/**
 * Created by 李先俊 on 2017/6/9.
 */

public class StringUtils {
    public static String getJsonToString(Object object){

        if(object==null){
            return "";
        }

        String jsonString= JSON.toJSONString(object);

        LogUtil.BIGLOG("JSON 转换__:        "+jsonString);

        return jsonString;
    }

    public static String getOrderState(String state){

        if(TextUtils.isEmpty(state)){
            return "";
        }

        if (state.equals(MyConfigStore.ORDERTYPEWAITPAY)) { // 待支付
            return"待支付";
        } else if (state.equals(MyConfigStore.ORDERTYPEWAITFAHUO)) { // 已支付
            return"待发货";
        } else if (state.equals(MyConfigStore.ORDERTYPEWAITSHOUHUO)) { // 已发货
            return"去收货";
        } else if (state.equals(MyConfigStore.ORDERTYPEYISHOUHUO)) { // 已收货
            return"已收货";
        } else if (state.equals(MyConfigStore.ORDERTYPECANCELSHOP)) { // 用户取消
            return"已取消";
        } else if (state.equals(MyConfigStore.ORDERTYPECANCELUSER)) { // 商户取消
            return"已取消";
        } else if (state.equals(MyConfigStore.ORDERTYPEERROR)) {
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

        return MyConfigStore.ORDERTYPEWAITPAY.equals(state);
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


}
