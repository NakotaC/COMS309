package com.example.androidapp.MainAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidapp.R;
import com.example.androidapp.Game.User;
import com.example.androidapp.Connectivity.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle the signup screen
 */
public class SignupActivity extends AppCompatActivity {
    private Button signupBtn;
    private Button backBtn;
    private TextView header;
    private EditText usernameEntry;
    private TextView usernameTakenTxt;
    private String username;

    private String password;

    private EditText passwordEntry;

private User user;
    private static final String url = "https://ed481f0d-bd99-4a49-8fe0-e84d74d506f6.mock.pstmn.io/signuptest";
    // private static final String URL_STRING_REQ = "coms-309-033.class.las.iastate.edu:8080/login";

    /**
     * Handles the elements and functionality of the screen when the screen is created
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupBtn = findViewById(R.id.signup_button);
        backBtn = findViewById(R.id.back_button);
        header = findViewById(R.id.header);
        passwordEntry = findViewById(R.id.password_entry);
        usernameEntry = findViewById(R.id.username_entry);
        usernameTakenTxt = findViewById(R.id.user_taken_text);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordEntry.getText().toString();
                username = usernameEntry.getText().toString();
                postRequest();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });

    }
    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-309-033.class.las.iastate.edu:8080/users/signup",
               //url,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String responseString;

                        try {
                            responseString = response.getString("message").replaceAll("\"", "");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                         if(responseString.equals("success")){

                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        }else{
                            usernameTakenTxt.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        usernameTakenTxt.setText(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                               headers.put("username", username);
                                headers.put("password", password);
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

