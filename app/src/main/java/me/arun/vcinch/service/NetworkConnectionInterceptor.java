package me.arun.vcinch.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  Observes, modifies, and potentially short-circuits requests going out and the corresponding
 *  responses coming back in. Typically interceptors add, remove, or transform headers on the request
 *   or response.
 */
public  class NetworkConnectionInterceptor implements Interceptor {



    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        return chain.proceed(request);
    }
}