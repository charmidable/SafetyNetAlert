package com.openclassrooms.safetynet.service;

import java.util.*;

import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.entity.Person;
import com.openclassrooms.safetynet.entity.Firestation;


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
        Map<Boolean, List<Person>> map = personService.getChildrenListAndAdultsListByAddress(address);

        if(map.get(true).size() == 0 && map.get(false).size() == 0)
        {
            throw new EntityDoesNotExistException("The address " + address);
        }

        if(map.get(true).size() == 0)
        {
            return new ArrayList<Person>() {};
        }

        record DTO(String address, List<Person> children, List<Person> adults){}

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

        return new DTO(firestationService.getFirestationNumberByAdress(address), address, personService.getPersonsByAddress(address));
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


//    public Optional<Person> personInfo(String firstName, String lastName) throws IllegalArgumentException
//    {
//        return personService.getPersonByName(firstName, lastName);
//    }

    public Person personInfo(String firstName, String lastName)
    {
        return personService.getPersonByName(firstName, lastName);
    }


    public Object communityEmail(String city)
    {
        record DTO(String city, List<String> email){}

        return new DTO(city, personService.getEmailsOfAllTheCity(city));
    }


    public boolean isNumberStationExist(int stationNumber)
    {
        return firestationService.isNumberStationExist(stationNumber);
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