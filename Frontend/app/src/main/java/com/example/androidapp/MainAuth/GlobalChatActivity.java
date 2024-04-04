package com.example.androidapp.MainAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapp.Connectivity.WebSocketListener;
import com.example.androidapp.Connectivity.WebSocketManager;
import com.example.androidapp.Game.User;
import com.example.androidapp.R;

import org.java_websocket.handshake.ServerHandshake;

public class GlobalChatActivity extends AppCompatActivity implements WebSocketListener
{
    private EditText msgContent;
    private Button sendButton;
    private Button exitButton;
    private TextView textView1;
    private TextView chatHistory;
    private User user;
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_globalchat);
     Bundle extras = getIntent().getExtras();
     assert extras != null;
     user = (User) extras.getSerializable("USEROBJ");

     textView1 = findViewById(R.id.textView);
     msgContent.findViewById(R.id.msgContent);
     sendButton.findViewById(R.id.sendButton);
     chatHistory.findViewById(R.id.chatHistory);
     exitButton.findViewById(R.id.exitButton);



     String serverUrl = "http://coms-309-033.class.las.iastate.edu:8080/chat/" + user.getId();
     WebSocketManager.getInstance().connectWebSocket(serverUrl);
     WebSocketManager.getInstance().setWebSocketListener(GlobalChatActivity.this);

     sendButton.setOnClickListener(v -> {
         try {
             // send message
             WebSocketManager.getInstance().sendMessage(msgContent.getText().toString());
         } catch (Exception e) {
             Log.d("ExceptionSendMessage:", e.getMessage().toString());
         }
     });

     exitButton.setOnClickListener(view -> {
         // got to chat activity
         Intent intent = new Intent(this, HomeActivity.class);
         intent.putExtra("USEROBJ", user);
         startActivity(intent);
     });
 }

    @Override
    public void onWebSocketOpen(ServerHandshake message)
    {
        runOnUiThread(() -> {
            chatHistory.setText("Welcome " + user.getUsername());
        });
    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            String s = chatHistory.getText().toString();
            chatHistory.setText(s + "\n"+ message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote)
    {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = chatHistory.getText().toString();
            chatHistory.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}
