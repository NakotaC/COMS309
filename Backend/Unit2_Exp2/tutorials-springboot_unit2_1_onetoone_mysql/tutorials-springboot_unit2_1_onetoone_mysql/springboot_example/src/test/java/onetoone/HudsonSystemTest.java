package onetoone;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class HudsonSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    ClanRepository clanRepository;

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

}
