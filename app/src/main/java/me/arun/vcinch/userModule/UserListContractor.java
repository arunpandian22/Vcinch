package me.arun.vcinch.userModule;

import me.arun.vcinch.entities.UserList;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */

/**
 * A Contractor interface has the abstract methods which are supposed tob implemented in view and presenter class
 */
public interface UserListContractor {

    /**
     * a view related abstract classes
     */
    interface View {
        /**
         * A abstract method to load the userlist adapter
         * @param userList users list
         */
        void loadUserListAdapter(UserList userList);

        /**
         * A abstract method to show teh error in teh error state
         * @param e value contains the all error and exceptions
         */
        void showErrorState(Throwable e);

        /**
         * A abstract method to refresh the data
         */
        void refreshData();

        /**
         * A abstract method to show the internet strip
         */
        void showInternetStateStrip();

        /**
         * A abstract emthod to show the progressbar
         * @param isShow a boolean values decides whether a progress has to be shown or not
         */
        void isProgressBarShow(boolean isShow);
    }

    /**
     * a presenter related abstract classes
     */
    interface Presenter {
        /**
         * A abstract method to make the api call for the users list
         * @param page a value for the which page has to load
         */
        void requestUserListData(int page);
    }

}
