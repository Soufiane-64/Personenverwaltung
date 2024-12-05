package de.szut.springboot_crud_service_demo.controller;

import de.szut.springboot_crud_service_demo.model.Person;
import de.szut.springboot_crud_service_demo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/SpringBootCrudService/person")
public class PersonController {

    private final PersonRepository personRepository;

    // Constructor Dependency Injection
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Welcome Endpoint
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to Person Management Service!");
    }

    // Create a new person
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    // Get all persons
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Get person by ID with detailed error handling
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Person> getPersonById(@PathVariable long id) {
        // Add logging or debug print
        System.out.println("Attempting to retrieve person with ID: " + id);

        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            // Add debug print
            System.out.println("Person found: " + person.get());
            return ResponseEntity.ok(person.get());
        } else {
            // Add debug print
            System.out.println("No person found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Update a person
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable long id,
            @RequestBody Person personDetails) {

        return personRepository.findById(id)
                .map(existingPerson -> {
                    existingPerson.setFirstname(personDetails.getFirstname());
                    existingPerson.setSurname(personDetails.getSurname());

                    Person updatedPerson = personRepository.save(existingPerson);
                    return ResponseEntity.ok(updatedPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a person
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable long id) {
        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}