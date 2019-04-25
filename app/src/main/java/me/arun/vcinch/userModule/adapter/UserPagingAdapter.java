package me.arun.vcinch.userModule.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
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

/**
 * A adapter class to paging adapter for the paging library
 */
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


    /**
     *  Callback for calculating the diff between two non-null items in a list.
     */
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


    /**
     * A view holder for the paging adapter
     */
    public class UserViewHolder  extends RecyclerView.ViewHolder  {
        public TextView id, name;
        public CircularImageView profileImage;

        public UserViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.tvUsername);
            name =  view.findViewById(R.id.tvUserId);
            profileImage = view.findViewById(R.id.ivProfile);
        }


        /**
         * A method to set hte values in the item view
         * @param datum a object which is used to set the value in the item view
         */
        void bindTo(Datum datum)
        {
            if (datum!=null){
                Log.d("", "onBindViewHolder: ");
                Log.d(TAG, "bindTo: ");
               id.setText("Id: "+datum.getId());
              name.setText("Name: "+datum.getFirstName());
              if (!TextUtils.isEmpty(datum.getAvatar()))
                Glide.with(context)
                        .load(datum.getAvatar()).placeholder(context.getDrawable(R.drawable.ic_user_place_holder))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(profileImage);
              else
                  profileImage.setBackgroundDrawable(context.getDrawable(R.drawable.ic_user_place_holder));
            }

        }

        /**
         * A method to invalidate the views of item when the Datum object is null
         */
        void clear()
        {
            itemView.invalidate();
           id.invalidate();
           name.invalidate();
           profileImage.invalidate();
        }
    }
}
