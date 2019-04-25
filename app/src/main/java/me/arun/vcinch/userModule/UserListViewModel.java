package me.arun.vcinch.userModule;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.userModule.adapter.ItemDataSourceFactory;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 * A view model for the UsersList activity.
 * A view model is normally responsible for preparing data and managing the data for activity
 */
public class UserListViewModel  extends AndroidViewModel {

    private static final int INITIAL_LOAD_KEY = 0;
    private static final int PAGE_SIZE = 10;
    private static final int PREFETCH_DISTANCE = 5;

    public LiveData<PagedList<Datum>> usersList=null;
    ItemDataSourceFactory itemDataSource;

    public UserListViewModel(@NonNull Application application) {
        super(application);
     setPagination();
    }

     void setPagination()
    {
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(3)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(3)
                .build();
        itemDataSource=new ItemDataSourceFactory();
        usersList = new LivePagedListBuilder<>(itemDataSource, config).build();
    }

    public void refresh() {

        itemDataSource.getItemLiveDataSource().getValue().invalidate();
    }
}
