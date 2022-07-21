package com.openclassrooms.safetynet.controller;

import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import com.openclassrooms.safetynet.service.MedicalrecordService;
import com.openclassrooms.safetynet.entity.Medicalrecord;


@Slf4j
@RestController
public class MedicalrecordController
{
    // ======================================
    // =             Attributes             =
    // ======================================

    private final MedicalrecordService service;

    // ======================================
    // =            Constructors            =
    // ======================================

    public MedicalrecordController(MedicalrecordService _service)
    {
        this.service = _service;
    }

    // ======================================
    // =        Controller  Methods         =
    // ======================================

    @PostMapping(value = "/medicalrecord")
    public Medicalrecord createMedicalrecord(@RequestBody Medicalrecord medicalrecord)
    {
        log.info("MedicalrecordController.createMedicalrecord CALLED. MedicalRecord = " + medicalrecord);

        try
        {
            service.addMedicalrecord(medicalrecord);
        }
        catch (EntityAlreadyExistException exception)
        {
            log.error("MedicalrecordController.createMedicalRecord FAILED. MedicalRecord = " + medicalrecord);
            throw exception;
        }

        return medicalrecord;
    }


    @PutMapping(value = "/medicalrecord")
    public Medicalrecord updateMedicalRecord(@RequestBody Medicalrecord medicalrecord)
    {
        log.info("MedicalrecordController.updateMedicalRecord CALLED. medicalrecord = " + medicalrecord);

        try
        {
            service.updateMedicalrecord(medicalrecord);
        }
        catch (EntityDoesNotExistException exception)
        {
            log.error("MedicalrecordController.updateMedicalRecord FAILED. medicalrecord = " + medicalrecord);
            throw exception;
        }

        return medicalrecord;
    }


    @DeleteMapping(value = "/medicalrecord")
    public Medicalrecord deleteMedicalrecord(@RequestBody Medicalrecord medicalrecord)
    {
        log.info("MedicalrecordController.deleteMedicalrecord CALLED. medicalrecord = " + medicalrecord);

        Medicalrecord oldMedicalrecord;

        try
        {
            oldMedicalrecord = service.removeMedicalrecord(medicalrecord);
        }
        catch (EntityDoesNotExistException exception)
        {
            log.error("MedicalrecordController.deletePerson FAILED. medicalrecord = " + medicalrecord);
            throw exception;
        }

        return oldMedicalrecord;
    }
}