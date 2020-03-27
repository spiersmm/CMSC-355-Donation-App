// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipientMain extends AppCompatActivity {

	private String SelectedItemRecord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipient_main);


	createItemRecordSpinner();

	} // end onCreate()



	/**
	 *  method to create spinner for item record
	 *  to select from available, or received items.
	 */
	public void createItemRecordSpinner() {
		final Spinner itemRecord = findViewById(R.id.ItemRecord);

		List<String> itemRecordItems = new ArrayList<>();
			itemRecordItems.add("Available");
			itemRecordItems.add("Recieved");

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







	/*
    Method for filter button and multiple choice popup dialog
    consists of inner .setMultiChoiceItems() method for the checkbox list
    .setPositivebutton() method for an 'ok' button
    .setNeutralButton() method for a 'cancel' button
     */
	public void filterCategory(View view) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);    // the alert popup class
		builder.setTitle("Category Filter"); // set title of dialog


		final String[] options = { "Clothes", "Food", "Furniture", "Toiletries", "Games/Toys", "Books", "Electronics", "Other"};    // options for the popup
		final boolean[] checked = new boolean[] { false, false, false, false, false, false, false, false };   // default state of checkboxes ( seems to be required )
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

		builder.show(); // methood to show the popup dialog

	}



	/*
    Method for filter button and multiple choice popup dialog
    consists of inner .setMultiChoiceItems() method for the checkbox list
    .setPositivebutton() method for an 'ok' button
    .setNeutralButton() method for a 'cancel' button
     */
	public void filterCondition(View view) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);    // the alert popup class
		builder.setTitle("Condition Filter"); // set title of dialog


		final String[] options = { "Perfect", "Ok", "Poor" };    // options for the popup
		final boolean[] checked = new boolean[] { false, false, false };   // default state of checkboxes ( seems to be required )
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

		builder.show(); // methood to show the popup dialog

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
		final boolean[] checked = new boolean[] { false, false, false };   // default state of checkboxes ( seems to be required )
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

		builder.show(); // methood to show the popup dialog

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
		final boolean[] checked = new boolean[] { false, false };   // default state of checkboxes ( seems to be required )
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

		builder.show(); // methood to show the popup dialog

	}















} // end class
