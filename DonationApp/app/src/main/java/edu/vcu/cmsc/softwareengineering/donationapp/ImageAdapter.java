package edu.vcu.cmsc.softwareengineering.donationapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<newItemInfo> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context, List<newItemInfo> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        newItemInfo itemCurrent = mUploads.get(position);
        holder.textViewName.setText(itemCurrent.getItemDescription());
        Picasso.get()
                .load(itemCurrent.getItemImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public List<newItemInfo> getmUploads() {
        return mUploads;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);    // ImageAdapter.OnItemClickListener (1)
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = user.getEmail();
            // change menu based on whether user is a donor or a recipient
            if(email.contains(".org")) {
                menu.setHeaderTitle("Select Action");
                MenuItem editClick = menu.add(Menu.NONE, 1, 1, "Favorite Item");
                MenuItem deleteClick = menu.add(Menu.NONE, 2, 2, "Message Donor");
                MenuItem markClick = menu.add(Menu.NONE, 3, 3, "Mark Item as Received");

                editClick.setOnMenuItemClickListener(this);
                deleteClick.setOnMenuItemClickListener(this);
                markClick.setOnMenuItemClickListener(this);
            }
            else {
                menu.setHeaderTitle("Select Action");
                MenuItem editClick = menu.add(Menu.NONE, 1, 1, "Edit Item");
                MenuItem deleteClick = menu.add(Menu.NONE, 2, 2, "Delete Item");
                MenuItem markClick = menu.add(Menu.NONE, 3, 3, "Mark Item as Donated");

                editClick.setOnMenuItemClickListener(this);
                deleteClick.setOnMenuItemClickListener(this);
                markClick.setOnMenuItemClickListener(this);
            }
        }


        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onEditClick(position);    // ImageAdapter.OnItemClickListener (2)
                            break;

                       case 2:
                           mListener.onDeleteClick(position);   // ImageAdapter.OnItemClickListener (3)
                           break;

                        case 3:
                            mListener.onMarkClick(position);    // ImageAdapter.OnItemClickListener (4)
                            break;
                    }
                }
            }
                    return false;
                }
            }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditClick(int position);

        void onDeleteClick(int position);

        void onMarkClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void updateData(List<newItemInfo> filteredData) {
        mUploads = filteredData;
        notifyDataSetChanged();
    }

}
