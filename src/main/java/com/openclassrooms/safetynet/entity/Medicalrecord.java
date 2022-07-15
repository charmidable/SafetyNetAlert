package com.openclassrooms.safetynet.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Medicalrecord(
                            @JsonProperty("firstName")
                            final String firstName,

                            @JsonProperty("lastName")
                            final String lastName
                        )
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Medicalrecord(Person person)
    {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
    }

    // ======================================
    // =          Business Methods          =
    // ======================================

    @JsonIgnore
    public Integer getAge()
    {
        if(birthdate == null ) return null;
        return LocalDate.now().getYear() - getBirthdate().getYear();
    }

    @JsonIgnore
    public Boolean isChild()
    {
        if(getAge() == null) return false;
        return getAge() < 19;
    }

    public void clearMedicalrecord()
    {
        medications.clear();
        allergies.clear();
        birthdate = null;
    }

    // ======================================
    // =           Object Methods           =
    // ======================================

    @Override
    public final int hashCode()
    {
        int result = firstName.toLowerCase().hashCode();
        result = 31 * result + lastName.toLowerCase().hashCode();
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