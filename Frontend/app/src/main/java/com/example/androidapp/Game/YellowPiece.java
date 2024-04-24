package com.example.androidapp.Game;

public class YellowPiece  extends GamePieceAbstract{
   int pieceNum;
   int location;
   int homeLocation = 32;
   int startLocation = 34;
   int startX = 260;
   int startY = 380;

   public YellowPiece(int pieceNum){
      this.pieceNum = pieceNum;
   }


   public int move(int numToMove){
      if(location == 0){
         if(numToMove == 1){
            location = startLocation;
            currX = startX;
            currY = startY;
         }
      }else if(location == 1){
         //TODO
      }else if(location == 2){
         //TODO
      }else if(location == 3){
         //TODO
      }







   return location;
   }
}
