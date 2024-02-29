package com.example.androidapp;

public class User {
    private String username;
    private String[] ownedItems;
    private int bank;

    User(){
        username = null;
        ownedItems = null;
        bank = 0;
    }
    User(String username, int bank, String[] items){
        this.username = username;
        this.bank = bank;
        this.ownedItems = items;
    }

    public int getBank() {
        return bank;
    }

    public String[] getOwnedItems() {
        return ownedItems;
    }

    public String getUsername() {
        return username;
    }

    public String[] addItem(String item){
        int length = this.ownedItems.length + 1;
        String[] temp = new String[length];
        for (int i = 0; i < length - 1; i++){
            temp[i] = this.ownedItems[i];
        }
        temp[length-1] = item;
        return temp;
    }
}
