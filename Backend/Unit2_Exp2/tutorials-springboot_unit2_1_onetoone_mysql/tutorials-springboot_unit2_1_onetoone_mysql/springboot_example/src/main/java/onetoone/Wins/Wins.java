package onetoone.Wins;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import onetoone.Users.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Wins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @Column(name="wins")
    private Integer wins;

    public Wins(Integer wins) {
        this.wins = wins;
    }
    public Wins() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }
}
