package me.arun.vcinch;

import android.app.Application;
import android.content.Context;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public class VcinchApplication extends Application {
    private static VcinchApplication mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;

    }


    public static VcinchApplication getContext() {
        return mContext;
    }



}
