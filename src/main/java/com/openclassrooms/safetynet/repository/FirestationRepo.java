package com.openclassrooms.safetynet.repository;

import java.util.Map;
import java.util.List;

import com.openclassrooms.safetynet.dao.EntitiesCollections;
import com.openclassrooms.safetynet.entity.Firestation;
import org.springframework.stereotype.Component;


@Component
public class FirestationRepo
{
    // ============================================
    // =                Attribute                 =
    // ============================================

    private final  EntitiesCollections collections;

    // =============================================
    // =               Constructors                =
    // =============================================

    public FirestationRepo(EntitiesCollections _collections)
    {
        this.collections = _collections;
    }

    // =============================================
    // = Different getters for the same collection =
    // =============================================

    public Map<Integer, List<Firestation>> getMap()
    {
        return collections.getFirestationsMap();
    }

    public List<Firestation> getList()
    {
        return collections.getFirestations();
    }
}