package onetoone;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import onetoone.Clans.ClanRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import onetoone.Wins.WinsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

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
}