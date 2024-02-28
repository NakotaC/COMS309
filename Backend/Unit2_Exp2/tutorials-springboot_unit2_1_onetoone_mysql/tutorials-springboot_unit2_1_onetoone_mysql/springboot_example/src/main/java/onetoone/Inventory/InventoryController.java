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
    String buyItem(@RequestHeader("item") int iid, @RequestHeader("username") String username){
        List<User> users = userRepository.findAll();
        int uid = 1;
        for (int i = 1; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                uid = users.get(i).getId();
            }
        }
        User user = users.get(uid);
        ShopItem item = shopItemsRepository.findById(iid);
        user.setItems(item);
        userRepository.save(user);
        return "Success";
    }


}
