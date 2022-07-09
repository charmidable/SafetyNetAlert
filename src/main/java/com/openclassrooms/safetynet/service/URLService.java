package com.openclassrooms.safetynet.service;

import java.util.*;

import com.openclassrooms.safetynet.entity.Firestation;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.entity.Person;


@Service
public class URLService
{
    // ======================================
    // =             Attributes             =
    // ======================================

    private final PersonService personService;
    private final FirestationService firestationService;


    // ======================================
    // =            Constructors            =
    // ======================================

    public URLService(PersonService personService, FirestationService firestationService)
    {
        this.personService = personService;
        this.firestationService = firestationService;
    }


    // ======================================
    // =       Public Service Methods       =
    // ======================================

    public Object firestation(int stationNumber)
    {
        List<Person> personList = getPeopleByStationNumber(stationNumber);

        long numberOfChild = personList.stream().filter(Person::isChild).count();

        long numberOfAdult = personList.size() - numberOfChild;

        record DTO(int stationNumber, long numberOfChilds, long numberOfAdults, List<Person> people){}

        return new DTO(stationNumber, numberOfChild, numberOfAdult, personList);
    }


    public Object childAlert(String address)
    {
        Map<Boolean, List<Person>> map = personService.getFamilyHouseWithChildByAdress(address);

        if(map.get(true).size() == 0) return "";

        record DTO(String adress, List<Person> childs, List<Person> adults){}

        return new DTO(address, map.get(true), map.get(false));
    }


    public Object phoneAlert(int firestationNumber)
    {
        record DTO(int stationNumber, List<String> people){}

        return new DTO(firestationNumber, getPeopleByStationNumber(firestationNumber).stream().map(Person::getPhone).distinct().toList());
    }


    public Object fire(String address)
    {
        record DTO(int stationNumber, String address, List<Person> people){}

        return new DTO(firestationService.getFirestationNumberByAdress(address), address, personService.getPersonsByAdress(address));
    }


    public Map<Firestation, List<Person>> flood(Integer... fireStationNumbers)
    {
        Map<Firestation, List<Person>> DTO = new HashMap<>();

        Arrays.stream(fireStationNumbers).map(i -> firestationService.getMap().get(i))
                                         .flatMap(List::stream)
                                         .distinct()
                                         .sorted()
                                         .forEach(f -> DTO.put(f,  personService.getPersonsByAdress(f.address())));
        return DTO;
    }


    public Person personInfo(String firstName, String lastName)
    {
        return personService.getPersonByName(firstName, lastName);
    }


    public Object communityEmail(String city)
    {
        record DTO(String city, List<String> email){}

        return new DTO(city, personService.getEmailsOfAllTheCity(city));
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