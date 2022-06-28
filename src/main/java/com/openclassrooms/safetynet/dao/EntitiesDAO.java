package com.openclassrooms.safetynet.dao;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Value;

import com.openclassrooms.safetynet.repository.Repo;


public class EntitiesDAO
{
    @Value("${jsonpath}")
//    @Value("jsonpath")
    private static String       jsonDataSource;
//    private static File         file    = new File(jsonDataSource);
    private static File         file    = new File("src/main/resources/data.json");
    private static Repo   repo    = Repo.getInstance();
    private static ObjectMapper mapper  = new ObjectMapper().registerModule(new JavaTimeModule())
                                                            .enable(SerializationFeature.INDENT_OUTPUT);


    public static void loadJsonToRepo() throws IOException
    {
        System.out.println("jsonDataSource = " + jsonDataSource);
        repo = mapper.readValue(file, Repo.class);
    }


    public static void saveRepoToJson() throws IOException
    {
        mapper.writeValue(new File("src/main/resources/data3.json"), repo);
    }
}