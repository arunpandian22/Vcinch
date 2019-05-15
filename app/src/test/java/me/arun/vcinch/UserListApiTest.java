package me.arun.vcinch;
import android.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.entities.UserList;
import me.arun.vcinch.service.Api;
import me.arun.vcinch.service.ApiInterface;
import me.arun.vcinch.userModule.UserListActivity;
import me.arun.vcinch.userModule.UserListContractor;
import me.arun.vcinch.userModule.UserListPresenter;
import me.arun.vcinch.userModule.UserListViewModel;
import static org.mockito.Mockito.when;

/**
 * Created by Arun Pandian M on 25/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
@RunWith(MockitoJUnitRunner.class)
public class UserListApiTest {

    @Mock
    UserListContractor.View view;

    @Mock
    UserListViewModel userListViewModel;

    UserListPresenter userListPresenter;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userListPresenter=new UserListPresenter(view,userListViewModel);
    }

    @Test
    public void testUserListGet()
    {

        UserList userList = Mockito.mock(UserList.class);

        ApiInterface apiInterface=Mockito.mock(ApiInterface.class);

        when(apiInterface.getUserList("users",1)).thenReturn(Single.just(userList));

        userListPresenter.requestUserListData(1);

        view.loadUserListAdapter(userList);

        // Validation wheather Response Passed or not!
        Mockito.verify(view, Mockito.times(1)).loadUserListAdapter(userList);

    }

    @Test

    public void testUserListGetFailed(){
        Exception exception = new Exception();
        UserList userList = Mockito.mock(UserList.class);
        ApiInterface apiInterface=Mockito.mock(ApiInterface.class);

        when(apiInterface.getUserList("users",1))
                .thenReturn(Single.<UserList>error(exception));

        userListPresenter.requestUserListData(1);

        view.showErrorState(exception);

        // Validation wheather Response failed or not!
        Mockito.verify(view, Mockito.times(1)).showErrorState(exception);

    }

}
