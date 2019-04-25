package me.arun.vcinch.userModule;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
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
import me.arun.vcinch.utils.AppConstants;
import me.arun.vcinch.utils.networkreceiver.NetworkChangeReceiver;

/**
 * a Activity created to show the users list in the UserListActivity
 */
public class UserListActivity extends AppCompatActivity implements UserListContractor.View {

    private UserListViewModel viewModel;
    private UserListPresenter userListPresenter;
    String TAG = "UserListActivity";
    RelativeLayout rlInternetStatus,rlEmptyState;
    TextView tvInternetStatus,tvError,tvErrorDes;
    SwipeRefreshLayout srUserList;
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    ProgressBar progressBar;
    ArrayList<Datum> usersList = new ArrayList<>();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    NetworkChangeReceiver networkChangeReceiver=new NetworkChangeReceiver();
    boolean isFromNointernet=false;


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
                    if (((ModelEmptyErrorData) o).isProgressbar)
                        isProgressBarShow(true);
                    else
                        isProgressBarShow(false);

                    if (((ModelEmptyErrorData) o).isError){
                        setEmptyState(true,((ModelEmptyErrorData) o).Error);
                    }else
                        setEmptyState(false,((ModelEmptyErrorData) o).Error);
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
        viewModel.refresh();

    }

    @Override
    public void showInternetStateStrip() {

    }

    @Override
    public void isProgressBarShow(boolean isShow) {
        if (progressBar != null) {
            if (isShow)
                progressBar.setVisibility(View.VISIBLE);
            else {
                progressBar.setVisibility(View.GONE);
                if (srUserList!=null && srUserList.isRefreshing())
                    srUserList.setRefreshing(false);
            }
        }

    }


    /**
     * A method to initialize the all the view objects which are used in this current activity
     */
    public void uiInitialization() {
        recyclerView = findViewById(R.id.rvUsersList);
        rlInternetStatus = findViewById(R.id.rlInternetStatus);
        tvInternetStatus = findViewById(R.id.tvInternetStatus);
        progressBar = findViewById(R.id.pbUserList);
        rlEmptyState=findViewById(R.id.rlEmptyState);
        tvError=findViewById(R.id.tvError);
        tvErrorDes=findViewById(R.id.tvErrorDescription);
        srUserList=findViewById(R.id.srUserList);
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

        srUserList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
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
            isFromNointernet=true;
            rlInternetStatus.setVisibility(View.VISIBLE);
            rlInternetStatus.setBackgroundColor(getResources().getColor(R.color.offlineStripColor));
            tvInternetStatus.setText(getResources().getText(R.string.noInternetConnection));
        } else {

            if (isFromNointernet) {
                rlInternetStatus.setVisibility(View.VISIBLE);
                rlInternetStatus.setBackgroundColor(getResources().getColor(R.color.onlinebackStripColor));
                tvInternetStatus.setText(getResources().getText(R.string.onlineIsBack));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rlInternetStatus.setVisibility(View.GONE);
                        isFromNointernet=false;
                    }
                },5000);
            }

        }
    }

    /**
     * A method to show or hide the empty and error state
     * @param isShowErrorstate a boolean values to show error content
     * @param error a string value of the error state
     */
    public void setEmptyState(boolean isShowErrorstate,final String error)
    {
        if (isShowErrorstate) {
            if (recyclerView!=null&&recyclerView.getVisibility()==View.VISIBLE)
            recyclerView.setVisibility(View.GONE);
            if (rlEmptyState!=null&& rlEmptyState.getVisibility()==View.GONE)
                rlEmptyState.setVisibility(View.VISIBLE);
            switch (error){
                case  AppConstants.NETWORK_FAILURE:
                    tvError.setText(getResources().getText(R.string.oops));
                    tvErrorDes.setText(getResources().getText(R.string.noInterConnection));
                    break;
                case  AppConstants.EMPTY_DATA:
                    tvError.setText(getResources().getText(R.string.no_content_yet));
                    tvErrorDes.setText(getResources().getText(R.string.pleaseJoin));
                    break;
                case  AppConstants.API_FAILURE:
                    tvError.setText(getResources().getText(R.string.sorry));
                    tvErrorDes.setText(getResources().getText(R.string.something_went_wrong));
                    break;
                case AppConstants.NO_OFFLINE:
                    tvError.setText(getResources().getText(R.string.no_offline_yet));
                    tvErrorDes.setText(getResources().getText(R.string.no_offline_data_des));
            }
        } else {
            if (recyclerView!=null&&recyclerView.getVisibility()==View.GONE)
                recyclerView.setVisibility(View.VISIBLE);
            if (rlEmptyState!=null&& rlEmptyState.getVisibility()==View.VISIBLE)
                rlEmptyState.setVisibility(View.GONE);
        }
    }
}
