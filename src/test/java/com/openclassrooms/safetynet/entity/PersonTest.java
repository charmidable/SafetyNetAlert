package com.openclassrooms.safetynet.entity;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static   org.junit.jupiter.api.Assertions.*;
import          org.junit.jupiter.api.BeforeEach;
import          org.junit.jupiter.api.Test;


class PersonTest
{
    private Person        person;
    private List<String>  medications = Arrays.asList("m1", "m2");
    private List<String>  allergies   = Arrays.asList("a1", "a2");
    private Medicalrecord mr;
    private Integer       age         = 20;
    private LocalDate     date        = LocalDate.now().minus(age, ChronoUnit.YEARS);
    private String        address     = "99 street";
    private String        firstName   = "fn";
    private String        lastName    = "ln";
    private String        phone       = "999";
    private String        email       = "aa@aa";
    private String        city        = "town";
    private int           zip         =  222;

    @BeforeEach
    void setUp()
    {
        person = new Person(firstName, lastName);
        mr     = new Medicalrecord(person);
        mr.setMedications(medications);
        mr.setAllergies(allergies);
        mr.setBirthdate(date);
        person.setMedicalrecord(mr);
        person.setAddress(address);
        person.setEmail(email);
        person.setPhone(phone);
        person.setCity(city);
        person.setZip(zip);

    }

    @Test
    void testGetAllergies()
    {
        assertEquals(person.getAllergies(), allergies);
    }

    @Test
    void testGetMedications()
    {
        assertEquals(person.getMedications(), medications);
    }

    @Test
    void testGetBirthdate()
    {
        assertEquals(person.getBirthdate(), date);
    }

    @Test
    void testGetAge()
    {
        assertEquals(person.getAge(), mr.getAge());
    }

    @Test
    void testIsChild()
    {
        assertEquals(person.isChild(), mr.isChild());
    }

    @Test
    void testToString()
    {
        assertNotNull(person.toString());
    }

    @Test
    void testEquals()
    {
        Person p1 = new Person(firstName, lastName);
        p1.setMedicalrecord(mr);
        p1.setAddress(address);
        p1.setEmail(email);
        p1.setPhone(phone);
        p1.setCity(city);
        p1.setZip(zip);

        assertTrue(person.equals(p1));
    }

    @Test
    void testHashCode()
    {
        assertEquals(person.hashCode(), mr.hashCode());
    }

    @Test
    void testGetFirstName()
    {
        assertEquals(person.getFirstName(), firstName);
    }

    @Test
    void testGetLastName()
    {
        assertEquals(person.getLastName(), lastName);
    }

    @Test
    void testGetAddress()
    {
        assertEquals(person.getAddress(), address);
    }

    @Test
    void testGetCity()
    {
        assertEquals(person.getCity(), city);
    }

    @Test
    void testGetPhone()
    {
        assertEquals(person.getPhone(), phone);
    }

    @Test
    void testGetEmail()
    {
        assertEquals(person.getEmail(), email);
    }

    @Test
    void testGetZip()
    {
        assertEquals(person.getZip(), zip);
    }

    @Test
    void testGetMedicalrecord()
    {
        assertEquals(person.getMedicalrecord(), mr);
    }

    @Test
    void testSetAddress()
    {
        assertEquals(person.getAddress(), address);
        person.setAddress("xxx");
        assertNotEquals(person.getAddress(), address);
    }

    @Test
    void testSetCity()
    {
        assertEquals(person.getCity(), city);
        person.setCity("xxx");
        assertNotEquals(person.getCity(), city);
    }

    @Test
    void testSetPhone()
    {
        assertEquals(person.getPhone(), phone);
        person.setPhone("xxx");
        assertNotEquals(person.getPhone(), phone);
    }

    @Test
    void testSetEmail()
    {
        assertEquals(person.getAddress(), address);
        person.setAddress("xxx");
        assertNotEquals(person.getAddress(), address);
    }

    @Test
    void testSetZip()
    {
        assertEquals(person.getZip(), zip);
        person.setZip(111);
        assertNotEquals(person.getZip(), zip);
    }

    @Test
    void testSetMedicalrecord()
    {
        assertEquals(person.getMedicalrecord(), mr);
        Medicalrecord mr2 = new Medicalrecord(person);
        mr2.setMedications(allergies);
        mr2.setAllergies(medications);
        mr2.setBirthdate(LocalDate.now().minus(2, ChronoUnit.YEARS));
        person.setMedicalrecord(mr2);
        assertNotEquals(person.getMedicalrecord(), mr);
    }
}