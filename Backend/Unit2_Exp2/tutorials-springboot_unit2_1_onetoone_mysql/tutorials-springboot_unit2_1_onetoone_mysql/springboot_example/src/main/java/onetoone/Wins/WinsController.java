package onetoone.Wins;

import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class WinsController {
    @Autowired
    WinsRepository winsRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/wins")
    List<Wins> getWins() {
        return winsRepository.findAll();
    }



    @PostMapping(path = "/wins/{user_id}/{amount_to_add}")
    void postWins(@PathVariable int user_id, @PathVariable int amount_to_add) {
        Wins winner = winsRepository.findById(user_id);
        winner.setWins(winner.getWins() + amount_to_add);
        winsRepository.save(winner);
    }
}
