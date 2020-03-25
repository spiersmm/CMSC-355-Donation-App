package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RecipientLogin extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText recipientEmail, recipientPassword;
    String email, password;

    // login to Recipient Account
    public void login(String emailRecipient, String passwordRecipient) {
        auth.signInWithEmailAndPassword(emailRecipient, passwordRecipient)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Intent recipientLogin = new Intent(getApplicationContext(), RecipientMain.class);
                            startActivity(recipientLogin);
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
    public void sendForgotPasswordEmail(String emailtoSend) {
        auth.sendPasswordResetEmail(emailtoSend)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_login);

        auth = FirebaseAuth.getInstance();
        Button loginButton = findViewById(R.id.buttonRecipientLogin);
        Button forgotPasswordButton = findViewById(R.id.buttonForgotPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                recipientEmail = findViewById(R.id.editTextRecipientUsernameLogin);
                recipientPassword = findViewById(R.id.editTextRecipientPasswordLogin);

                email = recipientEmail.getText().toString();
                password = recipientPassword.getText().toString();
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!email.contains(".org")) {
                    Toast.makeText(getApplicationContext(), "Must be a valid organization to enter a recipient account", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_LONG).show();
                    return;
                }
                login(email, password);
            }
        });
        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                recipientEmail = findViewById(R.id.editTextRecipientUsernameLogin);
                email = recipientEmail.getText().toString();
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!email.contains(".org")) {
                    Toast.makeText(getApplicationContext(), "Must be a valid organization to enter a recipient account", Toast.LENGTH_LONG).show();
                    return;
                }
                sendForgotPasswordEmail(email);
            }
        });
    }

}
