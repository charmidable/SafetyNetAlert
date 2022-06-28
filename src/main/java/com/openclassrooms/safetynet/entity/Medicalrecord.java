package com.openclassrooms.safetynet.entity;

import java.util.List;
import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;


public record Medicalrecord(
                                String firstName,
                                String lastName,
                                @JsonFormat(pattern = "MM/dd/yyyy")
                                LocalDate birthdate,
                                List<String> allergies,
                                List<String> medications
                            )
{
    @Override
    public int hashCode()
    {
        int     result = firstName.hashCode();
                result = 31 * result + lastName.hashCode();
        return  result;
    }
}