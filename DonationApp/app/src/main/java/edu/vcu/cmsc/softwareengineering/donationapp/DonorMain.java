// Project B Team 1
// Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.widget.Button;
import android.view.View;

public class DonorMain extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donor_main);

		Button postItemButton = findViewById(R.id.postItem);

		postItemButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// when click add new item button, go to item posting screen
				Intent postNewItem = new Intent(getApplicationContext(), postNewItem.class);
				startActivity(postNewItem);


			}
		});
	}
}
