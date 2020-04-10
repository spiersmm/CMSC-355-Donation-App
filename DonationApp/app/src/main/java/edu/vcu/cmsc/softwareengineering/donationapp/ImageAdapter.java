package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem editClick = menu.add(Menu.NONE, 1, 1, "Edit Item");
            MenuItem deleteClick = menu.add(Menu.NONE, 2, 2, "Delete Item");

            editClick.setOnMenuItemClickListener(this);
            deleteClick.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String description, category, condition, quantity, delivery;
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onEditClick(position);
                            newItemInfo itemCurrent = mUploads.get(position);
                            Intent editItem = new Intent(mContext.getApplicationContext(), postNewItem.class);
                            editItem.putExtra("description", itemCurrent.getItemDescription());
                            editItem.putExtra("category", itemCurrent.getItemCategory());
                            editItem.putExtra("delivery", itemCurrent.getItemDeliveryMethod());
                            editItem.putExtra("condition", itemCurrent.getItemCondition());
                            editItem.putExtra("quantity", itemCurrent.getItemQuantity());
                            editItem.putExtra("image", itemCurrent.getItemImageUrl());
                            mContext.startActivity(editItem);
                        case 2:
                            // remove item that was clicked
                            mListener.onDeleteClick(position);
                            itemCurrent = mUploads.get(position);
                            FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
                            StorageReference deleteImage = mFirebaseStorage.getReferenceFromUrl(itemCurrent.getItemImageUrl());
                            deleteImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void a) {
                                    Log.e("Picture","#deleted");
                                }
                            });

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Query itemQueury = ref.child("Item Info").child(user.getUid()).orderByChild("itemDescription").equalTo(itemCurrent.getItemDescription());

                            itemQueury.addListenerForSingleValueEvent(new ValueEventListener() {
                                private static final String TAG = "item";

                                @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                    ds.getRef().removeValue();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e(TAG, "onCancelled", databaseError.toException());
                            }
                            });



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
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
