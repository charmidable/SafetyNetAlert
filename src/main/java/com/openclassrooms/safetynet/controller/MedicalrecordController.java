package com.openclassrooms.safetynet.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.entity.Medicalrecord;


@RestController
public class MedicalrecordController
{

    // ======================================
    // =             Attributes             =
    // ======================================

    private final PersonService service;


    // ======================================
    // =            Constructors            =
    // ======================================

    public MedicalrecordController(PersonService service)
    {
        this.service = service;
    }


    // ======================================
    // =        Controller  Methods         =
    // ======================================


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