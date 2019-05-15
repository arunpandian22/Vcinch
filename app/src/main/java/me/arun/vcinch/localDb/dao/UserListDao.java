package me.arun.vcinch.localDb.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import me.arun.vcinch.entities.UserList;

/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */

/**
 * A dao class to UserList table to manipulate the basic query
 */

@Dao
public interface UserListDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(UserList... userLists);
    @Delete
    void delete(UserList... userLists);

    @Query("Delete  FROM UserList WHERE mId== :id")
    void delete(int id);

    @Query("Select * FROM UserList Where mPage==:page")
    UserList getUsers(String page);

}
