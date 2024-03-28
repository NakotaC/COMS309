package onetoone.Game;

import com.fasterxml.jackson.core.JsonProcessingException;
import onetoone.Inventory.Inventory;
import onetoone.Inventory.InventoryRepository;
import onetoone.ShopItems.ShopItem;
import onetoone.ShopItems.ShopItemsRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ShopItemsRepository shopItemsRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/deck")
    List<Inventory> getInventory() { return inventoryRepository.findAll(); }

    //@GetMapping(path = "/inventory/shop")

    @GetMapping(path = "/draw")
    int draw(@RequestHeader("gameid") String gameid) throws JsonProcessingException {
        int gid = Integer.parseInt(gameid);
        return gameRepository.findById(gid).Draw();
    }


}
