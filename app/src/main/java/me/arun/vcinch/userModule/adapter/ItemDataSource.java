package me.arun.vcinch.userModule.adapter;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.arun.vcinch.VcinchApplication;
import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.entities.UserList;
import me.arun.vcinch.localDb.AppDataBase;
import me.arun.vcinch.service.Api;
import me.arun.vcinch.utils.NetworkHelper;

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
    AppDataBase appDataBase=VcinchApplication.getAppDataBase();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Datum> callback) {
        Log.d(TAG, "loadInitial: ");
        if (NetworkHelper.isNetworkAvailable(VcinchApplication.getContext())) {
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
                                appDataBase.userListDao().insert(userList);
                            }
                        }
    
                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: "+e.getMessage());
                        }
                    });
        } else {
            UserList userList=appDataBase.userListDao().getUsers(""+FIRST_PAGE);
            if (userList!=null && userList.getData()!=null)
                callback.onResult(userList.getData(), null, FIRST_PAGE + 1);
            Log.d(TAG, "loadInitial: offline"+userList.getData().size());
            Log.d(TAG, "loadInitial: ");

        }
    }

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

                            if (userList!= null) {
                                callback.onResult(userList.getData(), adjacentKey);
                                appDataBase.userListDao().insert(userList);
                            }
                        }
    
                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: "+e.getMessage());
                        }
                    });
        } else {
            Log.d(TAG, "loadBefore: offline");
            UserList userList=appDataBase.userListDao().getUsers(""+params.key);
            if (userList!=null && userList.getData()!=null)
                callback.onResult(userList.getData(), adjacentKey);

        }
    }

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
                            Log.d(TAG, "onError: "+e.getMessage());
                        }
                    });
        }else {
            Log.d(TAG, "loadAfter: offline");
            UserList userList=appDataBase.userListDao().getUsers(""+params.key);
            if (userList!=null && userList.getData()!=null)
                callback.onResult(userList.getData(), params.key + 1);


        }

    }
}
