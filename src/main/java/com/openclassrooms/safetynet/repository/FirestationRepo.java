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
    // =  Initialisation Attribute & Methods   =
    // =========================================

    private EntitiesCollections collections;

    public void setCollections(EntitiesCollections ec)
    {
        collections = ec;
    }


    // ===========================================
    // =  Getters for the different collections  =
    // ===========================================

    public Map<Integer, List<String>> getMap()
    {
        return collections.getFirestationsMap();
    }

    public List<Firestation> getList()
    {
        return collections.getFirestations();
    }
}