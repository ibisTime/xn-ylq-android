package com.chengdai.ehealthproject.uitls.nets;


import com.chengdai.ehealthproject.BuildConfig;
import com.chengdai.ehealthproject.model.api.ApiServer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 * 服务器api
 * Created by Administrator on 2016/9/1.
 */
public class RetrofitUtils {

    public static RetrofitUtils retrofitUtils;

    private  ApiServer apiServer;

    public RetrofitUtils() {
        apiServer = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .client(OkHttpUtils.getInstance())
                .addConverterFactory(FastJsonConVerter.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiServer.class);
    }

    /**
     * 服务接口单例
     *
     * @return Retrofit
     */
    public static RetrofitUtils bulid() {
        if (retrofitUtils == null) {
            retrofitUtils = new RetrofitUtils();
        }
        return retrofitUtils;
    }


    private ApiServer getApiServer() {
        return apiServer;
    }

    public static ApiServer getLoaderServer() {
        return bulid().getApiServer();
    }

    /**
     * 获取URL  根据版本切换不同版本
     *
     * @return
     */
    public static String getBaseURL() {

        if (BuildConfig.IS_DEBUG){

        }

//        return "http://121.43.101.148:3401/forward-service/";//开发
//        return "http://118.178.124.16:3401/forward-service/";//测试
                return "http://116.62.114.86:8901/forward-service/";//线上
    }

}
