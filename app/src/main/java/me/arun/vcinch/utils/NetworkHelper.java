package me.arun.vcinch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * A class is created to check the internet status
 */

public class NetworkHelper {

    public NetworkHelper() {
    }

    /**
     * A method to check the whether network is available or not
     *
     * @param context a param has the context of the current Activity
     * @return it returns true if the network is available
     */
    public static boolean isNetworkAvailable(Context context) {

        if (isConnectedWiFi(context))
            return true;
        else {
            if (isConnectedMobile(context))
                return true;
            else
                return false;
        }
    }


    /**
     * A method to Check whetehr the Mobile network is available or not
     *
     * @param context a param has the context of the current Activity
     * @return it returns true if the mobile network is available
     */
    public static boolean isConnectedMobile(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            return true;
        else
            return false;
    }


    /**
     * A method to Check whether  the WiFi network is available or not
     *
     * @param context a param has the context of the current Activity
     * @return it returns true if the WiFi network is available
     */
    public static boolean isConnectedWiFi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
            return true;
        else
            return false;
    }

}