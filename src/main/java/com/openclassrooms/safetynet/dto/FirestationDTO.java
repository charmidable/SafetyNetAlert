package com.openclassrooms.safetynet.dto;

import java.util.List;

import com.openclassrooms.safetynet.entity.Person;



public record FirestationDTO(String stationNumber, long numberOfChilds, long numberOfAdults, List<Person> people)
{

}