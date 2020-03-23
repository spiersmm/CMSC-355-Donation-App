package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RecipientSignUp extends AppCompatActivity {

    EditText recipientName, recipientEmail, recipientPhone, recipientNumber,
        recipientUsername, recipientPassword;
    Button recipientSignupButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_sign_up);

        recipientName = findViewById(R.id.recipientName);
        recipientEmail = findViewById((R.id.editTextRecipientEmail));
        recipientPhone = findViewById(R.id.recipientPhone);
        recipientNumber = findViewById(R.id.editTextRecipientNumber);
        recipientUsername = findViewById(R.id.editTextRecipientUsernameSignup);
        recipientPassword = findViewById(R.id.editTextRecipientPasswordSignup);

        recipientSignupButton = findViewById(R.id.buttonRecipientSignup);

        auth = FirebaseAuth.getInstance();

        recipientSignupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = recipientName.getText().toString();
                String email = recipientEmail.getText().toString();
                String phone = recipientPhone.getText().toString();
                String number = recipientNumber.getText().toString();
                String username = recipientUsername.getText().toString();
                String password = recipientPassword.getText().toString();

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
                if(!email.contains(".org")) {
                    Toast.makeText(getApplicationContext(), "Please enter a .org email so we can verify you are a valid organization", Toast.LENGTH_LONG).show();
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
                if(TextUtils.isEmpty(number)) {
                    Toast.makeText(getApplicationContext(), "Please enter your organizations registration number", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(),"Password must be at least 8 digits",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent recipientSignup = new Intent(getApplicationContext(), RecipientMain.class);

                startActivity(recipientSignup);

            }
         });

    }
}
