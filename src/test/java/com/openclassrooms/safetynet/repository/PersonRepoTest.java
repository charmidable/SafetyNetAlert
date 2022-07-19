package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.dao.EntitiesCollections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepoTest
{
    @Autowired
    PersonRepo repo;

    @Autowired
    EntitiesCollections collections;

    @Test
    void testGetMap()
    {
        assertTrue(repo.getMap() == collections.getPersonsMap());
    }

    @Test
    void testGetList()
    {
        assertEquals(repo.getList(), collections.getPersons());
    }
}