package com.openclassrooms.safetynet.service;

import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.safetynet.entity.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepo;


@Component
public class FirestationService
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Autowired
    FirestationRepo firestationRepo;


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


    public Map<Integer, List<String>> getMap()
    {
        return firestationRepo.getMap();
    }


    public List<String> getAdressesCoveredByTheFireStation(int stationNumber)
    {
        return firestationRepo.getMap().get(stationNumber);
    }
}





//    public List<String> getAdressesCoveredByTheFireStations(Integer... fireStationNumber)
//    {
//        List<List<String>> adresseListList = new ArrayList<>();
//
//        Arrays.stream(fireStationNumber).forEach(i -> adresseListList.add(getAdressesCoveredByTheFireStation(i)));
//
//        return  adresseListList.stream().flatMap(Collection::stream).toList();
//    }
//
//
//    public List<Integer> getAllFirestationNumber()
//    {
//        return  firestationRepo.getList()
//                .stream()
//                .flatMap(station -> Stream.of(station.station()))
//                .distinct()
//                .toList();
//    }
//
//    public List<Firestation> getFirestationsList()
//    {
//        return firestationRepo.getList();
//    }