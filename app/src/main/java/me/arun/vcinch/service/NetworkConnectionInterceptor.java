package me.arun.vcinch.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public  class NetworkConnectionInterceptor implements Interceptor {



    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        return chain.proceed(request);
    }
}