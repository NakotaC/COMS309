package onetoone.EquippedItems;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import onetoone.ShopItems.ShopItem;
import onetoone.Users.User;

import java.util.ArrayList;
import java.util.List;

@Entity
public class EquippedItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    private List<ShopItem> shopItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;


    public EquippedItems() {
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ShopItem> getShopItems(){
        return this.shopItems;
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
        List<ShopItem> inv = this.getShopItems();
        inv.add(shopItem);
        this.user.setequippedItems(inv);
        System.out.println(shopItems);
    }
}
