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
		Button pastPostsButton = findViewById(R.id.goToPastPostingsButton);

		postItemButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent postNewItem = new Intent(getApplicationContext(), postNewItem.class);
				postNewItem.putExtra("category", "Category");
				postNewItem.putExtra("delivery", "Delivery Method");
				postNewItem.putExtra("condition", "Condition");
				postNewItem.putExtra("quantity", "Quantity");
				postNewItem.putExtra("image", "noImage");
				startActivity(postNewItem);
			}
		});



        pastPostsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent pastPostings = new Intent(getApplicationContext(), DonorMainPastPostings.class);

                startActivity(pastPostings);
            }
        });


		mRecylcerView = findViewById(R.id.recycler_view);
		mRecylcerView.setHasFixedSize(true);
		mRecylcerView.setLayoutManager(new LinearLayoutManager(this));

		mProgressCircle = findViewById(R.id.progress_circle);

		mUploads = new ArrayList<>();

		mAdapter = new ImageAdapter(DonorMain.this, mUploads);
		mRecylcerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(DonorMain.this);

		user = FirebaseAuth.getInstance().getCurrentUser();

		myDatabaseReference = FirebaseDatabase.getInstance().getReference("Item Info");

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
				Toast.makeText(DonorMain.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
				mProgressCircle.setVisibility(View.INVISIBLE);
			}
		});

	}



	@Override
	public void onItemClick(int position) {
		Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
		newItemInfo itemCurrent = mUploads.get(position);
		Intent seeItemDetails = new Intent(getApplicationContext(), MoreInfo.class);
		seeItemDetails.putExtra("description", itemCurrent.getItemDescription());
		seeItemDetails.putExtra("category", itemCurrent.getItemCategory());
		seeItemDetails.putExtra("delivery", itemCurrent.getItemDeliveryMethod());
		seeItemDetails.putExtra("condition", itemCurrent.getItemCondition());
		seeItemDetails.putExtra("quantity", itemCurrent.getItemQuantity());
		seeItemDetails.putExtra("image", itemCurrent.getItemImageUrl());
		startActivity(seeItemDetails);
	}
	// donor edit item
	@Override
	public void onEditClick(int position) {
		Toast.makeText(this, "Edit click at position: " + position, Toast.LENGTH_SHORT).show();
		newItemInfo itemCurrent = mUploads.get(position);
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		// find item in database
		Query itemQueury = ref.child("Item Info").child(user.getUid()).orderByChild("itemDescription").equalTo(itemCurrent.getItemDescription());
		// delete old item
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
		// go to postNewItem activity to edit item
		// should be able to see image and the original item details
		Intent editItem = new Intent(getApplicationContext(), postNewItem.class);
		editItem.putExtra("description", itemCurrent.getItemDescription());
		editItem.putExtra("category", itemCurrent.getItemCategory());
		editItem.putExtra("delivery", itemCurrent.getItemDeliveryMethod());
		editItem.putExtra("condition", itemCurrent.getItemCondition());
		editItem.putExtra("quantity", itemCurrent.getItemQuantity());
		editItem.putExtra("image", itemCurrent.getItemImageUrl());
		startActivity(editItem);
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
		Query itemQueury = ref.child("Item Info").child(user.getUid()).orderByChild("itemDescription").equalTo(itemCurrent.getItemDescription());

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
		Toast.makeText(this, "Item has been donated " + position, Toast.LENGTH_SHORT).show();
        newItemInfo itemCurrent = mUploads.get(position);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // find item in database
        Query itemQueury = ref.child("Item Info").child(user.getUid()).orderByChild("itemDescription").equalTo(itemCurrent.getItemDescription());
        // delete old item
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
        // go to postNewItem activity to edit item
        // should be able to see image and the original item details
        Intent markItemAsDonated = new Intent(getApplicationContext(), markItemAsDonated.class);
        markItemAsDonated.putExtra("description", itemCurrent.getItemDescription());
        markItemAsDonated.putExtra("category", itemCurrent.getItemCategory());
        markItemAsDonated.putExtra("delivery", itemCurrent.getItemDeliveryMethod());
        markItemAsDonated.putExtra("condition", itemCurrent.getItemCondition());
        markItemAsDonated.putExtra("quantity", itemCurrent.getItemQuantity());
        markItemAsDonated.putExtra("image", itemCurrent.getItemImageUrl());
        startActivity(markItemAsDonated);
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