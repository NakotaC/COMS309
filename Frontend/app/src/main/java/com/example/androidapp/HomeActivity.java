package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton button1;
    private MaterialButton clanButton;
    private MaterialButton shopButton;
    private Button webSocketsTestButton;
    private TextView text1;
    private ImageButton statsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


       // imageButton1 = findViewById(R.id.imageButton1);
        text1 = findViewById(R.id.text1);
        button1 = findViewById(R.id.button);
        clanButton = findViewById(R.id.clanButton);
        shopButton = findViewById(R.id.shopButton);
        statsButton = findViewById(R.id.statsButton);
        webSocketsTestButton = findViewById(R.id.webSocketsTestButton);


        button1.setOnClickListener(this);
        clanButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        statsButton.setOnClickListener(this);
        webSocketsTestButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        int id1 = v.getId();
        if (id1 == R.id.clanButton)
        {
            startActivity(new Intent(HomeActivity.this, ClanActivity.class));
        } else if (id1 == R.id.statsButton)
        {
            startActivity(new Intent(HomeActivity.this, LeaderboardActivity.class));
        }
       else if (id1 == R.id.shopButton)
       {
            startActivity(new Intent(HomeActivity.this, ShopActivity.class));
       }
       else if (id1 == R.id.webSocketsTestButton)
       {
       startActivity(new Intent(HomeActivity.this, WebSocketTestActivity.class));
       }
    }
}