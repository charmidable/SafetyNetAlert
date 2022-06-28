package com.openclassrooms.safetynet.repository;

import static   java.util.function.Function.identity;
import static   java.util.stream.Collectors.*;
import          java.util.List;
import          java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Person;

public final class Repository
{

    // ================================
    // =         Attributes           =
    // ================================

    private transient Map<Integer, Medicalrecord> medicalrecords;
    private transient Map<Integer, List<String>>  firestations;
    private transient Map<Integer, Person>        persons;


    // ===============================
    // =      Constructor &          =
    // =  Singleton Pattern Methods  =
    // ===============================

    @JsonCreator
    public final static Repository getInstance()
    {
        return INSTANCE;
    }

    private Repository()
    {
        initialize();
    }

    private final Object readResolve()
    {
        return INSTANCE;
    }

    private static final transient Repository INSTANCE = new Repository();


    // ===================================
    // =        Getters & Setters        =
    // =         used by Jackson         =
    // = Deserialization & Serialization =
    // ===================================

    public List<Person> getPersons()
    {
        return persons.values().stream().collect(toList());
    }


    private void setPersons(List<Person> personList)
    {
        persons = personList.stream().collect(toMap(Person::hashCode, identity()));
    }


    public List<Medicalrecord> getMedicalrecords()
    {
        return medicalrecords.values().stream().collect(toList());
    }


    private void setMedicalrecords(List<Medicalrecord> recordList)
    {
        medicalrecords = recordList.stream().collect(toMap(Medicalrecord::hashCode, identity()));
    }


    public List<Firestation> getFirestations()
    {
        return firestations.entrySet()
                           .stream()
                           .flatMap(stationNumber -> stationNumber.getValue()
                                                                  .stream()
                                                                  .map(value -> new Firestation(
                                                                                                  value,
                                                                                                  stationNumber.getKey()
                                                                                                )
                                                                      )
                                  ).collect(toList());
    }


    private void setFirestations(List<Firestation> stations)
    {
        firestations =  stations
                        .stream()
                        .collect(
                                    groupingBy  (
                                                    Firestation::station,
                                                    mapping(
                                                                Firestation::address,
                                                                toList()
                                                            )
                                                )
                                );
    }


    // ==================================
    // =       Getters for Maps         =
    // ==================================

    @JsonIgnore
    public Map<Integer, Medicalrecord>  getMedicalrecordsMap()
    {
        return medicalrecords;
    }

    @JsonIgnore
    public Map<Integer, List<String>>   getFirestationsMap()
    {
        return firestations;
    }

    @JsonIgnore
    public Map<Integer, Person>         getPersonsMap()
    {
        return persons;
    }


    // ======================================
    // =           Object Methods           =
    // ======================================

    @Override
    public String toString()
    {
        return  "Repository{"       +
                "medicalrecords = " + medicalrecords    +
                ", firestations = " + firestations      +
                ", persons = "      + persons           +
                '}';
    }


    public void addMedicalRecord(Medicalrecord record)
    {
        medicalrecords.put(record.hashCode(), record);
    }


    // ===============================
    // =   initialization methods    =
    // ===============================

    public void initialize()
    {
        Person.setRepo(this);
    }
}