package me.arun.vcinch.userModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.arun.vcinch.R;
import me.arun.vcinch.model.Datum;
import me.arun.vcinch.model.UserList;
import me.arun.vcinch.userModule.adapter.UserPagingAdapter;
import me.arun.vcinch.userModule.adapter.UsersAdapter;

public class UserListActivity extends AppCompatActivity implements UserListContractor.View {

    private UserListViewModel viewModel;
    private UserListPresenter userListPresenter;
    String TAG = "UserListActivity";
    RelativeLayout rlInternetStatus;
    TextView tvInternetStatus;
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    ProgressBar progressBar;
    ArrayList<Datum> usersList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersAdapter=new UsersAdapter(usersList,this);
        uiInitialization();

        userListPresenter = new UserListPresenter(this, viewModel);
//        userListPresenter.requestUserListData(1);

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
        if (progressBar!=null){
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
        progressBar=findViewById(R.id.pbUserList);
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
}
