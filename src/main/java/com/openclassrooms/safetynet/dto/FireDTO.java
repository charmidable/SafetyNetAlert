package com.openclassrooms.safetynet.dto;

import com.openclassrooms.safetynet.entity.Person;

import java.util.List;

public record FireDTO(int stationNumber, String address, List<Person> people)
{

}
