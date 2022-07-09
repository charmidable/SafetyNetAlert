package com.openclassrooms.safetynet.controller;

import org.springframework.web.bind.annotation.*;

import          com.openclassrooms.safetynet.entity.Person;
import          com.openclassrooms.safetynet.service.PersonService;


@RestController
public class PersonController
{

    // ======================================
    // =             Attributes             =
    // ======================================

    private final PersonService service;


    // ======================================
    // =            Constructors            =
    // ======================================

    public PersonController(PersonService service)
    {
        this.service = service;
    }


    // ======================================
    // =    Controller Methods      =
    // ======================================

    @PostMapping(value = "/person")
    public void createPerson(Person person)
    {
        service.addPerson(person);
    }


    @PutMapping(value = "/person")
    public void updatePerson(Person person)
    {
        service.updatePerson(person);
    }


    @DeleteMapping(value = "/person")
    public void deletePerson(
                                @RequestParam("firstName")  String firstName,
                                @RequestParam("lastName")   String lastName
                            )
    {
        service.removePerson(firstName, lastName);
    }

}