package me.arun.vcinch.utils.networkreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import me.arun.vcinch.VcinchApplication;
import me.arun.vcinch.userModule.UserListActivity;

import static me.arun.vcinch.utils.AppConstants.IS_CONNECTION_AVAILABLE;
import static me.arun.vcinch.utils.AppConstants.NETWORK_STATE_CHANGE_ACTION;

/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    public String TAG = "NetworkChangeReceiver";
    boolean isNetworkAvailable;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(TAG, "onReceive: ");
        String action=intent.getAction();

        assert action != null;
        if(!TextUtils.isEmpty(action)&&action.equals(NETWORK_STATE_CHANGE_ACTION))
        {
            isNetworkAvailable = intent.getBooleanExtra(IS_CONNECTION_AVAILABLE, false);
            Log.d(TAG, "onReceive: isNetworkAvailable"+isNetworkAvailable);
        }
        else
            isNetworkAvailable=checkNetworkAvailability(context, ConnectionType.MOBILE_AND_WIFI);

        if (isNetworkAvailable)
            onNetworkConnect();
        else
            onNetworkDisConnect();

    }


    public void onNetworkConnect() {
        Log.d(TAG, "onReceive: Network Available");
        callActivityNetworkChangeMethod(true);
    }

    public void onNetworkDisConnect() {
        Log.d(TAG, "onReceive: Network not available ");
        callActivityNetworkChangeMethod(false);

    }

    /**
     * A method to check the whether network is available or not
     *
     * @param context        a param has the value of the current Activity
     * @param connectionType a param has the value of ConnectionType enum type
     * @return it returns true if the connection is available based on given ConnectionType enum type
     */
    boolean checkNetworkAvailability(Context context, ConnectionType connectionType) {
        Log.d(TAG, "checkNetworkAvailability: ");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            int dataType = networkInfo.getType();

            switch (connectionType) {
                case MOBILE_DATA_ONLY:
                    if (dataType == ConnectivityManager.TYPE_MOBILE)
                        return true;
                case WIFI_ONLY:
                    if (dataType == ConnectivityManager.TYPE_WIFI)
                        return true;
                case MOBILE_AND_WIFI:
                    if (dataType == ConnectivityManager.TYPE_MOBILE || dataType == ConnectivityManager.TYPE_WIFI)
                        return true;

                default:
                    return false;
            }

        } else
            return false;
    }

    /**
     * A method to call the method of homeActivity receiveBackOnline
     * @param isNetworkAvailable a param has the boolean value of the isNetworkAvailable
     */
    public void callActivityNetworkChangeMethod(boolean isNetworkAvailable) {
        if(VcinchApplication.activity instanceof UserListActivity) {
            UserListActivity userListActivity = (UserListActivity) VcinchApplication.activity;
            userListActivity.receiveBackOnline(isNetworkAvailable);
            Log.d(TAG, "onReceive: internet");
        }
    }


    /**
     * A enum class to  define the connection type
     */
    public enum ConnectionType {
        MOBILE_DATA_ONLY, WIFI_ONLY, MOBILE_AND_WIFI;
    }

}
