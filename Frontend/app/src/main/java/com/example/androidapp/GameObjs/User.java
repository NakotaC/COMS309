package com.example.androidapp.GameObjs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private SerializableJSONArray ownedItems;
    private int bank;

    public int getPlayerNum() {
        return playerNum;
    }

    private int playerNum;


    User(){
        username = null;
        ownedItems = null;
        bank = 0;
        playerNum = 0;
    }


    public User(JSONObject object) throws JSONException {
        this.username = object.getString("username");
        this.bank = object.getInt("bank");
        this.ownedItems = new SerializableJSONArray(object.getJSONArray("ownedItems"));
        playerNum = 0;
    }
    User(String username, int bank, JSONArray items){
        this.username = username;
        this.bank = bank;
        this.ownedItems = new SerializableJSONArray(items);
        playerNum = 0;
    }

    public int getBank() {
        return bank;
    }

    public SerializableJSONArray getOwnedItems() {
        return ownedItems;
    }

    public String getUsername() {
        return username;
    }
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    public JSONArray addItem(String item) throws JSONException {
        int length = this.ownedItems.getJSONArray().length() + 1;
        JSONArray temp = new JSONArray();
        for (int i = 0; i < length - 1; i++){
            temp.put(this.ownedItems.getJSONArray().get(i));
        }
        temp.put(item);
        return temp;
    }
}
