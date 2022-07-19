package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import com.openclassrooms.safetynet.dto.*;
import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
class URLServiceTest
{
    @Autowired
    URLService urlService;

    @Autowired
    PersonService personService;

    @Autowired
    FirestationService firestationService;

    @Test
    void testFirestation()
    {
        Integer i = 3;
        FirestationDTO dto = urlService.firestation(i);
        assertEquals(dto.stationNumber(), i);
        assertEquals(dto.numberOfAdults(), 2);
        assertEquals(dto.numberOfChilds(), 1);
        assertEquals(dto.people().size(), 3);
        assertTrue(dto.people().contains(personService.getPersonByName("f31", "l3")));
        assertTrue(dto.people().contains(personService.getPersonByName("f32", "l3")));
        assertTrue(dto.people().contains(personService.getPersonByName("f33", "l3")));

        assertThrows(EntityDoesNotExistException.class, () -> urlService.firestation(9));
    }

    @Test
    void testChildAlert()
    {
        String address = "a33";
        ChildAlertDTO dto = (ChildAlertDTO)urlService.childAlert(address);
        assertEquals(dto.address(), address);
        assertEquals(dto.children().size(), 1);
        assertTrue(dto.children().contains(personService.getPersonByName("f33", "l3")));
        assertEquals(dto.adults().size(), 2);
        assertTrue(dto.adults().contains(personService.getPersonByName("f32", "l3")));
        assertTrue(dto.adults().contains(personService.getPersonByName("f31", "l3")));

        assertThrows(EntityDoesNotExistException.class, () -> urlService.childAlert("xxx"));
    }

    @Test
    void testPhoneAlert()
    {
        int stationNumber = 3;
        PhoneAlertDTO dto = urlService.phoneAlert(stationNumber);
        assertEquals(dto.stationNumber(), stationNumber);
        assertEquals(dto.phoneList().size(), 1);
        assertTrue(dto.phoneList().contains("p3"));

        assertThrows(EntityDoesNotExistException.class, () -> urlService.phoneAlert(9));
    }

    @Test
    void testFire()
    {
        String address = "a33";
        FireDTO dto = urlService.fire(address);
        assertEquals(dto.address(), address);
        assertEquals(dto.stationNumber(), 3);
        assertEquals(dto.people().size(), 3);
        assertTrue(dto.people().contains(personService.getPersonByName("f33", "l3")));
        assertTrue(dto.people().contains(personService.getPersonByName("f32", "l3")));
        assertTrue(dto.people().contains(personService.getPersonByName("f31", "l3")));

        assertThrows(EntityDoesNotExistException.class, () -> urlService.fire("xxx"));
    }

    @Test
    void testFlood()
    {
        Map<Firestation, List<Person>> dto = urlService.flood(1,2,3);
        assertEquals(dto.keySet().size(), 3);
        System.out.println(dto.keySet());
        Firestation f1 = firestationService.getFirestationByAdress("a1").get();
        Firestation f2 = firestationService.getFirestationByAdress("a2").get();
        Firestation f3 = firestationService.getFirestationByAdress("a33").get();
        assertTrue(dto.keySet().contains(f1));
        assertTrue(dto.keySet().contains(f2));
        assertTrue(dto.keySet().contains(f3));
        assertEquals(dto.get(f1).size(), 1);
        assertTrue(dto.get(f1).contains(personService.getPersonByName("f1", "l1")));
        assertEquals(dto.get(f2).size(), 1);
        assertTrue(dto.get(f2).contains(personService.getPersonByName("f2", "l2")));
        assertEquals(dto.get(f3).size(), 3);
        assertTrue(dto.get(f3).contains(personService.getPersonByName("f33", "l3")));
        assertTrue(dto.get(f3).contains(personService.getPersonByName("f32", "l3")));
        assertTrue(dto.get(f3).contains(personService.getPersonByName("f31", "l3")));

        assertThrows(EntityDoesNotExistException.class, () -> urlService.flood(1, 2, 3, 9));
    }

    @Test
    void testPersonInfo()
    {
        Person person = urlService.personInfo("f1", "l1");
        assertEquals(person, personService.getPersonByName("f1", "l1"));

        assertThrows(EntityDoesNotExistException.class, () -> urlService.personInfo("xxx", "yyy"));
    }

    @Test
    void testCommunityEmail()
    {
        String city = "c1";
        CommunityEmailDTO dto = urlService.communityEmail(city);
        assertEquals(dto.city(), city);
        assertEquals(dto.email().size(), 1);
        assertTrue(dto.email().contains("e1"));

        assertThrows(EntityDoesNotExistException.class, () -> urlService.communityEmail("xxx"));
    }

    @Test
    void testIsNumberStationExist()
    {
        assertTrue(urlService.isNumberStationExist(1));
        assertFalse(urlService.isNumberStationExist(9));
    }

    @Test
    void testGetPeopleByStationNumber()
    {
        List<Person> people = urlService.getPeopleByStationNumber(3);
        assertEquals(people.size(), 3);
        assertTrue(people.contains(personService.getPersonByName("f33", "l3")));
        assertTrue(people.contains(personService.getPersonByName("f32", "l3")));
        assertTrue(people.contains(personService.getPersonByName("f31", "l3")));

        assertThrows(EntityDoesNotExistException.class, () -> urlService.getPeopleByStationNumber(9));
    }
}