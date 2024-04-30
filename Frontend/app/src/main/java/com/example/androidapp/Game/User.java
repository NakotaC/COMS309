package com.example.androidapp.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Class to represent the user and hold data that could be useful
 */
public class User implements Serializable {
    private final String username;
    private  SerializableJSONArray inventory;
    private SerializableJSONArray equippedItems;
    private SerializableJSONArray quests;
    private int bank;
    private final int id;
    private int xp;
    private int playerNum;

    public String invStr;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    private int gameId;

    /**
     * Gets the player Number(Keeps track of their place in the rotation)
     * @return player number
     */
    public int getPlayerNum() {
        return playerNum;
    }



    /**
     * Creates a User object with all fields blank
     */
    public User(){
        username = null;
        inventory = null;
        equippedItems = null;
        bank = 0;
        id = 0;
        playerNum = 0;
    }

    /**
     * Creates a User object from a given JSONObject
     * @param object is the JSONObject that has the user data
     * @throws JSONException if object is missing params. Exception will be thrown
     */
    public User(JSONObject object, JSONArray inventory, JSONArray equippedItems, JSONArray quests) throws JSONException {
        this.username = object.getString("username");
        this.bank = object.getInt("bank");
        this.xp = object.getInt("xp");
        this.id = object.getInt("id");
        this.equippedItems = new SerializableJSONArray(equippedItems);
        this.inventory = new SerializableJSONArray(inventory);
        this.quests = new SerializableJSONArray(quests);
        playerNum = 0;
        invStr = null;
    }
    /**
     * Gets the value of the bank of the User
     * @return The value in the user's bank
     */
    public int getBank() {
        return bank;
    }

    public int getId() {
        return id;
    }

    /**
     * Gets the items that the User owns
     * @return Array of items in the user's inventory
     */
    public JSONArray getInventory() {
        return inventory.getJSONArray();
    }

    public JSONArray getEquippedItems() {
        return equippedItems.getJSONArray();
    }
    public JSONArray getQuests()
    {
    return quests.getJSONArray();
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


    public JSONArray addEquippedItem(JSONObject obj) throws JSONException {
        int length = this.equippedItems.getJSONArray().length() + 1;
        JSONArray temp = new JSONArray();
        for (int i = 0; i < length - 1; i++){
            temp.put(this.equippedItems.getJSONArray().getJSONObject(i));
        }
        temp.put(obj);
        this.equippedItems = new SerializableJSONArray(temp);
        return temp;
    }
    public JSONArray removeEquippedItem(int position) throws JSONException {
        JSONArray result;
        result = new JSONArray();

        for(int i = 0; i < equippedItems.getJSONArray().length(); i++){
            if(i != position){
                result.put(equippedItems.getJSONArray().get(i));
            }
        }
        this.equippedItems = new SerializableJSONArray(result);
        return result;
    }
    public int getItemPosition(JSONObject obj) throws JSONException {
        int i;
        for (i = 0; i < inventory.getJSONArray().length(); i++) {
            if (inventory.getJSONArray().getJSONObject(i).getString("itemName").equals(obj.getString("itemName"))) {
                break;
            }
        }
        return i;
    }
}
