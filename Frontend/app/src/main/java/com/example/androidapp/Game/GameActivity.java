package com.example.androidapp.Game;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.Connectivity.WebSocketListener;
import com.example.androidapp.Connectivity.WebSocketManager;
import com.example.androidapp.R;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Class to handle and represent the Game screen
 */
public class GameActivity extends AppCompatActivity implements WebSocketListener {

    private Button turnBtn;

    private TextView turnText, playerText, headerText;

    private final TurnManager turnmgr = new TurnManager();
    private User user;
    private final String serverUrl = "";

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

        /* initialize UI elements */
        turnBtn = (Button) findViewById(R.id.turnBtn);
        turnText = (TextView) findViewById(R.id.TurnText);
        playerText = (TextView) findViewById(R.id.playerNumTxt);
        headerText = findViewById(R.id.inGameHeader);

        turnBtn.setVisibility(View.INVISIBLE);

        /* connect this activity to the websocket instance */
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(GameActivity.this);

        /* send button listener */
        turnBtn.setOnClickListener(v -> {
            try {
                // send message
                WebSocketManager.getInstance().sendMessage(String.valueOf(user.getPlayerNum()));
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
        if(user.getPlayerNum() == 0){
            user.setPlayerNum(Integer.parseInt(message));
            if(user.getPlayerNum() == turnmgr.getCurrTurn()){
                turnBtn.setVisibility(View.VISIBLE);
            }
        }else {
            runOnUiThread(() -> {
                String s = turnText.getText().toString();
                turnText.setText(s + "\n" + message);
            });
            turnmgr.takeTurn();
            if(user.getPlayerNum() == turnmgr.getCurrTurn()){
                turnBtn.setVisibility(View.VISIBLE);
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
}