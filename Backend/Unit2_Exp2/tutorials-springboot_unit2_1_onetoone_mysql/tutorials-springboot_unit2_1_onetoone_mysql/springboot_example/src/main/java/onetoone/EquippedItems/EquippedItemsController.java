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

import java.util.ArrayList;
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
//    @GetMapping(path = "/equippedItems")
//    List<EquippedItems> getEquippedItems() { return equippedItemsRepository.findAll(); }

    //@GetMapping(path = "/inventory/shop")


    @GetMapping(path = "/equippedItems/{uid}")
    List<ShopItem> getAllEquippedItemsUser(@PathVariable int uid){
        User u = userRepository.findById(uid);
        EquippedItems i = u.getEquipped();
        List<ShopItem> tmp = i.getShopItems();
        return tmp;
    }
    @DeleteMapping(path = "/unequip/{uid}")
    String ue(@RequestHeader("itemNum") String iiid, @PathVariable int uid){
        User u = userRepository.findById(uid);
        EquippedItems i = u.getEquipped();
        List<ShopItem> c = i.getShopItems();
        int iiiid = Integer.parseInt(iiid);
        c.remove(iiiid);
        i.setShopItems(c);
        equippedItemsRepository.save(i);
        u.setEquipped(i);
        userRepository.save(u);
        return success;
    }

    @PutMapping(path = "/equip/{uid}")
    String eq(@RequestHeader("itemNum") String iiid, @PathVariable int uid){
        User u = userRepository.findById(uid);
        EquippedItems i = u.getEquipped();
        List<ShopItem> c = i.getShopItems();
        Inventory i2 = u.getInventory();
        List<ShopItem> c2 = i2.getShopItems();
        int iiiid = Integer.parseInt(iiid);
        c.add(c2.get(iiiid));
        i.setShopItems(c);
        equippedItemsRepository.save(i);
        u.setEquipped(i);
        userRepository.save(u);
        return success;
    }

    @PostMapping(path = "/newEquip/{uid}")
    void newInv(@PathVariable int uid) {
        EquippedItems i = new EquippedItems();
        User user = userRepository.findById(uid);
        i.setUser(user);
        ArrayList<ShopItem> inv1items = new ArrayList<>();
        i.setShopItems(inv1items);


        equippedItemsRepository.save(i);
    }
}
