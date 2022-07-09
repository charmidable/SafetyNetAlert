package com.openclassrooms.safetynet.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.entity.Firestation;


@RestController
public class FirestationController
{

    // ======================================
    // =             Attributes             =
    // ======================================

    private final FirestationService service;


    // ======================================
    // =            Constructors            =
    // ======================================

    public FirestationController(FirestationService service)
    {
        this.service = service;
    }


    // ======================================
    // =   Controller Firestation Methods   =
    // ======================================

    @PostMapping(value = "/firestation")
    public void createFirestation(Firestation firestation)
    {
        service.addFirestation(firestation);
    }


    @PutMapping(value = "/firestation")
    public void updateFirestation(Firestation firestation)
    {
        service.updateFirestation(firestation);
    }


    @DeleteMapping(value = "/firestation")
    public void deleteFirestation(Firestation firestation)
    {
        service.removeFirestation(firestation);
    }

}