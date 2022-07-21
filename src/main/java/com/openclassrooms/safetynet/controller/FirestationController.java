package com.openclassrooms.safetynet.controller;

import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.service.FirestationService;
import com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;


@Slf4j
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

    public FirestationController(FirestationService _service)
    {
        this.service = _service;
    }

    // ======================================
    // =        Controller  Methods         =
    // ======================================

    @PostMapping(value = "/firestation")
    public Firestation createFirestation(@RequestBody Firestation firestation)
    {
        log.info("FirestationController.createFirestation CALLED. firestation = " + firestation);

        try
        {
            service.addFirestation(firestation);
        }
        catch (EntityAlreadyExistException exception)
        {
            log.error("FirestationController.createFirestation FAILED. firestation = " + firestation + "  " + exception.getMessage());
            throw exception;
        }
        return firestation;
    }


    @PutMapping(value = "/firestation")
    public Firestation updateFirestation(@RequestBody Firestation firestation)
    {
        log.info("FirestationController.updateFirestation CALLED. firestation = " + firestation);

        try
        {
            service.updateFirestation(firestation);
        }
        catch (EntityDoesNotExistException exception)
        {
            log.error("FirestationController.updateFirestation FAILED. firestation = " + firestation + "  " + exception.getMessage());
            throw exception;
        }
        return firestation;
    }


    @DeleteMapping(value = "/firestation")
    public Firestation deleteFirestation(@RequestBody Firestation firestation)
    {
        log.info("FirestationController.deleteFirestation CALLED. firestation = " + firestation);

        try
        {
            service.removeFirestation(firestation);
        }
        catch (EntityDoesNotExistException exception)
        {
            log.error("FirestationController.deleteFirestation FAILED. firestation = " + firestation + "  " + exception.getMessage());
            throw exception;
        }
        return firestation;
    }
}