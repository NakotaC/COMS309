package onetoone;

import onetoone.Clans.ClanRepository;
import onetoone.EquippedItems.EquippedItems;
import onetoone.EquippedItems.EquippedItemsRepository;
import onetoone.Game.Game;
import onetoone.Inventory.Inventory;
import onetoone.Inventory.InventoryRepository;
import onetoone.ShopItems.ShopItem;
import onetoone.ShopItems.ShopItemsRepository;
import onetoone.Wins.Wins;
import onetoone.Wins.WinsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import onetoone.Clans.Clan;
import onetoone.Clans.ClanRepository;

import onetoone.Game.Game;
import onetoone.Game.GameRepository;

import onetoone.MatchHistory.History;
import onetoone.MatchHistory.HistoryRepository;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TimeZone;



/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);



        //STUFF TO TEST THAT THE APP WORKS FROM NOTHING
        //REMOVE ALL STUFF BELOW MAIN AND TRY TO USE THE APP FROM SCRATCH
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Example GET request
//        String url = "http://localhost:8080/";
//        String response = restTemplate.getForObject(url + "wins", String.class);
//        System.out.print(response);
//
//        String requestBody = null;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("username", "John");
//        headers.set("password", "JohnPassword");
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//        restTemplate.postForObject(url + "users/signup", requestEntity, String.class);
//
//        restTemplate.postForObject(url + "clans/sorryplayer/1", requestBody, String.class);
//        headers.set("item", "1");
//        headers.set("username", "John");
//        restTemplate.postForObject(url + "/inventory/shop/buy", requestEntity, String.class);
//
//        headers.set("itemNum", "0");
//        restTemplate.put(url + "/equip/1", requestEntity, String.class);
    }

    // Create 3 users with their machines
    /**
     * 
     * @param userRepository repository for the User entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Laptop object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, WinsRepository winsRepository, ShopItemsRepository shopItemsRepository, ClanRepository clanRepository, InventoryRepository inventoryRepository, GameRepository gameRepository, EquippedItemsRepository equippedItemsRepository) {
        return args -> {
            //NEEDS TO STAY IN MAIN WHEN GAME IS "RELEASED"
            if(clanRepository.count() <= 0) {
                Clan noClan = new Clan("noClan", -2, userRepository);
                noClan.setMax_members(100000);
                clanRepository.save(noClan);
                ShopItem SI1 = new ShopItem("Item1", "Description of Item1");
                ShopItem SI2 = new ShopItem("Item2", "Description of Item2");
                ShopItem SI3 = new ShopItem("Item3", "Description of Item3");
                ShopItem SI4 = new ShopItem("Item4", "Description of Item4");
                ShopItem SI5 = new ShopItem("Item5", "Description of Item5");
                ShopItem SI6 = new ShopItem("Item6", "Description of Item6");
                ShopItem SI7 = new ShopItem("Item7", "Description of Item7");
                ShopItem SI8 = new ShopItem("Item8", "Description of Item8");
                shopItemsRepository.save(SI1);
                shopItemsRepository.save(SI2);
                shopItemsRepository.save(SI3);
                shopItemsRepository.save(SI4);
                shopItemsRepository.save(SI5);
                shopItemsRepository.save(SI6);
                shopItemsRepository.save(SI7);
                shopItemsRepository.save(SI8);
                Game g = new Game();
                gameRepository.save(g);
                Game g2 = new Game();
                gameRepository.save(g2);
            }





            //
            //silly init testing stuff that doesnt matter
            //


        };
    }

}
