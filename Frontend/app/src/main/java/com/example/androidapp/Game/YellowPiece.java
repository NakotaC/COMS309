package com.example.androidapp.Game;

public class YellowPiece  extends GamePieceAbstract{
   int pieceNum;
   int location;
   int homeLocation = 32;
   int startLocation = 34;
   int startX = 264;
   int startY = 388;

   public YellowPiece(int pieceNum){
      this.pieceNum = pieceNum;
   }
}
