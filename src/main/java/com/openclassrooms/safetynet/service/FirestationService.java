package com.openclassrooms.safetynet.service;

import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepo;


@Component
public class FirestationService
{

    // ======================================
    // =             Attributes             =
    // ======================================

    private final FirestationRepo  firestationRepo;


    // ======================================
    // =            Constructors            =
    // ======================================

    public FirestationService(FirestationRepo firestationRepo)
    {
        this.firestationRepo = firestationRepo;
    }


    // ======================================
    // =          Service  Methods          =
    // ======================================

    public int getFirestationNumberByAdress(String address)
    {
        return firestationRepo.getList().stream()
                                        .filter(station -> station.address().equalsIgnoreCase(address))
                                        .map(Firestation::station)
                                        .findFirst()
                                        .get();
    }


    public Map<Integer, List<Firestation>> getMap()
    {
        return firestationRepo.getMap();
    }


    public List<String> getAdressesCoveredByTheFireStation(int stationNumber)
    {
        return firestationRepo.getMap().get(stationNumber).stream().map(Firestation::address).toList();
    }


    public void addFirestation(Firestation firestation)
    {
        firestationRepo.getList().add(firestation);
    }


    public void updateFirestation(Firestation firestation)
    {
        Optional<Firestation> toDelete = firestationRepo.getList().stream().filter(f -> f.address() == firestation.address()).findFirst();

        if(toDelete.isPresent()) firestationRepo.getList().remove(toDelete);

        firestationRepo.getList().add(firestation);
    }


    public void removeFirestation(Firestation firestation)
    {
        firestationRepo.getList().remove(firestation);
    }

}