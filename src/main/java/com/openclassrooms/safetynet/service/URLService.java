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

    public URLService(PersonService personService, FirestationService firestationService)
    {
        this.personService      = personService;
        this.firestationService = firestationService;
    }


    // ======================================
    // =       Public Service Methods       =
    // ======================================

    public FirestationDTO firestation(int stationNumber)
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


    public PhoneAlertDTO phoneAlert(int firestationNumber)
    {
        return new PhoneAlertDTO(firestationNumber, getPeopleByStationNumber(firestationNumber).stream().map(Person::getPhone).distinct().toList());
    }


    public FireDTO fire(String address)
    {
        return new FireDTO(firestationService.getFirestationNumberByAdress(address), address, personService.getPersonsByAddress(address));
    }


    public Map<Firestation, List<Person>> flood(Integer... fireStationNumbers)
    {
        var DTO = new HashMap<Firestation, List<Person>>();

        try
        {
            Arrays.stream(fireStationNumbers)
                  .distinct()
                  .map(i -> firestationService.getMap().get(i))
                  .flatMap(List::stream)
                  .forEach(f -> DTO.put(f,  personService.getPersonsByAddress(f.address())));
        }
        catch (NullPointerException exception)
        {
            for (int i : fireStationNumbers)
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


    public boolean isNumberStationExist(int stationNumber)
    {
        return firestationService.isNumberStationExist(stationNumber);
    }


    // ======================================
    // =        Private Tool Methods        =
    // ======================================

    List<Person> getPeopleByStationNumber(int stationNumber)
    {
        return  personService.getPersons()
                             .stream()
                             .filter (person -> firestationService.getAdressesCoveredByTheFireStation(stationNumber).contains(person.getAddress()))
                             .toList();
    }
}