package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.entity.Person;

import java.time.LocalDate;

public class EndpointService
{
    public boolean addPerson(Person person)
    {
        throw new IllegalStateException();
    }

    public boolean editPerson(Person person)
    {
        throw new IllegalStateException();
    }

    public boolean setBirthday(Person person, LocalDate localDate)
    {
        throw new IllegalStateException();
    }

    public boolean setAddress(String newAddress)
    {
        throw new IllegalStateException();
    }

    public boolean addMedication(Person person, String medicationToAdd)
    {
        throw new IllegalStateException();
    }

    public boolean removeMedication(Person person, String medicationToRemove)
    {
        throw new IllegalStateException();
    }

    public boolean addAllergie(Person person, String allergieToAdd)
    {
        throw new IllegalStateException();
    }

    public boolean removeAllergie(Person person, String allergieToRemove)
    {
        throw new IllegalStateException();
    }

    public boolean removeAllAllergies(Person person)
    {
        throw new IllegalStateException();
    }

    public boolean removeAllMedications(Person person)
    {
        throw new IllegalStateException();
    }

    public boolean removeAllMedicationsAndAllergies(Person person)
    {
        throw new IllegalStateException();
    }
}
