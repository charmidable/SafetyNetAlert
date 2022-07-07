package com.openclassrooms.safetynet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;

import          com.openclassrooms.safetynet.service.URLService;
import static   com.openclassrooms.safetynet.controller.tool.Filter.*;

@RestController
public class URLController
{

    // ======================================
    // =            Attributes              =
    // ======================================

    @Autowired
    private URLService service;


    // ======================================
    // =        Controller Methods          =
    // ======================================

    @GetMapping("/firestation")
    public MappingJacksonValue firestation(@RequestParam final int stationNumber)
    {
        MappingJacksonValue mapping  = new MappingJacksonValue(service.firestation(stationNumber));
        mapping.setFilters(firestationFilterProvider);
        return mapping;
    }

    @GetMapping("/childAlert")
    public MappingJacksonValue childAlert(@RequestParam final String address)
    {
        MappingJacksonValue mapping  = new MappingJacksonValue(service.childAlert(address));
        mapping.setFilters(childAlertFilterProvider);
        return mapping;
    }

    @GetMapping("/phoneAlert")
    public MappingJacksonValue phoneAlert(@RequestParam final int stationNumber)
    {
        MappingJacksonValue mapping  = new MappingJacksonValue(service.phoneAlert(stationNumber));
        mapping.setFilters(phoneAlertFilterProvider);
        return mapping;
    }

    @GetMapping("/fire")
    public MappingJacksonValue fire(@RequestParam final String address)
    {
        MappingJacksonValue mapping  = new MappingJacksonValue(service.fire(address));
        mapping.setFilters(fireFilterProvider);
        return mapping;
    }

    @GetMapping("/flood")
    public MappingJacksonValue flood(@RequestParam final Integer... aListOfStationNumber)
    {
        MappingJacksonValue mapping  = new MappingJacksonValue(service.flood(aListOfStationNumber));
        mapping.setFilters(floodFilterProvider);
        return mapping;
    }

    @GetMapping("/personInfo")
    public MappingJacksonValue personInfo(@RequestParam final String firstName, String lastName)
    {
        MappingJacksonValue mapping  = new MappingJacksonValue(service.personInfo(firstName, lastName));
        mapping.setFilters(personInfoFilterProvider);
        return mapping;
    }

    @GetMapping("/communityEmail")
    public Object communityEmail(@RequestParam final String city)
    {
        return service.communityEmail(city);
    }
}