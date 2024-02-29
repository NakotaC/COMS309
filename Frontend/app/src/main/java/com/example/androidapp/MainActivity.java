package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginBtn;
    private Button signupBtn;
    private TextView mainTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTxt = findViewById(R.id.MainText);
        loginBtn = findViewById(R.id.Login_btn);
        signupBtn = findViewById(R.id.Signup_btn);

        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
    int id = v.getId();
    if(id == R.id.Login_btn){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    } else if (id == R.id.Signup_btn) {
        startActivity(new Intent(MainActivity.this, SignupActivity.class));
    }
    }
}
