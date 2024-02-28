package onetoone.Inventory;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import onetoone.ShopItems.ShopItem;
import onetoone.Users.User;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    private List<ShopItem> shopItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    public Inventory() {
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Integer getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ShopItem> getShopItems(){
        return shopItems;
    }

    public void setShopItems(List<ShopItem> shopItems){
        if (this.shopItems == null){
            this.shopItems = new ArrayList<>();
        }
        this.shopItems = shopItems;
    }
    public void setShopItems(ShopItem shopItem){
        if (this.shopItems == null){
            this.shopItems = new ArrayList<>();
        }
        this.shopItems.add(shopItem);
    }

}
