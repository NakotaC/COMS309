package com.example.androidapp.GameObjs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private SerializableJSONArray ownedItems;
    private int bank;

    /**
     * Gets the player Number(Keeps track of their place in the rotation)
     * @return player number
     */
    public int getPlayerNum() {
        return playerNum;
    }

    private int playerNum;

    /**
     * Creates a User object with all fields blank
     */
    User(){
        username = null;
        ownedItems = null;
        bank = 0;
        playerNum = 0;
    }

    /**
     * Creates a User object from a given JSONObject
     * @param object is the JSONObject that has the user data
     * @throws JSONException if object is missing params. Exception will be thrown
     */
    public User(JSONObject object) throws JSONException {
        this.username = object.getString("username");
        this.bank = object.getInt("bank");
        this.ownedItems = new SerializableJSONArray(object.getJSONArray("ownedItems"));
        playerNum = 0;
    }

    /**
     * Gets the value of the bank of the User
     * @return The value in the user's bank
     */
    public int getBank() {
        return bank;
    }

    /**
     * Gets the items that the User owns
     * @return Array of items in the user's inventory
     */
    public SerializableJSONArray getOwnedItems() {
        return ownedItems;
    }

    /**
     * Gets the username of the User
     * @return returns the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the player number of the user
     * @param playerNum is the given number for the user's turn in rotation
     */
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    /**
     * Adds an item to the user's owned item array
     * @param item The item that is being added
     * @return Returns a new array with the item added to it
     * @throws JSONException if an un-initialized element is accessed an exception will be thrown
     */
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
