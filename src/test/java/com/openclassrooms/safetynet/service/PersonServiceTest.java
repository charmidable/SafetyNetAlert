package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Map;

import com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Person;
import com.openclassrooms.safetynet.repository.PersonRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import        org.junit.jupiter.api.Test;

import org.hamcrest.Matchers;


@SpringBootTest
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
class PersonServiceTest
{
    @Autowired
    PersonService service;

    @Autowired
    PersonRepo repo;

    @Test
    void testGetPersons()
    {
        Matchers.containsInAnyOrder(service.getPersons(), repo.getList());
    }

    @Test
    void testGetChildrenListAndAdultsListByAddress()
    {
        Map<Boolean, List<Person>> map;

        map = service.getChildrenListAndAdultsListByAddress("a33");
        assertTrue(map.get(true).size() == 1);
        assertEquals(map.get(true).stream().findFirst().get(), service.getPersonByName("f33", "l3"));

        assertTrue(map.get(false).size() == 2);
        assertTrue(map.get(false).stream().toList().contains(service.getPersonByName("f32", "l3")));
        assertTrue(map.get(false).stream().toList().contains(service.getPersonByName("f31", "l3")));

        map = service.getChildrenListAndAdultsListByAddress("xxx");
        assertTrue(map.get(true).isEmpty());
        assertTrue(map.get(false).isEmpty());

        map = service.getChildrenListAndAdultsListByAddress("a1");
        assertTrue(map.get(true).isEmpty());
        assertTrue(map.get(false).size() == 1);
        assertTrue(map.get(false).stream().toList().contains(service.getPersonByName("f1", "l1")));
    }

    @Test
    void testGetPersonsByAddress()
    {
        assertTrue(service.getPersonsByAddress("a1").size() == 1);
        assertTrue(service.getPersonsByAddress("a1").contains(service.getPersonByName("f1", "l1")));

        assertTrue(service.getPersonsByAddress("xxx").size() == 0);
    }

    @Test
    void testIsCityExist()
    {
        assertTrue(service.isCityExist("c1"));
        assertFalse(service.isCityExist("c9"));
    }

    @Test
    void testGetEmailsOfAllTheCity()
    {
        assertEquals(service.getEmailsOfAllTheCity("c1").size(), 1 );
        assertTrue(service.getEmailsOfAllTheCity("c1").contains("e1") );
        assertThrows(EntityDoesNotExistException.class, () ->service.getEmailsOfAllTheCity("xxx"));
    }

    @Test
    void testGetPersonByName()
    {
        assertThrows(EntityDoesNotExistException.class, () -> service.getPersonByName("fff", "lll"));
        assertNotNull(service.getPersonByName("f1", "l1"));
    }

    @Test
    void testAddPerson()
    {
        Person person = new Person("fff", "lll");
        assertThrows(EntityDoesNotExistException.class, () -> service.getPersonByName(person.getFirstName(), person.getLastName()));
        service.addPerson(person);
        assertEquals(service.getPersonByName(person.getFirstName(), person.getLastName()), person);
        assertThrows(EntityAlreadyExistException.class, () -> service.addPerson(person));
        // CLEAN
        service.removePerson(person);
    }

    @Test
    void testRemovePerson()
    {
        Person person;
        assertNotNull(person = service.getPersonByName("f1", "l1"));
        service.removePerson(person);
        assertThrows(EntityDoesNotExistException.class, () -> service.getPersonByName("f1", "l1"));
        // CLEAN
        service.addPerson(person);
    }

    @Test
    void testUpdatePerson()
    {
        Person person1 = new Person("xxx", "yyy");
        assertThrows(EntityDoesNotExistException.class, () -> service.updatePerson(person1));

        Person person2;
        person2 = service.getPersonByName("f1", "l1");

        String address  = person2.getAddress();
        String email    = person2.getEmail();
        String phone    = person2.getPhone();
        String city     = person2.getCity();
        String zip      = person2.getZip();
        Medicalrecord mr= person2.getMedicalrecord();

        Person person3 = new Person(person2.getFirstName(), person2.getLastName());
        person3.setAddress("aaa");
        person3.setEmail("eee");
        person3.setPhone("ppp");
        person3.setCity("ccc");
        person3.setZip("666");
        Medicalrecord mr2 = new Medicalrecord(person3);
        person3.setMedicalrecord(mr2);

        service.updatePerson(person3);

        Person person4 = service.getPersonByName(person2.getFirstName(), person2.getLastName());

        assertNotEquals(person4.getAddress(), address);
        assertEquals(person4.getAddress(), "aaa");

        assertNotEquals(person4.getEmail(), email);
        assertEquals(person4.getEmail(), "eee");

        assertNotEquals(person4.getPhone(), phone);
        assertEquals(person4.getPhone(), "ppp");

        assertNotEquals(person4.getCity(), city);
        assertEquals(person4.getCity(), "ccc");

        assertNotEquals(person4.getZip(), zip);
        assertEquals(person4.getZip(), "666");

        assertNotEquals(person4.getMedicalrecord(), mr2);
        assertEquals(person4.getMedicalrecord(), mr);

        // CLEAN
        Person person5 = new Person(person4.getFirstName(), person4.getLastName());
        person5.setAddress(address);
        person5.setEmail(email);
        person5.setPhone(phone);
        person5.setCity(city);
        person5.setZip(zip);
        person5.setMedicalrecord(mr2);
        service.updatePerson(person5);
    }
}