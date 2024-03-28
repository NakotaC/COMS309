package com.example.androidapp.Leaderboard;

/**
 * The following class is used to store user stats for the leaderboard screen.
 */
public class LeaderboardItemObject {

    //variable declaration
    private final String name;
    private final String score;

    /**
     * The following method is used to store each individual user's name and score.
     * @param name
     * @param score
     */
    public LeaderboardItemObject(String name, String score) {
        this.name = name;
        this.score = score;
    }

    /**
     * The following method is a getter method. It is used to get user's name.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * The following method is a getter method. It is used to get user's score.
     * @return
     */
    public String getScore() {
        return score;
    }
}
