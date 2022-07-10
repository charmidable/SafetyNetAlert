package com.openclassrooms.safetynet.dao;

import          java.util.Map;
import          java.util.List;
import static   java.util.stream.Collectors.*;
import static   java.util.function.Function.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.extern.slf4j.Slf4j;

import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.entity.Person;

@Slf4j
public class EntitiesCollections
{
    // =======================================
    // =             Attributes              =
    // =======================================

    private   Map<Integer, Medicalrecord> medicalrecords;
    private   Map<Integer, Person>        persons;
    private   List<Firestation>           firestations;


    // =======================================
    // =         Getters & Setters           =
    // =          used by Jackson            =
    // =   Serialization & Deserialization   =
    // =======================================

    public List<Person> getPersons()
    {
        log.info("getPersons() called from  EntitiesCollections");
        return persons.values().stream().collect(toList());
    }


    public void setPersons(List<Person> personList)
    {
        log.info("setPersons() called from  EntitiesCollections with List<Person> personList parameter: " + personList);
        persons = personList.stream().collect(toMap(Person::hashCode, identity()));
    }


    public List<Medicalrecord> getMedicalrecords()
    {
        log.info("getMedicalrecords() called from  EntitiesCollections");
        return persons.values().stream().map(Person::getMedicalrecord).collect(toList());
    }


    public void setMedicalrecords(List<Medicalrecord> recordList)
    {
        log.info("setMedicalrecords() called from  EntitiesCollections with List<Medicalrecord> recordList parameter: " + recordList);
        medicalrecords = recordList.stream().collect(toMap(Medicalrecord::hashCode, identity()));
    }


    public List<Firestation> getFirestations()
    {
        log.info("getFirestations() called from  EntitiesCollections");
        return firestations;
    }


    public void setFirestations(List<Firestation> firestations)
    {
        log.info("setFirestations() called from  EntitiesCollections with List<Firestation> firestations parameter: " + firestations);
        this.firestations = firestations;
    }


    // =======================================
    // =      Getters for Repositories       =
    // =======================================


    @JsonIgnore
    public Map<Integer, Person> getPersonsMap()
    {
        log.info("getPersonsMap() called from  EntitiesCollections");
        return persons;
    }


    @JsonIgnore
    Map<Integer, Medicalrecord> getMedicalrecordsMap()
    {
        log.info("getMedicalrecordsMap() called from  EntitiesCollections");
        return medicalrecords;
    }


    @JsonIgnore
    public Map<Integer, List<Firestation>> getFirestationsMap()
    {
        log.info("getFirestationsMap() called from  EntitiesCollections");
        return firestations.stream().collect(
                                                groupingBy(
                                                             Firestation::station,
                                                             mapping(
                                                                      identity(),
                                                                      toList()
                                                                    )
                                                          )
                                            );
    }
}