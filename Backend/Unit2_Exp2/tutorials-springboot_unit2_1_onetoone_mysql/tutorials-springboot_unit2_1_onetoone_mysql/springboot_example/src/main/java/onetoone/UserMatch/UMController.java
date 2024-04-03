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

import java.util.ArrayList;
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



    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)


}
