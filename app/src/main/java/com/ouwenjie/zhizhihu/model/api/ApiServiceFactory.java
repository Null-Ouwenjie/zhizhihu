package com.ouwenjie.zhizhihu.model.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ouwenjie.zhizhihu.App;
import com.ouwenjie.zhizhihu.AppConfig;
import com.ouwenjie.zhizhihu.common.LLog;
import com.ouwenjie.zhizhihu.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 文杰 on 2015/10/22.
 */
public class ApiServiceFactory {

    private static volatile OkHttpClient client = null;

    private static ApiService apiService = null;

    static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    public static synchronized ApiService getApiService() {
        if (client == null) {
            initHttpClient();
        }
        if (apiService == null) {
            synchronized (ApiService.class) {
                if (apiService == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(client)
                            .baseUrl(AppConfig.KZH_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    apiService = retrofit.create(ApiService.class);
                }
            }
        }
        return apiService;
    }

    public static synchronized OkHttpClient getHttpClient() {
        if (client == null) {
            initHttpClient();
        }
        return client;
    }

    /**
     * 每个获取 APIService 的方法都在先检测 client 是否为空
     */
    private static void initHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        File file = new File(App.getContext().getCacheDir(), "ResponseCache");
        builder.cache(new Cache(file, AppConfig.RESPONSE_CACHE_SIZE))
                .readTimeout(AppConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(AppConfig.CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        if (AppConfig.isDebug) {
            // TODO: 2016/3/19 Release 版本需要注释此拦截器
            builder.addNetworkInterceptor(new StethoInterceptor());
            builder.addInterceptor(LoggingInterceptor);
        }
        builder.addNetworkInterceptor(HttpInterceptor);
        builder.addInterceptor(HttpInterceptor);
        client = builder.build();
    }

    private static final Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            LLog.d("LoggingInterceptor ",
                    String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            LLog.d("LoggingInterceptor ",
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            ResponseBody responseBody = response.body();
            String responseBodyString = response.body().string();   // 取出响应的 body 的内容，只能取一次
            Response newResponse = response.newBuilder()
                    .body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes()))
                    .build();       //  重新构建回 response
            LLog.d("LoggingInterceptor ", "Received response body \n " + responseBodyString);   // 查看返回的body
            return newResponse;
        }
    };
    private static final Interceptor HttpInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request newRequest;
            if (!NetworkUtils.isNetworkAvailable(App.getContext())) {
                newRequest = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LLog.e("Interceptor ", " no network");
            } else {
                newRequest = request.newBuilder()
                        .build();
            }
            Response originalResponse = chain.proceed(newRequest);
            if (NetworkUtils.isNetworkAvailable(App.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", cacheControl)
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .build();
            }
        }
    };

    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
