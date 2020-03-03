package edu.vcu.cmsc.softwareengineering.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.vcu.cmsc.softwareengineering.donationapp.data.LoginData;

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
		
		 data = new LoginData(findViewById(R.id.usernameEditText),
					 findViewById(R.id.passwordEditText),
					 findViewById(R.id.loginButton));
		 
		 data.setLoginListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				onLogin(v);
			 }
		 });
	}
	
	public void onLogin(View view) {
		Intent intent;
		if (data.goesToDonor()) {
			intent = new Intent(this, DonorMain.class);
		} else {
			intent = new Intent(this, PostListActivity.class);
		}
		
		intent.putExtra(this.getPackageName(), data.getArgs());
		startActivity(intent);
		
	}
	
	
}
