package me.arun.vcinch.service;
import android.content.Context;
import android.util.Log;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.arun.vcinch.BuildConfig;
import me.arun.vcinch.VcinchApplication;
import me.arun.vcinch.userModule.cachingUtils.GsonCacheableConverter;
import me.arun.vcinch.userModule.cachingUtils.GsonResponseListener;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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

   String TAG="Api";


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

        Log.d(TAG, "buildRetrofit: ");
        //setup cache

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addNetworkInterceptor(new NetworkConnectionInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        this.apiInterface = retrofit.create(ApiInterface.class);
    }




    /**
     * This function returns ApiInterface with headers
     * @return ApiInterface.java
     */
    public ApiInterface getApiInterface() {
        return this.apiInterface;
    }


}
