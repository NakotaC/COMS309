package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{
private TextView shopHeader;
       private TextView respondText;
    private Button btnJsonArrReq;
    private ListAdapter adapter;
    private ListView listView;
    int buyNum;
    Button back;

    String username;
    private static final String URL = "https://1c9efe9d-cfe0-43f4-b7e3-dac1af491ecf.mock.pstmn.io/shop2";

    //inverntory/shop
    //inventory/shop/buy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopactivity);

        shopHeader = findViewById(R.id.shop_text);
        respondText = findViewById(R.id.respondText);
        listView = findViewById(R.id.shopList);
        back = findViewById(R.id.shopBackBtn);

        // Initialize the adapter with an empty list (data will be added later)
        adapter = new ListAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);


        back.setOnClickListener(this);


        makeJsonArrayReq();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                buyNum = (int)id;
                try {
                    postRequest();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

        @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.shopBackBtn){
            startActivity(new Intent(ShopActivity.this, DummyHome.class));
        }
    }
    /**
     * Making json array request
     * */
    private void makeJsonArrayReq() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
               // "coms-309-033.class.las.iastate.edu:8080/inventory/shop",
                URL,
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
                               ListAdapter tmp = adapter;
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

    private void postRequest() throws JSONException {

        // Convert input to JSONObject
        Gson gson = new Gson();

        String jsonString = "{\"buyNum\" : " + buyNum + "}";
        JSONObject postBody = null;
        try {
            postBody = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                //"coms-309-033.class.las.iastate.edu:8080/inventory/shop/buy",
                URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String msg = response.toString().replaceAll("\"", "");

                        respondText.setText(msg);
                        respondText.setVisibility(TextView.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("username", username);
                               headers.put("item", String.valueOf(buyNum));
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
