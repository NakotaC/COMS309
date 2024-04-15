package onetoone.MatchHistory;
import io.swagger.annotations.Api;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @GetMapping(path = "/history/all")
    List<History> getHistory() { return historyRepository.findAll(); }

    @GetMapping(path = "/history/{user_id}")
    List<History> getUserHistory(@PathVariable int user_id) {
        List<History> h = new ArrayList<>();
        for(int i = 1; i <= historyRepository.count(); i++) {
            if(historyRepository.findById(i).getId() == user_id) {
                h.add(historyRepository.findById(i));
            }
        }
        return h;
    }

    @PostMapping(path = "/history/{user_id}/{winner_id}")
    void postHistory(@PathVariable int user_id, @PathVariable int winner_id) {
        History h = new History(new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")), userRepository.findById(user_id),  userRepository.findById(winner_id));
        historyRepository.save(h);
    }

    //comment for CICD
    @DeleteMapping(path="/history/{match_id}")
    String deleteHistoryId(@PathVariable int match_id){
        if(historyRepository.findById(match_id) == null) {
            return failure;
        }
        historyRepository.deleteById(match_id);
        return success;
    }

}
