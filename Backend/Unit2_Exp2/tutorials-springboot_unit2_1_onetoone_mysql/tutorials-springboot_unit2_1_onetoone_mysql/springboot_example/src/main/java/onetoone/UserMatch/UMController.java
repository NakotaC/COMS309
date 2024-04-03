package onetoone.UserMatch;

import io.swagger.annotations.Api;
import onetoone.MatchHistory.History;
import onetoone.MatchHistory.HistoryRepository;
import onetoone.ShopItems.ShopItem;
import onetoone.ShopItems.ShopItemsRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

@Api(value = "UMController", description = "REST APIs related to the Inventory Entity")
@RestController
public class UMController {
    @Autowired
    UMRepository UMRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/UM")
    List<UM> getHistory() { return UMRepository.findAll(); }

    //NOT WORKING 100% only works 2 times, then breaks???
    @PostMapping(path = "/UM/{user_id}")
    void postUM(@PathVariable int user_id) {
        UM user_match = new UM(userRepository.findById(user_id));
        UMRepository.save(user_match);
        History h = new History(new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")), userRepository.findById(user_id));
        historyRepository.save(h);
    }

}
