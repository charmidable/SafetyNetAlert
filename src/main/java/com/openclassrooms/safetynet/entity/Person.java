package com.openclassrooms.safetynet.entity;

import          java.util.List;
import          java.time.LocalDate;
import static   java.util.stream.Collectors.joining;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private         int             zip;
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
        medicalrecord   = new Medicalrecord(firstName, lastName);
    }


    // ======================================
    // =   Delegate Medicalrecord Methods   =
    // ======================================

    public List<String> getAllergies()
    {
        return medicalrecord.getAllergies();
    }

    public List<String> getMedications()
    {
        return medicalrecord.getMedications();
    }

    @JsonFormat(pattern = "MM/dd/yyyy")
    public void setBirthdate(LocalDate birthdate)
    {
        medicalrecord.setBirthdate(birthdate);
    }

    public void setAllergies(List<String> allergies)
    {
        medicalrecord.setAllergies(allergies);
    }

    public void setMedications(List<String> medications)
    {
        medicalrecord.setMedications(medications);
    }

    public boolean addMedication(String medication)
    {
        return medicalrecord.addMedication(medication);
    }

    public boolean removeMedication(String medication)
    {
        return medicalrecord.removeMedication(medication);
    }

    public boolean addAllergie(String allergie)
    {
        return medicalrecord.addAllergie(allergie);
    }

    public boolean removeAllergie(String allergie)
    {
        return medicalrecord.removeAllergie(allergie);
    }

    public void removeAllMedications()
    {
        medicalrecord.removeAllMedications();
    }

    public void removeAllAllergies()
    {
        medicalrecord.removeAllAllergies();
    }

    public void clearAllAllergiesAndMedications()
    {
        medicalrecord.clearAllAllergiesAndAllMedications();
    }

    public LocalDate getBirthdate()
    {
        return medicalrecord.getBirthdate();
    }

    public Integer getAge()
    {
        return LocalDate.now().getYear() - getBirthdate().getYear();
    }

    public boolean isChild()
    {
        return getAge() < 19;
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
                ", birthdate="  + getBirthdate() +
                ", allergies="  + getAllergies().stream().collect(joining(", ", "{", "}"))  +
                ", medications="+ getMedications().stream().collect(joining(", ", "{", "}"))+
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
}