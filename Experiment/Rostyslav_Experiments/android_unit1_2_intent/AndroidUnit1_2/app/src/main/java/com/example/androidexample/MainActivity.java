package com.example.androidexample;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.tomer.fadingtextview.FadingTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private FadingTextView openingMessage;
    private String[] openingMessageText = {"Welcome", "to", "the", "fabulous", "Connect",
            "ONLINE!!!!!!!!!!"
    };
    private Button randomButton;
    public Intent intent1 = new Intent(MainActivity.this, Main1Activity.class);

@Override
protected void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



openingMessage = findViewById(R.id.openingMessage);
openingMessage.setTexts(openingMessageText);

new Timer().schedule(new TimerTask()
{
    @Override
    public void run() {
        startActivity(intent1);
    }
}, 10000);



}
}

