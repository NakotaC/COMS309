package onetomany;

import java.util.Date;

import onetomany.Monitors.Monitor;
import onetomany.Monitors.MonitorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetomany.Laptops.Laptop;
import onetomany.Laptops.LaptopRepository;
import onetomany.Phones.Phone;
import onetomany.Phones.PhoneRepository;
import onetomany.Users.User;
import onetomany.Users.UserRepository;

@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 users with their machines and phones
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, LaptopRepository laptopRepository, PhoneRepository phoneRepository, MonitorRepository monitorRepository) {
        return args -> {
            User user1 = new User("John", "john@somemail.com", new Date());
            User user2 = new User("Jane", "jane@somemail.com", new Date());
            User user3 = new User("Justin", "justin@somemail.com", new Date());
            Laptop laptop1 = new Laptop( 2.5, 4, 8, "Lenovo", 300);
            Laptop laptop2 = new Laptop( 4.1, 8, 16, "Hp", 800);
            Laptop laptop3 = new Laptop( 3.5, 32, 32, "Dell", 2300); 
            for(int i=6; i<13; i++)
                phoneRepository.save(new Phone("Apple", (int)Math.pow(1.3, i), Math.pow(1.1, i)*1000, "iPhone "+i, (int)Math.pow(1.3, i)*100));
            monitorRepository.save(new Monitor("LG", "1920x1080", "570X", 730));
            monitorRepository.save(new Monitor("DELL", "1920x1080", "GOAT", 460));
            monitorRepository.save(new Monitor("SAMSUNG", "1920x1080", "MONKEY", 300));
            monitorRepository.save(new Monitor("ASUS", "1920x1080", "280FP", 200));
            monitorRepository.save(new Monitor("GIGABYTE", "1920x1080", "SUPER", 345));
            monitorRepository.save(new Monitor("DELL", "1920x1080", "CREW", 800));
            monitorRepository.save(new Monitor("LG", "1920x1080", "1000SUN", 1000));
            monitorRepository.save(new Monitor("SAMSUNG", "1920x1080", "FNBP", 100000));
            monitorRepository.save(new Monitor("DELL", "1920x1080", "HUH", 400));
            laptop1.addMonitors(monitorRepository.findById(1));
            laptop1.addMonitors(monitorRepository.findById(2));
            laptop1.addMonitors(monitorRepository.findById(3));
            laptop2.addMonitors(monitorRepository.findById(4));
            laptop2.addMonitors(monitorRepository.findById(5));
            laptop2.addMonitors(monitorRepository.findById(6));
            laptop3.addMonitors(monitorRepository.findById(7));
            laptop3.addMonitors(monitorRepository.findById(8));
            laptop3.addMonitors(monitorRepository.findById(9));
            user1.setLaptop(laptop1);
            user2.setLaptop(laptop2);
            user3.setLaptop(laptop3);
            user1.addPhones(phoneRepository.findById(1));
            user1.addPhones(phoneRepository.findById(2));            
            user1.addPhones(phoneRepository.findById(6));
            user2.addPhones(phoneRepository.findById(3));
            user2.addPhones(phoneRepository.findById(4)); 
            user3.addPhones(phoneRepository.findById(5));
            user3.addPhones(phoneRepository.findById(7));
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        };
    }

}