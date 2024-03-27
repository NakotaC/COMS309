package com.example.androidapp.Leaderboard;

public class LeaderboardItemObject {
    private final String name;
    private final String score;

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
