package com.example.androidapp.MainAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidapp.HomeActivity;
import com.example.androidapp.R;
import com.example.androidapp.GameObjs.User;
import com.example.androidapp.connectivity.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private Button backBtn;
    private TextView header;
    private EditText usernameEntry;
    private TextView wrongPassTxt;
    private String username;

    private String password;

    private EditText passwordEntry;

    private static final String URL_STRING_REQ = "https://ed481f0d-bd99-4a49-8fe0-e84d74d506f6.mock.pstmn.io/login5";
   // private static final String URL_STRING_REQ = "coms-309-033.class.las.iastate.edu:8080/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        loginBtn = findViewById(R.id.login_button);
        backBtn = findViewById(R.id.back_button);
        header = findViewById(R.id.header);
        passwordEntry = findViewById(R.id.password_entry);
        usernameEntry = findViewById(R.id.username_entry);
        wrongPassTxt = findViewById(R.id.wrongPass);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordEntry.getText().toString();
                username = usernameEntry.getText().toString();
                makeStringReq();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }
    private void makeStringReq() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                //"http://coms-309-033.class.las.iastate.edu:8080/users/login",
                URL_STRING_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Volley Response", response);
                        String responseStr = response.replaceAll("\"", "");
                        JSONObject responseObj = null;
                        try {
                          responseObj = new JSONObject(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        if(responseObj.has("username")){
                           Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            User user = null;
                            try {
                                user = new User(responseObj);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                           intent.putExtra("USEROBJ", user);

                            startActivity(intent);
                        }else{
                            wrongPassTxt.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("username", username);
                headers.put("password", password);
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}

