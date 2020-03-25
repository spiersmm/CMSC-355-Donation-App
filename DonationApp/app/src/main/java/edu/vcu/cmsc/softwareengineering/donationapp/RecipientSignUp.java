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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecipientSignUp extends AppCompatActivity {

    EditText recipientName, recipientEmail, recipientPhone, recipientNumber,
         recipientPassword;
    Button recipientSignupButton;
    private FirebaseAuth auth;

    // create a recipient account
    public void createAccount(String emailRecipient, String passwordRecipient){
        auth.createUserWithEmailAndPassword(emailRecipient, passwordRecipient)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    private static final String TAG = "EmailPassword";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // if we create new user than go to donor signup page
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Intent recipientSignup = new Intent(getApplicationContext(), RecipientMain.class);
                            startActivity(recipientSignup);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_sign_up);

        recipientName = findViewById(R.id.recipientName);
        recipientEmail = findViewById((R.id.editTextRecipientEmail));
        recipientPhone = findViewById(R.id.recipientPhone);
        recipientNumber = findViewById(R.id.editTextRecipientNumber);
        recipientPassword = findViewById(R.id.editTextRecipientPasswordSignup);

        recipientSignupButton = findViewById(R.id.buttonRecipientSignup);

        auth = FirebaseAuth.getInstance();

        recipientSignupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = recipientName.getText().toString();
                String email = recipientEmail.getText().toString();
                String phone = recipientPhone.getText().toString();
                String number = recipientNumber.getText().toString();
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
