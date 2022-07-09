package com.openclassrooms.safetynet.entity;

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
    private         Integer         zip;
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
        firstName       = _firstName;
        lastName        = _lastName;
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
    public final boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!firstName.equals(person.firstName)) return false;
        return lastName.equals(person.lastName);
    }

    @Override
    public final int hashCode()
    {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    public boolean isChild()
    {
       return medicalrecord.isChild();
    }
}