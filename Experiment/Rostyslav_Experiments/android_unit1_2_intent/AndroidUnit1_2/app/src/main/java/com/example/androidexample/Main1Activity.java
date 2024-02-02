package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tomer.fadingtextview.FadingTextView;

public class Main1Activity extends AppCompatActivity {


    private TextView messageText;     // define message textview variable
    private Button joinButton;// define counter button variable
    private ImageView avatar2;
    private ImageView avatar3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);             // link to Main activity XML
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        joinButton = findViewById(R.id.main_join_btn);// link to counter button in the Main activity XML
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);


        /* extract data passed into this activity from another activity */
        Bundle extras = getIntent().getExtras();
            messageText.setText("Please select your avatar to continue");

        /* click listener on counter button pressed */
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Main1Activity.this, SignupActivity.class);
                startActivity(intent2);
            }
        });
    }
}