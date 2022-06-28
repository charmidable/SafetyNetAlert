package com.openclassrooms.safetynet.repository;

import static   java.util.function.Function.identity;
import static   java.util.stream.Collectors.*;

import          java.time.LocalDate;
import          java.util.List;
import          java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Person;
import org.springframework.stereotype.Component;

//@Component
public final class Repo
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
    public final static Repo getInstance()
    {
        return INSTANCE;
    }

    private Repo()
    {
        initialize();
    }

    private final Object readResolve()
    {
        return INSTANCE;
    }

    private static final transient Repo INSTANCE = new Repo();


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


    public void addMedicalRecord(Medicalrecord record)
    {
        medicalrecords.put(record.hashCode(), record);
    }

    public Medicalrecord removeMedicalRecord(int key)
    {
        return medicalrecords.remove(key);
    }

    public void addPerson(String firstName, String lastName, String address, LocalDate date)
    {
        Person person = new Person(firstName, lastName, address, date);
    }

    public void removePerson(Person person)
    {
        medicalrecords.remove(person.hashCode());
        persons.remove(person.hashCode());
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


    // ===============================
    // =   initialization methods    =
    // ===============================

    public void initialize()
    {
        Person.setRepo(this);
    }
}