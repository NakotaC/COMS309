package onetoone.ShopItems;

import onetoone.Inventory.Inventory;
import onetoone.Users.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String item_name;

    private String description;

    public ShopItem(){

    }

    public ShopItem( String item_name, String description) {
        this.item_name = item_name;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getItemName() {
        return item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Inventory getInventory() {
//        return inventory;
//    }
//
//    public void setInventory(Inventory inventory) {
//        this.inventory = inventory;
//    }
}
