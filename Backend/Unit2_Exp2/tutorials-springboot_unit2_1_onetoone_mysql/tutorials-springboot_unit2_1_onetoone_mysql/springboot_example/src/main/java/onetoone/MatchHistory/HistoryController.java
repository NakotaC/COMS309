package onetoone.MatchHistory;

import io.swagger.annotations.Api;
//import onetoone.UserMatch.UM;
//import onetoone.UserMatch.UMRepository;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import onetoone.Users.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

@Api(value = "HistoryController", description = "REST APIs related to the History Entity")
@RestController
public class HistoryController {
    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    onetoone.UserMatch.UMRepository UMRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @GetMapping(path = "/history/all")
    List<History> getHistory() { return historyRepository.findAll(); }

    @GetMapping(path = "/history/{user_id}")
    User getUserHistory(@PathVariable int user_id) {
        return userRepository.findById(user_id);
    }

    @PostMapping(path = "/history/{user_id}/{winner_id}")
    void postHistory(@PathVariable int user_id, @PathVariable int winner_id) {
//        UM user_match = new UM(userRepository.findById(user_id));
//        UMRepository.save(user_match);
        History h = new History(new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")), userRepository.findById(user_id),  userRepository.findById(winner_id));
        historyRepository.save(h);

//        User user = userRepository.findById(user_id);
//        user.setUM(user_match);
//        userRepository.save(user);
    }

}
