package onetoone;

import onetoone.Wins.Wins;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import onetoone.Clans.ClanRepository;
import onetoone.Wins.WinsRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class HudsonSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    ClanRepository clanRepository;

    @Autowired
    WinsRepository winsRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    //
    // ClansController
    //

    @Test
    public void GetClan1Test() {
        // Send request and receive response
        Response response = RestAssured.given().get("/clans/1");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals("{" +
                    "    \"id\": 1," +
                    "    \"members\": [" +
                    "        {" +
                    "            \"id\": 2," +
                    "            \"username\": \"Tom\"," +
                    "            \"password\": \"TomPassword\"," +
                    "            \"equipped\": {" +
                    "                \"id\": 2," +
                    "                \"shopItems\": [" +
                    "                    {" +
                    "                        \"id\": 4," +
                    "                        \"description\": \"Description of Item4\"," +
                    "                        \"itemName\": \"Item4\"" +
                    "                    }" +
                    "                ]" +
                    "            }" +
                    "        }," +
                    "        {" +
                    "            \"id\": 3," +
                    "            \"username\": \"Robby\"," +
                    "            \"password\": \"RobbyPassword\"," +
                    "            \"equipped\": {" +
                    "                \"id\": 3," +
                    "                \"shopItems\": [" +
                    "                    {" +
                    "                        \"id\": 8," +
                    "                        \"description\": \"Description of Item8\"," +
                    "                        \"itemName\": \"Item8\"" +
                    "                    }" +
                    "                ]" +
                    "            }" +
                    "        }" +
                    "    ]," +
                    "    \"clanName\": \"JennyClan\"," +
                    "    \"leader\": 2" +
                    "}", response.getBody().asString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GetAllClanTest() {
        // Send request and receive response
        Response response = RestAssured.given().get("/clans");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertNotNull(response.getBody().asString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CreateClanTest() {
        // Send request and receive response
        Response response = RestAssured.given().post("/clans/SorryClans/2");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals(clanRepository.findById(clanRepository.findAll().size() - 1), response.getBody().asString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //
    //WinsController
    //

    @Test
    public void GetWinsTest() {
        // Send request and receive response
        Response response = RestAssured.given().get("/wins");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertNotNull(response.getBody().asString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AddWinsToUserTest(){
        Wins before = winsRepository.findById(1);
        // Send request and receive response
        Response response = RestAssured.given().post("/wins/1/1");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);


        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals(before.getWins() + 1, winsRepository.findById(1).getWins());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PostQuestToUserTest(){

        Wins before = winsRepository.findById(1);
        Wins before_before = before;
        before.setQuest(-1);
        before.setScalar(-1);
        before.setQDate(-1);

        winsRepository.save(before);

        // Send request and receive response
        Response response = RestAssured.given().get("/quest/1");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            int j[] = new int[2];
            j[0] = before.getQuest(); j[1] = before.getScalar();
            assertEquals(j, response.getBody().asString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response response1 = RestAssured.given().post("/quest/1");

        // Check status code
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String returnString1 = response1.getBody().asString();
        try {
            JSONArray returnArr1 = new JSONArray(returnString1);
            JSONObject returnObj1 = returnArr1.getJSONObject(returnArr1.length() - 1);
            assertNotEquals(before.getQuest(), winsRepository.findById(1).getQuest());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int after_first = winsRepository.findById(1).getQuest();
        before.setQDate(0);

        winsRepository.save(before);
        Response response2 = RestAssured.given().post("/quest/1");

        // Check status code
        int statusCode2 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String returnString2 = response1.getBody().asString();
        try {
            JSONArray returnArr2 = new JSONArray(returnString2);
            JSONObject returnObj2 = returnArr2.getJSONObject(returnArr2.length() - 1);
            assertNotEquals(after_first, winsRepository.findById(1).getQuest());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        before.setQDate(0);

        winsRepository.save(before);
        Response response3 = RestAssured.given().put("/quest/1");

        // Check status code
        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);

        String returnString3 = response1.getBody().asString();
        try {
            JSONArray returnArr3 = new JSONArray(returnString3);
            JSONObject returnObj3 = returnArr3.getJSONObject(returnArr3.length() - 1);
            assertNotEquals(0 ,winsRepository.findById(1).getQDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Check response body for correct response
        Response response4 = RestAssured.given().put("/quest/1/22");

        // Check status code
        int statusCode4 = response4.getStatusCode();
        assertEquals(200, statusCode4);

        String returnString4 = response1.getBody().asString();
        try {
            JSONArray returnArr3 = new JSONArray(returnString4);
            JSONObject returnObj3 = returnArr3.getJSONObject(returnArr3.length() - 1);
            assertNotEquals(22 ,winsRepository.findById(1).getQuest());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        winsRepository.save(before_before);

    }

}
