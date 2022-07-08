package com.openclassrooms.safetynet.service;

import static   java.util.stream.Collectors.*;
import          java.util.List;
import          java.util.Map;
import          java.util.Optional;

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
    // =          Service  Methods          =
    // ======================================

    // ==============================================
    // =   Service Methods for Person Controller    =
    // ==============================================

    public void addPerson(Person person)
    {
        personRepo.getMap().put(person.hashCode(), person);
    }


    public Optional<Person> getPersonByName(String firstName, String lastName)
    {
        return Optional.ofNullable(personRepo.getMap().get(firstName.hashCode() * 31 + lastName.hashCode()));
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


    // =================================================================
    // =     Service Methods for Medical Record Person Controller      =
    // =================================================================

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


    public Medicalrecord getMedicalrecordByName(String firstName, String lastName)
    {
        Optional<Person> person = Optional.ofNullable(personRepo.getMap().get(firstName.hashCode() * 31 + lastName.hashCode()));

        if(person.isPresent()) return person.get().getMedicalrecord();

        return null;
    }


    public void removeMedicalrecordByName(String firstName, String lastName)
    {
        Person person = personRepo.getMap().get(firstName.hashCode() * 31 + lastName.hashCode());
        person.clearAllAllergiesAndMedications();
        person.setBirthdate(null);
    }


    public void updateMedicalrecord(Medicalrecord newMedicalrecord)
    {
        Person person = personRepo.getMap().get(newMedicalrecord.getFirstName().hashCode() * 31 + newMedicalrecord.getLastName().hashCode());

        if(newMedicalrecord.getBirthdate()   != null) person.setBirthdate   (newMedicalrecord.getBirthdate());
        if(newMedicalrecord.getMedications() != null) person.setMedications (newMedicalrecord.getMedications());
        if(newMedicalrecord.getAllergies()   != null) person.setAllergies   (newMedicalrecord.getAllergies());
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
}