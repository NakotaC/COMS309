package onetoone.Game;

import antlr.GrammarAnalyzer;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.models.auth.In;
import onetoone.Users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String deck;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    private void initdeck() throws JsonProcessingException {
        ArrayList<Integer> deck = this.getDeck();
        if (!deck.isEmpty()){
            deck = new ArrayList<>();
        }
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
        setDeck(deck);
    }

    public Game() throws JsonProcessingException {
        initdeck();
    }

    public ArrayList<Integer> getDeck() throws JsonProcessingException{
        if(deck == null || deck.isEmpty()){
            return new ArrayList<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(deck, new TypeReference<ArrayList<Integer>>(){});
    }
    public void setDeck(ArrayList<Integer> deck) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        this.deck=objectMapper.writeValueAsString(deck);
    }


    public int Draw() throws JsonProcessingException {
        Random rand = new Random();
        ArrayList<Integer> deck = getDeck();
        if(deck.isEmpty()){
            System.out.println("Shuffling");
            Shuffle();
            deck = this.getDeck();
        }
        int card = deck.remove(rand.nextInt(deck.size()));
        setDeck(deck);
        return card;
    }
    public void Shuffle() throws JsonProcessingException {
        initdeck();
    }


}
