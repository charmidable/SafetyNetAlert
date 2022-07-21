package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.dao.EntitiesCollections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import        org.junit.jupiter.api.Test;


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