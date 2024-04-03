package onetoone.MatchHistory;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import onetoone.Users.User;

import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private GregorianCalendar time;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User winner;

    public History(){

    }

    public History(GregorianCalendar time, User winner) {
        this.time = time;
        this.winner = winner;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public GregorianCalendar getItemName() {
        return time;
    }

    public void setTime(GregorianCalendar time) {
        this.time = time;
    }

    public User getUser() {
        return winner;
    }

    public void setUser(User winner) {
        this.winner = winner;
    }

//    public Inventory getInventory() {
//        return inventory;
//    }
//
//    public void setInventory(Inventory inventory) {
//        this.inventory = inventory;
//    }
}
