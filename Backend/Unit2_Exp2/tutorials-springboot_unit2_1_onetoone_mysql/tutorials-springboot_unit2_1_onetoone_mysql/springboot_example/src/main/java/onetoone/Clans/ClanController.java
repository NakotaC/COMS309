package onetoone.Clans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClanController {
    @Autowired
    ClanRepository clanRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/clans")
    List<Clan> getAllClans() { return clanRepository.findAll(); }

    @GetMapping(path = "/clans/{clan_id}")
    Clan getClan(@PathVariable int clan_id) {
        return clanRepository.findById(clan_id);
    }

//    @PostMapping(path = "clans/{clan_name}")
//    String newClan(@PathVariable String clan_name) {
//        for(int i = 1; i < clanRepository.size(); i++){
//            if(clan_name == clanRepository.findById(i)) {
//
//            }
//        }
//        return success;
//    }
}
