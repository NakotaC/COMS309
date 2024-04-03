package onetoone.MatchHistory;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(value = "HistoryController", description = "REST APIs related to the History Entity")
@RestController
public class HistoryController {
    @Autowired
    HistoryRepository historyRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/history")
    List<History> getHistory() { return historyRepository.findAll(); }

}
