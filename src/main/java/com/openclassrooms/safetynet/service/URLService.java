package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.entity.Person;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;


public class URLService
{

    // ======================================
    // =             Attributes             =
    // ======================================
    private final RepositoryService repo = new RepositoryService();


    // ======================================
    // =       Public Service Methods       =
    // ======================================

    // firestation ? stationNumber = <station_number>
    public List<Person> getPeopleByStationNumber(int stationNumber)
    {
        List<Person> personList = repo.getPeopleByStationNumber(stationNumber);

        long numberOfChild = personList.stream().filter(Person::isChild).count();

        long numberOfAdult = personList.size() - numberOfChild;

        return repo.getPeopleByStationNumber(stationNumber);
    }

    // childAlert ? address = <address>
    public Map<Boolean, List<Person>> getFamilyWithChildByAdress(String address)
    {
        return repo.getFamilyHouseWithChild(address);
    }

    // phoneAlert ? firestation = <firestation_number>
    public List<Person> getPhoneNumberByStationNumber(int stationNumber)
    {
        return repo.getPeopleByStationNumber(stationNumber);
    }

    // fire ? address = <address>
    private final List<Person> getPeopleWithTheirFirestationByAdress(String adress)
    {
        repo.getPersonsByAdress(adress);
        repo.getFirestationByAdress(adress);
        return null;
    }

    // stations ? stations = <a list of station_numbers>
    public Map<Integer, Map<String, Person>> getPeopleByStationGroupingByAdress(Integer... fireStationNumber)
    {
        List<Firestation> firestationList = repo.getFirestationsList()
                                                .stream()
                                                .filter(station -> asList(fireStationNumber).contains(station.station())).toList();
        System.out.println(firestationList);
        return null;
    }

//    public Person getPersonByName(String firstName, String lastName)
//    {
//        Person person =
//    }

}