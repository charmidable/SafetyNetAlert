package com.openclassrooms.safetynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static   com.openclassrooms.safetynet.dao.EntitiesDAO.*;
import          com.openclassrooms.safetynet.repository.Repo;

import java.io.IOException;

@SpringBootApplication
public class SafetyNetApplication implements CommandLineRunner
{

    public static void main(String[] args)
    {
        SpringApplication.run(SafetyNetApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        try
        {
            loadJsonToRepo();
            Thread.sleep(1000);

//            System.out.println(Repository.getInstance().getFirestations());
            System.out.println(Repo.getInstance().getPersons());
//
////             new URLService().getPeopleByStationGroupingByAdress( 1, 4);
//
//            System.out.println(new RepositoryService().getAdressesCoveredByTheFireStation(4));
//            System.out.println(new RepositoryService().getFamilyHouseWithChild());
//
////            System.out.println(new RepositoryService().getPeopleByStationNumber(1));
//
////            System.out.println("Person.repo == null : " + Person.repo == null);
////            System.out.println(Person.repo);
////            System.out.println(Person.repo.getClass());
////
////            System.out.println(Person.repo.getPerson(1));
////            System.out.println(Person.repo.getMedicalRecord(1));
////            System.out.println(Person.repo.getFireStation(1));
                saveRepoToJson();
        }
        catch (IOException | InterruptedException e)
        {
            System.out.println("E X C E P T I O N");
            e.printStackTrace();
        }
    }
}
