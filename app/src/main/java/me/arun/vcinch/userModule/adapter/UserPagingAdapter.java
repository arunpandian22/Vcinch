package me.arun.vcinch.userModule.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import me.arun.vcinch.R;
import me.arun.vcinch.entities.Datum;
import me.arun.vcinch.utils.CircularImageView;


public class UserPagingAdapter extends PagedListAdapter<Datum,UserPagingAdapter.UserViewHolder>
{
   Context context;
   String TAG="UserPagingAdapter";
    public UserPagingAdapter(Context context)
    {
        super(DIFF_CALLBACK);
        this.context=context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position)
    {
        Datum datum = getItem(position);

        if (datum != null)
        {
            holder.bindTo(datum);
        } else {
            holder.clear();
        }
    }



    public static final DiffUtil.ItemCallback<Datum> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Datum>()
            {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Datum oldUser, @NonNull Datum newUser)
                {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getId().equals(newUser.getId()) ;
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Datum oldUser, @NonNull Datum newUser)
                {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    @SuppressLint("DiffUtilEquals") boolean equals = oldUser.equals(newUser);
                    return equals;
                }
            };


    public class UserViewHolder  extends RecyclerView.ViewHolder  {
        public TextView id, name;
        public CircularImageView profileImage;

        public UserViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.tvUsername);
            name =  view.findViewById(R.id.tvUserId);
            profileImage = view.findViewById(R.id.ivProfile);
        }


        void bindTo(Datum datum)
        {
            if (datum!=null){
                Log.d("", "onBindViewHolder: ");
                Log.d(TAG, "bindTo: ");
               id.setText("Id: "+datum.getId());
              name.setText("Name: "+datum.getFirstName());
                Glide.with(context)
                        .load(datum.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(profileImage);
            }

        }

        void clear()
        {
            itemView.invalidate();
           id.invalidate();
           name.invalidate();
           profileImage.invalidate();
        }
    }
}
