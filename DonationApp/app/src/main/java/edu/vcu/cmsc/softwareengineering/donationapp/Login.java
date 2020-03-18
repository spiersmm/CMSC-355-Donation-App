package edu.vcu.cmsc.softwareengineering.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.vcu.cmsc.softwareengineering.donationapp.data.LoginData;
// Project B Team 1
// CMSC 355 Spring 2020
/**
 * currently this class starts the app. The data entered by the user is handled
 * in a 'LoginData' object.
 */


public class Login extends AppCompatActivity {
	
	private LoginData data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Button loginButton = findViewById(R.id.DonorLoginButton);
		Button donorSignupButton = findViewById(R.id.DonorSignupButton);
		Button recipientSignUpButton = findViewById(R.id.RecipientSignupButton);
		
		// data = new LoginData(findViewById(R.id.usernameEditText),
		//			 findViewById(R.id.passwordEditText),
		//			 findViewById(R.id.loginButton));
		 
		loginButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//if (data.goesToDonor()) {
				Intent leaveLogin = new Intent(getApplicationContext(), DonorMain.class);
				//} else {
				//	intent = new Intent(this, PostListActivity.class);
				//}

				//	intent.putExtra(this.getPackageName(), data.getArgs());
				startActivity(leaveLogin);
			}
		});
		donorSignupButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent donorSignup = new Intent(getApplicationContext(), DonorSignup.class);

				startActivity(donorSignup);
			}
		});
		recipientSignUpButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent recipientSignup = new Intent(getApplicationContext(), RecipientSignUp.class);

				startActivity(recipientSignup);
			}
		});
	}
	

	
	
}
