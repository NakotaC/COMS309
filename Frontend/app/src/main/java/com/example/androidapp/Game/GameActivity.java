package com.example.androidapp.Game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidapp.Connectivity.VolleySingleton;
import com.example.androidapp.Connectivity.WebSocketListener;
import com.example.androidapp.Connectivity.WebSocketManager;
import com.example.androidapp.MainAuth.HomeActivity;
import com.example.androidapp.R;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle and represent the Game screen
 */
public class GameActivity extends AppCompatActivity implements WebSocketListener {

    private Button turnBtn, backBtn;
    private Button addMatchButton;
    private TextView turnText, playerText, headerText;
    private int numPlayers;
    private final TurnManager turnmgr = new TurnManager();
    private User user;
    int cardNum;
    private String serverUrl;

    private  YellowPiece[] yellowPieces= {new YellowPiece(1),
            new YellowPiece(2),
            new YellowPiece(3),
            new YellowPiece(4)};


    /**
     * Handles the creation and functionality of screen elements when the screen is created
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        user = (User) extras.getSerializable("USEROBJ");

       serverUrl = "ws://coms-309-033.class.las.iastate.edu:8080/game/" + user.getUsername();
        /* initialize UI elements */
        turnBtn = (Button) findViewById(R.id.turnBtn);
        playerText = (TextView) findViewById(R.id.playerNumTxt);
        headerText = findViewById(R.id.inGameHeader);
        backBtn = findViewById(R.id.gameBackBtn);

        turnBtn.setVisibility(View.INVISIBLE);

        /* connect this activity to the websocket instance */
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(GameActivity.this);

        addMatchButton.setOnClickListener(v -> {
        postRequest();
        });
        backBtn.setOnClickListener(v -> {
            user.setGameId(0);
            user.setPlayerNum(0);
            Intent intent = new Intent(GameActivity.this, HomeActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        });
        /* send button listener */
        turnBtn.setOnClickListener(v -> {
            try {
            drawCard();
            String msg = "{\"playerNum\":\"" + user.getPlayerNum() + "\", \"Card\":\""+ String.valueOf(cardNum) + "\"}";
            // send message
            WebSocketManager.getInstance().sendMessage(msg);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage());
        }
        });
    }

    /**
     * Handles what to do when a websocket message is received
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        JSONObject obj;
        try {
            obj = new JSONObject(message);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if(user.getPlayerNum() == 0){
            try {
                user.setPlayerNum(obj.getInt("playerNum"));
                numPlayers = obj.getInt("playerNum");
                user.setGameId(obj.getInt("gameId"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            if(user.getPlayerNum() == turnmgr.getCurrTurn()){
                turnBtn.setVisibility(View.VISIBLE);
            }
        }else if(obj.has("gameId")){
            try {
                numPlayers = obj.getInt("playerNum");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {

            try {
                yellowPieces[0].move(obj.getInt("Card"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Handles what to do when a websocket connection is closed
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = turnText.getText().toString();
            turnText.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    /**
     * Handles what to do when a Websocket is opened
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    /**
     * Handles what to do when an error is received
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {}
    private void drawCard() {

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://coms-309-033.class.las.iastate.edu:8080/draw" + user.getGameId(),
                 //"https://1c9efe9d-cfe0-43f4-b7e3-dac1af491ecf.mock.pstmn.io/draw2",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());

                        try {
                            cardNum = response.getInt("Card");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        sendMessage();
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
               headers.put("gameid", "1");
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(objectRequest);
    }
    private void postRequest() {
        int winner = user.getId() + 1;
        // Convert input to JSONObject
        JSONObject postBody = null;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://coms-309-033.class.las.iastate.edu:8080/history/" + user.getId() + "/" +
                        winner + "/",
                //url,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String responseString;

                        try {
                            Log.d("Volley Response", response.toString());
                            responseString = response.getString("message").replaceAll("\"", "");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                            Toast.makeText(GameActivity.this, "Match added",
                                    Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  usernameTakenTxt.setText(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
            //    headers.put("username", username);
            //    headers.put("password", password);
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
    private int Card() {
        drawCard();
        return 1;
    }
    private void sendMessage() {
        try {
            String msg = "{\"player\":\"" + user.getPlayerNum() + "\", \"Card\":\"" + String.valueOf(cardNum) + "\"}";
            // send message
            WebSocketManager.getInstance().sendMessage(msg);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage());
        }
    }
}