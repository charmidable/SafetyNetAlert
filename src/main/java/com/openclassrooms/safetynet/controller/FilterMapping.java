package com.openclassrooms.safetynet.controller;

import java.util.HashSet;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import org.springframework.http.converter.json.MappingJacksonValue;


class FilterMapping
{
    private final SimpleFilterProvider  simpleFilterProvider = new SimpleFilterProvider().addFilter("person_filter", null);

    private final  MappingJacksonValue  mappingJacksonValue  = new MappingJacksonValue(null);

    private final static FilterMapping  filterMapping        = new FilterMapping();

    {
        mappingJacksonValue.setFilters(simpleFilterProvider);
    }

    static MappingJacksonValue mapping(Object dto, HashSet<String> fieldsToKeep)
    {
        filterMapping.mappingJacksonValue .setValue(dto);
        filterMapping.simpleFilterProvider.setDefaultFilter(SimpleBeanPropertyFilter.filterOutAllExcept(fieldsToKeep));
        return filterMapping.mappingJacksonValue;
    }
}