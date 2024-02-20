package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button strButton, jsonObjButton, jsonArrButton, imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        strButton = findViewById(R.id.btnStringRequest);
        jsonObjButton = findViewById(R.id.btnJsonObjRequest);
        jsonArrButton = findViewById(R.id.btnJsonArrRequest);
        imgButton = findViewById(R.id.btnImageRequest);

        /* button click listeners */
        strButton.setOnClickListener(this);
        jsonObjButton.setOnClickListener(this);
        jsonArrButton.setOnClickListener(this);
        imgButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnStringRequest) {
            startActivity(new Intent(MainActivity.this, StringReqActivity.class));
        } else if (id == R.id.btnJsonObjRequest) {
            startActivity(new Intent(MainActivity.this, JsonObjReqActivity.class));
        } else if (id == R.id.btnJsonArrRequest) {
            startActivity(new Intent(MainActivity.this, JsonArrReqActivity.class));
        } else if (id == R.id.btnImageRequest) {
            startActivity(new Intent(MainActivity.this, ImageReqActivity.class));
        }
    }
}