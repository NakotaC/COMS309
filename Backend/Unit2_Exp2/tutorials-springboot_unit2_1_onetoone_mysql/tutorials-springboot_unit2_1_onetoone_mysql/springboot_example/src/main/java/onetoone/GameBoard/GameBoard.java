package onetoone.GameBoard;

import lombok.Data;

import javax.persistence.*;

    @Entity
    @Table(name = "GameBoard")
    @Data
    public class GameBoard {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private int user1;

        @Column
        private int user1pos;

        @Column
        private int user2;

        @Column
        private int user2pos;

        @Column
        private int user3;

        @Column
        private int user3pos;

        @Column
        private int user4;

        @Column
        private int user4pos;

//        Also have like deckstate??? not sure hpow to do thats
    }
