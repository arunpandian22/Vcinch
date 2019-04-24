package me.arun.vcinch.userModule.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.arun.vcinch.model.Datum;
import me.arun.vcinch.model.UserList;
import me.arun.vcinch.service.Api;

/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public class ItemDataSource extends PageKeyedDataSource<Integer, Datum> {

    String TAG="ItemDataSource";
    //the size of a page that we want
    public static final int PAGE_SIZE =3;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    private static final String endPoint = "users";

    UserList userList;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Datum> callback) {
        Log.d(TAG, "loadInitial: ");
        Api.getInstance().getApiInterface().getUserList(endPoint, FIRST_PAGE)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserList userList) {
                        if (userList!= null) {
                            callback.onResult(userList.getData(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Datum> callback) {
        Log.d(TAG, "loadBefore: ");

        Api.getInstance().getApiInterface().getUserList(endPoint, params.key)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserList userList) {
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (userList!= null) {
                            callback.onResult(userList.getData(), adjacentKey);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Datum> callback) {
        Log.d(TAG, "loadAfter: ");

        Api.getInstance().getApiInterface().getUserList(endPoint, params.key)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserList userList) {

                        if (userList != null ) {
                            callback.onResult(userList.getData(), params.key + 1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {


                    }
                });

    }
}
