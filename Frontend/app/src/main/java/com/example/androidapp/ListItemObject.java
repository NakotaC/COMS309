package com.example.androidapp;

public class ListItemObject {
    private final String name;
    private final String description;

    public ListItemObject(String name, String description) {
        this.name = name;
        this.description = String.valueOf(description);
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return description;
    }
}
