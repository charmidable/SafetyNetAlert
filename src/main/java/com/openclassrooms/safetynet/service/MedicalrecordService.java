package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Person;
import com.openclassrooms.safetynet.repository.PersonRepo;
import org.springframework.stereotype.Service;

@Service
public class MedicalrecordService
{

    // ======================================
    // =             Attributes             =
    // ======================================

    private final PersonRepo personRepo;


    // ======================================
    // =            Constructors            =
    // ======================================

    public MedicalrecordService(PersonRepo personRepo)
    {
        this.personRepo = personRepo;
    }


    // =========================================
    // = Methods for Medical Record Controller =
    // =========================================

    public void addMedicalrecord(Medicalrecord medicalrecord)
    {
        Person person = personRepo.getMap().get(medicalrecord.hashCode());

        if(person != null)
        {
            person.setMedicalrecord(medicalrecord);
        }
        else
        {
            person = new Person(medicalrecord.getFirstName(), medicalrecord.getLastName());
            person.setMedicalrecord(medicalrecord);
            personRepo.getMap().put(person.hashCode(), person);
        }
    }


    public void removeMedicalrecord(Medicalrecord medicalrecord)
    {
        Person person = personRepo.getMap().get(medicalrecord.hashCode());
        person.getMedicalrecord().clearMedicalrecord();
    }


    public void updateMedicalrecord(Medicalrecord newMedicalrecord)
    {
        Person person = personRepo.getMap().get(newMedicalrecord.hashCode());

        if(newMedicalrecord.getBirthdate()   != null) person.getMedicalrecord().setBirthdate   (newMedicalrecord.getBirthdate());
        if(newMedicalrecord.getMedications() != null) person.getMedicalrecord().setMedications (newMedicalrecord.getMedications());
        if(newMedicalrecord.getAllergies()   != null) person.getMedicalrecord().setAllergies   (newMedicalrecord.getAllergies());
    }
}
