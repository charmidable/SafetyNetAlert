package com.openclassrooms.safetynet.controller;

import          java.time.LocalDate;
import          java.util.Arrays;

import          com.fasterxml.jackson.databind.ObjectMapper;
import          com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static   org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import          org.springframework.test.web.servlet.MockMvc;
import          org.springframework.test.web.servlet.RequestBuilder;
import          org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import          org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import          org.springframework.boot.test.context.SpringBootTest;
import          org.springframework.test.context.TestPropertySource;
import          org.springframework.beans.factory.annotation.Autowired;
import          org.springframework.http.MediaType;

import          org.junit.jupiter.api.Test;

import          com.openclassrooms.safetynet.entity.Medicalrecord;
import          com.openclassrooms.safetynet.entity.Person;
import          com.openclassrooms.safetynet.service.PersonService;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
class MedicalrecordControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService service;

    private RequestBuilder request;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    void testCreateMedicalrecordThatAllReadyExist() throws Exception
    {
        Medicalrecord medicalrecord = new Medicalrecord( new Person("f1", "l1"));

        request = MockMvcRequestBuilders.post("/medicalrecord")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(medicalrecord))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isAlreadyReported());
    }


    @Test
    void testUpdateMedicalrecordThatDoesNotExist() throws Exception
    {
        // BUILD
        Person person = new Person("xx", "yy");
        service.addPerson(person);
        Medicalrecord medicalrecord = new Medicalrecord(person);

        // TEST
        request = MockMvcRequestBuilders.put("/medicalrecord").accept(MediaType.APPLICATION_JSON)
                                                                        .content(mapper.writeValueAsString(medicalrecord))
                                                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());

        // CLEAN
        service.removePerson(person);
    }


    @Test
    void testDeleteMedicalrecordThatDoesNotExist() throws Exception
    {
        // BUILD
        Person person = new Person("xx", "yy");
        service.addPerson(person);
        Medicalrecord medicalrecord = new Medicalrecord(person);

        // TEST
        request = MockMvcRequestBuilders.delete("/medicalrecord").accept(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(medicalrecord))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());

        // CLEAN
        service.removePerson(person);
    }


    @Test
    void testCreateMedicalrecord() throws Exception
    {
        Person person = new Person("xx", "yy");
        service.addPerson(person);
        Medicalrecord medicalrecord = new Medicalrecord(person);

        request = MockMvcRequestBuilders.post("/medicalrecord")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(medicalrecord))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        // CLEAN
        service.removePerson(person);
    }


    @Test
    void testUpdateMedicalrecord() throws Exception
    {
        Person person = new Person("f4", "l4");

        // BUILD
        service.addPerson(person);
        person.getMedicalrecord().setBirthdate(LocalDate.now());
        // TESTE

        Medicalrecord medicalrecord = new Medicalrecord(person);
        medicalrecord.setBirthdate(LocalDate.now());
        medicalrecord.setMedications(Arrays.asList("chloroquine"));
        medicalrecord.setAllergies(Arrays.asList("lactose"));

        request = MockMvcRequestBuilders.put("/medicalrecord").accept(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(medicalrecord))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        // CLEAN
        service.removePerson(person);
    }


    @Test
    void testDeleteMedicalrecord() throws Exception
    {
        Person person = new Person("f4", "l4");

        // BUILD
        service.addPerson(person);
        person.getMedicalrecord().setBirthdate(LocalDate.now());

        // TEST
        request = MockMvcRequestBuilders.delete("/medicalrecord").accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person.getMedicalrecord()))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        // CLEAN
        service.removePerson(person);
    }
}