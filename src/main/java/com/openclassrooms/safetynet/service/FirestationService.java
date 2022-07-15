package com.openclassrooms.safetynet.service;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.Map;

import com.openclassrooms.safetynet.Exception.EntityAlreadyExistException;
import com.openclassrooms.safetynet.Exception.EntityDoesNotExistException;
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

    public Map<Integer, List<Firestation>> getMap()
    {
        return firestationRepo.getMap();
    }


    public List<Integer> getNumberStationList()
    {
        return firestationRepo.getList().stream()
                .map(Firestation::station)
                .distinct()
                .toList();
    }


    public Optional<Firestation> getFirestationByAdress(String address)
    {
        return firestationRepo.getList()
                              .stream()
                              .filter(station -> station.address().equalsIgnoreCase(address))
                              .findFirst();
    }


    public int getFirestationNumberByAdress(String address)
    {
        try
        {
            return getFirestationByAdress(address).get().station();
        }
        catch(NoSuchElementException exception)
        {
            throw new EntityDoesNotExistException("No firestation cover the address  \"" + address + "\"" );
        }
    }


    public boolean isNumberStationExist(int stationNumber)
    {
        return getNumberStationList().contains(stationNumber);
    }


    public List<String> getAdressesCoveredByTheFireStation(int stationNumber)
    {
        try
        {
            return firestationRepo.getMap().get(stationNumber).stream().map(Firestation::address).toList();
        }
        catch (NullPointerException e)
        {
            throw new EntityDoesNotExistException("Firestation number " +   stationNumber + " does not exist");
        }
    }


    public void addFirestation(Firestation newFirestation)
    {
        Optional<Firestation> firestation = getFirestationByAdress(newFirestation.address());

        if(firestation.isPresent())
        {
            throw new EntityAlreadyExistException("The address " + newFirestation.address() + " is already covered by the station number" +  firestation.get().station());
        }

        if(firestationRepo.getMap().get(newFirestation.station()) == null)
        {
            ArrayList<Firestation> stationList = new ArrayList<Firestation>();
            stationList.add(newFirestation);
            firestationRepo.getMap().put(newFirestation.station(), stationList);
        }
        else
        {
            firestationRepo.getMap().get(newFirestation.station()).add(newFirestation);
        }
    }


    public void updateFirestation(Firestation firestation)
    {
        if(!firestationRepo.getMap().containsKey(firestation.station()))
        {
            throw new EntityDoesNotExistException("Firestation number " +   firestation.station() + " does not exist");
        }

        avoidDoublon(firestation);

        firestationRepo.getMap().get(firestation.station()).add(firestation);
    }


    public void removeFirestation(Firestation firestation)
    {
        if(!firestationRepo.getMap().get(firestation.station()).remove(firestation))
        {
            throw new EntityDoesNotExistException("Firestation number " +   firestation.station() + " does not exist");
        }
    }


    public void avoidDoublon(Firestation firestation)
    {
        Optional<Firestation> firestationToDelete = firestationRepo.getList().stream().filter(f -> f.address().equalsIgnoreCase(firestation.address())).findFirst();

        if(firestationToDelete.isPresent()) firestationRepo.getMap().get(firestationToDelete.get().station()).remove(firestationToDelete.get());
    }


    public List<Firestation> getList()
    {
        return firestationRepo.getList();
    }
}