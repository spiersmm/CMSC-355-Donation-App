// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import okhttp3.internal.cache.DiskLruCache;

public class RecipientMain extends AppCompatActivity implements ImageAdapter.OnItemClickListener{

	private String SelectedItemRecord;
	private List<List<String>> filters;

	private RecyclerView mRecylcerView;
	private ImageAdapter mAdapter;
	private ProgressBar progressCircle;


	private DatabaseReference myDatabaseReference;
	public List<newItemInfo> mUploads;

	ListView listView;
	newItemInfo info;
	FirebaseUser user;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipient_main);


	createItemRecordSpinner();
	setFilters();

	/*
	Recipients item listing 'rmRecyclerView'
	 */

		mRecylcerView = findViewById(R.id.recycler_view);
		mRecylcerView.setHasFixedSize(true);
		mRecylcerView.setLayoutManager(new LinearLayoutManager(this));

		progressCircle = findViewById(R.id.progressCircleR);

		mUploads = new ArrayList<>();

		mAdapter = new ImageAdapter(RecipientMain.this, mUploads);
		mRecylcerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(RecipientMain.this);

		user = FirebaseAuth.getInstance().getCurrentUser();


		myDatabaseReference = FirebaseDatabase.getInstance().getReference("Item Info");

		myDatabaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

				mUploads.clear();

				for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
					//if(Objects.equals(postSnapshot.getKey(), user.getUid())) {
//					if(postSnapshot.getKey().equals(user.getUid())) {	// this if will filter for the specific logged in user
						for (DataSnapshot postSnapshot2 : postSnapshot.getChildren()) {
							newItemInfo newItem = postSnapshot2.getValue(newItemInfo.class);
							mUploads.add(newItem);
						}
