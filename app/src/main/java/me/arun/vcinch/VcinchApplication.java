package me.arun.vcinch;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import me.arun.vcinch.localDb.AppDataBase;
import me.arun.vcinch.utils.RxBusUtil;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 * A application class of the activity. here I defined the object which is has to accessed by all the classes
 */
public class VcinchApplication extends Application {
    private static VcinchApplication mContext;
    public static Activity activity;
    public static AppDataBase appDataBase;
    public static RxBusUtil rxBus;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        appDataBase=AppDataBase.getAppDatabase(getApplicationContext());
        rxBus = new RxBusUtil();
        setActivityRegister();
    }


    public static VcinchApplication getContext() {
        return mContext;
    }

    public static AppDataBase  getAppDataBase(){
        return appDataBase;
    }



    /**
     * A method to register the Activity
     */
    public void setActivityRegister() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity currentActivity, Bundle bundle) {
                activity = currentActivity;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity currentActivity) {
                activity = currentActivity;

            }

            @Override
            public void onActivityPaused(Activity currentActivity) {
                activity = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });


    }

    public RxBusUtil bus() {
        return rxBus;
    }
}
