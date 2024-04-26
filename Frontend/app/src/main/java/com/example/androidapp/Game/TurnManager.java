package com.example.androidapp.Game;

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
        numPlayers = playerNum;
    }

    /**
     * gets the current turn
     * @return returns an int corresponding to whose turn it is
     */
    public int getCurrTurn(){
        return currTurn;
    }

    /**
     * Tells the Turn Manager to move to the next players turn. Counts from 1 to number of players, then back to 1
     */
   public void takeTurn(){
        if(this.currTurn == numPlayers){
            this.currTurn = 1;
        }else{
            this.currTurn++;
        }
    }
}
