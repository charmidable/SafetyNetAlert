package com.openclassrooms.safetynet.controller.tool;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import org.springframework.http.converter.json.MappingJacksonValue;

public class FilterMapping
{
    private  static MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(null);

    public static FilterProvider keepAllFieldsFilterProvider= getPersonFilterProvider("firstName","lastName","address","zip","city","phone","email");
    public static FilterProvider firestationFilterProvider  = getPersonFilterProvider("firstName","lastName","address","phone");
    public static FilterProvider childAlertFilterProvider   = getPersonFilterProvider("firstName","lastName","age");
    public static FilterProvider personInfoFilterProvider   = getPersonFilterProvider("firstName","lastName","address","age","email","allergies","medications");
    public static FilterProvider floodFilterProvider        = getPersonFilterProvider("firstName","lastName","age","phone","allergies","medications");
    public static FilterProvider fireFilterProvider         = getPersonFilterProvider("firstName","lastName","adress","phone","allergies","medications");

    public static SimpleFilterProvider getPersonFilterProvider(String... fieldsToKeep)
    {
        return  new SimpleFilterProvider().addFilter("person_filter", SimpleBeanPropertyFilter.filterOutAllExcept(fieldsToKeep));
    }

    public static MappingJacksonValue keepAllFieldsMapping(Object object)
    {
        mappingJacksonValue.setValue(object);
        mappingJacksonValue.setFilters(keepAllFieldsFilterProvider);
        return mappingJacksonValue;
    }

    public static MappingJacksonValue firestationMapping(Object object)
    {
        mappingJacksonValue.setValue(object);
        mappingJacksonValue.setFilters(firestationFilterProvider);
        return mappingJacksonValue;
    }

    public static MappingJacksonValue childAlertMapping(Object object)
    {
        mappingJacksonValue.setValue(object);
        mappingJacksonValue.setFilters(childAlertFilterProvider);
        return mappingJacksonValue;
    }

    public static MappingJacksonValue personInfoMapping(Object object)
    {
        mappingJacksonValue.setValue(object);
        mappingJacksonValue.setFilters(personInfoFilterProvider);
        return mappingJacksonValue;
    }

    public static MappingJacksonValue floodMapping(Object object)
    {
        mappingJacksonValue.setValue(object);
        mappingJacksonValue.setFilters(floodFilterProvider);
        return mappingJacksonValue;
    }

    public static MappingJacksonValue fireMapping(Object object)
    {
        mappingJacksonValue.setValue(object);
        mappingJacksonValue.setFilters(fireFilterProvider);
        return mappingJacksonValue;
    }
}