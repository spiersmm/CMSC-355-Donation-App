package edu.vcu.cmsc.softwareengineering.donationapp.data;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * A class for handling login data. Very rough but this
 * general format should be easy for testing throughout the
 * application.
 */
public class LoginData {
	
	private EditText username;
	private EditText password;
	private Button login;
	
	public LoginData(View id, View id1, View id2) {
		username = (EditText) id;
		password = (EditText) id1;
		login = (Button) id2;
	}
	
	public void setLoginListener(View.OnClickListener l) {
		login.setOnClickListener(l);
	}
	public String[] getArgs() {
		return new String[] {
				username.getText().toString(),
				password.getText().toString()
		};
	}
	
	public boolean goesToDonor() {
		//TODO determine destination (donor or recipient)
		if (username.getText().toString().endsWith("rec"))
			return false;
		return true;
	}
	
}
