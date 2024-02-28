package onetoone.Clans;

import onetoone.Users.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer clan_leader;
    private String clan_name;

    @OneToMany(mappedBy = "clan")
    private List<User> members;

    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getLeader() {
        return clan_leader;
    }
    public void setLeader(Integer id) {
        this.clan_leader = id;
    }

    public String getClanName() {
        return clan_name;
    }

    public void setClanName(String name) {
        this.clan_name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
