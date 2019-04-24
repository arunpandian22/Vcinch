package me.arun.vcinch.service;

import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import io.reactivex.Single;
import me.arun.vcinch.model.UserList;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public interface ApiInterface {

    /**
     * GET Method
     * @param url is path followed by base url
     * @return response of the API call
     */
    @Headers({"Accept: application/json"})
    @GET
    Single<UserList> getUserList(@Url String url, @Query("page") int page);
}
