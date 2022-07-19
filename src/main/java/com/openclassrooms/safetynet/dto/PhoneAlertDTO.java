package com.openclassrooms.safetynet.dto;

import java.util.List;

public record PhoneAlertDTO(int stationNumber, List<String> phoneList)
{
}
