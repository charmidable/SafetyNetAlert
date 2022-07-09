package com.openclassrooms.safetynet.service;

import static   java.util.stream.Collectors.*;
import          java.util.List;
import          java.util.Map;

import com.openclassrooms.safetynet.entity.Medicalrecord;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.repository.PersonRepo;
import com.openclassrooms.safetynet.entity.Person;


@Service
public class PersonService
{

    // ======================================
    // =             Attributes             =
    // ======================================

    private final PersonRepo personRepo;


    // ======================================
    // =            Constructors            =
    // ======================================

    public PersonService(PersonRepo personRepo)
    {
        this.personRepo = personRepo;
    }


    // ======================================
    // =      Methods for URL Service       =
    // ======================================

    public List<Person> getPersons()
    {
        return personRepo.getList();
    }


    public Map<Boolean, List<Person>> getFamilyHouseWithChildByAdress(String address)
    {
        return personRepo.getList()
                .stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .collect(
                        partitioningBy(
                                Person::isChild,
                                toList()
                        )
                );
    }


    public List<Person> getPersonsByAdress(String adress)
    {
        return personRepo.getList()
                .stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(adress))
                .distinct()
                .toList();
    }


    public List<String> getEmailsOfAllTheCity(String city)
    {
        return personRepo.getList()
                .stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .toList();
    }


    // ======================================
    // =   Methods for Person Controller    =
    // ======================================

    public void addPerson(Person person)
    {
        personRepo.getMap().put(person.hashCode(), person);
    }


    public Person getPersonByName(String firstName, String lastName)
    {
        return personRepo.getMap().get(firstName.hashCode() * 31 + lastName.hashCode());
    }


    public void removePerson(String firstName, String lastName)
    {
        personRepo.getMap().remove(firstName.hashCode() * 31 + lastName.hashCode());
    }


    public void updatePerson(Person newPerson)
    {
        Person oldPerson = personRepo.getMap().get(newPerson.hashCode());

        if(oldPerson != null)
        {
            if (newPerson.getCity()    != null) oldPerson.setCity    (newPerson.getCity());
            if (newPerson.getZip()     != null) oldPerson.setZip     (newPerson.getZip());
            if (newPerson.getAddress() != null) oldPerson.setAddress (newPerson.getAddress());
            if (newPerson.getEmail()   != null) oldPerson.setEmail   (newPerson.getEmail());
            if (newPerson.getPhone()   != null) oldPerson.setPhone   (newPerson.getPhone());
        }
    }


    // =========================================
    // = Methods for Medical Record Controller =
    // =========================================

    public void addMedicalrecord(Medicalrecord medicalrecord)
    {
        Person person = personRepo.getMap().get(medicalrecord.getFirstName().hashCode() * 31 + medicalrecord.getLastName().hashCode());

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


    public void removeMedicalrecordByName(String firstName, String lastName)
    {
        Person person = personRepo.getMap().get(firstName.hashCode() * 31 + lastName.hashCode());
        person.getMedicalrecord().clearMedicalrecord();
    }


    public void updateMedicalrecord(Medicalrecord newMedicalrecord)
    {
        Person person = personRepo.getMap().get(newMedicalrecord.getFirstName().hashCode() * 31 + newMedicalrecord.getLastName().hashCode());

        if(newMedicalrecord.getBirthdate()   != null) person.getMedicalrecord().setBirthdate   (newMedicalrecord.getBirthdate());
        if(newMedicalrecord.getMedications() != null) person.getMedicalrecord().setMedications (newMedicalrecord.getMedications());
        if(newMedicalrecord.getAllergies()   != null) person.getMedicalrecord().setAllergies   (newMedicalrecord.getAllergies());
    }
}