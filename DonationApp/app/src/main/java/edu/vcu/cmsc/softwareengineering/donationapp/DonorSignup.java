package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.regex.Pattern;


public class DonorSignup extends AppCompatActivity {

    EditText donorName, donorEmail, donorPhone, donorDOB, donorPassword;
    Button donorSignupButton;
    private FirebaseAuth auth;

    // create a donor account
    public void createAccount(String emailDonor, String passwordDonor){
        auth.createUserWithEmailAndPassword(emailDonor, passwordDonor)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    private static final String TAG = "EmailPassword";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // if we create new user than go to donor signup page
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Intent donorSignup = new Intent(getApplicationContext(), DonorMain.class);
                            startActivity(donorSignup);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_signup);

        donorName = findViewById(R.id.editTextDonorName);
        donorEmail = findViewById(R.id.editTextDonorEmail);
        donorPhone = findViewById(R.id.editTextDonorPhone);
        donorDOB = findViewById(R.id.editTextDonorDOB);
        donorPassword = findViewById(R.id.editTextDonorPasswordSignup);

        donorSignupButton = findViewById(R.id.buttonDonorSignup);

        auth = FirebaseAuth.getInstance();

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();


        donorSignupButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String name = donorName.getText().toString();
                String email = donorEmail.getText().toString();
                String phone = donorPhone.getText().toString();
                String dob = donorDOB.getText().toString();
                String password = donorPassword.getText().toString();

                // make sure sign up boxes are not empty and input is valid
                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(dob)) {
                    Toast.makeText(getApplicationContext(), "Please enter your date of birth dd/mm/yyyy", Toast.LENGTH_LONG).show();
                    return;
                }

                if(dob.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Please enter your date of birth dd/mm/yyyy", Toast.LENGTH_LONG).show();
                    return;
                }
                // make sure date is correct format
                Pattern p = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
                if(!p.matcher(dob).matches()) {
                    Toast.makeText(getApplicationContext(), "Please enter your date of birth dd/mm/yyyy", Toast.LENGTH_LONG).show();
                    return;
                }
                // make sure user is at least 18
                String[] dobParts = dob.split("/");
                String month = dobParts[0];
                String day = dobParts[1];
                String year = dobParts[2];
                Calendar cal = Calendar.getInstance();
                int thisYear = cal.get(Calendar.YEAR);
                int thisMonth = cal.get(Calendar.MONTH);
                thisMonth++;
                int thisDay = cal.get(Calendar.DAY_OF_MONTH);

                if(thisYear - Integer.parseInt(year) < 18) {
                    Toast.makeText(getApplicationContext(), "Must be 18 years old to make an account", Toast.LENGTH_LONG).show();
                    return;
                }
                if(thisYear - Integer.parseInt(year) == 18 && thisMonth - Integer.parseInt(month) < 0) {
                    Toast.makeText(getApplicationContext(), "Must be 18 years old to make an account", Toast.LENGTH_LONG).show();
                    return;
                }
                if(thisYear - Integer.parseInt(year) == 18 && thisMonth - Integer.parseInt(month) == 0 && thisDay - Integer.parseInt(day) < 0) {
                    Toast.makeText(getApplicationContext(), "Must be 18 years old to make an account", Toast.LENGTH_LONG).show();
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
                if(email.contains(".org")) {
                    Toast.makeText(getApplicationContext(), "Donors can not use organization email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!Patterns.PHONE.matcher(phone).matches()) {
                    Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid password", Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.length() < 8) {
                    Toast.makeText(getApplicationContext(),"Password must be at least 8 digits",Toast.LENGTH_LONG).show();
                    return;
                }
                createAccount(email, password);
            }
        });


    }
}
