
package me.arun.vcinch.entities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import me.arun.vcinch.localDb.Converters;

/**
 * A Model class for the UserList table
 */
@Entity
public class UserList {

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    @SerializedName("id")
    public Long mId;


    @SerializedName("data")
    @TypeConverters({Converters.class})
    public ArrayList<Datum> mData;

    @NonNull
    @PrimaryKey
    @SerializedName("page")
    public Long mPage;
    @SerializedName("per_page")
    public Long mPerPage;
    @SerializedName("total")
    public Long mTotal;
    @SerializedName("total_pages")
    public Long mTotalPages;

    public List<Datum> getData() {
        return mData;
    }

    public void setData(ArrayList<Datum> data) {
        mData = data;
    }

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public Long getPerPage() {
        return mPerPage;
    }

    public void setPerPage(Long perPage) {
        mPerPage = perPage;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

}
