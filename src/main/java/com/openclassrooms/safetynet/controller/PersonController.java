package com.openclassrooms.safetynet.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import          com.openclassrooms.safetynet.entity.Person;
import          com.openclassrooms.safetynet.service.PersonService;
import static   com.openclassrooms.safetynet.controller.tool.Filter.*;

@RestController
public class PersonController
{
    @Autowired
    private PersonService service;


    @GetMapping("/persons")
    public MappingJacksonValue getPersons()
    {
        MappingJacksonValue mapping = new MappingJacksonValue(service.getPersons());
        mapping.setFilters(keepAllFieldsFilterProvider);
        return mapping;
    }


    @GetMapping("/person/{id}")
    public MappingJacksonValue getPersonById(@PathVariable("id") final int id)
    {
        MappingJacksonValue mapping;

        Optional<Person> person = service.getPersonById(id);

        if (person.isPresent())
        {
            mapping = new MappingJacksonValue(person.get());
            mapping.setFilters(keepAllFieldsFilterProvider);
            return mapping;
        }
        else
        {
            return new MappingJacksonValue("Person Not Found");
        }
    }


    @GetMapping("/personname")
    public MappingJacksonValue getPerson(
                                            @RequestParam("firstName")  String firstName,
                                            @RequestParam("lastName")   String lastName
                                        )
    {
        MappingJacksonValue mapping;

        Optional<Person> person = service.getPersonByName(firstName, lastName);

        if (person.isPresent())
        {
            mapping = new MappingJacksonValue(person.get());
            mapping.setFilters(keepAllFieldsFilterProvider);
            return mapping;
        }
        else
        {
            return new MappingJacksonValue("Person Not Found");
        }
    }
}