// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.FileDownloadTask;
import com.squareup.picasso.Picasso;


// Activity for having a donor post a new item for donation
public class markItemAsDonated extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;

    // variables for storing item data
    private String EnteredDescription;
    private String selectedCategory;
    private String selectedDeliveryMethod;
    private String selectedCondition;
    private String selectedQuantity;
    private String imageURL;
    private String EnteredRecipientName;



    Button markItem;

    private Button chooseImageButton;
    private ImageView submitImage; // camera icon, submit an image

    private Uri ImageUri;




    FirebaseDatabase myDatabase;
    DatabaseReference myDatabaseReference;
    StorageReference myStorageReference;
    FirebaseUser user;

    String editDescription, editCategory, editDelivery, editCondition, editQuantity, editImage, editRecipientName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_item_as_donated);

        Intent intent = getIntent();
        editDescription = intent.getStringExtra("description");
        editCategory = intent.getStringExtra("category");
        editDelivery = intent.getStringExtra("delivery");
        editCondition = intent.getStringExtra("condition");
        editQuantity = intent.getStringExtra("quantity");
        editImage = intent.getStringExtra("image");
        editRecipientName = intent.getStringExtra("recipientName");

        // create drop down menus for posting a new item
        createCategorySpinner();
        createDeliverySpinner();
        createConditionSpinner();
        createQuantitySpinner();

        final EditText description = (EditText) findViewById(R.id.editTextDescription);
        description.setText(editDescription);
        final EditText recipientName = (EditText) findViewById(R.id.editRecipientName);
        markItem = findViewById(R.id.MarkItem);


        markItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDatabase = FirebaseDatabase.getInstance();
                user = FirebaseAuth.getInstance().getCurrentUser();
                myStorageReference = FirebaseStorage.getInstance().getReference("Donated Item Info Pictures");
                myDatabaseReference = myDatabase.getReference("Donated Item Info").child(user.getUid());
                EnteredDescription = description.getText().toString();
                EnteredRecipientName = recipientName.getText().toString();
                uploadFile(EnteredDescription, EnteredRecipientName); // Item pushed to database in uploadFile method


                Intent goBackToDonorMainPastPostings = new Intent(getApplicationContext(), DonorMainPastPostings.class);
                startActivity(goBackToDonorMainPastPostings);
            }

        });

        chooseImageButton = findViewById(R.id.chooseImageButton);
        submitImage = findViewById(R.id.itemImageView);
        if(!(editImage.equals("noImage"))) {
            Picasso.get().load(editImage).into(submitImage);
            imageURL = editImage;
        }

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
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

            //Picasso.with(this).load(ImageUri).into(submitImage);

            submitImage.setImageURI(ImageUri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile(final String EnteredDescription, final String EnteredRecipientName) {
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(ImageUri != null) {
            final StorageReference imageReference = myStorageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(ImageUri));

            imageReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageURL = uri.toString();
                            newItemInfo newItem = new newItemInfo(EnteredDescription,
                                    selectedCategory, selectedCondition,
                                    selectedDeliveryMethod, selectedQuantity, imageURL, email, EnteredRecipientName);

                            myDatabaseReference.push().setValue(newItem);
                        }
                    });
                }
            });









                    /*.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(postNewItem.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            /* imageURL = taskSnapshot.getUploadSessionUri().toString();
                            newItemInfo newItem = new newItemInfo(EnteredDescription,
                                    selectedCategory, selectedCondition,
                                    selectedDeliveryMethod, selectedQuantity, imageURL);

                            myDatabaseReference.push().setValue(newItem);

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            imageURL = downloadUrl.toString();
                            newItemInfo newItem = new newItemInfo(EnteredDescription,
                                    selectedCategory, selectedCondition,
                                    selectedDeliveryMethod, selectedQuantity, imageURL);

                            myDatabaseReference.push().setValue(newItem);


                        }
                    });
                        */

        }
        else if(imageURL.equals(editImage)){
            newItemInfo newItem = new newItemInfo(EnteredDescription,
                    selectedCategory, selectedCondition,
                    selectedDeliveryMethod, selectedQuantity, imageURL, email, EnteredRecipientName);

            myDatabaseReference.push().setValue(newItem);
        }
        else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT). show();
        }

    }


    public void createCategorySpinner() {
        final Spinner categories = (Spinner) findViewById(R.id.spinnerCategories);
        List<String> categoryItems = new ArrayList<String>();
        categoryItems.add(editCategory);
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
        deliveryItems.add(editDelivery);
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
        Items.add(editCondition);
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
        Items.add(editQuantity);
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