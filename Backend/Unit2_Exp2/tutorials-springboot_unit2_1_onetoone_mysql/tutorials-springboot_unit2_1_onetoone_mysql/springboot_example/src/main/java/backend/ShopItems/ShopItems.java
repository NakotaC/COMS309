package backend.Inventory;

import javax.persistence.*;

import backend.Users.User;

import java.util.List;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String item_name;

    @ManyToMany
    private List<User> users;
}
