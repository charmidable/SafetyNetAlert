package com.openclassrooms.safetynet.dao;

import java.io.File;
import java.util.HashSet;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;


@Component
public  class JsonDAO
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Value("#{'${loadAndSave}'.split(';')}")
    private HashSet<String> personFieldsToKeep;

    @Value("#{'${jsonpath}'}")
    private File file;

    private EntitiesCollections collections;
    private ObjectMapper        mapper;


    // ======================================
    // =            DAO Methods             =
    // ======================================

    @Bean
    public EntitiesCollections loadFromJson() throws IOException
    {
        initMapper(personFieldsToKeep);
        return collections = mapper.readValue(file, EntitiesCollections.class);
    }


    public void saveToJson() throws IOException
    {
        mapper.writeValue(file, collections);
    }

    private void initMapper(HashSet<String> fields)
    {
        mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                   .enable(SerializationFeature.INDENT_OUTPUT)
                                   .setFilterProvider(new SimpleFilterProvider().addFilter("person_filter", SimpleBeanPropertyFilter.filterOutAllExcept(personFieldsToKeep)));
    }
}