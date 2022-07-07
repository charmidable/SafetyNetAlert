package com.openclassrooms.safetynet.service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.safetynet.entity.Person;


@Service
public class URLService
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Autowired
    PersonService personService;

    @Autowired
    FirestationService firestationService;


    // ======================================
    // =       Public Service Methods       =
    // ======================================

    public Object firestation(int stationNumber)
    {
        List<Person> personList = getPeopleByStationNumber(stationNumber);

        long numberOfChild = personList.stream().filter(Person::isChild).count();

        long numberOfAdult = personList.size() - numberOfChild;

        record Result(int stationNumber, long numberOfChilds, long numberOfAdults, List<Person> people){}

        return new Result(stationNumber, numberOfChild, numberOfAdult, personList);
    }


    public Object childAlert(String address)
    {
        Map<Boolean, List<Person>> map = personService.getFamilyHouseWithChildByAdress(address);

        if(map.get(true).size() == 0) return "";

        record Result(String adress, List<Person> childs, List<Person> adults){}

        return new Result(address, map.get(true), map.get(false));
    }


    public Object phoneAlert(int firestationNumber)
    {
        record Result(int stationNumber, List<String> people){}

        return new Result(firestationNumber, getPeopleByStationNumber(firestationNumber).stream().map(Person::getPhone).distinct().toList());
    }


    public Object fire(String address)
    {
        record Result(int stationNumber, String address, List<Person> people){}

        return new Result(firestationService.getFirestationNumberByAdress(address), address, personService.getPersonsByAdress(address));
    }


    public Map<Integer, Map<String, List<Person>>> flood(Integer... fireStationNumber)
    {
        List<Integer> numeros = List.of(fireStationNumber);

        Map<Integer, Map<String, List<Person>>> floodResult = new HashMap<>();

        numeros.stream().forEach(i -> floodResult.put(  i, new HashMap<String, List<Person>>()));

        numeros.stream().forEach(i -> firestationService.getMap()
               .get(i)
               .forEach((String address) -> floodResult.get( i).put(address, personService.getPersonsByAdress(address))));

        return floodResult;
    }


    public Optional<Person> personInfo(String firstName, String lastName)
    {
        return personService.getPersonByName(firstName, lastName);
    }


    public Object communityEmail(String city)
    {
        record Result(String city, List<String> email){}

        return new Result(city, personService.getEmailsOfAllTheCity(city));
    }


    // ======================================
    // =        Private Tool Methods        =
    // ======================================

    private List<Person> getPeopleByStationNumber(int stationNumber)
    {
        return  personService.getPersons()
                .stream()
                .filter (person -> firestationService.getAdressesCoveredByTheFireStation(stationNumber).contains(person.getAddress()))
                .toList();
    }
}