package com.openclassrooms.safetynet.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.entity.Medicalrecord;
import com.openclassrooms.safetynet.entity.Person;

class Data
{
    // ======================================
    // =             Attributes             =
    // ======================================
    private ArrayList<Medicalrecord> medicalrecordListTest   = new ArrayList();
    private ArrayList<Firestation>   firestationListTest     = new ArrayList();
    private ArrayList<Firestation>   station1ListTest        = new ArrayList();
    private ArrayList<Firestation>   station2ListTest        = new ArrayList();
    private ArrayList<Person>        personsListTest         = new ArrayList();


    // ======================================
    // =           Initialization           =
    // ======================================
    {
        Person p1 = new Person("f1", "l1");
        p1.setZip(1);
        p1.setCity("c1");
        p1.setPhone("p1");
        p1.setEmail("e1");
        p1.setAddress("a1");

        Person p2 = new Person("f2", "l2");
        p2.setZip(2);
        p2.setCity("c2");
        p2.setPhone("p2");
        p2.setEmail("e2");
        p2.setAddress("a2");

        //=============================================================

        Person p3 = new Person("f31", "l3");
        p3.setZip(3);
        p3.setCity("c3");
        p3.setPhone("p3");
        p3.setEmail("e3");
        p3.setAddress("a33");

        Person p4 = new Person("f32", "l3");
        p4.setZip(3);
        p4.setCity("c3");
        p4.setPhone("p3");
        p4.setEmail("e3");
        p4.setAddress("a33");

        Person p5 = new Person("f33", "l3");
        p5.setZip(3);
        p5.setCity("c3");
        p5.setPhone("p3");
        p5.setEmail("e3");
        p5.setAddress("a33");


        //=============================================================

        Medicalrecord mr1 = new Medicalrecord(p1);
        mr1.setBirthdate(LocalDate.of(0001, 0001, 0001));
        mr1.setMedications(Arrays.asList("m11", "m12"));
        mr1.setAllergies(Arrays.asList("a11", "a12"));
        p1.setMedicalrecord(mr1);

        Medicalrecord mr2 = new Medicalrecord(p2);
        mr2.setBirthdate(LocalDate.of(0002, 0002, 0002));
        mr2.setMedications(Arrays.asList("m21", "m22"));
        mr2.setAllergies(Arrays.asList("a21", "a22"));
        p2.setMedicalrecord(mr2);
        //=============================================================

        Medicalrecord mr3 = new Medicalrecord(p3);
        mr3.setBirthdate(LocalDate.of(1991, 0001, 0001));
        mr3.setMedications(Arrays.asList("m11", "m12"));
        mr3.setAllergies(Arrays.asList("a11", "a12"));
        p3.setMedicalrecord(mr3);

        Medicalrecord mr4 = new Medicalrecord(p4);
        mr4.setBirthdate(LocalDate.of(1992, 0002, 0002));
        mr4.setMedications(Arrays.asList("m11", "m12"));
        mr4.setAllergies(Arrays.asList("a11", "a12"));
        p4.setMedicalrecord(mr4);


        Medicalrecord mr5 = new Medicalrecord(p5);
        mr5.setBirthdate(LocalDate.of(2020, 0001, 0001));
        mr5.setMedications(Arrays.asList("m11", "m12"));
        mr5.setAllergies(Arrays.asList("a11", "a12"));
        p5.setMedicalrecord(mr5);





        //=============================================================

        Firestation f11 = new Firestation(1, "a1");
        Firestation f21 = new Firestation(2, "a2");
        Firestation f31 = new Firestation(3, "a33");

        medicalrecordListTest.add(mr1);
        medicalrecordListTest.add(mr2);
        firestationListTest.add(f11);
//        firestationListTest.add(f12);
        firestationListTest.add(f21);
        firestationListTest.add(f31);
//        firestationListTest.add(f22);
//        station1ListTest.add(f11);
//        station1ListTest.add(f12);
//        station2ListTest.add(f21);
//        station2ListTest.add(f22);
        personsListTest.add(p1);
        personsListTest.add(p2);
        //=============================================================
        personsListTest.add(p3);
        personsListTest.add(p4);
        personsListTest.add(p5);
        medicalrecordListTest.add(mr3);
        medicalrecordListTest.add(mr4);
        medicalrecordListTest.add(mr5);
        //=============================================================
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public ArrayList<Medicalrecord> getMedicalrecordListTest()
    {
        return medicalrecordListTest;
    }

    public ArrayList<Firestation>   getFirestationListTest()
    {
        return firestationListTest;
    }

    public ArrayList<Person>        getPersonsListTest()
    {
        return personsListTest;
    }
}