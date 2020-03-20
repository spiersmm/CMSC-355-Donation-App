package edu.vcu.cmsc.softwareengineering.donationapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DonorLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);

        Button loginButton = findViewById(R.id.buttonDonorLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), DonorMain.class);

                startActivity(login);
            }
        });
    }
}
