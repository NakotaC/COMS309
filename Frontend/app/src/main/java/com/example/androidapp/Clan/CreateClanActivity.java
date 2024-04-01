package com.example.androidapp.Clan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.R;
import com.google.android.material.chip.*;

public class CreateClanActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView textView1;
    private TextView textView2;
    private TextView editTextText2;
    private TextView textView3;
    private ChipGroup chipGroup1;
    private ChipGroup chipGroup2;
    private Chip chip1;
    private Chip chip2;
    private Chip chip3;
    private Chip chip4;
    private Button createButton;
    public void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_createclan);

    textView1.findViewById(R.id.textView1);
    editTextText2.findViewById(R.id.editTextText2);
    textView2.findViewById(R.id.textView2);
    textView3.findViewById(R.id.textView3);
    chipGroup1.findViewById(R.id.chipGroup1);
    chip1.findViewById(R.id.chip1);
    chip2.findViewById(R.id.chip2);
    textView3.findViewById(R.id.textView3);
    chipGroup2.findViewById(R.id.chipGroup2);
    chip3.findViewById(R.id.chip3);
    chip4.findViewById(R.id.chip4);
    createButton.findViewById(R.id.createButton);

    createButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {

    }
}
