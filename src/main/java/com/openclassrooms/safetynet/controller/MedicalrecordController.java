package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.service.MedicalrecordService;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.entity.Medicalrecord;


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

    public MedicalrecordController(MedicalrecordService service)
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
    public void deleteMedicalRecordByName(Medicalrecord medicalRecord)
    {
        service.removeMedicalrecord(medicalRecord);
    }

}