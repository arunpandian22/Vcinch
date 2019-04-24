package me.arun.vcinch.userModule.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;
import me.arun.vcinch.R;
import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.utils.CircularImageView;

/**
 * Created by Arun Pandian M on 23/April/2019
 * arunsachin222@gmail.com
 * Chennai
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{
    private List<Datum> userList;
    private Activity activity;
    String TAG="UsersAdapter";

    public void setUserList(List<Datum> userList) {
        this.userList = userList;
        Log.d(TAG, "setUserList: "+userList.size());
        notifyDataSetChanged();
    }

    public UsersAdapter(List<Datum> userList, Activity activity) {
        this.userList = userList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Datum datum=userList.get(position);
        if (datum!=null){
            Log.d(TAG, "onBindViewHolder: ");
            holder.id.setText("Id: "+datum.getId());
            holder.name.setText("Id: "+datum.getFirstName());
            Glide.with(activity)
                    .load(datum.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.profileImage);
        }

    }

    @Override
    public void onViewRecycled(@NonNull UserViewHolder holder) {
        super.onViewRecycled(holder);

        if (activity!=null && holder.profileImage!=null) {
            Glide.with(activity).clear(holder.profileImage);
        }
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+userList.size());
        return userList.size();
    }

    public class UserViewHolder  extends RecyclerView.ViewHolder  {
        public TextView id, name;
        public CircularImageView profileImage;

        public UserViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.tvUsername);
            name =  view.findViewById(R.id.tvUserId);
            profileImage = view.findViewById(R.id.ivProfile);
        }
    }
}
