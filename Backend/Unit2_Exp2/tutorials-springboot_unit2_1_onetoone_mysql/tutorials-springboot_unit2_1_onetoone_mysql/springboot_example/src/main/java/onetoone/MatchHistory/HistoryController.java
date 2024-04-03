package onetoone.MatchHistory;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@Api(value = "HistoryController", description = "REST APIs related to the History Entity")
@RestController
public class HistoryController {
    @Autowired
    HistoryRepository historyRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/history/{user_id}")
    List<History> getHistory(@PathVariable int user_id) {
        List<History> h = new ArrayList<>();
        for(int i = 0; i < historyRepository.count(); i++) {
            if(historyRepository.findById(i).getUser().getId() == user_id) {
                h.add(historyRepository.findById(i));
            }
        }
        return h;
    }

}
