package onetoone;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import onetoone.ChatRoom.Message;
import onetoone.Clans.Clan;
import onetoone.Clans.ClanRepository;
import onetoone.EquippedItems.EquippedItems;
import onetoone.Game.Game;
import onetoone.Game.GameRepository;
import onetoone.Inventory.Inventory;
import onetoone.ShopItems.ShopItem;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import onetoone.Wins.Wins;
import onetoone.Wins.WinsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MoreSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    ClanRepository clanRepository;

    @Autowired
    WinsRepository winsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void TestClans() {
        Response response = RestAssured.given().get("/clans");
        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().get("/clans/open");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().get("/clans/1");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().post("clans/SuperSorry/1");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().post("clans/SuperDuperSorry/3/Open/64");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().post("member/SuperSorry/2");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().post("member/2");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().post("clan/Clan/cLAN/CLAM/CLam/cLAM");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);
    }


    @Test
    public void TestEquippedItems() {
        Response response = RestAssured.given().get("/equippedItems/1");
        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().header("itemNum", 1).
                when().put("equip/1");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().header("itemNum", 1).
                when().delete("/unequip/1");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().post("/newEquip/13");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

    }

    @Test
    public void TestGame() {
        Response response = RestAssured.given().header("gameid", 1).get("/draw/str");
        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);
    }

    @Test
    public void TestInventory() {
        Response response = RestAssured.given().get("/inventory");
        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().get("/inventory/1");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().get("/inventory/shop");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        User user = new User("Gerald IIV", "GP", clanRepository);
        userRepository.save(user);
        response = RestAssured.given().post("/inventory/" + user.getId());
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().header("item", 5).header("username", user.getUsername()).post("/inventory/shop/buy");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().delete("/users/" + user.getId());
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);
    }

    @Test
    public void TestMatchHistory() {
        Response response = RestAssured.given().get("/history/all");
        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().get("/history/1");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().post("/history/1/1");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        response = RestAssured.given().delete("/history/1");
        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);
    }

    @Test
    public void TestShopItems() {
        Response response = RestAssured.given().get("/shopItems");
        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);
    }

    @Test
    public void TestUser() {
        Response response = RestAssured.given().get("/users/1");
        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);
    }

    @Test
    public void TestWins() {
        Response response = RestAssured.given().post("/quest/1/1/1");
        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);
    }

    @Test
    public void TestSHOPITEMS() {
        ShopItem si = new ShopItem("tmp", "tmpdesc");
        si.setId(16);
        assertEquals(si.getId(), 16);
        si.setDescription("newdesc");
        assertEquals(si.getDescription(), "newdesc");
    }
    @Test
    public void TestINVENTORY() {
        Inventory i = new Inventory();
        i.setId(4);
        assertEquals(i.getId(), 4);
        User u = new User();
        EquippedItems  e = new EquippedItems();
        e.setUser(u);
        e.setId(16);
        assertEquals(e.getId(), 16);
        User euser = e.getUser();
        assertEquals(e.getUser(), euser);
        ShopItem si = new ShopItem("tmp", "tmpdesc");
        e.setShopItems(si);
    }

    @Test
    public void TestUSER() {
        Clan c = new Clan();
        c.setId(4);
        int tmp11 = c.getId();
        assertEquals(tmp11, 4);
        User u = new User();
        Wins w = new Wins(5);
        u.setWins2(5);
        assertEquals(u.getWins().getWins(), w.getWins());
        u.setId(44);
        assertEquals(u.getId(), 44);
        u.setUsername("newuname");
        assertEquals(u.getUsername(), "newuname");
        u.setPassword("newpass");
        assertEquals(u.getPassword(), "newpass");

        ShopItem si = new ShopItem("tmp", "tmpdesc");
        EquippedItems  e = new EquippedItems();
        e.setUser(u);
        u.setEquipped(e);
        e.setShopItems(si);
        u.setequippedItems(si);
        assertEquals(u.getEquipped(), e);
        u.setXp(10);
        assertEquals(u.getXp(), 10);
        u.setBank(10);
        assertEquals(u.getBank(), 10);

    }
    @Test
    public void TestMESSAGE() {

        Message m = new Message("fortnite", "Whats up");
        long t = 4;
        m.setId(t);
        long fort = m.getId();
        assertEquals(fort, t);
        m.setUserName("newfortnite");
        assertEquals(m.getUserName(), "newfortnite");
        m.setContent("new Whats up");
        assertEquals(m.getContent(), "new Whats up");
        Date sent = new Date();
        m.setSent(sent);
        assertEquals(m.getSent(), sent);
    }

    @Test
    public void TestGAME() throws JsonProcessingException {

        Game g = gameRepository.findById(1);
        g.setId(5);
        assertEquals(g.getId(), 5);
        g.setId(1);
        assertEquals(g.getId(), 1);
        g.Draw();
        g.Shuffle();

    }





}