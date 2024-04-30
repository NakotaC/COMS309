package onetoone.Clans;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import onetoone.Users.UserRepository;
import onetoone.Users.User;

import java.util.ArrayList;
import java.util.List;
@Api(value = "ClanController", description = "REST APIs related to the Clan Entity")
@RestController
public class ClanController {
    @Autowired
    ClanRepository clanRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //@ApiOperation(value = "Get list of all clans", response = Iterable.class, tags = "getClans")
    @GetMapping(path = "/clans")
    List<Clan> getAllClans() {
        return clanRepository.findAll();
    }

    @GetMapping(path = "/clans/open")
    List<Clan> getAllOpenClans() {
        ArrayList<Clan> openClans = new ArrayList<>();
        for (int i = 1; i < clanRepository.count() + 1; i++) {
            if (clanRepository.findById(i).getClanType().toLowerCase().equals("open")) {
                System.out.print(clanRepository.findById(i).getClanType().toLowerCase());
                openClans.add(clanRepository.findById(i));
            }
        }
        return openClans;
    }

    @GetMapping(path = "/clans/{clan_id}")
    Clan getClan(@PathVariable int clan_id) {
        return clanRepository.findById(clan_id);
    }
    //could possibly be replaced with a findByClanName JPA method (just learned about these)
    @PostMapping(path = "clans/{clan_name}/{user_id}")
    String newClan(@PathVariable int user_id, @PathVariable String clan_name) {
        for (int i = 1; i < clanRepository.count() + 1; i++) {
            if (clan_name.equals(clanRepository.findById(i).getClanName())) {
                return failure;
            }
        }
        Clan clan1 = new Clan(clan_name, user_id, userRepository);
        clanRepository.save(clan1);
        User user = userRepository.findById(user_id);
        int userOC = user.getClan();
        Clan pc = clanRepository.findById(userOC);
        ArrayList<Integer> pcarray = pc.toIntList(pc.getMembers());
        pcarray.remove(Integer.valueOf(user.getId()));
        pc.setMember(pcarray);
        clanRepository.save(pc);
        user.setClan(clan1);
        userRepository.save(user);

        return success;
    }

    @PostMapping(path = "clans/{clan_name}/{user_id}/{type}/{max_members}")
    String newClanPlus(@PathVariable int user_id, @PathVariable String clan_name,
                       @PathVariable String type, @PathVariable int max_members) {
        for (int i = 1; i < clanRepository.count() + 1; i++) {
            if (clan_name.equals(clanRepository.findById(i).getClanName())) {
                return failure;
            }
        }
        Clan clan1 = new Clan(clan_name, user_id, userRepository);
        clan1.setMax_members(max_members);
        clan1.setClanType(type);
        clanRepository.save(clan1);
        User user = userRepository.findById(user_id);
        int userOC = user.getClan();
        Clan pc = clanRepository.findById(userOC);
        ArrayList<Integer> pcarray = pc.toIntList(pc.getMembers());
        pcarray.remove(Integer.valueOf(user.getId()));
        pc.setMember(pcarray);
        clanRepository.save(pc);
        user.setClan(clan1);
        userRepository.save(user);

        return success;
    }

    @PostMapping(path = "member/{clan_name}/{user_id}")
    String AddMemberClan(@PathVariable int user_id, @PathVariable String clan_name) {
        System.out.print(clan_name);
        System.out.print(clanRepository.findById(1).getClanName());
        //delete user from previous clan
        User poorSap = userRepository.findById(user_id);
        int previous_clan = userRepository.findById(user_id).getClan();
        Clan pc = clanRepository.findById(previous_clan);
        ArrayList<Integer> pcarray = pc.toIntList(pc.getMembers());
        pcarray.remove(Integer.valueOf(poorSap.getId()));
        pc.setMember(pcarray);
        clanRepository.save(pc);
        //if this fails add them back to noClan
        for (int i = 1; i < clanRepository.count() + 1; i++) {
            if (clan_name.equals(clanRepository.findById(i).getClanName())) {
                Clan clan1 = clanRepository.findById(i);
                ArrayList<Integer> la = clan1.toIntList(clan1.getMembers());
                la.add(user_id);
                clan1.setMember(la);
                clanRepository.save(clan1);
                User user = userRepository.findById(user_id);
                user.setClan(clan1);
                userRepository.save(user);
                return success;
            }
        }
        Clan clan1 = clanRepository.findById(1);
        ArrayList<Integer> la = clan1.toIntList(clan1.getMembers());
        la.add(user_id);
        clan1.setMember(la);
        clanRepository.save(clan1);
        User user = userRepository.findById(user_id);
        user.setClan(clan1);
        userRepository.save(user);
        return failure;
    }



    @PostMapping(path = "clan/Clan/cLAN/CLAM/CLam/cLAM")
    void initNoClan() {
        Clan noClan = new Clan("noClan", -2, userRepository);
        noClan.setMax_members(100000);
        clanRepository.save(noClan);
    }


    //essentially
    @PostMapping(path = "member/{user_id}")
    String kickLeaveClan(@PathVariable int user_id) {
        User poorSap = userRepository.findById(user_id);
        int previous_clan = userRepository.findById(user_id).getClan();
        Clan pc = clanRepository.findById(previous_clan);
        ArrayList<Integer> pcarray = pc.toIntList(pc.getMembers());
        pcarray.remove(Integer.valueOf(poorSap.getId()));
        pc.setMember(pcarray);
        clanRepository.save(pc);
        Clan clan1 = clanRepository.findById(1);
        ArrayList<Integer> la = clan1.toIntList(clan1.getMembers());
        la.add(user_id);
        clan1.setMember(la);
        clanRepository.save(clan1);
        User user = userRepository.findById(user_id);
        user.setClan(clan1);
        userRepository.save(user);

        return success;
    }

    @PostMapping(path = "/clanxp")
    String setClanXp() {
        int clanxp = 0;
        //Starting at two in order to leave noClan xp at 0

        for(int i = 2; i <= clanRepository.count(); i++) {
            clanxp = 0;
            ArrayList<Integer> mems = clanRepository.findById(i).toIntList(clanRepository.findById(i).getMembers());
            for(int j = 0; j < mems.size(); j++) {
                clanxp += userRepository.findById(mems.get(j)).getXp();
            }
            clanRepository.findById(i).setClan_xp(clanxp);
            clanRepository.save(clanRepository.findById(i));

        }
        return success;
    }

    

}
