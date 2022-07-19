package com.openclassrooms.safetynet.dao;

import static   com.openclassrooms.safetynet.dao.MapListComparison.*;

import static   org.junit.jupiter.api.Assertions.*;
import          org.junit.jupiter.api.Test;

import          org.hamcrest.Matchers;


class EntitiesCollectionsTest extends DAOTest
{
    @Test
    void testGetPersons()
    {
        Matchers.containsInAnyOrder(collections.getPersons(), data.getPersonsListTest());
    }

    @Test
    void testGetFirestations()
    {
        Matchers.containsInAnyOrder(collections.getFirestations(), data.getFirestationListTest());
    }

    @Test
    void testGetMedicalrecords()
    {
        Matchers.containsInAnyOrder(collections.getMedicalrecords(), data.getMedicalrecordListTest());
    }

    @Test
    void setPersons()
    {
        System.out.println(data.getPersonsListTest());
        System.out.println(" ");
        System.out.println(collections.getPersonsMap());
        System.out.println(" ");
        assertTrue( areListAndMapOfEntityAreEqual(data.getPersonsListTest(), collections.getPersonsMap()) );
    }

    @Test
    void setMedicalrecords()
    {
        assertTrue( areListAndMapOfEntityAreEqual(data.getMedicalrecordListTest(), collections.getMedicalrecordsMap()) );
    }

    @Test
    void setFirestations()
    {
        assertTrue( areListAndMapOfListOfEntityAreEqual(data.getFirestationListTest(), collections.getFirestationsMap()) );
    }
}