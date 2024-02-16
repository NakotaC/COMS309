package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonObjReqActivity extends AppCompatActivity {

    private Button btnJsonObjReq;
    private TextView msgResponse;

    private Button menu_button;

    private static final String URL_JSON_OBJECT = "https://ef1121ad-2002-4195-9fa4-b838cf616118.mock.pstmn.io/Object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_obj_req);

        btnJsonObjReq = findViewById(R.id.btnJsonObj);
        msgResponse = findViewById(R.id.msgResponse);
        menu_button = findViewById(R.id.menu_button);

        btnJsonObjReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonObjReq();
            }
        });
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JsonObjReqActivity.this, MainActivity.class));
            }
        });
    }

    /**
     * Making json object request
     */
    private void makeJsonObjReq() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                URL_JSON_OBJECT,
                null, // Pass null as the request body since it's a GET request
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());
                        msgResponse.setText(response.toString());
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }
}