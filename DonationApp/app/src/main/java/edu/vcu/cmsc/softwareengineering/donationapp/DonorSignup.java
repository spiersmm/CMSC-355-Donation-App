package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;


public class DonorSignup extends AppCompatActivity {

    EditText donorName, donorEmail, donorPhone, donorDOB, donorUsername, donorPassword;
    Button donorSignupButton;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_signup);

        donorName = findViewById(R.id.editTextDonorName);
        donorEmail = findViewById(R.id.editTextDonorEmail);
        donorPhone = findViewById(R.id.editTextDonorPhone);
        donorDOB = findViewById(R.id.editTextDonorDOB);
        donorUsername = findViewById(R.id.editTextDonorUsernameSignup);
        donorPassword = findViewById(R.id.editTextDonorPasswordSignup);

        donorSignupButton = findViewById(R.id.buttonDonorSignup);

        auth = FirebaseAuth.getInstance();

        donorSignupButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String name = donorName.getText().toString();
                String email = donorEmail.getText().toString();
                String phone = donorPhone.getText().toString();
                String dob = donorDOB.getText().toString();
                String username = donorUsername.getText().toString();
                String password = donorPassword.getText().toString();

                // make sure sign up boxes are not empty
                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(dob)) {
                    Toast.makeText(getApplicationContext(), "Please enter your date of birth xx/xx/xxxx", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid password", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent donorSignup = new Intent(getApplicationContext(), DonorMain.class);

                startActivity(donorSignup);
            }
        });

    }
}
