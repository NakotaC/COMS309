package com.example.androidapp.Game;

abstract class GamePieceAbstract {
    int pieceNum;
    int location;
    short xInc, yInc;
    int homeLocation, startLocation;

    public int getStartLocation() {
        return startLocation;
    }

    public short getxInc() {
        return xInc;
    }

    public short getyInc() {
        return yInc;
    }

    public boolean isHome(){
        return location == homeLocation;
    }
    public int getPieceNum(){
        return pieceNum;
    }
    public int getLocation(){
        return location;
    }

}
