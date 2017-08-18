package com.cdkj.ylq.module.api;


import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.model.UserLoginModel;
import com.cdkj.ylq.model.CanUseCouponsModel;
import com.cdkj.ylq.model.CanUseMoneyModel;
import com.cdkj.ylq.model.CerttificationInfoModel;
import com.cdkj.ylq.model.CoupoonsModel;
import com.cdkj.ylq.model.HuokeListModel;
import com.cdkj.ylq.model.KeyDataModel;
import com.cdkj.ylq.model.MsgListModel;
import com.cdkj.ylq.model.PorductListModel;
import com.cdkj.ylq.model.UseMoneyRecordModel;
import com.cdkj.ylq.model.UserInfoModel;
import com.cdkj.ylq.model.ZMCertFirstStepModel;
import com.cdkj.ylq.model.ZMCertSecStepModel;
import com.cdkj.ylq.model.ZmScoreGetModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 李先俊 on 2017/6/8.
 */

public interface MyApiServer {


    /**
     * //注册
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<UserLoginModel>> userRegister(@Field("code") String code, @Field("json") String json);
    /**
     *获取用户信息详情
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<UserInfoModel>> getUserInfoDetails(@Field("code") String code, @Field("json") String json);
    /**
     *获取用户信息详情
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<PorductListModel>> getProductListData(@Field("code") String code, @Field("json") String json);

    /**
     *获取认证信息
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<CerttificationInfoModel>> getCerttificationInfo(@Field("code") String code, @Field("json") String json);

    /**
     *获取数据字典
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<KeyDataModel>> getKeyData(@Field("code") String code, @Field("json") String json);

    /**
     *获取优惠券数据
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<CoupoonsModel>> getCouponsListData(@Field("code") String code, @Field("json") String json);

    /**
     *获取优惠券数据
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<HuokeListModel>> getHuokeListData(@Field("code") String code, @Field("json") String json);

    /**
     *获取产品使用状态数据
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<UseMoneyRecordModel.ListBean>> getUseMoneyData(@Field("code") String code, @Field("json") String json);

    /**
     *获取可用优惠券数据
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<CanUseCouponsModel>> getCanUseCouponsListData(@Field("code") String code, @Field("json") String json);

    /**
     *zm one
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ZMCertFirstStepModel>> ZmCertFirstStep(@Field("code") String code, @Field("json") String json);

    /**
     *zm two
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ZMCertSecStepModel>> ZmCertSecStep(@Field("code") String code, @Field("json") String json);

    /**
     *zm 分获取
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ZmScoreGetModel>> getZmScore(@Field("code") String code, @Field("json") String json);

    /**
     *获取额度
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<CanUseMoneyModel>> getCanUseMoney(@Field("code") String code, @Field("json") String json);

    /**
     *获取额度
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<PorductListModel.ListBean>> getCanUseProduct(@Field("code") String code, @Field("json") String json);
    /**
     *记录列表
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<UseMoneyRecordModel>> getRecordList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取推荐列表
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<MsgListModel>> getMsgList(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取推荐列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<PorductListModel.ListBean>> getProductDetail(@Field("code") String code, @Field("json") String json);




}
