package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.dao.EntitiesCollections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirestationRepoTest
{

    @Autowired
    FirestationRepo repo;

    @Autowired
    EntitiesCollections collections;


    @Test
    void getMap()
    {
        assertTrue(repo.getMap() == collections.getFirestationsMap());
    }

    @Test
    void getList()
    {
        assertEquals(repo.getList(), collections.getFirestations());
    }
}