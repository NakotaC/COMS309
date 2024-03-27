package com.example.androidapp.Clan;

public class ClanItemObject
{

    private String clanName;
    private String clanLevel;
    private String clanAvailability;





    public ClanItemObject(String clanName, String clanLevel) {
        this.clanName = clanName;
        this.clanLevel = clanLevel;

    }





    public String getClanName() {

        return clanName;

    }



    public void setCourseName(String random) {

        this.clanName = random;

    }






    public String getClanLevel() {

        return clanLevel;

    }



    public void setClanLevel(String random) {

        this.clanLevel = random;

    }



    public String getClanAvailability() {

        return clanAvailability;

    }



    public void setClanAvailability(String random) {

        this.clanLevel = random;

    }

}