package me.arun.vcinch.di.component;

import dagger.Component;
import me.arun.vcinch.userModule.UserListActivity;
import me.arun.vcinch.di.PerActivity;
import me.arun.vcinch.di.module.ActivityModule;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent
{
    void inject(UserListActivity userListActivity);
}
