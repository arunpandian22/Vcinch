package me.arun.vcinch.service;

import io.reactivex.Single;
import me.arun.vcinch.entities.UserList;
import retrofit2.Call;
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

    /**
     * A API call for the  get the users list
     * @param url a endpoint of the url
     * @param page a pagenumber for the users list
     * @return it returns the userslist response  or appropriate error to the single observer callbacks
     */
    @Headers({"Accept: application/json"})
    @GET
    Single<UserList> getUserList(@Url String url, @Query("page") int page);

    @Headers({"Accept: application/json"})
    @GET
    Call<UserList> getUserLists(@Url String url, @Query("page") int page);
}
