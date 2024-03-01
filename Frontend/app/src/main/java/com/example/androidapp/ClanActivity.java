package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.LinkedList;


public class ClanActivity extends AppCompatActivity implements View.OnClickListener {






    private RecyclerView courseRV;






    private RecyclerViewAdapter adapter;
    private LinkedList<ClanItemObject> clanItemList;
    private MaterialToolbar materialToolbar;
    private Button newClanButton;







    String url = "https://7715c946-ec19-485b-aca3-cab84de8d329.mock.pstmn.io/clans";
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan);


        courseRV = findViewById(R.id.idRVCourses);
        materialToolbar = findViewById(R.id.materialToolbar);

        materialToolbar.setOnClickListener(this);

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
        } //else if (id1 == R.id.newClanButton) {
           // startActivity(new Intent(ClanActivity.this, LeaderboardActivity.class));
       // }
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
                        String clanLevel = responseObj.getString("clanLevel");
                        String clanAvailability = responseObj.getString("clanAvailability");
                        clanItemList.add(new ClanItemObject(clanName, clanLevel, clanAvailability));
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



    private void constructRecyclerView()
    {
        adapter = new RecyclerViewAdapter(clanItemList, ClanActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(adapter);

    }


}