package com.openclassrooms.safetynet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
public abstract class DAOTest
{
    @Autowired
    EntitiesCollections collections;

    protected Data data = new Data();
}