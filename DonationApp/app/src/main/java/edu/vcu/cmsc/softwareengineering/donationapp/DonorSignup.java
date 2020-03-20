package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.GenericDeclaration;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


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

                // make sure sign up boxes are not empty and input is valid
                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(dob)) {
                    Toast.makeText(getApplicationContext(), "Please enter your date of birth dd/mm/yyyy", Toast.LENGTH_LONG).show();
                    return;
                }
             //   String[] dobParts = dob.split("/");
             //   String month = dobParts[0];
             //   String day = dobParts[1];
            //    String year = dobParts[2];
            //    int thisYear = Calendar.YEAR;
            //    if(thisYear - Integer.parseInt(year) < 18) {
            //        Toast.makeText(getApplicationContext(), "Must be 18 years old to make an account", Toast.LENGTH_LONG).show();
            //        return;
            //    }

                if(dob.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Please enter your date of birth dd/mm/yyyy", Toast.LENGTH_LONG).show();
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
                if(password.length() < 8) {
                    Toast.makeText(getApplicationContext(),"Password must be more than 8 digit",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent donorSignup = new Intent(getApplicationContext(), DonorMain.class);

                startActivity(donorSignup);
            }
        });

    }
}
