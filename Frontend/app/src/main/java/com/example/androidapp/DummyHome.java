package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidapp.R;


public class DummyHome extends AppCompatActivity {
private TextView header;
private TextView welcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummyhomeactivity);
    Bundle extras = getIntent().getExtras();
     String username =  extras.getString("USERNAME");
        header = findViewById(R.id.homeheader);
       welcome = findViewById(R.id.splash);

        welcome.setText("Welcome " + username);
        welcome.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
    }
}