package me.arun.vcinch.userModule;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.arun.vcinch.entities.UserList;
import me.arun.vcinch.service.Api;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 * A class created to act as a presenter for the Activity
 */
public class UserListPresenter implements UserListContractor.Presenter {
    private UserListContractor.View view;
    private Gson gson = new Gson();
    private UserListViewModel userListViewModel;

    public UserListPresenter(UserListContractor.View view, UserListViewModel userListViewModel) {
        this.view = view;
        this.userListViewModel = userListViewModel;
    }

    @Override
    public void requestUserListData(int page) {
        Api.getInstance().getApiInterface().getUserList("users", page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserList userList) {
                        view.loadUserListAdapter(userList);
                    }

                    @Override
                    public void onError(Throwable e) {

                        view.showErrorState(e);
                    }
                });


    }
}
