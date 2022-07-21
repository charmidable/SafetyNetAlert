package com.openclassrooms.safetynet.dto;

import java.util.List;

public record PhoneAlertDTO(String stationNumber, List<String> phoneList)
{
}
