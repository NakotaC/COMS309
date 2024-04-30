package onetoone;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import onetoone.Game.GameRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class JackSystemTest {

    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository repo) {
        userRepository = repo;  // we are setting the static variable
    }


    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void loginTest() {
        Response response = RestAssured.given().
                header("username", "John").
                header("password", "JohnPassword").
                when().
                get("/users/login");

        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        String compareCharizards = "{\"id\":1,\"username\":\"John\",\"password\":\"JohnPassword\",\"bank\":0,\"xp\":0,\"equipped\":{\"id\":1,\"shopItems\":[{\"id\":1,\"description\":\"Red game piece\",\"itemName\":\"Red\"}]}}";

        String rs = response.getBody().asString();
        try {
            //JSONArray ra = new JSONArray(rs);
            //JSONObject ro = ra.getJSONObject(ra.length()-1);
            assertEquals(compareCharizards, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void drawTest() {
        Response response = RestAssured.given().
                header("gameid", "1").
                when().
                get("/draw/str");

        int statuscode = response.getStatusCode();
        assertEquals(200, statuscode);
        String rs = response.getBody().asString();
        String zero = "{\"Card\":\"0\"}";
        String one = "{\"Card\":\"1\"}";
        String two = "{\"Card\":\"2\"}";
        String three = "{\"Card\":\"3\"}";
        String four = "{\"Card\":\"4\"}";
        String five = "{\"Card\":\"5\"}";
        String six = "{\"Card\":\"6\"}";
        String seven = "{\"Card\":\"7\"}";
        String eight = "{\"Card\":\"8\"}";
        String ten = "{\"Card\":\"10\"}";
        String eleven = "{\"Card\":\"11\"}";
        String twelve = "{\"Card\":\"12\"}";
        try {
            if (!(zero.equals(rs) || one.equals(rs) || two.equals(rs) || three.equals(rs) || four.equals(rs) || five.equals(rs) || six.equals(rs) || seven.equals(rs) || eight.equals(rs) || ten.equals(rs) || eleven.equals(rs) || twelve.equals(rs))) {
                assertEquals(1, 2);
            } else {
                assertEquals(1, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itemUETest() {
        Response response1 = RestAssured.given().
                get("/equippedItems/1");
        String rs1 = response1.getBody().asString();

        int statuscode = response1.getStatusCode();
        assertEquals(200, statuscode);

        assertEquals(rs1,rs1);

        Response response = RestAssured.given().
                header("itemNum", "0").
                when().
                delete("/unequip/1");

        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        String successString = "{\"message\":\"success\"}";

        String rs = response.getBody().asString();

        assertEquals(successString, rs);

        Response response2 = RestAssured.given().
                get("/equippedItems/1");

        String rs2 = response2.getBody().asString();
        assertNotSame(rs2,rs1);

        Response response3 = RestAssured.given().
                header("itemNum", "0").
                when().
                put("/equip/1");
        String rs3 = response3.getBody().asString();

        assertEquals(successString, rs3);

        statuscode = response1.getStatusCode();
        assertEquals(200, statuscode);

        Response response4 = RestAssured.given().
                get("/equippedItems/1");
        String rs4 = response4.getBody().asString();

        statuscode = response4.getStatusCode();
        assertEquals(200, statuscode);

        assertEquals(rs1,rs4);
    }
    @Test
    public void signupTest() {
        Response response1 = RestAssured.given().
                get("/users");
        String rs1 = response1.getBody().asString();

        int statuscode = response1.getStatusCode();
        assertEquals(200, statuscode);

        assertEquals(rs1,rs1);

        Response response = RestAssured.given().
                header("username", "TestUser").
                header("password", "TestUserPassword").
                when().
                post("/users/signup");

        statuscode = response.getStatusCode();
        assertEquals(200, statuscode);

        String successString = "{\"message\":\"success\"}";

        String rs = response.getBody().asString();

        assertEquals(successString, rs);

        Response response2 = RestAssured.given().
                get("/users");

        statuscode = response2.getStatusCode();
        assertEquals(200, statuscode);

        String rs2 = response2.getBody().asString();
        assertNotSame(rs2,rs1);

        List<User> fortniters = userRepository.findAll();
        User lastuser = fortniters.get((int) userRepository.count() - 1);
        int last = lastuser.getId();
        System.out.println(last);
        String fort = "/users/" + last;

        Response response3 = RestAssured.given().
                header("itemNum", "0").
                when().
                delete(fort);
        String rs3 = response3.getBody().asString();
        assertEquals(successString, rs3);

        statuscode = response3.getStatusCode();
        assertEquals(200, statuscode);

        Response response4 = RestAssured.given().
                get("/users");

        statuscode = response4.getStatusCode();
        assertEquals(200, statuscode);

        String rs4 = response4.getBody().asString();
        assertNotSame(rs4,rs1);
    }
}