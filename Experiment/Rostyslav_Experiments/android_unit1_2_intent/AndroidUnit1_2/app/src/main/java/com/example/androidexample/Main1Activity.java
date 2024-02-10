package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tomer.fadingtextview.FadingTextView;

public class Main1Activity extends AppCompatActivity {

    private FadingTextView openingMessage;

    private TextView messageText;     // define message textview variable
    private Button joinButton;// define counter button variable

    String[] openingMessageText = {"Welcome", "to", "the", "fabulous", "Connect", "ONLINE!!!!!!!!!!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);             // link to Main activity XML
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /* initialize UI elements */
        openingMessage = findViewById(R.id.openingMessage);
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        joinButton = findViewById(R.id.main_join_btn);// link to counter button in the Main activity XML

        /* extract data passed into this activity from another activity */
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            openingMessage.setTexts(openingMessageText);
            messageText.setText("");
        } else {
            String number = extras.getString("NUM");  // this will come from LoginActivity
            openingMessage.stop();
            messageText.setText("The number of users online is " + number);
        }

        /* click listener on counter button pressed */
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when counter button is pressed, use intent to switch to Counter Activity */
                Intent intent = new Intent(Main1Activity.this, CounterActivity.class);
                startActivity(intent);
            }
        });
    }
}