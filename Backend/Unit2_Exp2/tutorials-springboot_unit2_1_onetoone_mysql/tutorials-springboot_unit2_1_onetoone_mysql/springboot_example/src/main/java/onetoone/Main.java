package onetoone;

import onetoone.Clans.ClanRepository;
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



    }

    // Create 3 users with their machines
    /**
     * 
     * @param userRepository repository for the User entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Laptop object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, WinsRepository winsRepository, ShopItemsRepository shopItemsRepository, ClanRepository clanRepository, InventoryRepository inventoryRepository, GameRepository gameRepository, HistoryRepository historyRepository) {
        return args -> {
            User user1 = new User("John", "JohnPassword");
            User user2 = new User("Tom", "TomPassword");
            User user3 = new User("Robby", "RobbyPassword");
            Inventory inventory1 = new Inventory();
            Inventory inventory2 = new Inventory();
            Inventory inventory3 = new Inventory();
            ArrayList<ShopItem> inv1items = new ArrayList<>();
            ArrayList<ShopItem> inv2items = new ArrayList<>();
            ArrayList<ShopItem> inv3items = new ArrayList<>();
            ShopItem SI1 = new ShopItem("Item1", "Description of Item1");
            ShopItem SI2 = new ShopItem("Item2", "Description of Item2");
            ShopItem SI3 = new ShopItem("Item3", "Description of Item3");
            ShopItem SI4 = new ShopItem("Item4", "Description of Item4");
            ShopItem SI5 = new ShopItem("Item5", "Description of Item5");
            ShopItem SI6 = new ShopItem("Item6", "Description of Item6");
            ShopItem SI7 = new ShopItem("Item7", "Description of Item7");
            ShopItem SI8 = new ShopItem("Item8", "Description of Item8");
            inv1items.add(SI1);
            inv1items.add(SI2);
            inv1items.add(SI3);
            inv2items.add(SI4);
            inv2items.add(SI5);
            inv2items.add(SI6);
            inv3items.add(SI7);
            inv3items.add(SI8);
            user2.setWins(14000);
            inventory1.setShopItems(inv1items);
            inventory2.setShopItems(inv2items);
            inventory3.setShopItems(inv3items);
            shopItemsRepository.save(SI1);
            shopItemsRepository.save(SI2);
            shopItemsRepository.save(SI3);
            shopItemsRepository.save(SI4);
            shopItemsRepository.save(SI5);
            shopItemsRepository.save(SI6);
            shopItemsRepository.save(SI7);
            shopItemsRepository.save(SI8);
            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory2);
            inventoryRepository.save(inventory3);
            user1.setInventory(inventory1);
            user2.setInventory(inventory2);
            user3.setInventory(inventory3);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            Clan clan1 = new Clan("JennyClan", user2.getId());
            clanRepository.save(clan1);
            user2.setClan(clan1);
            user1.setClan(clan1);
            user3.setClan(clan1);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            Game g = new Game();
            gameRepository.save(g);
//            History h = new History(new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")), user1,  user1);
//            historyRepository.save(h);
        };
    }

}
