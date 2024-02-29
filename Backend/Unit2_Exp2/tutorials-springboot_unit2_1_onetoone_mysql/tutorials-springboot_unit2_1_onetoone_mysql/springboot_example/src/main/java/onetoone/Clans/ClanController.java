package onetoone.Clans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import onetoone.Users.UserRepository;
import onetoone.Users.User;

import java.util.List;

@RestController
public class ClanController {
    @Autowired
    ClanRepository clanRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/clans")
    List<Clan> getAllClans() {
        return clanRepository.findAll();
    }

    @GetMapping(path = "/clans/{clan_id}")
    Clan getClan(@PathVariable int clan_id) {
        return clanRepository.findById(clan_id);
    }
    //could possibly be replaced with a findByClanName JPA method (just learned about these)
    @PostMapping(path = "clans/{clan_name}/{user_id}")
    String newClan(@PathVariable int user_id, @PathVariable String clan_name) {
        for (int i = 1; i < clanRepository.count(); i++) {
            if (clan_name.equals(clanRepository.findById(i).getClanName())) {
                return failure;
            }
        }
        Clan clan1 = new Clan(clan_name, user_id);
        clanRepository.save(clan1);
        User user = userRepository.findById(user_id);
        System.out.println(user);
        user.setClan(clan1);
        userRepository.save(user);
        return success;
    }
}
