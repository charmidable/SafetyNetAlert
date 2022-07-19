package com.openclassrooms.safetynet.dao;

import          java.io.IOException;

import          org.springframework.beans.factory.annotation.Autowired;

import static   com.openclassrooms.safetynet.dao.MapListComparison.*;

import          org.junit.jupiter.api.*;
import static   org.junit.jupiter.api.Assertions.assertTrue;


class JsonDAOTest extends DAOTest
{
    @Autowired
    JsonDAO dao;

    void validateDataMap()
    {
        assertTrue( areListAndMapOfEntityAreEqual      ( data.getPersonsListTest(),       collections.getPersonsMap()        ) );
        assertTrue( areListAndMapOfEntityAreEqual      ( data.getMedicalrecordListTest(), collections.getMedicalrecordsMap() ) );
        assertTrue( areListAndMapOfListOfEntityAreEqual( data.getFirestationListTest(),   collections.getFirestationsMap()   ) );
    }


    @Test
    @DisplayName("Test loadFromJson() method")
    void loadFromJson()
    {
        validateDataMap();
    }


    @Test
    @DisplayName("Test saveToJson() method")
    void saveToJson() throws IOException
    {
        dao.saveToJson();
        dao.loadFromJson();
        validateDataMap();
    }
}