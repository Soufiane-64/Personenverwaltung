package de.szut.springboot_crud_service_demo.controller;

import de.szut.springboot_crud_service_demo.model.Person;
import de.szut.springboot_crud_service_demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiagnosticController {
    @Autowired
    private PersonRepository repository;

    @GetMapping("/diagnostic/persons")
    public List<Person> getAllPersonsWithDetails() {
        List<Person> persons = repository.findAll();
        persons.forEach(p -> System.out.println(
                "ID: " + p.getId() +
                        ", Firstname: " + p.getFirstname() +
                        ", Surname: " + p.getSurname()
        ));
        return persons;
    }
}
