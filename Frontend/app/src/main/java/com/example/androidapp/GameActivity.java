package com.example.androidapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.GameObjs.TurnManager;
import com.example.androidapp.GameObjs.User;
import com.example.androidapp.connectivity.WebSocketListener;
import com.example.androidapp.connectivity.WebSocketManager;

import org.java_websocket.handshake.ServerHandshake;
public class GameActivity extends AppCompatActivity implements WebSocketListener {

    private Button turnBtn;

    private TextView turnText, playerText, headerText;

    private TurnManager turnmgr = new TurnManager();
    private User user;
    private String serverUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameactivity);

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
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });
    }


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

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = turnText.getText().toString();
            turnText.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}