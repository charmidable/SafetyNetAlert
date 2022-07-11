package com.openclassrooms.safetynet.repository;

import java.util.List;
import java.util.Map;

import com.openclassrooms.safetynet.dao.EntitiesCollections;
import com.openclassrooms.safetynet.entity.Firestation;
import org.springframework.stereotype.Component;


@Component
public class FirestationRepo
{
    // =========================================
    // =              Attribute                =
    // =========================================

    private final  EntitiesCollections collections;


    // ======================================
    // =            Constructors            =
    // ======================================

    private FirestationRepo(EntitiesCollections collections)
    {
        this.collections = collections;
    }


    // ===========================================
    // =  Getters for the different collections  =
    // ===========================================

    public Map<Integer, List<Firestation>> getMap()
    {
        return collections.getFirestationsMap();
    }

    public List<Firestation> getList()
    {
        return collections.getFirestations();
    }
}