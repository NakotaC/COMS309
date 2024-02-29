package com.example.androidapp;

public class ClanItemObject
{
    private String clanID;
    private String clan_name;

    public ClanItemObject(String clanID, String clan_name)
    {
    this.clanID = clanID;
    this.clan_name = clan_name;
    }

    public String getClanID()
    {
    return this.clanID;
    }

    public String getClan_name()
    {
    return this.clan_name;
    }

    public void setClan_ID(String random)
    {
    this.clanID = random;
    }

    public void setClan_name(String random)
    {
    this.clan_name = random;
    }
}
