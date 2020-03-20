// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
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
	 *  to select from available, or recieved items.
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




	/**
	 *  method to create spinner for category filter
	 */
	public void createFilterCategorySpinner() {
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
	} // end createFilterCategorySpinner





	/**
	 *  method to create spinner
	 */
	public void createFilterDeliverySpinner() {
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
	}



	/**
	 *  method to create spinner
	 */
	public void createFilterConditionSpinner() {
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
	}


	/**
	 *  method to create spinner
	 */
	public void createFilterQuantitySpinner() {
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
	}













} // end class
