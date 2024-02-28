package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import onetoone.Laptops.LaptopRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import onetoone.Clans.Clan;
import onetoone.Clans.ClanRepository;

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
    CommandLineRunner initUser(UserRepository userRepository, ClanRepository clanRepository) {
        return args -> {
            User user1 = new User("John", "john2");
            User user2 = new User("Jenny", "8675309");


            userRepository.save(user1);
            userRepository.save(user2);


            Clan clan1 = new Clan("JennyClan", user2.getId());
            //System.out.println(user2.getId());
            clanRepository.save(clan1);
            user2.setClan(clan1);
            user1.setClan(clan1);
            userRepository.save(user2);
            userRepository.save(user1);

        };
    }

}
