package com.example.androidapp.Clan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidapp.Connectivity.VolleySingleton;
import com.example.androidapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateClanActivity extends AppCompatActivity implements View.OnClickListener
{
    private MaterialToolbar materialToolbar2;
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

    materialToolbar2 = findViewById(R.id.materialToolbar2);
    textView1 = findViewById(R.id.textView1);
    editTextText2 = findViewById(R.id.editTextText2);
    textView2 = findViewById(R.id.textView2);
    textView3 = findViewById(R.id.textView3);
    chipGroup1 = findViewById(R.id.chipGroup1);
    chip1 = findViewById(R.id.chip1);
    chip2 = findViewById(R.id.chip2);
    textView3 = findViewById(R.id.textView3);
    chipGroup2 = findViewById(R.id.chipGroup2);
    chip3 = findViewById(R.id.chip3);
    chip4 = findViewById(R.id.chip4);
    createButton = findViewById(R.id.createButton);

    materialToolbar2.setOnClickListener(this);
    createButton.setOnClickListener(this);
    chip1.setOnClickListener(this);
    chip2.setOnClickListener(this);
    chip3.setOnClickListener(this);
    chip4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
    int buttonClicked = v.getId();
    if (buttonClicked == R.id.materialToolbar2)
    {
        Intent intent = new Intent(CreateClanActivity.this, ClanActivity.class);
        startActivity(intent);
    }
    else if (buttonClicked == R.id.createButton)
    {

    }
    else if (buttonClicked == R.id.chip1)
    {
    chip1.setSelected(true);
    chip2.setSelected(false);
    }
    else if (buttonClicked == R.id.chip2)
    {
    chip2.setSelected(true);
    chip1.setSelected(false);
    }
    else if (buttonClicked == R.id.chip3)
    {
    chip3.setSelected(true);
    chip4.setSelected(false);
    }
    else if (buttonClicked == R.id.chip4)
    {
    chip4.setSelected(true);
    chip3.setSelected(false);
    }
    }

    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;
        String url = "";
        try {
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response to POST", response.toString());
                        String responseString = "";
                        try {
                            responseString = response.getString("message");
                            if(responseString.equals("success"))
                            {
                                Toast.makeText(CreateClanActivity.this, "Clan has been successfully added", Toast.LENGTH_SHORT).show();
                            }
                            else if (responseString.equals("failure"))
                            {
                                Toast.makeText(CreateClanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
