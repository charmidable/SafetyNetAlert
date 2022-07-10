package com.openclassrooms.safetynet.dao;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JsonDAO
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Value("#{'${jsonpath}'}")
    private File file;

    @Value("#{'${loadAndSave}'.split(';')}")
    private HashSet<String> personFieldsToKeep;


    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                                    .enable(SerializationFeature.INDENT_OUTPUT)
                                                    .setFilterProvider(getPersonFilter());


    // ======================================
    // =            DAO Methods             =
    // ======================================

    @Bean
    public EntitiesCollections loadFromJson() throws IOException
    {
        log.info("loadFromJson() method called from JsonDAO ");

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


    public void saveToJson(EntitiesCollections collections) throws IOException
    {
        log.info("saveToJson() method called from JsonDAO ");
        mapper.writeValue(file, collections);
    }


    // ======================================
    // =        Private Tool Methods        =
    // ======================================

    private FilterProvider getPersonFilter()
    {
        log.info("getPersonFilter() method called from JsonDAO ");
        return new SimpleFilterProvider().addFilter("person_filter", SimpleBeanPropertyFilter.filterOutAllExcept(personFieldsToKeep));
    }
}