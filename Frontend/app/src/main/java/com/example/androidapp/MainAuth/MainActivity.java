package com.example.androidapp.MainAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.R;

/**
 * Main class for the App. Acts as the first page user see
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;

    private Button signupBtn;

    private TextView mainTxt;


    /**
     * Builds out the screen and initializes the elements when the screen is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * Click listener to listen for clicks on the login and signup button. Sends user to respective screen when pressed.
     * @param v The view that was clicked.
     */
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