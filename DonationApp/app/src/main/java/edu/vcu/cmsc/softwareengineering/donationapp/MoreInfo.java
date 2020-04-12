package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MoreInfo extends AppCompatActivity {

    String editDescription, editCategory, editDelivery, editCondition, editQuantity, editImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);


        Intent intent = getIntent();
        editDescription = intent.getStringExtra("description");
        editCategory = intent.getStringExtra("category");
        editDelivery = intent.getStringExtra("delivery");
        editCondition = intent.getStringExtra("condition");
        editQuantity = intent.getStringExtra("quantity");
        editImage = intent.getStringExtra("image");

        ImageView image = findViewById(R.id.imageViewMoreInfo);

        Picasso.get().load(editImage).into(image);

        TextView descriptionView = findViewById(R.id.textViewDescription);
        TextView categoryView = findViewById(R.id.textViewCategory);
        TextView conditionView = findViewById(R.id.textViewCondition);
        TextView deliveryView = findViewById(R.id.textViewDelivery);
        TextView quantityView = findViewById(R.id.textViewQuantity);

        descriptionView.setText(editDescription);
        categoryView.setText(editCategory);
        conditionView.setText(editCondition);
        deliveryView.setText(editDelivery);
        quantityView.setText(editQuantity);
    }
}
