package com.openclassrooms.safetynet.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.openclassrooms.safetynet.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;


public class Person
{

    // ======================================
    // =             Attributes             =
    // ======================================

    private static  Map<Integer, Medicalrecord> medicalrecords;
//    @Autowired
    private static  Repo                        repo;
    private final   String                      firstName;
    private final   String                      lastName;
    private         String                      address;
    private         String                      city;
    private         String                      phone;
    private         String                      email;
    private         int                         zip;
    private         LocalDate                   birthdate;
    private         List<String>                Medications;
    private         List<String>                Allergies;


    // ======================================
    // =            Constructors            =
    // ======================================

    @JsonCreator
    public Person(
                    @JsonProperty("firstName")
                    String firstName,

                    @JsonProperty("lastName")
                    String lastName
                 )
    {
        this.firstName  = firstName;
        this.lastName   = lastName;
    }

    public Person(String firstName, String lastName, String address, LocalDate date)
    {
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.address    = address;
        createAndAddMedicalRecord(this, date);
    }


    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public int getZip()
    {
        return zip;
    }

    public void setZip(int zip)
    {
        this.zip = zip;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }



    // ======================================
    // =         Medical Information        =
    // ======================================



    public static void setRepo(Repo _repo)
    {
        repo = _repo;
    }

    public void createAndAddMedicalRecord(Person person, LocalDate birthdate)
    {
        repo.addMedicalRecord(
                               new Medicalrecord(
                                                    person.getFirstName(),
                                                    person.getLastName(),
                                                    birthdate,
                                                    new ArrayList<String>(),
                                                    new ArrayList<String>()
                                                 )
                             );
    }

    public void setBirthdate(LocalDate date)
    {
        repo.removeMedicalRecord(hashCode());

        repo.addMedicalRecord(
                                new Medicalrecord(
                                                    getFirstName(),
                                                    getLastName(),
                                                    birthdate = date,
                                                    getAllergies(),
                                                    getMedications()
                                                 )
                             );
    }


    public Medicalrecord getMedicalRecord()
    {
        return repo.getMedicalrecordsMap().get(hashCode());
    }


    public LocalDate getBirthdate()
    {
        return this.getMedicalRecord().birthdate();
    }



    public final Integer getAge()
    {
        return LocalDate.now().getYear() - this.getMedicalRecord().birthdate().getYear();
    }


    public List<String> getMedications()
    {
        return getMedicalRecord().medications();
    }


    public List<String> getAllergies()
    {
        return getMedicalRecord().allergies();
    }


    public Boolean isChild()
    {
        return getAge() <= 18 ? true : false;
    }

    public boolean addMedication(String medication)
    {
        return getMedicalRecord().medications().add(medication);
    }

    public boolean addAllergie(String allergie)
    {
        return getMedicalRecord().allergies().add(allergie);
    }

    public boolean removeMedication(String medication)
    {
        return getMedicalRecord().medications().remove(medication);
    }

    public boolean removeAllergie(String allergie)
    {
        return getMedicalRecord().allergies().remove(allergie);
    }

    public void removeAllMedications()
    {
        getMedicalRecord().medications().clear();
    }

    public void removeAllAllergies()
    {
        getMedicalRecord().medications().clear();
    }

    public void clearMedicalRecords()
    {
        removeAllMedications();
        removeAllAllergies();
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
                ", allergies="  + getAllergies().stream().collect(Collectors.joining(", ", "{", "}")) +
                ", medications="+ getMedications().stream().collect(Collectors.joining(", ", "{", "}")) +
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