package me.arun.vcinch.di.component;
import android.content.Context;
import javax.inject.Singleton;
import dagger.Component;
import me.arun.vcinch.VcinchApplication;
import me.arun.vcinch.di.ApplicationContext;
import me.arun.vcinch.di.module.ApplicationModule;
import retrofit2.Retrofit;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent
{
    void inject(VcinchApplication vcinchApplication);
    @ApplicationContext
    Context getContext();
}