package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnJsonArrReq;
    private ListAdapter adapter;
    private ListView listView;

    Button buy1, buy2, buy3, buy4, buy5, buy6, buy7, buy8, back;


    private static final String URL_JSON_ARRAY = "http://coms-309-033.class.las.iastate.edu:8080/shop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopactivity);


        listView = findViewById(R.id.shopList);
        buy1 = findViewById(R.id.buy1);
        buy2 = findViewById(R.id.buy2);
        buy3 = findViewById(R.id.buy3);
        buy4 = findViewById(R.id.buy4);
        buy5 = findViewById(R.id.buy5);
        buy6 = findViewById(R.id.buy6);
        buy7 = findViewById(R.id.buy7);
        buy8 = findViewById(R.id.buy8);
        back = findViewById(R.id.shopBackBtn);

        // Initialize the adapter with an empty list (data will be added later)
        adapter = new ListAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        buy1.setOnClickListener(this);
        buy2.setOnClickListener(this);
        buy3.setOnClickListener(this);
        buy4.setOnClickListener(this);
        buy5.setOnClickListener(this);
        buy6.setOnClickListener(this);
        buy7.setOnClickListener(this);
        buy8.setOnClickListener(this);
        back.setOnClickListener(this);


        makeJsonArrayReq();


    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.shopBackBtn){
            startActivity(new Intent(ShopActivity.this, DummyHome.class));
        } else {

        }
    }
    /**
     * Making json array request
     * */
    private void makeJsonArrayReq() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                URL_JSON_ARRAY,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());

                        // Parse the JSON array and add data to the adapter
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                int price = jsonObject.getInt("price");

                                // Create a ListItemObject and add it to the adapter
                                ListItemObject item = new ListItemObject(name, price);
                                adapter.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
//                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("param1", "value1");
//                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);
    }
}