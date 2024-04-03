package onetoone.MatchHistory;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import onetoone.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import onetoone.Users.UserRepository;
import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private GregorianCalendar time;


    private int winner_id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;


    public History(){

    }

    public History(GregorianCalendar time, User user, User winner) {
        this.time = time;
        this.user = user;
        this.winner_id = winner.getId();
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
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWinnerId() {
        return winner_id;
    }

    public void setWinner(User user) {
        this.winner_id = user.getId();
    }

//    public Inventory getInventory() {
//        return inventory;
//    }
//
//    public void setInventory(Inventory inventory) {
//        this.inventory = inventory;
//    }
}