//					}
				}

				mAdapter.notifyDataSetChanged();

				progressCircle.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				Toast.makeText(RecipientMain.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
				progressCircle.setVisibility(View.INVISIBLE);
			}
		});



	} // end onCreate()

	// when click on an item, show more item details
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


	// recipient favorite item
	@Override
	public void onEditClick(int position) {
		Toast.makeText(this, "Favorite Item " + position, Toast.LENGTH_SHORT).show();
	}


	// recipient message donor
	@Override
	public void onDeleteClick(int position) {
		Toast.makeText(this, "Donor was notified " + position, Toast.LENGTH_SHORT).show();
		newItemInfo itemCurrent = mUploads.get(position);
		String email = itemCurrent.getEmail();
		Intent message = new Intent(getApplicationContext(), SendMessage.class);
		message.putExtra("donorEmail", email);
		startActivity(message);
	}
	// recipient mark item as received
	@Override
	public void onMarkClick(int position) {
		Toast.makeText(this, "Item has been received " + position, Toast.LENGTH_SHORT).show();
	}

	/**
	 *  method to create spinner for item record
	 *  to select from available, or received items.
	 */
	public void createItemRecordSpinner() {
		final Spinner itemRecord = findViewById(R.id.ItemRecord);

		List<String> itemRecordItems = new ArrayList<>();
			itemRecordItems.add("Available");
			itemRecordItems.add("Received");
			itemRecordItems.add("Favorited");

		ArrayAdapter<String> itemRecordAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemRecordItems);
			itemRecordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			itemRecord.setAdapter(itemRecordAdapter);

			itemRecord.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					/* TODO */
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					/* TODO */
				}
			});
	} // end createItemRecordSpinner()

	private void setFilters() {
		filters = new ArrayList<>();
		filters.add(newItemInfo.getCategoriesList());
		filters.add(newItemInfo.getConditionList());
		filters.add(null); //TODO filtering by date yet to be implemented
		filters.add(newItemInfo.getDeliveryMethodList());
	}

	/*
    Method for filter button and multiple choice popup dialog
    consists of inner methods: <li>.setMultiChoiceItems() method for the checkbox list</li>
    <li>.setPositivebutton() method for an 'ok' button</li>
    <li>.setNeutralButton() method for a 'cancel' button</li>
     */
	public void filterCategory(View view) {
		//state of the recycler before changing it
		final List<newItemInfo> dataSnapshot = mUploads;
		final List<newItemInfo> filteredList = new ArrayList<>();
		final String[] options = filters.get(0).toArray(new String[0]);    // options for the popup
		final List<String> categories = filters.get(0);
		final boolean[] checked = new boolean[8]; Arrays.fill(checked, false); // default state of checkboxes

		AlertDialog.Builder builder = new AlertDialog.Builder(this);    // the alert popup class
		builder.setTitle("Category Filter"); // set title of dialog


        /*
        Method for the actual multiple choice checkbox popup, containing onclick listener
        setMultiChoiceItems()
         */
		builder.setMultiChoiceItems(options, checked, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				checked[which] = isChecked;
				String category = categories.get(which);
				Toast.makeText(RecipientMain.this,category + " set to : " + isChecked, Toast.LENGTH_SHORT).show();

				if (isChecked) {
					for (newItemInfo item : dataSnapshot) {
						if (item.getItemCategory().equals(category)) {
							filteredList.add(item);
						}
					}
				} else {
					if (filteredList.size() == 0) {
						filteredList.addAll(dataSnapshot);
					} else {
						for (newItemInfo item : filteredList) {
							if (item.getItemCategory().equals(category)) {
								filteredList.remove(item);
							}
						}
					}
				}
			}
		});

        /*
        Method for the ok button and onclick handler
         */
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(RecipientMain.this,"ok button was clicked", Toast.LENGTH_SHORT).show();
				if (filteredList.size() == 0) filteredList.addAll(dataSnapshot);
				mAdapter.updateData(filteredList);
			}
		});


        /*
        Method for the cancel button and onclick handler (neutral for now)
        there is also a setNegativeButton method
         */

		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(RecipientMain.this,"cancel button was clicked", Toast.LENGTH_SHORT).show();
			}
		});

		builder.show(); // method to show the popup dialog

	}



	/*
    Method for filter button and multiple choice popup dialog
    consists of inner .setMultiChoiceItems() method for the checkbox list
    .setPositivebutton() method for an 'ok' button
    .setNeutralButton() method for a 'cancel' button
     */
	public void filterCondition(View view) {

		//state of the recycler before changing it
		final List<newItemInfo> dataSnapshot = mUploads;
		final List<newItemInfo> filteredList = new ArrayList<>();
		final String[] options = filters.get(1).toArray(new String[1]);    // options for the popup
		final List<String> conditions = filters.get(1);
		final boolean[] checked = new boolean[3]; Arrays.fill(checked, false); // default state of checkboxes

		AlertDialog.Builder builder = new AlertDialog.Builder(this);    // the alert popup class
		builder.setTitle("Conditions Filter"); // set title of dialog


        /*
        Method for the actual multiple choice checkbox popup, containing onclick listener
        setMultiChoiceItems()
         */
		builder.setMultiChoiceItems(options, checked, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				checked[which] = isChecked;
				String category = conditions.get(which);
				Toast.makeText(RecipientMain.this,category + " set to : " + isChecked, Toast.LENGTH_SHORT).show();

				if (isChecked) {
					for (newItemInfo item : dataSnapshot) {
						if (item.getItemCondition().equals(category)) {
							filteredList.add(item);
						}
					}
				} else {
					if (filteredList.size() == 0) {
						filteredList.addAll(dataSnapshot);
					} else {
						for (newItemInfo item : filteredList) {
							if (item.getItemCondition().equals(category)) {
								filteredList.remove(item);
							}
						}
					}
				}
			}
		});

        /*
        Method for the ok button and onclick handler
         */
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(RecipientMain.this,"ok button was clicked", Toast.LENGTH_SHORT).show();
				if (filteredList.size() == 0) filteredList.addAll(dataSnapshot);
				mAdapter.updateData(filteredList);
			}
		});


        /*
        Method for the cancel button and onclick handler (neutral for now)
        there is also a setNegativeButton method
         */

		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(RecipientMain.this,"cancel button was clicked", Toast.LENGTH_SHORT).show();
			}
		});

		builder.show(); // method to show the popup dialog








