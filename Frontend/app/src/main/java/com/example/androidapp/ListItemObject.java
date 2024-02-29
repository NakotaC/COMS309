package com.example.androidapp;

public class ListItemObject {
    private String name;
    private String price;

    public ListItemObject(String name, int price) {
        this.name = name;
        this.price = String.valueOf(price);
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
