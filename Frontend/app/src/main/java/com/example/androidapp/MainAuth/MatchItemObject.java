package com.example.androidapp.MainAuth;

public class MatchItemObject {

    private final String name;
    private final String match;


    public MatchItemObject(String name, String match) {
        this.name = name;
        this.match = match;
    }


    public String getName() {
        return name;
    }

    public String getMatch() {
        return match;
    }
}
