package onetoone.Game;

import com.fasterxml.jackson.core.JsonProcessingException;
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
@Api(value = "GameController", description = "REST APIs related to the Game Entity")
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
//    @GetMapping(path = "/deck")
//    List<Inventory> getInventory() { return inventoryRepository.findAll(); }

    //@GetMapping(path = "/inventory/shop")

    @GetMapping(path = "/draw")
    int draw(@RequestHeader("gameid") String gameid) throws JsonProcessingException {
        int gid = Integer.parseInt(gameid);
        int card = gameRepository.findById(gid).Draw();
        gameRepository.save(gameRepository.findById(gid));
        return card;
    }

    @GetMapping(path = "/draw/str")
    String drawString(@RequestHeader("gameid") String gameid) throws JsonProcessingException {
        int gid = Integer.parseInt(gameid);
        int card = gameRepository.findById(gid).Draw();
        String cards = Integer.toString(card);
        gameRepository.save(gameRepository.findById(gid));
        String tmp = "{\"Card\":\"";
        tmp = tmp.concat(cards);
        tmp = tmp.concat("\"}");
        return tmp;
    }


}
