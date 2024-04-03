package onetoone.UserMatch;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import onetoone.MatchHistory.History;
import onetoone.ShopItems.ShopItem;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class UM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    private List<History> history;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "winner_id")
//    @JsonManagedReference
//    private int winner_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

//    @Autowired
//    UserRepository userRepository;
    public UM() {
    }

    public UM(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<History> getHistory() {
        return this.history;
    }

    public void setUM(List<History> history){
        if (this.history == null){
            this.history = new ArrayList<>();
        }
        this.history = history;
    }
    public void setUM(History history){
        if (this.history == null){
            this.history = new ArrayList<>();
        }
        List<History> inv = this.getHistory();
        inv.add(history);
        this.user.setHistory(inv);
        System.out.println(history);
    }
}