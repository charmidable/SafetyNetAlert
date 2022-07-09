package com.openclassrooms.safetynet;

import java.io.IOException;

import com.openclassrooms.safetynet.entity.Person;
import com.openclassrooms.safetynet.service.PersonService;
import com.openclassrooms.safetynet.service.URLService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.safetynet.dao.JsonDAO;
import com.openclassrooms.safetynet.dao.EntitiesCollections;
import com.openclassrooms.safetynet.repository.PersonRepo;
import com.openclassrooms.safetynet.repository.FirestationRepo;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class SafetyNetApplication implements CommandLineRunner
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @Autowired private JsonDAO              dao;
    @Autowired private PersonRepo           personRepo;
    @Autowired private FirestationRepo      firestationRepo;
    @Autowired private URLService           urlService;
    @Autowired private PersonService        personService;
               private EntitiesCollections  collections;

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
        System.out.println(urlService.flood(1,4));
//        urlService.flood(1,4);
//        System.out.println(personService.getHashMapPersonsByAdress("947 E. Rose Dr"));


//        System.out.println(dao.filter);
//        System.out.println("loadAndSave : " + dao.loadAndSave);
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