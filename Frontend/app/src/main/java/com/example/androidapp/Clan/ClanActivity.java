package com.example.androidapp.Clan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.MainAuth.HomeActivity;
import com.example.androidapp.R;
import com.example.androidapp.Connectivity.VolleySingleton;
import com.google.android.material.appbar.MaterialToolbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class ClanActivity extends AppCompatActivity implements View.OnClickListener {






    private RecyclerView courseRV;






    private ClanRecyclerViewAdapter adapter;
    private LinkedList<ClanItemObject> clanItemList;
    private MaterialToolbar materialToolbar;
    private Button newClanButton;
    private TextView textView1;
    private TextView textView2;







    String url = "https://7715c946-ec19-485b-aca3-cab84de8d329.mock.pstmn.io/clans";
    private final String URL_POST_REQUEST = "http://coms-309-033.class.las.iastate.edu:8080/clans/";
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan);


        courseRV = findViewById(R.id.idRVCourses);
        materialToolbar = findViewById(R.id.materialToolbar);
        newClanButton = findViewById(R.id.newClanButton);
        textView1 = findViewById(R.id.clanNameInput);
        textView2 = findViewById(R.id.UserIdInput);

        materialToolbar.setOnClickListener(this);
        newClanButton.setOnClickListener(this);

        clanItemList = new LinkedList<>();
        parseJsonArray();

        constructRecyclerView();

    }
    @Override
    public void onClick(View v)
    {
        int id1 = v.getId();
        if (id1 == R.id.materialToolbar) {
            startActivity(new Intent(ClanActivity.this, HomeActivity.class));
        } else if (id1 == R.id.newClanButton)
        {
            postRequest();
            Toast.makeText(ClanActivity.this, "Clan has been successfully added", Toast.LENGTH_SHORT).show();
        }
    }



    private void parseJsonArray() {
        RequestQueue queue = Volley.newRequestQueue(ClanActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String clanName = responseObj.getString("clanName");
                        JSONArray members = responseObj.getJSONArray("members");
                        for (int j = 0; j < members.length(); j++)
                        {
                        Log.d("Clan Members", members.toString());
                        }
                        int clanLevel = responseObj.getInt("leader");
                        clanItemList.add(new ClanItemObject(clanName, String.valueOf(clanLevel)));
                        constructRecyclerView();

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                }

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ClanActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }

        });
        queue.add(jsonArrayRequest);

    }

    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;
        String finalUrlPostRequest = "";
        try {
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            finalUrlPostRequest = URL_POST_REQUEST + textView1.getText().toString() + "/";
            finalUrlPostRequest = finalUrlPostRequest + textView2.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                finalUrlPostRequest,
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
                                Toast.makeText(ClanActivity.this, "Clan has been successfully added", Toast.LENGTH_SHORT).show();
                            }
                            else if (responseString.equals("failure"))
                            {
                                Toast.makeText(ClanActivity.this, "Error", Toast.LENGTH_SHORT).show();
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



    private void constructRecyclerView()
    {
        adapter = new ClanRecyclerViewAdapter(clanItemList, ClanActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(adapter);

    }


}