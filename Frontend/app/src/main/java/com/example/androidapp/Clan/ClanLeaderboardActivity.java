package com.example.androidapp.Clan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidapp.Game.User;
import com.example.androidapp.R;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Stack;

public class ClanLeaderboardActivity extends AppCompatActivity implements View.OnClickListener
{
    private MaterialToolbar materialToolbar;
    private ListView membersList;
    private String members;
    private User user;
    private Stack<String> usernames = new Stack<>();
    ListAdapterClanLeaderboard adapter;
    private String USERS_URL = "http://coms-309-033.class.las.iastate.edu:8080/users/";
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clanleaderboard);
        Bundle extras = getIntent().getExtras();
        assert extras != null;

        members = (String) extras.getSerializable("membersList");
        user = (User) extras.getSerializable("USEROBJ");

        materialToolbar = findViewById(R.id.membersListToolbar);
        membersList = findViewById(R.id.membersList);

        materialToolbar.setOnClickListener(this);

        adapter = new ListAdapterClanLeaderboard(this, new LinkedList<>());
        membersList.setAdapter(adapter);

        String usernameList = members.replaceAll(",", "").replace("[",
                "").replace("]", "");


        if (!usernameList.equals(""))
        {
        String[] usersList = usernameList.split(" ");

        for (int i = 0; i < usersList.length; i++)
        {
        userGetRequest(Integer.parseInt(usersList[i]));
        }
        }
        else if (usernameList.equals(""))
        {
        ClanLeaderboardItemObject randomMember = new ClanLeaderboardItemObject("None");
        adapter.add(randomMember);
        }





    }

    @Override
    public void onClick(View v)
    {
    Intent intent = new Intent(ClanLeaderboardActivity.this, ClanActivity.class);
    intent.putExtra("USEROBJ", user);
    startActivity(intent);
    }

    public void userGetRequest(int userId)
    {
        RequestQueue queue = Volley.newRequestQueue(ClanLeaderboardActivity.this);

        String url = USERS_URL + userId + "/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    String username = response.getString("username");
                    ClanLeaderboardItemObject member = new ClanLeaderboardItemObject(username);
                    adapter.add(member);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(ClanLeaderboardActivity.this, "Failed to get the data..", Toast.LENGTH_SHORT).show();
            }

        });
        queue.add(jsonObjectRequest);
    }
}
