package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;


public class LoginActivity extends AppCompatActivity{
    private Button loginBtn;
    private Button backBtn;
    private TextView header;
    private TextInputEditText usernameEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
    loginBtn = findViewById(R.id.login_button);
    backBtn = findViewById(R.id.back_button);
    header = findViewById(R.id.header);


    }
}