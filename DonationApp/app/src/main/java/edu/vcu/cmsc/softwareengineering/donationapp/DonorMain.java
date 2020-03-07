// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorMain extends AppCompatActivity {


	ListView listView;
	DatabaseReference myDatabaseReference;
	newItemInfo info;



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





		DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
		DatabaseReference newItemInfoRef = rootRef.child("Item Info");
		ValueEventListener eventListener = new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				List<String> itemList = new ArrayList<>();
				for (DataSnapshot ds : dataSnapshot.getChildren()) {
					String description = ds.child("itemDescription").getValue(String.class);
					String category = ds.child("itemCategory").getValue(String.class);
					String condition = ds.child("itemCondition").getValue(String.class);
					String deliveryMethod = ds.child("itemDeliveryMethod").getValue(String.class);
					String quantity = ds.child("itemQuantity").getValue(String.class);
					itemList.add("Description: " + description +
							", Category: " + category +
							", Condition: " + condition +
							", Delivery Method: " + deliveryMethod +
							", Quantity: " + quantity);
				}
				ListView listView = (ListView) findViewById(R.id.dmListView);
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DonorMain.this,android.R.layout.simple_list_item_1,itemList);
				listView.setAdapter(arrayAdapter);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {}


		};
		newItemInfoRef.addListenerForSingleValueEvent(eventListener);









	}
}

