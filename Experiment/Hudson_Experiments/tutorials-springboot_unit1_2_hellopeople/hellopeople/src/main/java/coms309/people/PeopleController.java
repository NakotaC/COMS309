package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class PeopleController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, Person> peopleList = new  HashMap<>();

    @GetMapping("/people")
    public  HashMap<String,Person> getAllPersons() {
        return peopleList;
    }

    /*
    deleted their postfunction, because it created blank space and I thought that was both
    bad, and was unsure if it was dangerous to create an empty key value relation.
     */

    //made post that allows you make a new person with all info
    @PostMapping("/people/{first_name}/{last_name}/{address}/{telephone}")
    public String createPerson(@PathVariable String first_name, @PathVariable String last_name, @PathVariable String address, @PathVariable String telephone) {
        Person p = new Person(first_name, last_name, address, telephone);
        peopleList.put(p.getFirstName(), p);
        return "New person "+ p.getFirstName() + " full info saved success.";
    }
    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/people/{firstName}")
    public Person getPerson(@PathVariable String firstName) {
        Person p = peopleList.get(firstName);
        return p;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{firstName}")
    public Person updatePerson(@PathVariable String firstName, Person p) {
        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    @PutMapping("/people/{firstName}/{lastName}")
    public Person updatePerson(@PathVariable String firstName,
                               @PathVariable String lastName, Person p) {
        p.setLastName(lastName);

        p.setFirstName(peopleList.get(firstName).getFirstName());
        p.setLastName(peopleList.get(firstName).getLastName());
        p.setAddress(peopleList.get(firstName).getAddress());
        p.setTelephone(peopleList.get(firstName).getTelephone());

        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    @PutMapping("/people/{firstName}/{lastName}/{address}")
    public Person updatePerson(@PathVariable String firstName,
                               @PathVariable String lastName,
                               @PathVariable String address, Person p) {
        p.setLastName(lastName);
        p.setAddress(address);

        p.setFirstName(peopleList.get(firstName).getFirstName());
        p.setLastName(peopleList.get(firstName).getLastName());
        p.setAddress(peopleList.get(firstName).getAddress());
        p.setTelephone(peopleList.get(firstName).getTelephone());

        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    @PutMapping("/people/{firstName}/{lastName}/{address}/{telephone}")
    public Person updatePerson(@PathVariable String firstName,
                               @PathVariable String lastName,
                               @PathVariable String address,
                               @PathVariable String telephone, Person p) {
        p.setLastName(lastName);
        p.setAddress(address);
        p.setAddress(telephone);

        p.setFirstName(peopleList.get(firstName).getFirstName());
        p.setLastName(peopleList.get(firstName).getLastName());
        p.setAddress(peopleList.get(firstName).getAddress());
        p.setTelephone(peopleList.get(firstName).getTelephone());

        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    @PutMapping("/people/{firstName}//{address}/{telephone}")
    public Person updatePersone(@PathVariable String firstName,
                               @PathVariable String address,
                               @PathVariable String telephone, Person p) {
        p.setAddress(address);
        p.setAddress(telephone);

        p.setFirstName(peopleList.get(firstName).getFirstName());
        p.setLastName(peopleList.get(firstName).getLastName());
        p.setAddress(peopleList.get(firstName).getAddress());
        p.setTelephone(peopleList.get(firstName).getTelephone());

        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    @PutMapping("/people/{firstName}//{address}")
    public Person updatePersone(@PathVariable String firstName,
                                @PathVariable String address, Person p) {
        p.setAddress(address);

        p.setFirstName(peopleList.get(firstName).getFirstName());
        p.setLastName(peopleList.get(firstName).getLastName());
        p.setAddress(peopleList.get(firstName).getAddress());
        p.setTelephone(peopleList.get(firstName).getTelephone());

        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    @PutMapping("/people/{firstName}///{telephone}")
    public Person updatePersoone(@PathVariable String firstName,
                                @PathVariable String telephone, Person p) {
        p.setAddress(telephone);

        p.setFirstName(peopleList.get(firstName).getFirstName());
        p.setLastName(peopleList.get(firstName).getLastName());
        p.setAddress(peopleList.get(firstName).getAddress());
        p.setTelephone(peopleList.get(firstName).getTelephone());

        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    @PutMapping("/people/{firstName}/{lastName}//{telephone}")
    public Person updatePersoon(@PathVariable String firstName,
                               @PathVariable String lastName,
                               @PathVariable String telephone, Person p) {
        p.setLastName(lastName);
        p.setAddress(telephone);

        p.setFirstName(peopleList.get(firstName).getFirstName());
        p.setLastName(peopleList.get(firstName).getLastName());
        p.setAddress(peopleList.get(firstName).getAddress());
        p.setTelephone(peopleList.get(firstName).getTelephone());

        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }



    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/people/{firstName}")
    public HashMap<String, Person> deletePerson(@PathVariable String firstName) {
        peopleList.remove(firstName);
        return peopleList;
    }
}

