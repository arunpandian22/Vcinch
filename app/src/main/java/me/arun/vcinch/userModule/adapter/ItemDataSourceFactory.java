package me.arun.vcinch.userModule.adapter;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import me.arun.vcinch.entities.Datum;
/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public class ItemDataSourceFactory  extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Datum>> itemLiveDataSource = new MutableLiveData<>();
    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource();
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
