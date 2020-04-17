// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DonorMainPastPostings extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecylcerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;


    private DatabaseReference myDatabaseReference;
    public List<newItemInfo> mUploads;

    ListView listView;
    newItemInfo info;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_main_past_postings);


        Button currentPostsButton = findViewById(R.id.goToCurrentPostings);


        currentPostsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent currentPostings = new Intent(getApplicationContext(), DonorMain.class);

                startActivity(currentPostings);
            }
        });


        mRecylcerView = findViewById(R.id.recycler_view_past);
        mRecylcerView.setHasFixedSize(true);
        mRecylcerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle_past);

        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter(DonorMainPastPostings.this, mUploads);
        mRecylcerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DonorMainPastPostings.this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Donated Item Info");

        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    //if(Objects.equals(postSnapshot.getKey(), user.getUid())) {
                    if(postSnapshot.getKey().equals(user.getUid())) {
                        for (DataSnapshot postSnapshot2 : postSnapshot.getChildren()) {
                            newItemInfo newItem = postSnapshot2.getValue(newItemInfo.class);
                            mUploads.add(newItem);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DonorMainPastPostings.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }



    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        newItemInfo itemCurrent = mUploads.get(position);
        Intent seeItemDetails = new Intent(getApplicationContext(), MoreDonatedInfo.class);
        seeItemDetails.putExtra("description", itemCurrent.getItemDescription());
        seeItemDetails.putExtra("category", itemCurrent.getItemCategory());
        seeItemDetails.putExtra("delivery", itemCurrent.getItemDeliveryMethod());
        seeItemDetails.putExtra("condition", itemCurrent.getItemCondition());
        seeItemDetails.putExtra("quantity", itemCurrent.getItemQuantity());
        seeItemDetails.putExtra("image", itemCurrent.getItemImageUrl());
        seeItemDetails.putExtra("recipientName", itemCurrent.getRecipientName());
        startActivity(seeItemDetails);
    }
    // donor edit item
    @Override
    public void onEditClick(int position) {
        Toast.makeText(this, "Cannot Edit a Finalized Item", Toast.LENGTH_SHORT).show();

    }
    // donor delete item
    @Override
    public void onDeleteClick(int position) {
        Toast.makeText(this, "Delete click at position: " + position, Toast.LENGTH_SHORT).show();
        newItemInfo itemCurrent = mUploads.get(position);
        itemCurrent = mUploads.get(position);
        FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
        // delete image
        StorageReference deleteImage = mFirebaseStorage.getReferenceFromUrl(itemCurrent.getItemImageUrl());
        deleteImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void a) {
                Log.e("Picture","#deleted");
            }
        });
        // delete item
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query itemQueury = ref.child("Donated Item Info").child(user.getUid()).orderByChild("itemDescription").equalTo(itemCurrent.getItemDescription());

        itemQueury.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "item";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

    }
    // donor mark item as donated
    @Override
    public void onMarkClick(int position) {
        Toast.makeText(this, "Item has already been donated", Toast.LENGTH_SHORT).show();
    }
}

