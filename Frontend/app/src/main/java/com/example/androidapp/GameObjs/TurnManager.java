package com.example.androidapp.GameObjs;

/**
 * Class used to represent and handle players taking turns
 */
public class TurnManager {
    /**
     * holds the current player's turn
     */
    int currTurn;
    /**
     * holds the number of players
     */
    int numPlayers;
    /**
     * Creates basic TurnManager that starts with player 1's turn
     */
   public TurnManager(){
        currTurn = 1;
    }
    /**
     * Creates basic TurnManager that starts with specified player's turn
     */
  public TurnManager(int playerNum){
        currTurn = playerNum;
    }
    public int getCurrTurn(){
        return currTurn;
    }
   public void takeTurn(){
        if(this.currTurn == numPlayers){
            this.currTurn = 0;
        }else{
            this.currTurn++;
        }
    }
}
