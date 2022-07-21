package com.openclassrooms.safetynet.service;

import java.io.IOException;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import com.openclassrooms.safetynet.repository.FirestationRepo;
import com.openclassrooms.safetynet.entity.Firestation;

import static org.junit.jupiter.api.Assertions.*;
import        org.junit.jupiter.api.Test;

import        org.hamcrest.Matchers;


@SpringBootTest
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
class FirestationServiceTest
{
    @Autowired
    private FirestationRepo repo;

    @Autowired
    private FirestationService service;

    @Test
    void testGetMap()
    {
        assertEquals(service.getMap(), repo.getMap());
    }

    @Test
    void testGetList()
    {
        assertEquals(service.getList(), repo.getList());
    }

    @Test
    void testGetNumberStationList()
    {
        assertEquals(service.getNumberStationList(), Arrays.asList("1", "2", "3"));
    }

    @Test
    void testGetFirestationByAdress()
    {
        assertEquals(service.getFirestationByAdress("a1"), Optional.of(new Firestation("1", "a1")));
        assertFalse(service.getFirestationByAdress("xxx").isPresent());
    }

    @Test
    void testGetFirestationNumberByAdress()
    {
        assertEquals(service.getFirestationNumberByAdress("a1"), "1");
        assertThrows(EntityDoesNotExistException.class,  () -> service.getFirestationNumberByAdress("xxx") );
    }

    @Test
    void testIsNumberStationExist()
    {
        assertTrue(service.isNumberStationExist("1"));
        assertFalse(service.isNumberStationExist("9"));
    }

    @Test
    void testGetAdressesCoveredByTheFireStation()
    {
        Matchers.containsInAnyOrder(service.getAdressesCoveredByTheFireStation("1"),(Arrays.asList("a1", "a2") ));
    }

    @Test
    void testAddFirestation() throws IOException
    {
        assertThrows( EntityAlreadyExistException.class,  () -> service.addFirestation(new Firestation("9","a1")) );
        service.addFirestation(new Firestation("1","xxx"));
        Matchers.containsInAnyOrder(service.getAdressesCoveredByTheFireStation("1"), Arrays.asList("a1", "a2", "xxx"));
        service.removeFirestation(new Firestation("1","xxx"));
        service.addFirestation(new Firestation("9","yyy"));
        assertEquals(service.getAdressesCoveredByTheFireStation("9"), Arrays.asList("yyy"));
        service.removeFirestation(new Firestation("9","yyy"));
    }

    @Test
    void testUpdateFirestation() throws IOException
    {
        assertThrows( EntityDoesNotExistException.class,  () -> service.updateFirestation(new Firestation("9", "xxx")) );
        assertEquals(service.getFirestationNumberByAdress("a1"), "1");
        service.updateFirestation(new Firestation("2", "a1"));
        assertEquals(service.getFirestationNumberByAdress("a1"), "2");
        service.updateFirestation(new Firestation("1", "a1"));
    }

    @Test
    void testRemoveFirestation() throws IOException
    {
        assertThrows( EntityDoesNotExistException.class,  () -> service.removeFirestation(new Firestation("9", "xxx")) );
        assertEquals(service.getFirestationNumberByAdress("a1"), "1");
        service.removeFirestation(new Firestation("1", "a1"));
        assertThrows( EntityDoesNotExistException.class, () -> service.getFirestationNumberByAdress("a1"));
        service.addFirestation(new Firestation("1", "a1"));
    }

    @Test
    void testAvoidDoublon() throws IOException
    {
        List<Firestation> firestationList = service.getList();
        service.avoidDoublon(new Firestation("1", "xxx"));
        assertEquals(firestationList, service.getList());
        service.avoidDoublon(new Firestation("2", "a1"));
        assertNotEquals(firestationList, service.getList());
        service.addFirestation(new Firestation("1", "a1"));
    }
}