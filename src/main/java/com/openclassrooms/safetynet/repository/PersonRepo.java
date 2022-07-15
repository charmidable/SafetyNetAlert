package com.openclassrooms.safetynet.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.dao.EntitiesCollections;
import com.openclassrooms.safetynet.entity.Person;


@Component
public class PersonRepo
{
    // ============================================
    // =                Attribute                 =
    // ============================================

    private final EntitiesCollections collections;


    // =============================================
    // =               Constructors                =
    // =============================================

    public PersonRepo(EntitiesCollections collections)
    {
        this.collections = collections;
    }


    // =============================================
    // = Different getters for the same collection =
    // =============================================

    public Map<Integer, Person> getMap()
    {
        return collections.getPersonsMap();
    }

    public List<Person> getList()
    {
        return collections.getPersons();
    }
}