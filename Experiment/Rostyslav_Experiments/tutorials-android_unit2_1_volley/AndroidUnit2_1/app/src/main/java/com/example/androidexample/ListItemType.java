package com.example.androidexample;

public class ListItemType {
    private String name;
    private String email;

    public ListItemType (String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }
}
