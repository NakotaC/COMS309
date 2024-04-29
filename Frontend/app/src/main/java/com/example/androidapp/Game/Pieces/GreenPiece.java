package com.example.androidapp.Game.Pieces;

public class GreenPiece extends GamePieceAbstract{
    int pieceNum;
    int location;
    int homeLocation = 47;
    int startLocation = 49;
    short startX;
    short startY;

    public GreenPiece(int pieceNum){
        this.pieceNum = pieceNum;
    }
    public boolean isHome(){
        return location == homeLocation;
    }
    public int move(int numToMove){
        int numLeftToMove = 0;
        if(currArea == 0){
            if(numToMove == 1){
                location = startLocation;
                currX = startX;
                currY = startY;
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
                    location++;
                    currX += xInc;

                }else if(direction == 1){
                    location++;
                    currY += yInc;

                }else if(direction == 2){
                    location++;
                    currX -= xInc;


                }else if(direction == 3){
                    location++;
                    currY -= yInc;

                }
            }
        }else if(currArea == 2){
            if(numLeftToMove != 0){
                for(int i = 0; i < numLeftToMove; i++){
                    locationInHome++;
                    currX += xInc;
                    if(locationInHome == 5){
                        currArea = 3;
                    }
                }
            }else{
                for(int i = 0; i < numToMove; i++) {
                    locationInHome++;
                    currY -= yInc;
                    if (locationInHome == 5){
                        currArea = 3;
                    }
                }
            }
        }else if(currArea == 3){
            return -1;
        }
        return location;
    }
}
