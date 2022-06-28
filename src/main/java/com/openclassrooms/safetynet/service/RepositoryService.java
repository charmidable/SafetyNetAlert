package com.openclassrooms.safetynet.service;

import static   java.util.stream.Collectors.*;

import java.util.*;
import java.util.stream.Stream;

import com.openclassrooms.safetynet.repository.Repo;
import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.entity.Person;


public class RepositoryService
{

    // ======================================
    // =         Constructor                =
    // ======================================
    public RepositoryService() {}


    // ======================================
    // =             Attributes             =
    // ======================================
    private final Repo repo = Repo.getInstance();


    // ======================================
    // =          Service  Methods          =
    // ======================================

    public Person getPerson(int key)
    {
        return repo.getPersonsMap().get(key);
    }


    public void removePerson(Person person)
    {
        repo.getMedicalrecordsMap() .remove(person.hashCode(), repo.getMedicalrecordsMap().get(person.hashCode()));
        repo.getPersonsMap()        .remove(person.hashCode(), person);
    }


    public int getFirestationByAdress(String address)
    {
        return repo .getFirestations().stream()
                    .filter(station -> station.address().equalsIgnoreCase(address))
                    .map(Firestation::station)
                    .findFirst()
                    .get();
    }

    public List<Firestation> getFirestationsList()
    {
        return repo.getFirestations();
    }


    public List<String> getAdressesCoveredByTheFireStation(int fireStationNumber)
    {
        return  repo.getFirestationsMap().get(fireStationNumber);
    }


    public List<String> getAdressesCoveredByTheFireStation(Integer... fireStationNumber)
    {
        System.out.println("getAdressesCoveredByTheFireStation(Integer... fireStationNumber)");
        List<List<String>> adresseListList = new ArrayList<>();

        Arrays.stream(fireStationNumber).forEach(i -> adresseListList.add(getAdressesCoveredByTheFireStation(i)));

        return  adresseListList.stream().flatMap(Collection::stream).toList();
    }



    public List<Integer> getAllFirestationNumber()
    {
        return  repo.getFirestations()
                .stream()
                .flatMap(station -> Stream.of(station.station()))
                .distinct()
                .toList();
    }


    public List<Person> getPeopleByStationNumber(int stationNumber)
    {
        return  repo.getPersons()
                    .stream()
                    .filter (person -> getAdressesCoveredByTheFireStation(stationNumber).contains(person.getAddress()))
                    .toList();
    }


    public Map<Boolean, List<Person>> getFamilyHouseWithChild(String address)
    {
        return  repo.getPersons()
                    .stream()
                    .filter(person -> person.getAddress().equalsIgnoreCase(address))
                    .collect(
                               groupingBy(
                                             Person::isChild,
                                             toList()
                                         )
                            );
    }


    public List<Person> getPersonsByAdress(String adress)
    {
        return  repo.getPersons()
                    .stream()
                    .filter(person -> person.getAddress().equalsIgnoreCase(adress))
                    .toList();
    }

    public Person getPersonByName(String firstName, String lastName)
    {
        return repo.getPersonsMap().get(firstName.hashCode()  * 31 + lastName.hashCode());
    }
}