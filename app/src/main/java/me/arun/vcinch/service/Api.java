package me.arun.vcinch.service;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.arun.vcinch.BuildConfig;
import me.arun.vcinch.userModule.UserListActivity;
import me.arun.vcinch.utils.NetworkHelper;
import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static me.arun.vcinch.VcinchApplication.getContext;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public class Api {
    private static Api instance = null;
    Retrofit retrofit = null;
    // Keep your services here, build them in buildRetrofit method later
    private ApiInterface apiInterface;
   public  Context context;


    /**
     * Private constructor to ensure instance of this class is not created anywhere other than here
     */
    private Api() {

        buildRetrofit(BuildConfig.BASE_URL);
    }



    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }




    private void buildRetrofit(String baseUrl) {

        //setup cache
        File httpCacheDirectory = new File(getContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().cache(cache)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();









        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        this.apiInterface = retrofit.create(ApiInterface.class);
    }




    private static   Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetworkHelper.isNetworkAvailable(getContext())) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }

    };

    /**
     * This function returns ApiInterface with headers
     * @return ApiInterface.java
     */
    public ApiInterface getApiInterface() {
        return this.apiInterface;
    }

}
