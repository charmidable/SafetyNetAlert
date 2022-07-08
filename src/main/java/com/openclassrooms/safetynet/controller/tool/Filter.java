package com.openclassrooms.safetynet.controller.tool;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

public class Filter
{
    public static FilterProvider keepAllFieldsFilterProvider= getPersonFilterProvider("firstName","lastName","address","zip", "city","phone","email" );
    public static FilterProvider firestationFilterProvider  = getPersonFilterProvider("firstName","lastName","address","phone" );
    public static FilterProvider childAlertFilterProvider   = getPersonFilterProvider("firstName","lastName","age" );
    public static FilterProvider personInfoFilterProvider   = getPersonFilterProvider("firstName","lastName","address","age","email","allergies","medications");
    public static FilterProvider phoneAlertFilterProvider   = getPersonFilterProvider("phone" );
    public static FilterProvider floodFilterProvider        = getPersonFilterProvider("firstName","lastName","age","phone","allergies","medications");
    public static FilterProvider fireFilterProvider         = getPersonFilterProvider("firstName","lastName","adress","phone","allergies","medications");

    public static SimpleFilterProvider getPersonFilterProvider(String... fieldsToKeep)
    {
        return  new SimpleFilterProvider().addFilter("person_filter", SimpleBeanPropertyFilter.filterOutAllExcept(fieldsToKeep));
    }
}