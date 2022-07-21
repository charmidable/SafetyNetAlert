package com.openclassrooms.safetynet.controller;

import          java.util.HashSet;

import          com.fasterxml.jackson.databind.ObjectMapper;
import          com.fasterxml.jackson.core.JsonProcessingException;
import          com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import          com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import          org.springframework.beans.factory.annotation.Autowired;
import          org.springframework.beans.factory.annotation.Value;
import          org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import          org.springframework.http.MediaType;
import          org.springframework.boot.test.context.SpringBootTest;
import          org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import          org.springframework.test.web.servlet.MockMvc;
import          org.springframework.test.web.servlet.RequestBuilder;
import          org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static   org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import          org.springframework.test.context.TestPropertySource;

import          org.junit.jupiter.api.Test;

import          com.openclassrooms.safetynet.service.PersonService;
import          com.openclassrooms.safetynet.entity.Person;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
class PersonControllerTest
{
    @Value("#{'${keepAll_person_filter}'.split(';')}")
    private HashSet<String> keepAllFields;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService service;

    private RequestBuilder request;

    private SimpleFilterProvider provider = new SimpleFilterProvider().setFailOnUnknownId(false);
    private ObjectMapper         mapper   = Jackson2ObjectMapperBuilder.json().filters(provider).build();

    private String mapping(Person person, HashSet<String> fieldsToKeep) throws JsonProcessingException
    {
        provider.setDefaultFilter(SimpleBeanPropertyFilter.filterOutAllExcept(fieldsToKeep));
        return mapper.writeValueAsString(person);
    }


    @Test
    void testCreatePersonThatAllReadyExist() throws Exception
    {
        // BUILD
        Person person = new Person("f1", "l1");
        person.setAddress("aaa");
        person.setEmail("eee");
        person.setPhone("ppp");
        person.setZip("666");
        person.setCity("ccc");

        // TEST
        request = MockMvcRequestBuilders.post("/person")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(mapping(person, keepAllFields))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isAlreadyReported());
    }


    @Test
    void testUpdatePersonThatDoesNotExist() throws Exception
    {
        // BUILD
        Person person = new Person("xx", "yy");
        person.setAddress("aaa");
        person.setEmail("eee");
        person.setPhone("ppp");
        person.setZip("666");
        person.setCity("ccc");

        // TEST
        request = MockMvcRequestBuilders.put("/person").accept(MediaType.APPLICATION_JSON)
                                                                 .content(mapping(person, keepAllFields))
                                                                 .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }


    @Test
    void testDeletePersonThatDoesNotExist() throws Exception
    {
        // BUILD
        Person person = new Person("xx", "yy");
        person.setAddress("aaa");
        person.setEmail("eee");
        person.setPhone("ppp");
        person.setZip("666");
        person.setCity("ccc");

        // TEST
        request = MockMvcRequestBuilders.delete("/person").accept(MediaType.APPLICATION_JSON)
                                                                    .content(mapping(person, keepAllFields))
                                                                    .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }


    @Test
    void testCreatePerson() throws Exception
    {
        // BUILD
        Person person = new Person("f4", "l4");
        person.setAddress("aaa");
        person.setEmail("eee");
        person.setPhone("ppp");
        person.setZip("666");
        person.setCity("ccc");

        // TEST
        request = MockMvcRequestBuilders.post("/person")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(mapping(person, keepAllFields))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        //CLEAN
        service.removePerson(person);
    }


    @Test
    void testUpdatePerson() throws Exception
    {
        // BUILD
        Person person = new Person("f4", "l4");
        service.addPerson(person);
        person.setAddress("aaa");
        person.setEmail("eee");
        person.setPhone("ppp");
        person.setZip("666");
        person.setCity("ccc");

        // TEST
        request = MockMvcRequestBuilders.put("/person").accept(MediaType.APPLICATION_JSON)
                                                                 .content(mapping(person, keepAllFields))
                                                                 .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        // CLEAN
        service.removePerson(person);
    }


    @Test
    void testDeletePerson() throws Exception
    {
        // BUILD
        Person person = new Person("f4", "l4");
        service.addPerson(person);

        // TESTE
        request = MockMvcRequestBuilders.delete("/person").accept(MediaType.APPLICATION_JSON)
                                                                    .content(mapping(person, keepAllFields))
                                                                    .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }
}