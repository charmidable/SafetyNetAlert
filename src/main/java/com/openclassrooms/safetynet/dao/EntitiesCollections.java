package com.openclassrooms.safetynet.dao;

import          java.util.Collection;
import          java.util.Map;
import          java.util.List;
import static   java.util.stream.Collectors.*;
import static   java.util.function.Function.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.entity.Person;


public class EntitiesCollections
{
    // =======================================
    // =             Attributes              =
    // =======================================

    private Map<Integer, List<Firestation>> firestations;
    private Map<Integer, Medicalrecord>     medicalrecords;
    private Map<Integer, Person>            persons;


    // =======================================
    // =         Getters & Setters           =
    // =          used by Jackson            =
    // =   Serialization & Deserialization   =
    // =======================================

    public List<Person> getPersons()
    {
        return persons.values().stream().collect(toList());
    }


    public void setPersons(List<Person> personList)
    {
        persons = personList.stream().collect(toMap(Person::hashCode, identity()));
                getPersons().forEach(person -> person.setMedicalrecord(getMedicalrecordsMap().get(person.hashCode())));
    }


    public List<Medicalrecord> getMedicalrecords()
    {
        return persons.values().stream().map(Person::getMedicalrecord).collect(toList());
    }


    public void setMedicalrecords(List<Medicalrecord> recordList)
    {
        medicalrecords = recordList.stream().collect(toMap(Medicalrecord::hashCode, identity()));

    }


    public List<Firestation> getFirestations()
    {
        return firestations.values().stream().flatMap(Collection::stream).toList();
    }


    public void setFirestations(List<Firestation> _firestations)
    {
        this.firestations = _firestations.stream().collect(
                                                            groupingBy(
                                                                          Firestation::station,
                                                                          mapping(
                                                                                    identity(),
                                                                                    toList()
                                                                                 )
                                                                      )
                                                         );

    }


    // =======================================
    // =      Getters for Repositories       =
    // =======================================

    @JsonIgnore
    public Map<Integer, Person> getPersonsMap()
    {
        return persons;
    }

    @JsonIgnore
    Map<Integer, Medicalrecord> getMedicalrecordsMap()
    {
        return medicalrecords;
    }

    @JsonIgnore
    public Map<Integer, List<Firestation>> getFirestationsMap()
    {
        return firestations;
    }
}