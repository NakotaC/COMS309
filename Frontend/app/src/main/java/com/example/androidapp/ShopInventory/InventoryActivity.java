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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidapp.Connectivity.VolleySingleton;
import com.example.androidapp.Game.User;
import com.example.androidapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle the Shop screen
 */
public class InventoryActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView inventoryHeader, inventoryItemText, equippedText;
    private ListAdapterEquippedInventory adapterInventory;
    private ListAdapterEquippedInventory adapterEquipped;
    private ListView listViewEquipped, listViewInventory;
    private int equipNum, id;
    Button back;
    User user;
    JSONArray equippedItems;
    JSONArray ownedItems;
    private static String URL;
    //private static String URL = "http://coms-309-033.class.las.iastate.edu:8080/inventory/shop"

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
        setContentView(R.layout.activity_inventory);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        user = (User) extras.getSerializable("USEROBJ");


        assert user != null;
        equippedItems = user.getEquippedItems();
        id = user.getId();
        ownedItems = user.getInventory();

        URL = "https://1c9efe9d-cfe0-43f4-b7e3-dac1af491ecf.mock.pstmn.io/shop";
        //URL = "http://coms-309-033.class.las.iastate.edu:8080/inventory/" + id;

        inventoryHeader = findViewById(R.id.InventoryHeader);
        inventoryItemText = findViewById(R.id.inventoryItemTxt);
        equippedText = findViewById(R.id.equippedTxt);
        listViewEquipped = findViewById(R.id.listview_equipped);
        back = findViewById(R.id.inventoryBtn);

        // Initialize the adapter with an empty list (data will be added later)
        adapterEquipped = new ListAdapterEquippedInventory(this, new ArrayList<>());
        listViewEquipped.setAdapter(adapterEquipped);


        back.setOnClickListener(this);


        for (int i = 0; i < equippedItems.length() - 1; i++) {
            try {
                JSONObject jsonObject = equippedItems.getJSONObject(i);
                String name = jsonObject.getString("itemName");
                String description = jsonObject.getString("description");

                // Create a ListItemObject and add it to the adapter
                EquippedItemInventory item = new EquippedItemInventory(name, description);
                //   ListAdapterInventory tmp = adapter;
                adapterEquipped.add(item);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        listViewEquipped.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                equipNum = (int) id;
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
        if(id == R.id.inventoryBtn){
            Intent intent = new Intent(InventoryActivity.this, ShopActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        }
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
                "http://coms-309-033.class.las.iastate.edu:8080/inventory/equip/" + id,
                // URL,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    //TODO response to equipping item
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
                String temp = String.valueOf(equipNum);
                headers.put("item", String.valueOf(equipNum));
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
