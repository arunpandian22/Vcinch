package me.arun.vcinch.localDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.entities.UserList;
import me.arun.vcinch.localDb.dao.UserDao;
import me.arun.vcinch.localDb.dao.UserListDao;

/**
 * Created by Arun Pandian M on 24/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */

/**
 * A abstract class is created get the Datbase instance
 */
@Database(entities = {UserList.class, Datum.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance;
    public abstract UserListDao userListDao();
    public abstract UserDao userDao();
    public static AppDataBase getAppDatabase(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class,
                    "usersdb")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    //        .addMigrations(MIGRATION_1_2,MIGRATION_2_3)

}
