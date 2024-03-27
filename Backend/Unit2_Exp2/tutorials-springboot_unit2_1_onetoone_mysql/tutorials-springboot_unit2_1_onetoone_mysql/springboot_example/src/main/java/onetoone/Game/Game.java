package onetoone.Game;

import onetoone.Users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private ArrayList<Integer> deck;

    private void initdeck(){
        for (int i = 0; i < 5; i++) {
            deck.add(1);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(2);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(3);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(4);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(5);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(6);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(7);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(8);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(10);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(11);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(12);
        }
        for (int i = 0; i < 4; i++) {
            deck.add(0);
        }
    }

    public Game() {
        this.deck = new ArrayList<>();
        initdeck();
    }

    public ArrayList<Integer> getDeck(){
        return this.deck;
    }

    public int Draw(){
        Random rand = new Random();
        if(this.deck.isEmpty()){
            Shuffle();
        }
        return this.deck.remove(rand.nextInt(this.deck.size()));
    }
    public void Shuffle(){
        initdeck();
    }


}
