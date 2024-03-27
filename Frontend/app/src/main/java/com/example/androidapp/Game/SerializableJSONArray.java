package com.example.androidapp.Game;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Wrapper class used to make JSONArrays serializable
 */
public class SerializableJSONArray implements Serializable {
    /**
     * holds the JSONArray
     */
    private transient JSONArray jsonArray;

    /**
     * Creates a Serializable JSONArray given a JSONArray
     * @param jsonArray the JSONArray that you want to make serializable
     */
    public SerializableJSONArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    /**
     * Gets the JSONArray
     * @return returns the JSONArray
     */
    public JSONArray getJSONArray() {
        return jsonArray;
    }

    /**
     * Writes the object to an ObjectOutputString. Used to serialize
     * @param oos the ObjectOutputString to write to
     * @throws IOException if the oos is not correct it will throw an exception
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(jsonArray.toString());
    }

    /**
     * Reads and creates a JSONArray from an ObjectInputString
     * @param ois the ObjectInputString to read the JSONArray from
     * @throws ClassNotFoundException if the class can't be found
     * @throws IOException the ois is not correct
     * @throws JSONException if the creation of the JSON array fails
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException, JSONException {
        ois.defaultReadObject();
        jsonArray = new JSONArray((String) ois.readObject());
    }
}