//		AlertDialog.Builder builder = new AlertDialog.Builder(this);    // the alert popup class
//		builder.setTitle("Condition Filter"); // set title of dialog
//
//
//		final String[] options = { "Perfect", "Ok", "Poor" };    // options for the popup
//		final boolean[] checked = new boolean[] { false, false, false };   // default state of checkboxes
//		final List<String> list = Arrays.asList(options);
//
//        /*
//        Method for the actual multiple choice checkbox popup, containing onclick listener
//        setMultiChoiceItems()
//         */
//		builder.setMultiChoiceItems(options, checked, new DialogInterface.OnMultiChoiceClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//
//
//				checked[which] = isChecked;
//				String currentItem = list.get(which);
//
//				Toast.makeText(RecipientMain.this,currentItem + " set to : " + isChecked, Toast.LENGTH_SHORT).show();
//
//
//			}
//		});
//
//
//        /*
//        Method for the ok button and onclick handler
//         */
//		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(RecipientMain.this,"ok button was clicked", Toast.LENGTH_SHORT).show();
//			}
//		});
//
//
//        /*
//        Method for the cancel button and onclick handler (neutral for now)
//        there is also a setNegativeButton method
//         */
//
//		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(RecipientMain.this,"cancel button was clicked", Toast.LENGTH_SHORT).show();
//			}
//		});
//
//		builder.show(); // method to show the popup dialog

	}



	/*
    Method for filter button and multiple choice popup dialog
    consists of inner .setMultiChoiceItems() method for the checkbox list
    .setPositivebutton() method for an 'ok' button
    .setNeutralButton() method for a 'cancel' button
     */
	public void filterDate(View view) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);    // the alert popup class
		builder.setTitle("Date Filter"); // set title of dialog


		final String[] options = { "Within 5 days", "Within 10 days", "Within 30 days" };    // options for the popup
		final boolean[] checked = new boolean[] { false, false, false };   // default state of checkboxes
		final List<String> list = Arrays.asList(options);

        /*
        Method for the actual multiple choice checkbox popup, containing onlick listener
        setMultiChoiceItems()
         */
		builder.setMultiChoiceItems(options, checked, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {


				checked[which] = isChecked;
				String currentItem = list.get(which);

				Toast.makeText(RecipientMain.this,currentItem + " set to : " + isChecked, Toast.LENGTH_SHORT).show();


			}
		});


        /*
        Method for the ok button and onclick handler
         */
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(RecipientMain.this,"ok button was clicked", Toast.LENGTH_SHORT).show();
			}
		});


        /*
        Method for the cancel button and onclick handler (neutral for now)
        there is also a setNegativeButton method
         */

		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(RecipientMain.this,"cancel button was clicked", Toast.LENGTH_SHORT).show();
			}
		});

		builder.show(); // method to show the popup dialog

	}






	/*
    Method for filter button and multiple choice popup dialog
    consists of inner .setMultiChoiceItems() method for the checkbox list
    .setPositivebutton() method for an 'ok' button
    .setNeutralButton() method for a 'cancel' button
     */
	public void filterDelivery(View view) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);    // the alert popup class
		builder.setTitle("Delivery Method"); // set title of dialog


		final String[] options = { "Pickup", "Delivery" };    // options for the popup
		final boolean[] checked = new boolean[] { false, false };   // default state of checkboxes
		final List<String> list = Arrays.asList(options);

        /*
        Method for the actual multiple choice checkbox popup, containing onlick listener
        setMultiChoiceItems()
         */
		builder.setMultiChoiceItems(options, checked, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {

				checked[which] = isChecked;
				String currentItem = list.get(which);

				Toast.makeText(RecipientMain.this,currentItem + " set to : " + isChecked, Toast.LENGTH_SHORT).show();


			}
		});


        /*
        Method for the ok button and onclick handler
         */
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(RecipientMain.this,"ok button was clicked", Toast.LENGTH_SHORT).show();
			}
		});


        /*
        Method for the cancel button and onclick handler (neutral for now)
        there is also a setNegativeButton method
         */

		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(RecipientMain.this,"cancel button was clicked", Toast.LENGTH_SHORT).show();
			}
		});

		builder.show(); // method to show the popup dialog

	}


	/*
	Refresh list button method
	 */
	public void refresh(View view) {
		Toast.makeText(RecipientMain.this,"Refresh List", Toast.LENGTH_SHORT).show();
	}


} // end class
