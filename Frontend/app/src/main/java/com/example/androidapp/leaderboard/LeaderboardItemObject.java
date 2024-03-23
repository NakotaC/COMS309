package com.example.androidapp.leaderboard;

public class LeaderboardItemObject {
    private String name;
    private String score;

    public LeaderboardItemObject(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }
}
