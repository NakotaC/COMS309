package com.example.androidapp.ShopInventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidapp.Connectivity.VolleySingleton;
import com.example.androidapp.Game.User;
import com.example.androidapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class to handle the Shop screen
 */
public class InventoryActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView inventoryHeader, inventoryItemText, equippedText;
    private ListAdapterInventory adapterInventory;
    private ListAdapterEquippedInventory adapterEquipped;
    private ListView listViewEquipped, listViewInventory;
    private int equipNum, id;
    Button back;
    User user;
    JSONArray equippedItems;
    JSONArray ownedItems,  ownedItemsSansEquipped = new JSONArray();;
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
        listViewInventory = findViewById(R.id.listView_inventory);
        back = findViewById(R.id.inventoryBtn);

        // Initialize the adapter with an empty list (data will be added later)
        adapterEquipped = new ListAdapterEquippedInventory(this, new ArrayList<>());
        listViewEquipped.setAdapter(adapterEquipped);

        adapterInventory = new ListAdapterInventory(this, new ArrayList<>());
        listViewInventory.setAdapter(adapterInventory);

        back.setOnClickListener(this);
        JSONObject tmpOwned = null;
        int numRemoved = 0;
        for(int i = 0; i < ownedItems.length(); i++){
            boolean inEquipped = false;
            for(int k = 0; k < equippedItems.length(); k++){
                try {
                    tmpOwned = ownedItems.getJSONObject(i);
                    JSONObject tmpEquipped = equippedItems.getJSONObject(k);
                    if (tmpOwned.getString("itemName").equals(tmpEquipped.getString("itemName"))){
                        inEquipped = true;
                        break;
                    }else{
                        continue;
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!inEquipped){

                    ownedItemsSansEquipped = ownedItemsSansEquipped.put(tmpOwned);
                    numRemoved++;
            }
        }





        listViewEquipped.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                equipNum = (int)id;
                unequipRequest();
            }

        });

        listViewInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                equipNum = (int)id;
                equipRequest();
            }

        });

        for (int i = 0; i < equippedItems.length(); i++) {
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
        for (int i = 0; i < ownedItemsSansEquipped.length(); i++) {
            try {
                JSONObject jsonObject = ownedItemsSansEquipped.getJSONObject(i);
                String name = jsonObject.getString("itemName");
                String description = jsonObject.getString("description");

                // Create a ListItemObject and add it to the adapter
                ListItemObjectInventory item = new ListItemObjectInventory(name, description);
                //   ListAdapterInventory tmp = adapter;
                adapterInventory.add(item);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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


    private void unequipRequest() {

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                //"http://coms-309-033.class.las.iastate.edu:8080/users/signup",
                "https://1c9efe9d-cfe0-43f4-b7e3-dac1af491ecf.mock.pstmn.io/shop/unequip/fail",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    if(Objects.equals(response, "success")){
                        try {
                            user.removeEquippedItem(equipNum);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Intent intent = new Intent(InventoryActivity.this, InventoryActivity.class);
                        intent.putExtra("USEROBJ", user);
                        startActivity(intent);
                    }else{
                        Toast toast = new Toast(InventoryActivity.this);
                        toast.setText("Unequip Failed. Try Again.");
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                    }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
               // headers.put("username", );
                headers.put("itemNum", String.valueOf(equipNum));
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
    private void equipRequest() {

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                //"http://coms-309-033.class.las.iastate.edu:8080/users/signup",
                "https://1c9efe9d-cfe0-43f4-b7e3-dac1af491ecf.mock.pstmn.io/shop/equip/fail",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(Objects.equals(response, "success")){
                            try {
                                Gson gson = new Gson();
                                JSONObject temp = new JSONObject(gson.toJson(adapterInventory.getItem(equipNum)));
                                int inventoryNum = user.getItemPosition(temp);
                                user.addEquippedItem(ownedItems.getJSONObject(inventoryNum));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            Intent intent = new Intent(InventoryActivity.this, InventoryActivity.class);
                            intent.putExtra("USEROBJ", user);
                            startActivity(intent);
                        }else{
                            Toast toastEquip = new Toast(InventoryActivity.this);
                            toastEquip.setText("Equip Failed. Try Again.");
                            toastEquip.setDuration(Toast.LENGTH_LONG);
                            toastEquip.show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                // headers.put("username", );
                headers.put("itemNum", String.valueOf(equipNum));
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
