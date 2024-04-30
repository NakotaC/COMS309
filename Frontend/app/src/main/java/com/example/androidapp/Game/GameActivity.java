package com.example.androidapp.Game;

import static android.view.View.INVISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import com.example.androidapp.Game.Pieces.BluePiece;
import com.example.androidapp.Game.Pieces.GreenPiece;
import com.example.androidapp.Game.Pieces.RedPiece;
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
    private TextView turnText, playerText, headerText, drawnText;
    private  TextView pieceNum1, pieceNum2, pieceNum3, pieceNum4;
    private int numPlayers;
    private TurnManager turnmgr;
    private FrameLayout gameFrame;
    private ImageView gameBoard, yellowPiece1, yellowPiece2, yellowPiece3, yellowPiece4;
    private ImageView greenPiece1, greenPiece2, greenPiece3, greenPiece4;
    private ImageView redPiece1, redPiece2, redPiece3, redPiece4;
    private ImageView bluePiece1, bluePiece2, bluePiece3, bluePiece4;
    private User user;
    private CheckBox piece1, piece2, piece3, piece4;
    int cardNum;
    private String serverUrl;
    int selectedPiece;

    private final YellowPiece[] yellowPieces = {new YellowPiece(1),
            new YellowPiece(2),
            new YellowPiece(3),
            new YellowPiece(4)};

    private final GreenPiece[] greenPieces = {new GreenPiece(1),
            new GreenPiece(2),
            new GreenPiece(3),
            new GreenPiece(4)};
    private final RedPiece[] redPieces = new RedPiece[]{new RedPiece(1),
            new RedPiece(2),
            new RedPiece(3),
            new RedPiece(4)};
    private final BluePiece[] bluePieces = {new BluePiece(1),
            new BluePiece(2),
            new BluePiece(3),
            new BluePiece(4)};
    /**
     * Handles the creation and functionality of screen elements when the screen is created
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        user = (User) extras.getSerializable("USEROBJ");
        if(user.getGameId() != 0) {
            serverUrl = "ws://coms-309-033.class.las.iastate.edu:8080/game" + user.getGameId() +"/" + user.getUsername();
        }else{
            serverUrl = "ws://coms-309-033.class.las.iastate.edu:8080/game/" + user.getUsername();
        }
        /* initialize UI elements */
        turnBtn = (Button) findViewById(R.id.turnBtn);
        drawBtn = findViewById(R.id.drawBtn);
        playerText = (TextView) findViewById(R.id.playerNumTxt);
        headerText = findViewById(R.id.inGameHeader);
        backBtn = findViewById(R.id.gameBackBtn);
        gameFrame = findViewById(R.id.frameLayoutGame);
        drawnText = findViewById(R.id.drawnValText);
        gameBoard = findViewById(R.id.gameBoard);
        pieceNum1 = findViewById(R.id.pieceNum1);
        pieceNum2 = findViewById(R.id.pieceNum2);
        pieceNum3 = findViewById(R.id.pieceNum3);
        pieceNum4 = findViewById(R.id.pieceNum4);
        yellowPiece1 = findViewById(R.id.yellowPiece1);
        yellowPiece2 = findViewById(R.id.yellowPiece2);
        yellowPiece3 = findViewById(R.id.yellowPiece3);
        yellowPiece4 = findViewById(R.id.yellowPiece4);
        redPiece1 = findViewById(R.id.redPiece1);
        redPiece2 = findViewById(R.id.redPiece2);
        redPiece3 = findViewById(R.id.redPiece3);
        redPiece4 = findViewById(R.id.redPiece4);
        bluePiece1 = findViewById(R.id.bluePiece1);
        bluePiece2 = findViewById(R.id.bluePiece2);
        bluePiece3 = findViewById(R.id.bluePiece3);
        bluePiece4 = findViewById(R.id.bluePiece4);
        greenPiece1 = findViewById(R.id.greenPiece1);
        greenPiece2 = findViewById(R.id.greenPiece2);
        greenPiece3 = findViewById(R.id.greenPiece3);
        greenPiece4 = findViewById(R.id.greenPiece4);

        piece1 = findViewById(R.id.checkBoxPiece1);
        piece2 = findViewById(R.id.checkBoxPiece2);
        piece3 = findViewById(R.id.checkBoxPiece3);
        piece4 = findViewById(R.id.checkBoxPiece4);

        turnBtn.setVisibility(INVISIBLE);
        drawBtn.setVisibility(View.INVISIBLE);
        drawnText.setVisibility(INVISIBLE);
        pieceNum1.setVisibility(View.INVISIBLE);
        pieceNum2.setVisibility(View.INVISIBLE);
        pieceNum3.setVisibility(View.INVISIBLE);
        pieceNum4.setVisibility(View.INVISIBLE);

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
        turnBtn.setOnClickListener(v -> {
            if(selectedPiece != 0){
                sendMessage();
                turnBtn.setVisibility(INVISIBLE);
                drawnText.setVisibility(INVISIBLE);
                pieceNum1.setVisibility(View.INVISIBLE);
                pieceNum2.setVisibility(View.INVISIBLE);
                pieceNum3.setVisibility(View.INVISIBLE);
                pieceNum4.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * Handles what to do when a websocket message is received
     *
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

        if (user.getPlayerNum() == 0) {
            int gameId;
            try {
                 gameId = obj.getInt("gameid");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            if(gameId != 1){
                if(user.getGameId() == 0) {

                    try {
                        user.setGameId(obj.getInt("gameid"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intent = new Intent(GameActivity.this, GameActivity.class);
                    intent.putExtra("USEROBJ", user);
                    startActivity(intent);

                }else{
                    try {
                        user.setPlayerNum(obj.getInt("playernum"));
                        numPlayers = obj.getInt("playernum");
                        user.setGameId(obj.getInt("gameid"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                runOnUiThread(() -> {
                    playerText.setText("You are player " + user.getPlayerNum());
                });
            }else{
            try {
                user.setPlayerNum(obj.getInt("playernum"));
                numPlayers = obj.getInt("playernum");
                user.setGameId(obj.getInt("gameid"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            }
            turnmgr = new TurnManager(numPlayers);

            runOnUiThread(() -> {
                playerText.setText("You are player " + user.getPlayerNum());
            });

            if (user.getPlayerNum() == turnmgr.getCurrTurn()) {
                 runOnUiThread(() -> {
                drawBtn.setVisibility(View.VISIBLE);
            });
            }
        } else if (obj.has("gameid")) {

            try {
                numPlayers = obj.getInt("playernum");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            turnmgr = new TurnManager(numPlayers);

        } else{

            try {
                performTurn(obj.getInt("player"), obj.getInt("pieceNum"), obj.getInt("Card"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            if (user.getPlayerNum() == turnmgr.getCurrTurn() && checkWin() == 0) {
                runOnUiThread(() -> {
                    drawBtn.setVisibility(View.VISIBLE);
                });
            }
        }

    }

    /**
     * Handles what to do when a websocket connection is closed
     *
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
           // String s = turnText.getText().toString();
       //     turnText.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    /**
     * Handles what to do when a Websocket is opened
     *
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
    }

    /**
     * Handles what to do when an error is received
     *
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {
    }

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
                        drawnText.setText("You Drew A " + cardNum);
                        drawnText.setVisibility(View.VISIBLE);
                        drawBtn.setVisibility(INVISIBLE);
                        turnBtn.setVisibility(View.VISIBLE);
                        if (user.getPlayerNum() == 1) {
                            pieceNum1.setTranslationX(yellowPiece1.getX() + 80);
                            pieceNum1.setTranslationY(yellowPiece1.getY() + 50);
                            pieceNum2.setTranslationX(yellowPiece2.getX() + 80);
                            pieceNum2.setTranslationY(yellowPiece2.getY()+ 50);
                            pieceNum3.setTranslationX(yellowPiece3.getX() + 80);
                            pieceNum3.setTranslationY(yellowPiece3.getY()+ 50);
                            pieceNum4.setTranslationX(yellowPiece4.getX() + 80);
                            pieceNum4.setTranslationY(yellowPiece4.getY()+ 50);
                        }else if (user.getPlayerNum() == 2) {
                            pieceNum1.setTranslationX(greenPiece1.getX() + 80);
                            pieceNum1.setTranslationY(greenPiece1.getY()+ 10);
                            pieceNum2.setTranslationX(greenPiece2.getX() + 80);
                            pieceNum2.setTranslationY(greenPiece2.getY()+ 10);
                            pieceNum3.setTranslationX(greenPiece3.getX() + 80);
                            pieceNum3.setTranslationY(greenPiece3.getY()+ 10);
                            pieceNum4.setTranslationX(greenPiece4.getX() + 80);
                            pieceNum4.setTranslationY(greenPiece4.getY()+ 10);
                        }else if (user.getPlayerNum() == 3) {
                            pieceNum1.setTranslationX(redPiece1.getX() + 80);
                            pieceNum1.setTranslationY(redPiece1.getY()+ 10);
                            pieceNum2.setTranslationX(redPiece2.getX() + 80);
                            pieceNum2.setTranslationY(redPiece2.getY()+ 10);
                            pieceNum3.setTranslationX(redPiece3.getX() + 80);
                            pieceNum3.setTranslationY(redPiece3.getY()+ 10);
                            pieceNum4.setTranslationX(redPiece4.getX() + 80);
                            pieceNum4.setTranslationY(redPiece4.getY()+ 10);
                        }else if (user.getPlayerNum() == 4) {
                            pieceNum1.setTranslationX(bluePiece1.getX() + 80);
                            pieceNum1.setTranslationY(bluePiece1.getY()+ 10);
                            pieceNum2.setTranslationX(bluePiece2.getX() + 80);
                            pieceNum2.setTranslationY(bluePiece2.getY()+ 10);
                            pieceNum3.setTranslationX(bluePiece3.getX() + 80);
                            pieceNum3.setTranslationY(bluePiece3.getY()+ 10);
                            pieceNum4.setTranslationX(bluePiece4.getX() + 80);
                            pieceNum4.setTranslationY(bluePiece4.getY()+ 10);
                        }


                        pieceNum1.setVisibility(View.VISIBLE);
                        pieceNum2.setVisibility(View.VISIBLE);
                        pieceNum3.setVisibility(View.VISIBLE);
                        pieceNum4.setVisibility(View.VISIBLE);

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
                headers.put("gameid", String.valueOf(user.getGameId()));
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
        ) {
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
            String msg = "{\"player\":\"" + user.getPlayerNum() + "\", \"Card\":\"" + cardNum + "\", \"pieceNum\": \"" + selectedPiece +"\"}";
            // send message
            WebSocketManager.getInstance().sendMessage(msg);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage());
        }
    }

    private void performTurn(int playerNum, int PieceNum, int numToMove) {
        if (playerNum == 1) {
            yellowPieces[PieceNum - 1].move(numToMove);
            if(PieceNum == 1) {
                float transX = yellowPiece1.getX() + convertDpIntoPx(this, yellowPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = yellowPiece1.getY() + convertDpIntoPx(this, yellowPieces[PieceNum - 1].getDeltaYFromLastMove());
                yellowPiece1.setTranslationX(transX);
                yellowPiece1.setTranslationY(transY);
            } else if(PieceNum == 2) {
                float transX = yellowPiece2.getX() + convertDpIntoPx(this, yellowPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = yellowPiece2.getY() + convertDpIntoPx(this, yellowPieces[PieceNum - 1].getDeltaYFromLastMove());
                yellowPiece2.setTranslationX(transX);
                yellowPiece2.setTranslationY(transY);
            }else if(PieceNum == 3) {
                float transX = yellowPiece3.getX() + convertDpIntoPx(this, yellowPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = yellowPiece3.getY() + convertDpIntoPx(this, yellowPieces[PieceNum - 1].getDeltaYFromLastMove());
                yellowPiece3.setTranslationX(transX);
                yellowPiece3.setTranslationY(transY);
            }else if(PieceNum == 4) {
                float transX = yellowPiece4.getX() + convertDpIntoPx(this, yellowPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = yellowPiece4.getY() + convertDpIntoPx(this, yellowPieces[PieceNum - 1].getDeltaYFromLastMove());
                yellowPiece4.setTranslationX(transX);
                yellowPiece4.setTranslationY(transY);
            }
        } else if (playerNum == 2) {
            greenPieces[PieceNum - 1].move(numToMove);
            if(PieceNum == 1) {
                float transX = greenPiece1.getX() + convertDpIntoPx(this, greenPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = greenPiece1.getY() + convertDpIntoPx(this, greenPieces[PieceNum - 1].getDeltaYFromLastMove());
                greenPiece1.setTranslationX(transX);
                greenPiece1.setTranslationY(transY);
            } else if(PieceNum == 2) {
                float transX = greenPiece2.getX() + convertDpIntoPx(this, greenPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = greenPiece2.getY() + convertDpIntoPx(this, greenPieces[PieceNum - 1].getDeltaYFromLastMove());
                greenPiece2.setTranslationX(transX);
                greenPiece2.setTranslationY(transY);
            }else if(PieceNum == 3) {
                float transX = greenPiece3.getX() + convertDpIntoPx(this, greenPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = greenPiece3.getY() + convertDpIntoPx(this, greenPieces[PieceNum - 1].getDeltaYFromLastMove());
                greenPiece3.setTranslationX(transX);
                greenPiece3.setTranslationY(transY);
            }else if(PieceNum == 4) {
                float transX = greenPiece4.getX() + convertDpIntoPx(this, greenPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = greenPiece4.getY() + convertDpIntoPx(this, greenPieces[PieceNum - 1].getDeltaYFromLastMove());
                greenPiece4.setTranslationX(transX);
                greenPiece4.setTranslationY(transY);
            }
        } else if (playerNum == 3) {
            redPieces[PieceNum - 1].move(numToMove);
            if(PieceNum == 1) {
                float transX = redPiece1.getX() + convertDpIntoPx(this, redPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = redPiece1.getY() + convertDpIntoPx(this, redPieces[PieceNum - 1].getDeltaYFromLastMove());
                redPiece1.setTranslationX(transX);
                redPiece1.setTranslationY(transY);
            } else if(PieceNum == 2) {
                float transX = redPiece2.getX() + convertDpIntoPx(this, redPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = redPiece2.getY() + convertDpIntoPx(this, redPieces[PieceNum - 1].getDeltaYFromLastMove());
                redPiece2.setTranslationX(transX);
                redPiece2.setTranslationY(transY);
            }else if(PieceNum == 3) {
                float transX = redPiece3.getX() + convertDpIntoPx(this, redPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = redPiece3.getY() + convertDpIntoPx(this, redPieces[PieceNum - 1].getDeltaYFromLastMove());
                redPiece3.setTranslationX(transX);
                redPiece3.setTranslationY(transY);
            }else if(PieceNum == 4) {
                float transX = redPiece4.getX() + convertDpIntoPx(this, redPieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = redPiece4.getY() + convertDpIntoPx(this, redPieces[PieceNum - 1].getDeltaYFromLastMove());
                redPiece4.setTranslationX(transX);
                redPiece4.setTranslationY(transY);
            }
        } else if (playerNum == 4) {
            bluePieces[PieceNum - 1].move(numToMove);
            if(PieceNum == 1) {
                float transX = bluePiece1.getX() + convertDpIntoPx(this, bluePieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = bluePiece1.getY() + convertDpIntoPx(this, bluePieces[PieceNum - 1].getDeltaYFromLastMove());
                bluePiece1.setTranslationX(transX);
                bluePiece1.setTranslationY(transY);
            } else if(PieceNum == 2) {
                float transX = bluePiece2.getX() + convertDpIntoPx(this, bluePieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = bluePiece2.getY() + convertDpIntoPx(this, bluePieces[PieceNum - 1].getDeltaYFromLastMove());
                bluePiece2.setTranslationX(transX);
                bluePiece2.setTranslationY(transY);
            }else if(PieceNum == 3) {
                float transX = bluePiece3.getX() + convertDpIntoPx(this, bluePieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = bluePiece3.getY() + convertDpIntoPx(this, bluePieces[PieceNum - 1].getDeltaYFromLastMove());
                bluePiece3.setTranslationX(transX);
                bluePiece3.setTranslationY(transY);
            }else if(PieceNum == 4) {
                float transX = bluePiece4.getX() + convertDpIntoPx(this, bluePieces[PieceNum - 1].getDeltaXFromLastMove());
                float transY = bluePiece4.getY() + convertDpIntoPx(this, bluePieces[PieceNum - 1].getDeltaYFromLastMove());
                bluePiece4.setTranslationX(transX);
                bluePiece4.setTranslationY(transY);
            }
        }
        if(numToMove != 2) {
            turnmgr.takeTurn();
        }
        int winner = checkWin();
        if(winner != 0){
            runOnUiThread(() -> {
                playerText.setText("The Winner is Player " + winner);
                drawBtn.setVisibility(INVISIBLE);
            });
            postRequest();
        }

    }


    public static int convertDpIntoPx(Context mContext, float yourdpmeasure) {
        if (mContext == null) {
            return 0;
        }
        Resources r = mContext.getResources();
        int px = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, yourdpmeasure, r.getDisplayMetrics());
        return px;
    }

    public int checkWin(){
        int yellowCount = 0, greenCount = 0, blueCount = 0, redCount = 0;
        int result = 0;
        for(int i = 0; i < 4; i++){
            if(yellowPieces[i].getCurrArea() == 3){
                yellowCount++;
            }else  if(greenPieces[i].getCurrArea() == 3){
                greenCount++;
            }else  if(redPieces[i].getCurrArea() == 3){
                redCount++;
            }else  if(bluePieces[i].getCurrArea() == 3){
                blueCount++;
            }
        }
        if(yellowCount == 4){
            result = 1;
        }else if(redCount == 4){
            result = 2;
        }else if(greenCount == 4){
            result = 3;
        }else if(blueCount == 4){
            result = 4;
        }
        return result;
    }
}