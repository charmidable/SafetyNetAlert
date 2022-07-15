package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import com.openclassrooms.safetynet.repository.PersonRepo;
import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Person;


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

    // ======================================
    // =           Service Methods          =
    // ======================================

    public Medicalrecord addMedicalrecord(Medicalrecord medicalrecord)
    {
        Person person = personRepo.getMap().get(medicalrecord.hashCode());

        if(personRepo.getMap().get(medicalrecord.hashCode()) ==  null)
        {
            throw new EntityDoesNotExistException("Person with first name " + medicalrecord.getFirstName() + " and last name " + medicalrecord.getLastName() + " does not exist");
        }

        if(personRepo.getMap().get(medicalrecord.hashCode()).getMedicalrecord().getAge() != null)
        {
            throw new EntityAlreadyExistException("Medical record with first name " + medicalrecord.getFirstName() + " and last name " + medicalrecord.getLastName() + " already exists");
        }

        person.setMedicalrecord(medicalrecord);

        return personRepo.getMap().get(medicalrecord.hashCode()).getMedicalrecord();
    }


    public Medicalrecord updateMedicalrecord(Medicalrecord newMedicalrecord)
    {
        Person person = personRepo.getMap().get(newMedicalrecord.hashCode());

        if(person == null)
        {
            throw new EntityDoesNotExistException("Person with first name " + newMedicalrecord.getFirstName() + " and last name " + newMedicalrecord.getLastName() + " does not exist");
        }

        if(newMedicalrecord.getBirthdate()   != null) person.getMedicalrecord().setBirthdate   (newMedicalrecord.getBirthdate());
        if(newMedicalrecord.getAllergies()   != null) person.getMedicalrecord().setAllergies   (newMedicalrecord.getAllergies());
        if(newMedicalrecord.getMedications() != null) person.getMedicalrecord().setMedications (newMedicalrecord.getMedications());

        return person.getMedicalrecord();
    }


    public Medicalrecord removeMedicalrecord(Medicalrecord medicalrecord)
    {
        Person person = personRepo.getMap().get(medicalrecord.hashCode());

        if(person == null)
        {
            throw new EntityDoesNotExistException("Person with first name " + medicalrecord.getFirstName() + " and last name " + medicalrecord.getLastName() + " does not exist");
        }

        person.getMedicalrecord().clearMedicalrecord();

        return person.getMedicalrecord();
    }
}