package onetoone.Users;

import javax.persistence.*;

import onetoone.Clans.Clan;
import onetoone.Inventory.Inventory;
import onetoone.Wins.Wins;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@Entity
public class User {

     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "clan_id")
    private Clan clan;

    @OneToOne
    @JoinColumn(name = "inv_id")
    private Inventory inventory;

    @OneToOne
    @JoinColumn(name = "wins_id")
    private Wins wins;
    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */

    public User(String Username, String password) {
        this.username = Username;
        this.password = password;
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getUsername(){
        return username;
    }

    public void setUsername(String Username){
        this.username = Username;
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String Password){
        this.password = Password;
    }
    public int getClan(){
        return clan.getId();
    }

    public void setClan(Clan clan){
        this.clan = clan;
    }
    public Inventory getInventory(){
        return inventory;
    }

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public Wins getWins(){
        return wins;
    }

    public void setWins(Wins wins){
        this.wins = wins;
    }

}
