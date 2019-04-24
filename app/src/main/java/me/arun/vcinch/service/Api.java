package me.arun.vcinch.service;
import android.content.Context;
import android.util.Log;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.File;
import java.util.concurrent.TimeUnit;

import me.arun.vcinch.BuildConfig;
import me.arun.vcinch.VcinchApplication;
import me.arun.vcinch.userModule.cachingUtils.GsonCacheableConverter;
import me.arun.vcinch.userModule.cachingUtils.GsonResponseListener;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public class Api  implements GsonResponseListener {
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
        File httpCacheDirectory = new File(VcinchApplication.getContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonCacheableConverter.create(this))
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

    @Override
    public void onCacheableResponse(Class type, Object responseBody) {

    }
}
