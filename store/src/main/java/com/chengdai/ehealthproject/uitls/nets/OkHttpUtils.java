package com.chengdai.ehealthproject.uitls.nets;


import com.chengdai.ehealthproject.base.BaseStoreApplication;
import com.chengdai.ehealthproject.uitls.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Okhttp OkHttpClient 使用封装
 * Created by Administrator on 2016-09-05.
 */
public class OkHttpUtils {

    private final static int CONNECT_TIMEOUT = 35;//连接超时
    private final static int READ_TIMEOUT = 35;//数据返回超时
    private final static int WRITE_TIMEOUT = 35;//请求超时
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB缓存


    private static File httpCacheDirectory = new File(BaseStoreApplication.getInstance().getCacheDir(), "netCache");
//    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);


    public OkHttpUtils() {}

    /**
     * Okhttp 缓存设置
     */
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetUtils.isNetworkConnected()) {
                int maxAge = 20; // 在线缓存20m内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周

                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    private  static OkHttpClient client;

    /**
     * 获取 OkHttpClient 对象
     * @return OkHttpClient
     */
    public static OkHttpClient getInstance() {
        if(client==null){
            client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                    .cache(cache)
                    .retryOnConnectionFailure(true)//允许失败重试
                    .cookieJar(new CookiesManager())  //cookie 管理
                    .addInterceptor(getInterceptor(LogUtil.isLog))    //网络日志
//            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .build();
        }

        return client;
    }


    /**
     * 网络请求拦截器
     *
     * @param isShow 控制请求日志的显示
     * @return interceptor
     */
    private static HttpLoggingInterceptor getInterceptor(boolean isShow) {

        HttpLoggingInterceptor  interceptor;

        if (isShow) {
               interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
               interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return interceptor;
    }


}
