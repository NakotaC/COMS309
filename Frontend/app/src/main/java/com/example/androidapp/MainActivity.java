package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton button1;
    private MaterialButton clanButton;
    private MaterialButton shopButton;
    private TextView text1;
    private ImageButton imageButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // imageButton1 = findViewById(R.id.imageButton1);
        text1 = findViewById(R.id.text1);
        button1 = findViewById(R.id.button);
        clanButton = findViewById(R.id.clanButton);
        shopButton = findViewById(R.id.shopButton);


        button1.setOnClickListener(this);
        clanButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        int id1 = v.getId();
        if (id1 == R.id.clanButton) {
            startActivity(new Intent(MainActivity.this, ClanActivity.class));
        } // else if (id1 == R.id.button) {
            //startActivity(new Intent(MainActivity.this, FindingAGameActivity.class));
      //  }
       // else if (id1 == R.id.shopButton) {
     //       startActivity(new Intent(MainActivity.this, ShopActivity.class));
      //  }
    }
}