package onetoone.Clans;

import onetoone.Users.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clan_name;

    @OneToMany(mappedBy = "clan")
    private List<User> users;
}
