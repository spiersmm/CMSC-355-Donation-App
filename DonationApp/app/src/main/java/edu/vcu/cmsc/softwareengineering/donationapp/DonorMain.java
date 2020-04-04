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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DonorMain extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

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
		setContentView(R.layout.activity_donor_main);

		Button postItemButton = findViewById(R.id.postItem);

		postItemButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent postNewItem = new Intent(getApplicationContext(), postNewItem.class);
				startActivity(postNewItem);
			}
		});


		mRecylcerView = findViewById(R.id.recycler_view);
		mRecylcerView.setHasFixedSize(true);
		mRecylcerView.setLayoutManager(new LinearLayoutManager(this));

		mProgressCircle = findViewById(R.id.progress_circle);

		mUploads = new ArrayList<>();

		user = FirebaseAuth.getInstance().getCurrentUser();

		myDatabaseReference = FirebaseDatabase.getInstance().getReference("Item Info");

		myDatabaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
					//if(Objects.equals(postSnapshot.getKey(), user.getUid())) {
					  if(postSnapshot.getKey().equals(user.getUid())) {
						for (DataSnapshot postSnapshot2 : postSnapshot.getChildren()) {
							newItemInfo newItem = postSnapshot2.getValue(newItemInfo.class);
							mUploads.add(newItem);
						}
					}
				}
				mAdapter = new ImageAdapter(DonorMain.this, mUploads);
				mRecylcerView.setAdapter(mAdapter);
				mAdapter.setOnItemClickListener(DonorMain.this);
				mProgressCircle.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				Toast.makeText(DonorMain.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
				mProgressCircle.setVisibility(View.INVISIBLE);
			}
		});

	}

	@Override
	public void onItemClick(int position) {
		Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEditClick(int position) {
		Toast.makeText(this, "Edit click at position: " + position, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDeleteClick(int position) {
		Toast.makeText(this, "Delete click at position: " + position, Toast.LENGTH_SHORT).show();
	}
}

//Keeping all the comment below here, but commented out, in case I need to come back to it
		/* ValueEventListener eventListener = new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				 List<String> itemList = new ArrayList<>();
				for (DataSnapshot ds : dataSnapshot.getChildren()) {
					// only display data from user that is currently logged in
					if(ds.getKey().equals(user.getUid())) {
						for (DataSnapshot ds2 : ds.getChildren()) {
							String description = ds2.child("itemDescription").getValue(String.class);
							String category = ds2.child("itemCategory").getValue(String.class);
							String condition = ds2.child("itemCondition").getValue(String.class);
							String deliveryMethod = ds2.child("itemDeliveryMethod").getValue(String.class);
							String quantity = ds2.child("itemQuantity").getValue(String.class);
							String imageUrl = ds2.child("itemImageUrl").getValue(String.class);
							itemList.add("Description: " + description +
									", Category: " + category +
									", Condition: " + condition +
									", Delivery Method: " + deliveryMethod +
									", Quantity: " + quantity +
                                    ", Image URL: " + imageUrl);

						}
					}
				}
				ListView listView = (ListView) findViewById(R.id.dmListView);
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DonorMain.this,android.R.layout.simple_list_item_1,itemList);
				listView.setAdapter(arrayAdapter);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {}


		};
		newItemInfoRef.addListenerForSingleValueEvent(eventListener);

		 */