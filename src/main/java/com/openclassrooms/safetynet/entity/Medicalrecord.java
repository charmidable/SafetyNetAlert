package com.openclassrooms.safetynet.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class Medicalrecord
{
    // ======================================
    // =             Attributes             =
    // ======================================

    private final   String       firstName;
    private final   String       lastName;
    private         List<String> allergies;
    private         List<String> medications;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private         LocalDate    birthdate;

    // ======================================
    // =            Constructors            =
    // ======================================

    @JsonCreator
    public Medicalrecord(
                            @JsonProperty("firstName")
                            final String firstName,

                            @JsonProperty("lastName")
                            final String lastName
                        )
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // ======================================
    // =         Medical Methods            =
    // ======================================

    public boolean  addAllergie(String allergie)
    {
        return allergies.add(allergie);
    }

    public boolean  addMedication(String medication)
    {
        return medications.add(medication);
    }

    public boolean  removeAllergie(String allergie)
    {
        return allergies.remove(allergie);
    }

    public boolean  removeMedication(String medication)
    {
        return medications.remove(medication);
    }

    public void     removeAllAllergies()
    {
        allergies.clear();
    }

    public void     removeAllMedications()
    {
        medications.clear();
    }

    public void     clearAllAllergiesAndAllMedications()
    {
        removeAllMedications();
        removeAllAllergies();
    }


    // ======================================
    // =           Object Methods           =
    // ======================================

    @Override
    public int hashCode()
    {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "Medicalrecord{" +
                "firstName='"   + firstName   +
                ", lastName='"  + lastName    +
                ", birthdate="  + birthdate   +
                ", allergies="  + allergies   +
                ", medications="+ medications +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Medicalrecord)) return false;
        Medicalrecord that = (Medicalrecord) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName);
    }
}