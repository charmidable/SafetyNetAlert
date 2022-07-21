package com.openclassrooms.safetynet.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
@JsonFilter("person_filter")
public class Person
{
    // ======================================
    // =             Attributes             =
    // ======================================

    private final   String          firstName;
    private final   String          lastName;
    private         String          address;
    private         String          city;
    private         String          phone;
    private         String          email;
    private         String          zip;
    private         Medicalrecord   medicalrecord;


    // ======================================
    // =            Constructors            =
    // ======================================

    @JsonCreator
    public Person(
                    @JsonProperty("firstName")
                    String _firstName,

                    @JsonProperty("lastName")
                    String _lastName
                 )
    {
        firstName = _firstName;
        lastName  = _lastName;
    }

    // ======================================
    // =   Medicalrecord Delegate Methods   =
    // ======================================

    public List<String> getAllergies()
    {
        return medicalrecord.getAllergies();
    }

    public List<String> getMedications()
    {
        return medicalrecord.getMedications();
    }

    public LocalDate getBirthdate()
    {
        return medicalrecord.getBirthdate();
    }

    public Integer getAge()
    {
        return medicalrecord.getAge();
    }

    public boolean isChild()
    {
        return medicalrecord.isChild();
    }

    // ======================================
    // =           Object Methods           =
    // ======================================

    @Override
    public String toString()
    {
        return  "Person{"       +
                "firstName="    + firstName +
                ", lastName="   + lastName  +
                ", address="    + address   +
                ", city="       + city      +
                ", zip="        + zip       +
                ", phone="      + phone     +
                ", email="      + email     +
                '}';
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return  firstName    .equals(person.firstName)  &&
                lastName     .equals(person.lastName)   &&
                address      .equals(person.address)    &&
                phone        .equals(person.phone)      &&
                email        .equals(person.email)      &&
                city         .equals(person.city)       &&
                zip          .equals(person.zip)        &&
                medicalrecord.equals(person.medicalrecord);
    }


    @Override
    public final int hashCode()
    {
        int result = firstName.toLowerCase().hashCode();
        result = 31 * result + lastName.toLowerCase().hashCode();
        return result;
    }
}