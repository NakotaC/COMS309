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

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/people")
    public  HashMap<String,Person> getAllPersons() {
        return peopleList;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public  String createPerson(Person person) {
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
    }

    //Creates a person with a provided firstname
    @PostMapping("/people/{firstname}")
    public  String createPersonWithName(Person person, @PathVariable String firstname) {
        person.setFirstName(firstname);
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
    }

    @PostMapping("/people/{firstname}/{lastname}")
    public  String createPersonWithName(Person person, @PathVariable String firstname, @PathVariable String lastname) {
        person.setFirstName(firstname);
        person.setLastName(lastname);
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
    }

    @PostMapping("/people/{firstname}/{lastname}/{address}")
    public  String createPersonWithName(Person person, @PathVariable String firstname, @PathVariable String lastname, @PathVariable String address) {
        person.setFirstName(firstname);
        person.setLastName(lastname);
        person.setAddress(address);
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
    }
    @PostMapping("/people/{firstname}/{lastname}/{address}/{phone}")
    public  String createPersonWithName(Person person, @PathVariable String firstname, @PathVariable String lastname, @PathVariable String address, @PathVariable String phone) {
        person.setFirstName(firstname);
        person.setLastName(lastname);
        person.setAddress(address);
        person.setTelephone(phone);
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
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

    //Update first name and key based on first name
    @PutMapping("/people/{firstName}/firstnameandkey/{firstnameNew}")
    public Person updatePersonFnameAndKey(@PathVariable String firstName, Person p, @PathVariable String firstnameNew) {
        p = peopleList.get(firstName);
        p.setFirstName(firstnameNew);
        peopleList.remove(firstName);
        peopleList.put(firstnameNew, p);
        return peopleList.get(firstnameNew);
    }

    //Update first name but not the key based on first name
    @PutMapping("/people/{firstName}/firstname/{firstnameNew}")
    public Person updatePersonFname(@PathVariable String firstName, Person p, @PathVariable String firstnameNew) {
        p = peopleList.get(firstName);
        p.setFirstName(firstnameNew);
        return peopleList.get(firstName);
    }

    //Update last name based on first name
    @PutMapping("/people/{firstName}/lastname/{lastname}")
    public Person updatePersonLname(@PathVariable String firstName, Person p, @PathVariable String lastname) {
        p = peopleList.get(firstName);
        p.setLastName(lastname);
        return peopleList.get(firstName);
    }

    //Update address based on first name
    @PutMapping("/people/{firstName}/address/{address}")
    public Person updatePersonAdd(@PathVariable String firstName, Person p, @PathVariable String address) {
        p = peopleList.get(firstName);
        p.setAddress(address);
        return peopleList.get(firstName);
    }

    //update phone based on first name
    @PutMapping("/people/{firstName}/phone/{phone}")
    public Person updatePersonPhone(@PathVariable String firstName, Person p, @PathVariable String phone) {
        p = peopleList.get(firstName);
        p.setTelephone(phone);
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

