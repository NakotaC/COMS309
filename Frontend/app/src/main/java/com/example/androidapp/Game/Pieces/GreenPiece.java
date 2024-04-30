package com.example.androidapp.Game.Pieces;

public class GreenPiece extends GamePieceAbstract{
    int homeLocation = 47;
    int startLocation = 49;
    short startX = -4;
    short startY = 284;

    public GreenPiece(int pieceNum){
        super();
        this.pieceNum = pieceNum;
    }
    public boolean isHome(){
        return location == homeLocation;
    }
    public int move(int numToMove){
        int numLeftToMove = 0;
        deltaXFromLastMove = 0;
        deltaYFromLastMove = 0;
        if(currArea == 0){
            if(numToMove == 1 || numToMove == 2){
                location = startLocation;
                currArea = 1;
                direction = 3;
                currX = startX;
                currY = startY;
                deltaXFromLastMove -= xInc;
            }
        }else if(currArea == 1){
            for(int i = 0; i < numToMove; i++) {
                if (this.onCorner()) {
                    turnCorner();
                }
                if(isHome()){
                    direction = 0;
                    currArea = 2;
                    numLeftToMove = numToMove - i;
                    break;
                }

                if(direction == 0){
                    incLocation();
                    currX += xInc;
                    deltaXFromLastMove += xInc;

                }else if(direction == 1){
                    incLocation();
                    currY += yInc;
                    deltaYFromLastMove += yInc;

                }else if(direction == 2){
                    incLocation();
                    currX -= xInc;
                    deltaXFromLastMove -= xInc;


                }else if(direction == 3){
                    incLocation();
                    currY -= yInc;
                    deltaYFromLastMove -= yInc;

                }
            }
        }
        if(currArea == 2){
            if(numLeftToMove != 0){
                for(int i = 0; i < numLeftToMove; i++){
                    locationInHome++;
                    currX += xInc;
                    deltaXFromLastMove += xInc;
                    if(locationInHome >= 5){
                        currArea = 3;
                        break;
                    }
                }
            }else{
                for(int i = 0; i < numToMove; i++) {
                    locationInHome++;
                    currX += xInc;
                    deltaXFromLastMove += xInc;
                    if (locationInHome >= 5){
                        currArea = 3;
                        break;
                    }
                }
            }
        }else if(currArea == 3){
            return -1;
        }
        return location;
    }
}
