package com.openclassrooms.safetynet;

import java.io.IOException;

import com.openclassrooms.safetynet.entity.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.safetynet.dao.JsonDAO;
import com.openclassrooms.safetynet.dao.EntitiesCollections;
import com.openclassrooms.safetynet.repository.PersonRepo;
import com.openclassrooms.safetynet.repository.FirestationRepo;


@SpringBootApplication
public class SafetyNetApplication implements CommandLineRunner
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Autowired private JsonDAO dao;
    @Autowired private PersonRepo personRepo;
    @Autowired private FirestationRepo firestationRepo;
               private EntitiesCollections collections;

    // ======================================
    // =          Spring Methods            =
    // ======================================

    public static void main(String[] args)
    {
        SpringApplication.run(SafetyNetApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        init();
    }


    // ======================================
    // =           Tool Methods             =
    // ======================================

    private void init() throws IOException
    {
        collections = dao.loadFromJson();
        initRepo();
    }

    private void initRepo()
    {
        firestationRepo .setCollections(collections);
        personRepo      .setCollections(collections);
    }

    private void shutDown() throws Exception
    {
        dao.saveToJson(collections);
    }
}