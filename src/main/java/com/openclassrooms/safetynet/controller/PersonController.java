package com.openclassrooms.safetynet.controller;

import java.util.Optional;


import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import          com.openclassrooms.safetynet.entity.Person;
import          com.openclassrooms.safetynet.entity.Medicalrecord;
import          com.openclassrooms.safetynet.service.PersonService;
import static   com.openclassrooms.safetynet.controller.tool.Filter.*;

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
    // =    Controller Person Methods      =
    // ======================================

    @GetMapping("/person")
    public MappingJacksonValue findPersonByName(
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
            return null;
        }
    }


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


    // ======================================
    // =  Controller Medicalrecord Methods  =
    // ======================================

    @GetMapping("/medicalrecord")
    public MappingJacksonValue findMedicalrecordByName(
            @RequestParam("firstName")  String firstName,
            @RequestParam("lastName")   String lastName
    )
    {
        MappingJacksonValue mapping;

        Medicalrecord medicalrecord = service.getMedicalrecordByName(firstName, lastName);

        if (medicalrecord != null)
        {
            mapping = new MappingJacksonValue(medicalrecord);
            return mapping;
        }
        else
        {
            return null;
        }
    }


    @PostMapping(value = "/medicalrecord")
    public void createMedicalRecord(Medicalrecord medicalrecord)
    {
        service.addMedicalrecord(medicalrecord);
    }


    @PutMapping(value = "/medicalrecord")
    public void updateMedicalRecord(Medicalrecord medicalRecord) throws Exception
    {
        service.updateMedicalrecord(medicalRecord);
    }


    @DeleteMapping(value = "/medicalrecord")
    public void deleteMedicalRecordByName(
            @RequestParam("firstName")  String firstName,
            @RequestParam("lastName")   String lastName
    )
    {
        service.removeMedicalrecordByName(firstName, lastName);
    }

}