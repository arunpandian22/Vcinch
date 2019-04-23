package me.arun.vcinch.di.module;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import dagger.Module;
import dagger.Provides;
import me.arun.vcinch.VcinchApplication;
import me.arun.vcinch.di.ApplicationContext;
import retrofit2.Retrofit;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
@Module
public class ApplicationModule {

    public String TAG="ApplicationModule";

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication()
    {
        return mApplication;
    }

    @Provides
    VcinchApplication getApplication()
    {
        return (VcinchApplication) mApplication;
    }
}
