package com.openclassrooms.safetynet.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.dao.EntitiesCollections;
import com.openclassrooms.safetynet.entity.Person;


@Component
public class PersonRepo
{

    // =========================================
    // =  Initialisation Attribute & Methods   =
    // =========================================

    private EntitiesCollections collections;

    public void setCollections(EntitiesCollections _collections)
    {
        collections = _collections;
    }


    // ===========================================
    // =  Getters for the different collections  =
    // ===========================================

    public Map<Integer, Person> getMap()
    {
        return collections.getPersonsMap();
    }

    public List<Person> getList()
    {
        return collections.getPersons();
    }
}