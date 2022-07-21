package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
class MedicalrecordServiceTest
{
    @Autowired
    private MedicalrecordService service;

    @Autowired
    PersonService personService;

    @Test
    void addMedicalrecord()
    {
        final Medicalrecord medicalrecord1 = new Medicalrecord( new Person("f1", "l1"));
        assertThrows(EntityAlreadyExistException.class, () -> service.addMedicalrecord(medicalrecord1));

        final Medicalrecord medicalrecord2 = new Medicalrecord( new Person("f9", "l9"));
        assertThrows(EntityDoesNotExistException.class, () -> service.addMedicalrecord(medicalrecord2));

        Person person = new Person("f9", "l9");
        personService.addPerson(person);
        final Medicalrecord medicalrecord3 = new Medicalrecord(person);
        medicalrecord3.setBirthdate(LocalDate.now());
        service.addMedicalrecord(medicalrecord3);
        assertEquals(personService.getPersonByName(person.getFirstName(), person.getLastName()).getMedicalrecord(), medicalrecord3);

        // CLEAN
        personService.removePerson(person);
    }

    @Test
    void updateMedicalrecord()
    {
        final Medicalrecord medicalrecord1 = new Medicalrecord( new Person("f9", "l9"));
        assertThrows(EntityDoesNotExistException.class, () -> service.updateMedicalrecord(medicalrecord1));

        Person person = new Person("f9", "l9");
        personService.addPerson(person);
        final Medicalrecord medicalrecord2 = new Medicalrecord(person);
        medicalrecord2.setAllergies(Arrays.asList("lactose"));
        medicalrecord2.setMedications(Arrays.asList("chloroquine:500mg"));
        medicalrecord2.setBirthdate(LocalDate.now());
        assertThrows(EntityDoesNotExistException.class, () -> service.updateMedicalrecord(medicalrecord2));

        person.getMedicalrecord().setBirthdate(LocalDate.of(2000, 02, 22));
        service.updateMedicalrecord(medicalrecord2);
        assertEquals(personService.getPersonByName(person.getFirstName(), person.getLastName()).getMedicalrecord(), medicalrecord2);

        // CLEAN
        personService.removePerson(person);
    }

    @Test
    void removeMedicalrecord()
    {
        final Medicalrecord medicalrecord1 = new Medicalrecord( new Person("f9", "l9"));
        assertThrows(EntityDoesNotExistException.class, () -> service.removeMedicalrecord(medicalrecord1));

        Person person = new Person("f9", "l9");
        personService.addPerson(person);
        assertThrows(EntityDoesNotExistException.class, () -> service.removeMedicalrecord(medicalrecord1));

        person.getMedicalrecord().setBirthdate(LocalDate.now());
        assertNotNull(personService.getPersonByName("f9", "l9").getMedicalrecord().getBirthdate());
        service.removeMedicalrecord(new Medicalrecord(person));
        assertNull(personService.getPersonByName("f9", "l9").getMedicalrecord().getBirthdate());

        // CLEAN
        personService.removePerson(person);
    }
}