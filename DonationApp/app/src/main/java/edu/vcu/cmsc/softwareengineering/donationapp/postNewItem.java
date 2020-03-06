// Project B Team 1 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;


// Activity for having a donor post a new item for donation
public class postNewItem extends AppCompatActivity  {

    // variables for storing item data
    private String EnteredDescription;
    private String selectedCategory;
    private String selectedDeliveryMethod;
    private String selectedCondition;
    private String selectedQuantity;

    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_item);


        // create drop down menus for posting a new item
        createCategorySpinner();
        createDeliverySpinner();
        createConditionSpinner();
        createQuantitySpinner();
    }

    public void createCategorySpinner() {
        final Spinner categories = (Spinner) findViewById(R.id.spinnerCategories);

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
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedCategory = categories.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void createDeliverySpinner() {
        final Spinner delivery = (Spinner) findViewById(R.id.spinnerDelivery);

        List<String> deliveryItems = new ArrayList<String>();
        deliveryItems.add("Delivery Method");
        deliveryItems.add("Pickup");
        deliveryItems.add("Delivery");

        ArrayAdapter<String> deliveryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, deliveryItems);
        deliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        delivery.setAdapter(deliveryAdapter);
        delivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedDeliveryMethod = delivery.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void createConditionSpinner() {
        final Spinner condition = (Spinner) findViewById(R.id.spinnerCondition);

        List<String> Items = new ArrayList<String>();
        Items.add("Condition");
        Items.add("Perfect");
        Items.add("Ok");
        Items.add("Poor");

        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Items);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(conditionAdapter);
        condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedCondition = condition.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void createQuantitySpinner() {
        final Spinner quantity = (Spinner) findViewById(R.id.spinnerQuantity);

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
        quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedQuantity = quantity.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void getDescription() {
        description = findViewById(R.id.editTextDescription);
        EnteredDescription = description.getText().toString();
    }

}
