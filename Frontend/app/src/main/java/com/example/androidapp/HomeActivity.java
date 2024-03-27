package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.Clan.ClanActivity;
import com.example.androidapp.GameObjs.User;
import com.example.androidapp.ShopInventory.ShopActivity;
import com.example.androidapp.Leaderboard.LeaderboardActivity;
import com.google.android.material.button.MaterialButton;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton button1;
    private MaterialButton clanButton;
    private MaterialButton shopButton;
    private TextView text1;
    private User user;
    private ImageButton statsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        user = (User) extras.getSerializable("USEROBJ");

       // imageButton1 = findViewById(R.id.imageButton1);
        text1 = findViewById(R.id.text1);
        button1 = findViewById(R.id.button);
        clanButton = findViewById(R.id.clanButton);
        shopButton = findViewById(R.id.shopButton);
        statsButton = findViewById(R.id.statsButton);


        button1.setOnClickListener(this);
        clanButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        statsButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        int id1 = v.getId();
        if (id1 == R.id.clanButton) {
            Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        }  else if (id1 == R.id.button) {
            Intent intent = new Intent(HomeActivity.this, GameActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        }
        else if (id1 == R.id.shopButton) {
            Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
            startActivity(new Intent(HomeActivity.this, ClanActivity.class));
        } else if (id1 == R.id.statsButton) {
            startActivity(new Intent(HomeActivity.this, LeaderboardActivity.class));
        }
       else if (id1 == R.id.shopButton) {
            startActivity(new Intent(HomeActivity.this, ShopActivity.class));
        }
    }
}