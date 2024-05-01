package com.example.androidapp.ShopInventory;

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
import com.example.androidapp.Game.User;
import com.example.androidapp.MainAuth.HomeActivity;
import com.example.androidapp.R;
import com.example.androidapp.Connectivity.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle the Shop screen
 */
public class ShopActivity extends AppCompatActivity implements View.OnClickListener{
private TextView shopHeader;
       private TextView respondText, bankText;
    private Button btnJsonArrReq;
    private ListAdapterShop adapter;
    private ListView listView;
    int buyNum;
    Button back, inventory;
    User user;
    private static final String URL = "https://1c9efe9d-cfe0-43f4-b7e3-dac1af491ecf.mock.pstmn.io/shop";

    //inverntory/shop
    //inventory/shop/buy

    /**
     * Handles the creation and functionality of the elements when the screen is initialized
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        user = (User) extras.getSerializable("USEROBJ");

        shopHeader = findViewById(R.id.shop_text);
        respondText = findViewById(R.id.respondText);
        listView = findViewById(R.id.shopList);
        back = findViewById(R.id.shopBackBtn);
        inventory = findViewById(R.id.shopInventoryBtn);
        bankText = findViewById(R.id.bankValTxt);

        bankText.setText("Bank: " + user.getBank());
        
        // Initialize the adapter with an empty list (data will be added later)
        adapter = new ListAdapterShop(this, new ArrayList<>());
        listView.setAdapter(adapter);

     //   bankText.setText("Bank: " + user.getBank());
        back.setOnClickListener(this);
        inventory.setOnClickListener(this);


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

    /**
     * Handles the logic for the buttons on screen
     * @param v The view that was clicked.
     */
        @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.shopBackBtn){
            Intent intent = new Intent(ShopActivity.this, HomeActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        } else if(id == R.id.shopInventoryBtn) {
            Intent intent = new Intent(ShopActivity.this, InventoryActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        }
    }
    /**
     * Making json array request
     * */
    private void makeJsonArrayReq() {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(
                Request.Method.GET,
                "http://coms-309-033.class.las.iastate.edu:8080/inventory/shop",
                //URL,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());

                        // Parse the JSON array and add data to the adapter
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("itemName");
                                String description = jsonObject.getString("description");

                                // Create a ListItemObject and add it to the adapter
                                ListItemObjectShop item = new ListItemObjectShop(name, description);
                               ListAdapterShop tmp = adapter;
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

        JSONObject postBody = new JSONObject("{\"item\" : \"1\"}");
        postBody=null;
//        Log.d("\n", postBody.toString());
//        try {
//            postBody = new JSONObject(jsonString);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-309-033.class.las.iastate.edu:8080/inventory/shop/buy",
               // URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String msg = null;
                        try {
                            msg = response.getString("message").replaceAll("\"", "");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        respondText.setText(msg);
                        respondText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        respondText.setVisibility(TextView.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        error.printStackTrace();
                    }
                }
        ){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String temp = String.valueOf(buyNum);
                headers.put("item", String.valueOf(buyNum));
                headers.put("username", user.getUsername());
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
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                1000*20,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
