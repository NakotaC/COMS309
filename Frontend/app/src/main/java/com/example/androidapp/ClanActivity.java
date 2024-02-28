package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class ClanActivity extends AppCompatActivity implements View.OnClickListener
{
public Button button1;
public TextView text1;
public Button button2;
public Button button3;
private MaterialToolbar toolbar1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan);

        toolbar1 = findViewById(R.id.toolbar1);

        toolbar1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        int id1 = v.getId();
        if (id1 == R.id.toolbar1) {
            startActivity(new Intent(ClanActivity.this, MainActivity.class));
        }
    }
}
