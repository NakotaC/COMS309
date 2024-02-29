package com.example.androidapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class DummyHome extends AppCompatActivity {
private TextView header;
private TextView welcome;
private Button shopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummyhomeactivity);
   // Bundle extras = getIntent().getExtras();
//    if(extras != null) {
//        String username = extras.getString("USERNAME");
//        welcome.setText("Welcome " + username);
//    }else{
//        welcome.setText("Welcome");
//    }
        header = findViewById(R.id.homeheader);
       welcome = findViewById(R.id.splash);
       shopButton = findViewById(R.id.shop_button);


        welcome.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        startActivity(new Intent(DummyHome.this, ShopActivity.class));
            }
        });
    }


}