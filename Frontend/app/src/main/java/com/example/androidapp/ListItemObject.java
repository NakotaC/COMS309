package com.example.androidapp;

public class ListItemObject {
    private String name;
    private int price;

    public ListItemObject(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
