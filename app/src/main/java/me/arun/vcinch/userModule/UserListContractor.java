package me.arun.vcinch.userModule;

import me.arun.vcinch.entities.UserList;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public interface UserListContractor {
    interface View {
        void loadUserListAdapter(UserList userList);

        void showErrorState(Throwable e);

        void refreshData();

        void showInternetStateStrip();

        void isProgressBarShow(boolean isShow);
    }

    interface Presenter {
        void requestUserListData(int page);
    }

}
