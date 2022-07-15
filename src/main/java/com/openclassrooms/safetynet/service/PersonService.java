package com.openclassrooms.safetynet.service;

import static   java.util.stream.Collectors.*;

import          java.util.List;
import          java.util.Map;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import com.openclassrooms.safetynet.repository.PersonRepo;
import com.openclassrooms.safetynet.entity.Medicalrecord;
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


    public Map<Boolean, List<Person>> getChildrenListAndAdultsListByAddress(String address)
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


    public List<Person> getPersonsByAddress(String address)
    {
        return personRepo.getList()
                         .stream()
                         .filter(person -> person.getAddress().equalsIgnoreCase(address))
                         .toList();
    }


    public boolean isCityExist(String city)
    {
        return personRepo.getList()
                         .stream()
                         .map(Person::getCity)
                         .anyMatch(s -> s.equalsIgnoreCase(city));
    }


    public List<String> getEmailsOfAllTheCity(String city)
    {
        if(!isCityExist(city))
        {
            throw new EntityDoesNotExistException("City " + city + " does not exist.");
        }

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

    public Person addPerson(Person person)
    {
        if(personRepo.getMap().get(person.hashCode()) !=  null)
        {
            throw new EntityAlreadyExistException("Person with first name " + person.getFirstName() + " and last name " + person.getLastName() + " already exists");
        }

        person.setMedicalrecord(new Medicalrecord(person));

        return personRepo.getMap().putIfAbsent(person.hashCode(), person);
    }


    public Person getPersonByName(String firstName, String lastName)
    {
        Person person = personRepo.getMap().get(firstName.toLowerCase().hashCode() * 31 + lastName.toLowerCase().hashCode());

        if(person == null) throw new EntityDoesNotExistException("Person with first name " + firstName + " and last name " + lastName + " does not exist");

        return person;
    }


    public Person removePerson(Person person)
    {
        Person oldPerson = personRepo.getMap().remove(person.hashCode() );

        if (oldPerson == null)
        {
            throw new EntityDoesNotExistException("Person with first name " + person.getFirstName() + " and last name " + person.getLastName() + " does not exist");
        }

        return oldPerson;
    }


    public void updatePerson(Person newPerson)
    {
        Person oldPerson = personRepo.getMap().get(newPerson.hashCode());

        if(oldPerson == null)
        {
            throw new EntityDoesNotExistException("Person with first name " + newPerson.getFirstName() + " and last name " + newPerson.getLastName() + " does not exist");
        }

        if(oldPerson != null)
        {
            if (newPerson.getAddress() != null) oldPerson.setAddress (newPerson.getAddress());
            if (newPerson.getEmail()   != null) oldPerson.setEmail   (newPerson.getEmail());
            if (newPerson.getPhone()   != null) oldPerson.setPhone   (newPerson.getPhone());
            if (newPerson.getCity()    != null) oldPerson.setCity    (newPerson.getCity());
            if (newPerson.getZip()     != null) oldPerson.setZip     (newPerson.getZip());
        }
    }
}