package onetoone.Inventory;

import javax.persistence.*;

import onetoone.ShopItems.ShopItem;

import java.util.List;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    private List<ShopItem> shopItems;


    public Inventory() {
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<ShopItem> getShopItems(){
        return shopItems;
    }

    public void setShopItems(List<ShopItem> shopItems){
        this.shopItems = shopItems;
    }
}
