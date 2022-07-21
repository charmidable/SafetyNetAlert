package com.openclassrooms.safetynet.dao;

import          java.io.IOException;

import          org.springframework.beans.factory.annotation.Autowired;

import          org.junit.jupiter.api.*;
import static   org.junit.jupiter.api.Assertions.assertTrue;

import static   com.openclassrooms.safetynet.dao.MapListComparison.*;

class JsonDAOTest extends DAOTest
{
    @Autowired
    JsonDAO dao;

    void validateDataIntegrity()
    {
        assertTrue( areListAndMapOfEntityEqual(data.getPersonsListTest(), collections.getPersonsMap()) );

        assertTrue( areListAndMapOfEntityEqual(data.getMedicalrecordListTest(), collections.getMedicalrecordsMap()) );

        assertTrue( areListAndMapOfListOfEntityEqual(data.getFirestationListTest(), collections.getFirestationsMap()) );
    }


    @Test
    @DisplayName("Test loadFromJson() method")
    void testLoadFromJson()
    {
        validateDataIntegrity();
    }


    @Test
    @DisplayName("Test saveToJson() method")
    void testSaveToJson() throws IOException
    {
        dao.saveToJson();
        dao.loadFromJson();
        validateDataIntegrity();
    }
}