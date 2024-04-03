package onetoone.EquippedItems;

import io.swagger.annotations.Api;
import onetoone.Inventory.Inventory;
import onetoone.Inventory.InventoryRepository;
import onetoone.ShopItems.ShopItem;
import onetoone.ShopItems.ShopItemsRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(value = "EquippedItemsController", description = "REST APIs related to the EquippedItems Entity")
@RestController
public class EquippedItemsController {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ShopItemsRepository shopItemsRepository;

    @Autowired
    EquippedItemsRepository equippedItemsRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/equippedItems")
    List<EquippedItems> getEquippedItems() { return equippedItemsRepository.findAll(); }

    //@GetMapping(path = "/inventory/shop")


    @GetMapping(path = "/equippedItems/{uid}")
    List<ShopItem> getAllEquippedItemsUser(@PathVariable int uid){
        User u = userRepository.findById(uid);
        EquippedItems i = u.getEquipped();
        List<ShopItem> tmp = i.getShopItems();
        return tmp;
    }
    @GetMapping(path = "/inventory2/shop")
    List<ShopItem> getAllItems(){
        return shopItemsRepository.findAll();
    }

    @PostMapping(path = "/inventory2/shop/buy")
    String buyItem(@RequestHeader("item") String iiid, @RequestHeader("username") String username){
        int iid = Integer.parseInt(iiid);
        System.out.println(iid);
        List<User> users = userRepository.findAll();
        int uid = 1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                uid = users.get(i).getId();
                break;
            }
        }
        User user = userRepository.findById(uid);
        ShopItem item = shopItemsRepository.findById(iid);
        user.setItems(item);
        userRepository.save(user);
        return success;
    }
}
