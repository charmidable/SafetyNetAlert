package com.openclassrooms.safetynet.service;

import java.util.*;

import com.openclassrooms.safetynet.dto.*;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.entity.Person;
import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;

@Service
public class URLService
{
    // ======================================
    // =             Attributes             =
    // ======================================

    private final PersonService      personService;
    private final FirestationService firestationService;


    // ======================================
    // =            Constructors            =
    // ======================================

    public URLService(PersonService _personService, FirestationService _firestationService)
    {
        this.personService      = _personService;
        this.firestationService = _firestationService;
    }


    // ======================================
    // =       Public Service Methods       =
    // ======================================

    public FirestationDTO firestation(String stationNumber)
    {
        List<Person> personList = getPeopleByStationNumber(stationNumber);

        long numberOfChild = personList.stream().filter(Person::isChild).count();

        long numberOfAdult = personList.size() - numberOfChild;

        return new FirestationDTO(stationNumber, numberOfChild, numberOfAdult, personList);
    }


    public Object childAlert(String address)
    {
        Map<Boolean, List<Person>> map = personService.getChildrenListAndAdultsListByAddress(address);

        if(map.get(true).size() == 0 && map.get(false).size() == 0)
        {
            throw new EntityDoesNotExistException("The address " + address);
        }

        if(map.get(true).size() == 0)
        {
            return new ArrayList<Person>() {};
        }

        return new ChildAlertDTO(address, map.get(true), map.get(false));
    }


    public PhoneAlertDTO phoneAlert(String firestationNumber)
    {
        return new PhoneAlertDTO(firestationNumber, getPeopleByStationNumber(firestationNumber).stream().map(Person::getPhone).distinct().toList());
    }


    public FireDTO fire(String address)
    {
        return new FireDTO(firestationService.getFirestationNumberByAdress(address), address, personService.getPersonsByAddress(address));
    }


    public Map<Firestation, List<Person>> flood(String... fireStationNumbers)
    {
        var DTO = new TreeMap<Firestation, List<Person>>();

        try
        {
            Arrays.stream(fireStationNumbers)
                  .distinct()
                  .map(i -> firestationService.getMap().get(Objects.hash(i)))
                  .flatMap(List::stream)
                  .forEach(f -> DTO.put(f,  personService.getPersonsByAddress(f.address())));
        }
        catch (NullPointerException exception)
        {
            for (String i : fireStationNumbers)
            {
                if (!firestationService.isNumberStationExist(i))
                {
                    throw new EntityDoesNotExistException(" Firestation number " + i + " does not exist");
                }
            }
        }
        return DTO;
    }


    public Person personInfo(String firstName, String lastName)
    {
        return personService.getPersonByName(firstName, lastName);
    }


    public CommunityEmailDTO communityEmail(String city)
    {
        return new CommunityEmailDTO(city, personService.getEmailsOfAllTheCity(city));
    }


    public boolean isNumberStationExist(String stationNumber)
    {
        return firestationService.isNumberStationExist(stationNumber);
    }


    // ======================================
    // =        Private Tool Methods        =
    // ======================================

    List<Person> getPeopleByStationNumber(String stationNumber)
    {
        return  personService.getPersons()
                             .stream()
                             .filter (person -> firestationService.getAdressesCoveredByTheFireStation(stationNumber).contains(person.getAddress()))
                             .toList();
    }
}