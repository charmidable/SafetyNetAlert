package com.openclassrooms.safetynet.controller;

import        java.util.Arrays;
import        java.util.HashSet;
import static java.util.stream.Collectors.joining;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import        com.openclassrooms.safetynet.service.URLService;
import        com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import static com.openclassrooms.safetynet.controller.FilterMapping.mapping;


@Slf4j
@RestController
class URLController
{
    // ======================================
    // =            Attributes              =
    // ======================================

    @Value("#{'${fire_person_filter}'.split(';')}")        private HashSet<String> fireFields;
    @Value("#{'${flood_person_filter}'.split(';')}")       private HashSet<String> floodFields;
    @Value("#{'${childAlert_person_filter}'.split(';')}")  private HashSet<String> childAlertFields;
    @Value("#{'${personInfo_person_filter}'.split(';')}")  private HashSet<String> personInfoFields;
    @Value("#{'${firestation_person_filter}'.split(';')}") private HashSet<String> firestationFields;

    private final URLService service;

    // ======================================
    // =           Constructors             =
    // ======================================

    private URLController(URLService service)
    {
        this.service = service;
    }

    // ======================================
    // =        Controller Methods          =
    // ======================================

    @GetMapping("/firestation")
    private MappingJacksonValue firestation(@RequestParam final int stationNumber)
    {
        log.info("URLController.phoneAlert called with station number = " + stationNumber);
        try
        {
            return mapping(service.firestation(stationNumber), firestationFields);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("URLController.phoneAlert failed with station number = " + stationNumber);

            throw exception;
        }
    }


    @GetMapping("/childAlert")
    private MappingJacksonValue childAlert(@RequestParam final String address)
    {
        log.info("URLController.phoneAlert called with address = " + address);

        try
        {
            return mapping(service.childAlert(address), childAlertFields);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("URLController.phoneAlert failed with address = " + address);

            throw exception;
        }
    }


    @GetMapping("/fire")
    private MappingJacksonValue fire(@RequestParam final String address)
    {
        log.info("URLController.phoneAlert called with address = " + address);
        try
        {
            return mapping(service.fire(address), fireFields);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("URLController.phoneAlert failed with address = " + address);
            throw exception;
        }
    }


    @GetMapping("/flood")
    private MappingJacksonValue flood(@RequestParam final Integer... aListOfStationNumber)
    {
        log.info("URLController.flood called with station numbers = " + Arrays.stream(aListOfStationNumber).map(i -> i.toString()).collect(joining(", ")));
        try
        {
            return mapping(service.flood(aListOfStationNumber), floodFields);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("URLController.flood failed with station numbers = " + Arrays.stream(aListOfStationNumber).map(i -> i.toString()).collect(joining(", ")));
            throw exception;
        }
    }


    @GetMapping("/personInfo")
    private MappingJacksonValue personInfo(@RequestParam final String firstName, final String lastName)
    {
        log.info("URLController.personInfo called with firstName = " + firstName + " and lastName" + lastName);
        try
        {
            return mapping(service.personInfo(firstName, lastName), personInfoFields);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("URLController.personInfo failed with firstName = " + firstName + " and lastName" + lastName);

            throw exception;
        }
    }


    @GetMapping("/phoneAlert")
    private Object phoneAlert(@RequestParam final int stationNumber)
    {
        log.info("URLController.phoneAlert called with stationNumber = " + stationNumber);
        try
        {
            return service.phoneAlert(stationNumber);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("URLController.phoneAlert failed with stationNumber = " + stationNumber);
            throw exception;
        }
    }


    @GetMapping("/communityEmail")
    private Object communityEmail(@RequestParam final String city)
    {
        log.info("URLController.communityEmail called with city = " + city);
        try
        {
            return service.communityEmail(city);
        }
        catch(EntityDoesNotExistException exception)
        {
            log.error("URLController.communityEmail failed with city = " + city);
            throw exception;
        }
    }
}