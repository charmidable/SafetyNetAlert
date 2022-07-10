package com.openclassrooms.safetynet.repository;

import java.util.List;
import java.util.Map;

import com.openclassrooms.safetynet.dao.EntitiesCollections;
import com.openclassrooms.safetynet.entity.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FirestationRepo
{

    // =========================================
    // =  Initialisation Attribute & Methods   =
    // =========================================

    private final EntitiesCollections collections;

    public FirestationRepo(EntitiesCollections collections)
    {
        this.collections = collections;
    }

//    public void setCollections(EntitiesCollections ec)
//    {
//        collections = ec;
//    }


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