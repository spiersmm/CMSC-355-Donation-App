// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


// Activity for having a donor post a new item for donation
public class postNewItem extends AppCompatActivity  {


    private static final int PICK_IMAGE_REQUEST = 1;

    // variables for storing item data
    String EnteredDescription;
    private String selectedCategory;
    private String selectedDeliveryMethod;
    private String selectedCondition;
    private String selectedQuantity;


    Button post;

    private Button chooseImageButton;
    private Button uploadImageButton;
    private ImageView submitImage; // camera icon, submit an image

    private Uri ImageUri;


    FirebaseDatabase myDatabase;
    DatabaseReference myDatabaseReference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_item);


        // create drop down menus for posting a new item
        createCategorySpinner();
        createDeliverySpinner();
        createConditionSpinner();
        createQuantitySpinner();

        final EditText description = (EditText) findViewById(R.id.editTextDescription);
        post = findViewById(R.id.PostNewItem);

        post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDatabase = FirebaseDatabase.getInstance();
                user = FirebaseAuth.getInstance().getCurrentUser();
                myDatabaseReference = myDatabase.getReference("Item Info").child(user.getUid());

                newItemInfo newItem = new newItemInfo(description.getText().toString(),
                        selectedCategory, selectedCondition,
                        selectedDeliveryMethod, selectedQuantity);
                myDatabaseReference.push().setValue(newItem);
                Intent goBackToDonorMain = new Intent(getApplicationContext(), DonorMain.class);
                startActivity(goBackToDonorMain);
            }

        });

        chooseImageButton = findViewById(R.id.chooseImageButton);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        submitImage = findViewById(R.id.itemImageView);

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();

            submitImage.setImageURI(ImageUri);
        }
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
        categoryItems.add("Electronics");
        categoryItems.add("Other");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryItems);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(categoryAdapter);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedCategory = categories.getSelectedItem().toString();
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

}
