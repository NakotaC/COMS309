package com.example.androidapp.Clan;

/**
 * The following class is used to store clan data.
 */
public class ClanItemObject
{

    //variable declaration
    private String clanName;
    private String clanLevel;
    private String clanAvailability;


    /**
     * This method is initialized to store each individual clan's name and level.
     * @param clanName
     * @param clanLevel
     */
    public ClanItemObject(String clanName, String clanLevel) {
        this.clanName = clanName;
        this.clanLevel = clanLevel;

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

}