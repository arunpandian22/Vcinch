package me.arun.vcinch.userModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amitshekhar.DebugDB;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.arun.vcinch.R;
import me.arun.vcinch.VcinchApplication;
import me.arun.vcinch.data.ModelEmptyErrorData;
import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.entities.UserList;
import me.arun.vcinch.userModule.adapter.UserPagingAdapter;
import me.arun.vcinch.userModule.adapter.UsersAdapter;
import me.arun.vcinch.utils.networkreceiver.NetworkChangeReceiver;

public class UserListActivity extends AppCompatActivity implements UserListContractor.View {

    private UserListViewModel viewModel;
    private UserListPresenter userListPresenter;
    String TAG = "UserListActivity";
    RelativeLayout rlInternetStatus;
    TextView tvInternetStatus;
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    ProgressBar progressBar;
    ArrayList<Datum> usersList = new ArrayList<>();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    NetworkChangeReceiver networkChangeReceiver=new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersAdapter = new UsersAdapter(usersList, this);
        uiInitialization();
        userListPresenter = new UserListPresenter(this, viewModel);
        Log.d(TAG, "onCreate: DbAdrees"+ DebugDB.getAddressLog());
//        userListPresenter.requestUserListData(1);

       compositeDisposable.add(VcinchApplication.rxBus.toObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o)
            {
                if (o instanceof ModelEmptyErrorData)
                {

                }
                else{

                }
            }
        }));

    }

    @Override
    public void loadUserListAdapter(UserList userListRes) {
        Log.d(TAG, "loadUserListAdapter: " + userListRes.getPage());
        usersAdapter.setUserList(userListRes.getData());
    }

    @Override
    public void showErrorState(Throwable e) {
        Log.d(TAG, "showErrorState: " + e.getMessage());
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void showInternetStateStrip() {

    }

    @Override
    public void isProgressBarShow(boolean isShow) {
        if (progressBar != null) {
            if (isShow)
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.GONE);
        }

    }


    public void uiInitialization() {
        recyclerView = findViewById(R.id.rvUsersList);
        rlInternetStatus = findViewById(R.id.rlInternetStatus);
        tvInternetStatus = findViewById(R.id.tvInternetStatus);
        progressBar = findViewById(R.id.pbUserList);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        viewModel = ViewModelProviders.of(this).get(UserListViewModel.class);
//        recyclerView.setAdapter(usersAdapter);
        //Paging Library's ItemAdapter
        final UserPagingAdapter adapter = new UserPagingAdapter(this);

        //observer for item pageList
        viewModel.usersList.observe(this, new Observer<PagedList<Datum>>() {
            @Override
            public void onChanged(@Nullable PagedList<Datum> items) {
                adapter.submitList(items);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        this.registerReceiver(networkChangeReceiver, filter);
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }


    /**
     * A method to receive the network changes
     * @param isOnline a param has the boolean value of the whether the network is availability or not
     */
    public void receiveBackOnline(boolean isOnline) {
        Log.d(TAG, "receiveBackOnline: " + isOnline);
        if (!isOnline) {
            rlInternetStatus.setBackgroundColor(getResources().getColor(R.color.offlineStripColor));
            tvInternetStatus.setText(getResources().getText(R.string.noInternetConnection));
        } else {
            rlInternetStatus.setBackgroundColor(getResources().getColor(R.color.onlinebackStripColor));
            tvInternetStatus.setText(getResources().getText(R.string.onlineIsBack));
        }


    }
}
