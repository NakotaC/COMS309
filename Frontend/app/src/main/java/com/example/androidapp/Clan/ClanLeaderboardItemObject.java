package com.example.androidapp.Clan;

/**
 * The following class is used to store user stats for the leaderboard screen.
 */
public class ClanLeaderboardItemObject {

    //variable declaration
    private final String name;

    /**
     * The following method is used to store each individual user's name and score.
     * @param name
     */
    public ClanLeaderboardItemObject(String name) {
        this.name = name;
    }

    /**
     * The following method is a getter method. It is used to get user's name.
     * @return
     */
    public String getName() {
        return name;
    }

}
