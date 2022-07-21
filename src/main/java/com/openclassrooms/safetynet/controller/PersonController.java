package com.openclassrooms.safetynet.controller;

import java.util.HashSet;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;

import lombok.extern.slf4j.Slf4j;

import        com.openclassrooms.safetynet.entity.Person;
import        com.openclassrooms.safetynet.service.PersonService;
import        com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import        com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import static com.openclassrooms.safetynet.controller.FilterMapping.mapping;


@Slf4j
@RestController
public class PersonController
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Value("#{'${keepAll_person_filter}'.split(';')}")
    private       HashSet<String> keepAllFields;

    private final PersonService   service;

    // ======================================
    // =            Constructors            =
    // ======================================

    public PersonController(PersonService _service)
    {
        this.service   = _service;
    }

    // ======================================
    // =         Controller Methods         =
    // ======================================

    @PostMapping(value = "/person")
    public MappingJacksonValue createPerson(@RequestBody Person person)
    {
        log.info("PersonController.createPerson CALLED. person = " + person);

        try
        {
            service.addPerson(person);
        }
        catch(EntityAlreadyExistException exception)
        {
            log.error("PersonController.createPerson FAILED. person = " + person);
            throw exception;
        }
        return mapping(person, keepAllFields);
    }


    @PutMapping(value = "/person")
    public MappingJacksonValue updatePerson(@RequestBody Person person)
    {
        log.info("PersonController.updatePerson CALLED. person = " + person);

        try
        {
            service.updatePerson(person);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("PersonController.updatePerson FAILED. person = " + person);
            throw exception;
        }

        return mapping(person, keepAllFields);
    }


    @DeleteMapping(value = "/person")
    public MappingJacksonValue deletePerson(@RequestBody Person person)
    {
        log.info("PersonController.deletePerson CALLED. person = " + person);

        Person oldPerson;

        try
        {
            oldPerson = service.removePerson(person);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("PersonController.deletePerson FAILED. person = " + person);
            throw exception;
        }

        return mapping(oldPerson, keepAllFields);
    }
}