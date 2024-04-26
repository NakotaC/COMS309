package com.example.androidapp.Game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.example.androidapp.Game.Pieces.YellowPiece;
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

    private Button turnBtn, backBtn, drawBtn;
    private Button addMatchButton;
    private TextView turnText, playerText, headerText;
    private int numPlayers;
    private TurnManager turnmgr;
    private FrameLayout gameFrame;
    private ImageView gameBoard, yellowPiece1;
    private User user;
    private CheckBox piece1, piece2, piece3, piece4;
    int cardNum;
    private String serverUrl;
    int selectedPiece;

    private final YellowPiece[] yellowPieces= {new YellowPiece(1),
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
        drawBtn = findViewById(R.id.drawBtn);
        playerText = (TextView) findViewById(R.id.playerNumTxt);
        headerText = findViewById(R.id.inGameHeader);
        backBtn = findViewById(R.id.gameBackBtn);
        gameFrame = findViewById(R.id.frameLayoutGame);
        gameBoard = findViewById(R.id.gameBoard);
        yellowPiece1 = findViewById(R.id.yellowPiece1);
        piece1 = findViewById(R.id.checkBoxPiece1);
        piece2 = findViewById(R.id.checkBoxPiece2);
        piece3 = findViewById(R.id.checkBoxPiece3);
        piece4 = findViewById(R.id.checkBoxPiece4);

        //turnBtn.setVisibility(View.INVISIBLE);

        /* connect this activity to the websocket instance */
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(GameActivity.this);

//        addMatchButton.setOnClickListener(v -> {
//        postRequest();
//        });
        piece1.setOnClickListener(v -> {
            selectedPiece = 1;
            piece1.setChecked(true);
            piece2.setChecked(false);
            piece3.setChecked(false);
            piece4.setChecked(false);
        });
        piece2.setOnClickListener(v -> {
            selectedPiece = 2;
            piece2.setChecked(true);
            piece1.setChecked(false);
            piece3.setChecked(false);
            piece4.setChecked(false);
        });
        piece3.setOnClickListener(v -> {
            selectedPiece = 3;
            piece3.setChecked(true);
            piece1.setChecked(false);
            piece2.setChecked(false);
            piece4.setChecked(false);
        });
        piece4.setOnClickListener(v -> {
            selectedPiece = 4;
            piece4.setChecked(true);
            piece1.setChecked(false);
            piece2.setChecked(false);
            piece3.setChecked(false);
        });
        backBtn.setOnClickListener(v -> {
            user.setGameId(0);
            user.setPlayerNum(0);
            Intent intent = new Intent(GameActivity.this, HomeActivity.class);
            intent.putExtra("USEROBJ", user);
            startActivity(intent);
        });
        /* send button listener */
        drawBtn.setOnClickListener(v -> {
            try {
            drawCard();
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
//        try {
//            obj = new JSONObject(message);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        if(user.getPlayerNum() == 0){
//            try {
//                user.setPlayerNum(obj.getInt("playerNum"));
//                numPlayers = obj.getInt("playerNum");
//               // user.setGameId(obj.getInt("gameId"));
//                user.setGameId(1);
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }

            user.setPlayerNum(Integer.parseInt(message));
            numPlayers = Integer.parseInt(message);
            turnmgr = new TurnManager(numPlayers);
            playerText.setText("You are player " + String.valueOf(user.getPlayerNum()));

            if(user.getPlayerNum() == turnmgr.getCurrTurn()){
                turnBtn.setVisibility(View.VISIBLE);
            }

        }else if(message.length() < 3){
//            try {
//                numPlayers = obj.getInt("playerNum");
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }

           numPlayers = Integer.parseInt(message);
            turnmgr = new TurnManager(numPlayers);

        } else {

            try {
                obj = new JSONObject(message);
                performTurn(obj.getInt("player"), 1, obj.getInt("Card"));
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
                "http://coms-309-033.class.las.iastate.edu:8080/draw/str",
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
               headers.put("gameid", String.valueOf(1));
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
            String msg = "{\"player\":\"" + user.getPlayerNum() + "\", \"Card\":\"" + cardNum + "\"}";
            // send message
            WebSocketManager.getInstance().sendMessage(msg);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage());
        }
    }
    private void performTurn(int playerNum, int PieceNum, int numToMove){
        if(playerNum == 1){
            yellowPieces[PieceNum-1].move(numToMove);
        }else if(playerNum == 2){
            yellowPieces[PieceNum-1].move(numToMove);
        }
        else if(playerNum == 3){
            yellowPieces[PieceNum-1].move(numToMove);
        }
        else if(playerNum == 4){
            yellowPieces[PieceNum-1].move(numToMove);
        }
        float transX = yellowPieces[PieceNum-1].getCurrX();
        float transY = yellowPieces[PieceNum-1].getCurrY();
        yellowPiece1.setX(yellowPieces[PieceNum-1].getCurrX());
        yellowPiece1.setY(yellowPieces[PieceNum-1].getCurrY());
    }
}