package com.openclassrooms.safetynet.dao;

import java.io.IOException;
import java.io.File;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.openclassrooms.safetynet.repository.Repository;


public class EntitiesDAO
{
    @Value("jsonpath")
    private String jsonDataSource;
    private static File         file    = new File(jsonDataSource);
    private static Repository   repo    = Repository.getInstance();
    private static ObjectMapper mapper  = new ObjectMapper().registerModule(new JavaTimeModule());
                                                            .enable(SerializationFeature.INDENT_OUTPUT);


    public static void loadJsonToRepo() throws IOException
    {
        repo = mapper.readValue(file, Repository.class);
    }


    public static void saveRepoToJson() throws IOException
    {
        mapper.writeValue(new File("src/main/resources/data3.json"), repo);
    }
}