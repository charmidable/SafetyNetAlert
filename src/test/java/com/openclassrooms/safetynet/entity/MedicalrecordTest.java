package com.openclassrooms.safetynet.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static   org.junit.jupiter.api.Assertions.*;
import          org.junit.jupiter.api.BeforeEach;
import          org.junit.jupiter.api.Test;


class MedicalrecordTest
{
    private Medicalrecord medicalrecord;
    private List<String>  medications;
    private List<String>  allergies;
    private String        firstName     = "fn";
    private String        lastName      = "ln";
    private Integer       age           = 20;
    private Person        person        = new Person("fn", "ln");
    private LocalDate     date          = LocalDate.now().minus(age, ChronoUnit.YEARS);

    @BeforeEach
    void setUp()
    {
        allergies     = Arrays.asList("a1", "a2");
        medications   = Arrays.asList("m1", "m2");

        medicalrecord = new Medicalrecord(person);

        medicalrecord.setBirthdate  (date);
        medicalrecord.setAllergies  (allergies);
        medicalrecord.setMedications(medications);
    }

    @Test
    void testGetFirstName()
    {
        assertEquals( medicalrecord.getFirstName(), firstName );
    }

    @Test
    void testGetLastName()
    {
        assertEquals( medicalrecord.getLastName(), lastName );
    }

    @Test
    void testGetAllergies()
    {
        assertEquals( medicalrecord.getAllergies(), allergies );
    }

    @Test
    void testGetMedications()
    {
        assertEquals( medicalrecord.getMedications(), medications );
    }

    @Test
    void testGetBirthdate()
    {
        assertEquals( medicalrecord.getBirthdate(), date );
    }

    @Test
    void testGetAge()
    {
        assertEquals( medicalrecord.getAge(), age );
    }

    @Test
    void testIsChild()
    {
        assertFalse( medicalrecord.isChild() );
        medicalrecord.setBirthdate( LocalDate.now().minus(2, ChronoUnit.YEARS) );
        assertTrue( medicalrecord.isChild() );
    }

    @Test
    void testClearMedicalrecord()
    {
        medicalrecord.clearMedicalrecord();
        assertTrue ( medicalrecord.getAllergies().isEmpty()   );
        assertTrue ( medicalrecord.getMedications().isEmpty() );
        assertNull ( medicalrecord.getBirthdate() );
        assertFalse( medicalrecord.isChild() );
        assertNull ( medicalrecord.getAge()  );
    }

    @Test
    void testHashCode()
    {
        assertEquals(medicalrecord.hashCode() ,person.hashCode());
    }

    @Test
    void testToString()
    {
        assertNotNull(medicalrecord.toString());
    }

    @Test
    void testEquals()
    {
        Medicalrecord mr1 = new Medicalrecord(new Person(firstName, lastName));
        mr1.setAllergies(allergies);
        mr1.setMedications(medications);
        mr1.setBirthdate(date);

        assertTrue(medicalrecord.equals(mr1));

        Medicalrecord mr2 = new Medicalrecord(new Person(lastName, firstName));
        mr1.setAllergies(medications);
        mr1.setMedications(allergies);
        mr1.setBirthdate(LocalDate.now());

        assertFalse(medicalrecord.equals(mr2));
    }

    @Test
    void testSetAllergies()
    {
        assertEquals   ( medicalrecord.getAllergies(), allergies );
        medicalrecord.setAllergies(medications);
        assertNotEquals( medicalrecord.getAllergies(), allergies );
        assertEquals   ( medicalrecord.getAllergies(), medications );
    }

    @Test
    void testSetMedications()
    {
        assertEquals   ( medicalrecord.getMedications(), medications );
        medicalrecord.setMedications(allergies);
        assertNotEquals( medicalrecord.getMedications(), medications );
        assertEquals   ( medicalrecord.getAllergies(), allergies     );
    }

    @Test
    void testSetBirthdate()
    {
        assertTrue ( medicalrecord.getBirthdate().equals(date) );
        medicalrecord.setBirthdate(LocalDate.now().minus(2, ChronoUnit.YEARS));
        assertFalse( medicalrecord.getBirthdate().equals(date) );
    }
}