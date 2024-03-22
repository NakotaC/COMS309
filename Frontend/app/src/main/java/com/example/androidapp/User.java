package com.example.androidapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private SerializableJSONArray ownedItems;
    private int bank;

    User(){
        username = null;
        ownedItems = null;
        bank = 0;
    }
    User(JSONObject object) throws JSONException {
        this.username = object.getString("username");
        this.bank = object.getInt("bank");
        this.ownedItems = new SerializableJSONArray(object.getJSONArray("ownedItems"));
    }
    User(String username, int bank, JSONArray items){
        this.username = username;
        this.bank = bank;
        this.ownedItems = new SerializableJSONArray(items);
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
