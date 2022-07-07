package com.openclassrooms.safetynet.service;

import static   java.util.stream.Collectors.*;
import          java.util.List;
import          java.util.Map;
import          java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.repository.PersonRepo;
import com.openclassrooms.safetynet.entity.Person;


@Service
public class PersonService
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Autowired
    PersonRepo personRepo;


    // ======================================
    // =          Service  Methods          =
    // ======================================

    public List<Person> getPersons()
    {
        System.out.println("PersonService.getPersons() CALLED ");
        return personRepo.getList();
    }


    public Optional<Person> getPersonById(int key)
    {
        return Optional.ofNullable(personRepo.getMap().get(key));
    }


    public Optional<Person> getPersonByName(String firstName, String lastName)
    {
        return Optional.ofNullable(personRepo.getMap().get(firstName.hashCode() * 31 + lastName.hashCode()));
    }


    public void removePerson(Person person)
    {
        personRepo.getMap().remove(person.hashCode(), person);
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





/*
    public List<Person> getList()
    {
        return personRepo.getList();
    }
    public Map<String, List<Person>> getAllPeopleGroupingByAddress()
    {
        Map<String, List<Person>> result = new HashMap<>();

        personRepo.getList()
                  .stream()
                  .map(Person::getAddress)
                  .forEach(adress -> result.put(adress, getPersonsByAdress(adress)));

        return result;
    }

    public boolean addPerson(Person person)
    {
        throw new IllegalStateException();
    }

    public boolean editPerson(Person person)
    {
        throw new IllegalStateException();
    }

    public boolean setBirthday(Person person, LocalDate localDate)
    {
        throw new IllegalStateException();
    }

    public boolean setAddress(String newAddress)
    {
        throw new IllegalStateException();
    }

    public boolean addMedication(Person person, String medicationToAdd)
    {
        throw new IllegalStateException();
    }

    public boolean removeMedication(Person person, String medicationToRemove)
    {
        throw new IllegalStateException();
    }

    public boolean addAllergie(Person person, String allergieToAdd)
    {
        throw new IllegalStateException();
    }

    public boolean removeAllergie(Person person, String allergieToRemove)
    {
        throw new IllegalStateException();
    }

    public boolean removeAllAllergies(Person person)
    {
        throw new IllegalStateException();
    }

    public boolean removeAllMedications(Person person)
    {
        throw new IllegalStateException();
    }

    public boolean removeAllMedicationsAndAllergies(Person person)
    {
        throw new IllegalStateException();
    }

 */
