package edu.vcu.cmsc.softwareengineering.donationapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class DonorLogin extends AppCompatActivity {

    EditText donorEmail, donorPassword;
    String email, password;
    private FirebaseAuth auth;

    public void login(String emailDonor, String passwordDonor) {
        auth.signInWithEmailAndPassword(emailDonor, passwordDonor)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Intent donorlogin = new Intent(getApplicationContext(), DonorMain.class);
                            startActivity(donorlogin);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Login Failed!",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);

        Button loginButton = findViewById(R.id.buttonDonorLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                donorEmail = findViewById(R.id.editTextDonorUsernameLogin);
                donorPassword = findViewById(R.id.editTextDonorPasswordLogin);

                email = donorEmail.getText().toString();
                password = donorPassword.getText().toString();

                auth = FirebaseAuth.getInstance();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_LONG).show();
                    return;
                }
                login(email, password);
            }
        });
    }
}
