// Project B Team 1 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;

// Activity for having a donor post a new item for donation
public class postNewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_item);

        createCategorySpinner();
        createDeliverySpinner();
        createConditionSpinner();
        createQuantitySpinner();
    }

    public void createCategorySpinner() {
        Spinner categories = (Spinner) findViewById(R.id.spinnerCategories);

        List<String> categoryItems = new ArrayList<String>();
        categoryItems.add("Category");
        categoryItems.add("Clothes");
        categoryItems.add("Food");
        categoryItems.add("Furniture");
        categoryItems.add("Toiletries");
        categoryItems.add("Games/Toys");
        categoryItems.add("Books");
        categoryItems.add("Other");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryItems);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(categoryAdapter);
        //categories.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }

    public void createDeliverySpinner() {
        Spinner delivery = (Spinner) findViewById(R.id.spinnerDelivery);

        List<String> deliveryItems = new ArrayList<String>();
        deliveryItems.add("Delivery Method");
        deliveryItems.add("Pickup");
        deliveryItems.add("Delivery");

        ArrayAdapter<String> deliveryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, deliveryItems);
        deliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        delivery.setAdapter(deliveryAdapter);
    }

    public void createConditionSpinner() {
        Spinner condition = (Spinner) findViewById(R.id.spinnerCondition);

        List<String> Items = new ArrayList<String>();
        Items.add("Condition");
        Items.add("Perfect");
        Items.add("Ok");
        Items.add("Poor");

        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Items);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(conditionAdapter);
    }

    public void createQuantitySpinner() {
        Spinner quantity = (Spinner) findViewById(R.id.spinnerQuantity);

        List<String> Items = new ArrayList<String>();
        Items.add("Quantity");
        Items.add("1");
        Items.add("2");
        Items.add("3");
        Items.add("4");
        Items.add("5");
        Items.add("Over 5");

        ArrayAdapter<String> QuantityAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Items);
        QuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantity.setAdapter(QuantityAdapter);
    }


}
