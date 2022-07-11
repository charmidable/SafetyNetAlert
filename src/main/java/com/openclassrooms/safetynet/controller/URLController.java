package com.openclassrooms.safetynet.controller;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import        com.openclassrooms.safetynet.service.URLService;
import static com.openclassrooms.safetynet.controller.tool.FilterMapping.*;

@RestController
class URLController
{
    // ======================================
    // =            Attributes              =
    // ======================================

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
        return firestationMapping(service.firestation(stationNumber));
    }

    @GetMapping("/childAlert")
    private MappingJacksonValue childAlert(@RequestParam final String address)
    {
        return childAlertMapping(service.childAlert(address));
    }

    @GetMapping("/fire")
    private MappingJacksonValue fire(@RequestParam final String address)
    {
        return fireMapping(service.fire(address));
    }

    @GetMapping("/flood")
    private MappingJacksonValue flood(@RequestParam final Integer... aListOfStationNumber)
    {
        return floodMapping(service.flood(aListOfStationNumber));
    }

    @GetMapping("/personInfo")
    private MappingJacksonValue personInfo(@RequestParam final String firstName, String lastName)
    {
        return personInfoMapping(service.personInfo(firstName, lastName));
    }

    @GetMapping("/phoneAlert")
    private Object phoneAlert(@RequestParam final int stationNumber)
    {
        return service.phoneAlert(stationNumber);
    }

    @GetMapping("/communityEmail")
    private Object communityEmail(@RequestParam final String city)
    {
        return service.communityEmail(city);
    }
}