package me.arun.vcinch;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.entities.UserList;
import me.arun.vcinch.localDb.AppDataBase;
import me.arun.vcinch.localDb.dao.UserListDao;

/**
 * Created by Arun Pandian M on 25/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */

@RunWith(AndroidJUnit4.class)
public class RoomDbDaoTest {

    private UserListDao userListDao;

    @Before
    public void  setup() {
        userListDao = AppDataBase.getTestAppDatabase(InstrumentationRegistry.getTargetContext()).userListDao();
    }

    /**
     * A method to test the basic insert query check
     */
    @Test
    public void insertData()
    {
        UserList userList=new UserList();
        userList.setPage(1l);
        ArrayList<Datum> datumArrayList=new ArrayList<>();
        Datum firstdatum=new Datum();
        firstdatum.setFirstName("George");
        datumArrayList.add(firstdatum);
        userList.setData(datumArrayList);
        if (userListDao!=null)
            userListDao.insert(userList);
        UserList userListTest=userListDao.getUsers(""+1);
        Assert.assertEquals(userList.getData().get(0).mFirstName, userListTest.getData().get(0).mFirstName);
    }



}
