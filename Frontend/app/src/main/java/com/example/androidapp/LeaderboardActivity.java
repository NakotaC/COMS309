package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.button.MaterialButton;

public class LeaderboardActivity extends AppCompatActivity implements View.OnClickListener {
    public Button button1;
    public TextView text1;
    public Button button2;
    public Button button3;
    private MaterialToolbar toolbar1;
    private MaterialButton addWins;
    private EditText textInput1;
    private EditText textInput2;
    private ListAdapter adapter1;
    private ListView list1;
    private LinkedList clanListRandom;
    private static final String URL_LEADERBOARD_JSON_ARRAY =
            "https://7715c946-ec19-485b-aca3-cab84de8d329.mock.pstmn.io/users";
    private static final String URL_WINS_JSON_ARRAY =
            "https://7715c946-ec19-485b-aca3-cab84de8d329.mock.pstmn.io/wins";
    private static String URL_POST_REQUEST =
            "https://7715c946-ec19-485b-aca3-cab84de8d329.mock.pstmn.io/wins/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        toolbar1 = findViewById(R.id.toolbar1);
        addWins = findViewById(R.id.addWinsButton);
        textInput1 = findViewById(R.id.editTextUserId);
        textInput2 = findViewById(R.id.editTextAmount);
        list1 = findViewById(R.id.list1);


        toolbar1.setOnClickListener(this);
        addWins.setOnClickListener(this);


        adapter1 = new ListAdapter(this, new LinkedList<>());
        list1.setAdapter(adapter1);

        makeLeaderboardJsonArrayReq();


    }

    @Override
    public void onClick(View v) {
        int id1 = v.getId();
        if (id1 == R.id.toolbar1) {
            startActivity(new Intent(LeaderboardActivity.this, MainActivity.class));
        }
        else if (id1 == R.id.addWinsButton)
        {
        postRequest();
        }
    }

    private void makeLeaderboardJsonArrayReq() {
        LinkedList<String> names = new LinkedList();
        LinkedList<String> wins = new LinkedList();
        JsonArrayRequest jsonArrReq1 = new JsonArrayRequest(
                Request.Method.GET,
                URL_LEADERBOARD_JSON_ARRAY,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        System.out.println(response.toString());

                        // Parse the JSON array and add data to the adapter
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                names.add(i, jsonObject.getString("name"));


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

        JsonArrayRequest jsonArrReq2 = new JsonArrayRequest(
                Request.Method.GET,
                URL_WINS_JSON_ARRAY,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Volley Response", response.toString());
                        System.out.println(response.toString());

                        // Parse the JSON array and add data to the adapter
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                wins.add(i, jsonObject.getString("wins"));

                                // Create a ListItemObject and add it to the adapter
                                LeaderboardItemObject item = new LeaderboardItemObject(names.get(i),
                                        wins.get(i));
                                adapter1.add(item);

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
                });

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq1);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq2);
    }

    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;
        String finalUrlPostRequest = "";
        try {
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            finalUrlPostRequest = URL_POST_REQUEST + textInput1.getText().toString();
            postBody = new JSONObject(textInput2.getText().toString());
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
