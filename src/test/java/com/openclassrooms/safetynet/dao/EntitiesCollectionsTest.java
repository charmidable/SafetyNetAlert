package com.openclassrooms.safetynet.dao;

import          org.hamcrest.Matchers;
import          org.junit.jupiter.api.Test;

import static   org.junit.jupiter.api.Assertions.*;
import static   com.openclassrooms.safetynet.dao.MapListComparison.*;


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
    void testSetPersons()
    {
        assertTrue( areListAndMapOfEntityEqual(data.getPersonsListTest(), collections.getPersonsMap()) );
    }

    @Test
    void testSetMedicalrecords()
    {
        assertTrue( areListAndMapOfEntityEqual(data.getMedicalrecordListTest(), collections.getMedicalrecordsMap()) );
    }

    @Test
    void testSetFirestations()
    {
        assertTrue( areListAndMapOfListOfEntityEqual(data.getFirestationListTest(), collections.getFirestationsMap()) );
    }
}