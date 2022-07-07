package com.openclassrooms.safetynet.dao;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


@Component
public class JsonDAO
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Value("#{'${jsonpath}'}")
    private File file;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                                    .enable(SerializationFeature.INDENT_OUTPUT)
                                                    .setFilterProvider(getPersonFilter());


    // ======================================
    // =            DAO Methods             =
    // ======================================

    public EntitiesCollections loadFromJson() throws IOException
    {
        EntitiesCollections collections = mapper.readValue(file, EntitiesCollections.class);
        collections .getPersons()
                    .stream()
                    .forEach(person -> person.setMedicalrecord(collections.getMedicalrecordsMap()
                                                                          .get(person.hashCode())
                                                              )
                            );

        return collections;
    }


    public void saveToJson(EntitiesCollections collections) throws IOException
    {
        mapper.writeValue(file, collections);
    }


    // ======================================
    // =        Private Tool Methods        =
    // ======================================

    private FilterProvider getPersonFilter()
    {
        return new SimpleFilterProvider().addFilter("person_filter", SimpleBeanPropertyFilter.filterOutAllExcept("firstName;lastName;address;city;phone;email;zip".split(";")));
    }
}