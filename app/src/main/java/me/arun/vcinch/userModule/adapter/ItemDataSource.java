package me.arun.vcinch.userModule.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.arun.vcinch.VcinchApplication;
import me.arun.vcinch.data.ModelEmptyErrorData;
import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.entities.UserList;
import me.arun.vcinch.localDb.AppDataBase;
import me.arun.vcinch.service.Api;
import me.arun.vcinch.utils.AppConstants;
import me.arun.vcinch.utils.NetworkHelper;

/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 * <p>
 * Incremental data loader for page-keyed content, where requests return keys for next/previous
 * pages.
 */
public class ItemDataSource extends PageKeyedDataSource<Integer, Datum> {

    String TAG = "ItemDataSource";
    //the size of a page that we want
    public static final int PAGE_SIZE = 3;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    private static final String endPoint = "users";

    UserList userList;
    AppDataBase appDataBase = VcinchApplication.getAppDataBase();

    /**
     * A call back to load intial data for the paging adapter datasource factory
     *
     * @param params   Type of data used to query Value types out of the DataSource. here it has the page number
     * @param callback it is always valid for a DataSource loading method that takes a callback to stash the
     *                 callback and call it later. This enables DataSources to be fully asynchronous, and to handle
     *                 temporary, recoverable error states (such as a network error that can be retried).
     */
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Datum> callback) {
        Log.d(TAG, "loadInitial: ");

        VcinchApplication.rxBus.send(new ModelEmptyErrorData(false, true, ""));
        if (NetworkHelper.isNetworkAvailable(VcinchApplication.getContext())) {
            Api.getInstance().getApiInterface().getUserList(endPoint, FIRST_PAGE).timeout(60, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserList>() {
                        @Override
                        public void onSubscribe(Disposable d) {


                        }

                        @Override
                        public void onSuccess(UserList userList) {

                            if (userList != null && userList.getData() != null && userList.getData().size() > 0) {
                                callback.onResult(userList.getData(), null, FIRST_PAGE + 1);
                                appDataBase.userListDao().insert(userList);
                                VcinchApplication.rxBus.send(new ModelEmptyErrorData(false, false, ""));
                            } else
                                VcinchApplication.rxBus.send(new ModelEmptyErrorData(true, false, AppConstants.EMPTY_DATA));
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());
                            VcinchApplication.rxBus.send(new ModelEmptyErrorData(true, false, AppConstants.API_FAILURE));
                        }
                    });
        } else {
            UserList userList = appDataBase.userListDao().getUsers("" + FIRST_PAGE);
            if (userList != null && userList.getData() != null && userList.getData().size() > 0) {
                callback.onResult(userList.getData(), null, FIRST_PAGE + 1);
                VcinchApplication.rxBus.send(new ModelEmptyErrorData(false, false, ""));
            } else {
                VcinchApplication.rxBus.send(new ModelEmptyErrorData(true, false, AppConstants.NO_OFFLINE));
            }


        }
    }


    /**
     * A call back to load befor data for the paging adapter datasource factory while doing the double side pagination.Prepend page with the key specified by {@link LoadParams#key LoadParams.key}.
     *
     * @param params   Type of data used to query Value types out of the DataSource. here it has the page number
     * @param callback it is always valid for a DataSource loading method that takes a callback to stash the
     *                 callback and call it later. This enables DataSources to be fully asynchronous, and to handle
     *                 temporary, recoverable error states (such as a network error that can be retried).
     */

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Datum> callback) {
        Log.d(TAG, "loadBefore: ");
        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;

        if (NetworkHelper.isNetworkAvailable(VcinchApplication.getContext())) {
            Api.getInstance().getApiInterface().getUserList(endPoint, params.key)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserList>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(UserList userList) {

                            if (userList != null) {
                                callback.onResult(userList.getData(), adjacentKey);
                                appDataBase.userListDao().insert(userList);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());
                        }
                    });
        } else {
            Log.d(TAG, "loadBefore: offline");
            UserList userList = appDataBase.userListDao().getUsers("" + params.key);
            if (userList != null && userList.getData() != null) {
                callback.onResult(userList.getData(), adjacentKey);
                VcinchApplication.rxBus.send(new ModelEmptyErrorData(false, false, ""));
            }

        }
    }

    /**
     * A call back to load after data for the paging adapter datasource factory while doing the double side pagination.append page with the key specified by {@link LoadParams#key LoadParams.key}.
     *
     * @param params   Type of data used to query Value types out of the DataSource. here it has the page number
     * @param callback it is always valid for a DataSource loading method that takes a callback to stash the
     *                 callback and call it later. This enables DataSources to be fully asynchronous, and to handle
     *                 temporary, recoverable error states (such as a network error that can be retried).
     */
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Datum> callback) {
        Log.d(TAG, "loadAfter: ");

        if (NetworkHelper.isNetworkAvailable(VcinchApplication.getContext())) {
            Api.getInstance().getApiInterface().getUserList(endPoint, params.key)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserList>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(UserList userList) {

                            if (userList != null) {
                                callback.onResult(userList.getData(), params.key + 1);
                                appDataBase.userListDao().insert(userList);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());
                        }
                    });
        } else {
            Log.d(TAG, "loadAfter: offline");
            UserList userList = appDataBase.userListDao().getUsers("" + params.key);
            if (userList != null && userList.getData() != null) {
                callback.onResult(userList.getData(), params.key + 1);
            }


        }

    }
}
