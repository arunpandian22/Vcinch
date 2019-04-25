package me.arun.vcinch.userModule.adapter;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import me.arun.vcinch.entities.Datum;

/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 * <p>
 * Data-loading systems of an application or library can implement this interface to allow
 * {@code LiveData<PagedList>}s to be created.
 */
public class ItemDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource();
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    /**
     * a method to get live data source object
     * @return it returns the MutableLiveData object
     */
    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
