package onetoone.Inventory;

import onetoone.ShopItems.ShopItem;
import onetoone.ShopItems.ShopItemsRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ShopItemsRepository shopItemsRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/inventory")
    List<Inventory> getInventory() { return inventoryRepository.findAll(); }

    //@GetMapping(path = "/inventory/shop")


    @GetMapping(path = "/inventory/{uid}")
    List<ShopItem> getAllItemsUser(@PathVariable int uid){
        User u = userRepository.findById(uid);
        Inventory i = u.getInventory();
        List<ShopItem> tmp = i.getShopItems();
        return tmp;
    }
    @GetMapping(path = "/inventory/shop")
    List<ShopItem> getAllItems(){
        return shopItemsRepository.findAll();
    }

    @PostMapping(path = "/inventory/shop/buy")
    String buyItem(@RequestHeader("Item") int iid, @RequestBody int uid){
        ShopItem item = shopItemsRepository.findById(iid);
        User user = userRepository.findById(uid);
        user.setItems(item);
        userRepository.save(user);
        return success;
    }


}
