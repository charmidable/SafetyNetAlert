package com.openclassrooms.safetynet.dto;

import com.openclassrooms.safetynet.entity.Person;

import java.util.List;

public record ChildAlertDTO(String address, List<Person> children, List<Person> adults)
{
}
