package me.arun.vcinch.localDb.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.entities.UserList;

/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */

/**
 * A dao class to User table to manipulate the basic query
 */
@Dao
public interface UserDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Datum... users);
    @Delete
    void delete(Datum... userLists);

    @Query("Delete  FROM Datum WHERE mId== :id")
    void delete(int id);

    @Query("Select * FROM Datum Where mId==:id")
    Datum get(String id);
}
