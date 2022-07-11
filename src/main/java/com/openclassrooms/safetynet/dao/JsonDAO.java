package com.openclassrooms.safetynet.dao;

import java.io.File;
import java.util.HashSet;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;


@Component
class JsonDAO
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Value("#{'${jsonpath}'}")
    private File file;

    @Value("#{'${loadAndSave}'.split(';')}")
    private HashSet<String> personFieldsToKeep;

    private ObjectMapper mapper;

    // ======================================
    // =            DAO Methods             =
    // ======================================

    @Bean
    private EntitiesCollections loadFromJson() throws IOException
    {
        mapper = new ObjectMapper() .registerModule(new JavaTimeModule())
                                    .enable(SerializationFeature.INDENT_OUTPUT)
                                    .setFilterProvider(getPersonFilter(personFieldsToKeep));

        EntitiesCollections collections = mapper.readValue(file, EntitiesCollections.class);

        collections .getPersons()
                    .forEach(
                                person -> person.setMedicalrecord(
                                                                    collections.getMedicalrecordsMap()
                                                                               .get(person.hashCode())
                                                                 )
                            );

        return collections;
    }


    private void saveToJson(EntitiesCollections collections) throws IOException
    {
        mapper.writeValue(file, collections);
    }


    // ======================================
    // =        Private Tool Methods        =
    // ======================================


    private FilterProvider getPersonFilter(HashSet<String> _personFieldsToKeep)
    {
        return new SimpleFilterProvider().addFilter("person_filter", SimpleBeanPropertyFilter.filterOutAllExcept(_personFieldsToKeep));
    }
}