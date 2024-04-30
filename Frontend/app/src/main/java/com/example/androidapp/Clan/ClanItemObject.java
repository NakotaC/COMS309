package com.example.androidapp.Clan;

import com.example.androidapp.Game.User;

/**
 * The following class is used to store clan data.
 */
public class ClanItemObject
{

    //variable declaration
    private String clanName;
    private String clanLevel;
    private String clanAvailability;
    private User user;
    private String members;


    /**
     * This method is initialized to store each individual clan's name and level.
     * @param clanName
     * @param clanLevel
     */
    public ClanItemObject(String clanName, String clanLevel, User user, String members) {
        this.clanName = clanName;
        this.clanLevel = clanLevel;
        this.user = user;
        this.members = members;
    }


    /**
     * The following method is the getter method. It is used to get the clan name.
     * @return
     */
    public String getClanName() {

        return clanName;

    }


    /**
     * The following method is the setter method. It is used to set the clan name.
     * @param random
     */
    public void setCourseName(String random) {

        this.clanName = random;

    }





    /**
     * The following method is the getter method. It is used to get the clan level.
     * @return
     */
    public String getClanLevel() {

        return clanLevel;

    }


    /**
     * The following method is the setter method. It is used to set the clan level.
     * @param random
     */
    public void setClanLevel(String random) {

        this.clanLevel = random;

    }


    /**
     * The following method is the getter method. It is used to get the clan availability.
     * @return
     */
    public String getClanAvailability() {

        return clanAvailability;

    }


    /**
     * The following method is the setter method. It is used to set the clan availability.
     * @param random
     */
    public void setClanAvailability(String random) {

        this.clanLevel = random;

    }

    public User getUser()
    {
    return user;
    }

    public void setUser(User user)
    {
    this.user = user;
    }

    public String getMembers()
    {
    return members;
    }

    public void setMembers(String members)
    {
    this.members = members;
    }

}