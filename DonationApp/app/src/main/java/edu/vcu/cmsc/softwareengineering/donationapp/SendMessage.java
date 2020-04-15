package edu.vcu.cmsc.softwareengineering.donationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendMessage extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Button sendEmail = findViewById(R.id.buttonEmailSend);
        Intent intent = getIntent();
        email = intent.getStringExtra("donorEmail");
        sendEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    }
    // send email to donor
    protected void sendEmail() {
        EditText editSubject = findViewById(R.id.editTextEmailSubject);
        EditText editBody = findViewById(R.id.editTextEmailBody);
        String subject = editSubject.getText().toString();
        String body = editBody.getText().toString();
        Log.i("Send email", "");
        String[] TO = {email};  // need to fill this in with donor email for item that was selected
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
