package com.openclassrooms.safetynet.controller;

import          com.fasterxml.jackson.databind.ObjectMapper;

import          org.springframework.test.context.TestPropertySource;
import          org.springframework.beans.factory.annotation.Autowired;
import          org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import          org.springframework.boot.test.context.SpringBootTest;
import          org.springframework.http.MediaType;
import          org.springframework.test.web.servlet.MockMvc;
import          org.springframework.test.web.servlet.RequestBuilder;
import          org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static   org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import          com.openclassrooms.safetynet.entity.Firestation;
import          com.openclassrooms.safetynet.service.FirestationService;

import          org.junit.jupiter.api.Test;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
class FirestationControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FirestationService service;

    private RequestBuilder request;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testCreateFirestationThatAlreadyExixst() throws Exception
    {
        Firestation firestation = new Firestation("1", "a1");

        request = MockMvcRequestBuilders.post("/firestation")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(firestation))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isAlreadyReported());
    }


    @Test
    void testCreateFirestation() throws Exception
    {
        Firestation firestation = new Firestation("3", "a9");

        request = MockMvcRequestBuilders.post("/firestation")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(firestation))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        // CLEAN
        service.removeFirestation(firestation);
    }

    @Test
    void testUpdateFirestationThatDoesNotExist() throws Exception
    {
        Firestation firestation = new Firestation("9", "a9");

        request = MockMvcRequestBuilders.put("/firestation")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(firestation))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());

    }

    @Test
    void testUpdateFirestation() throws Exception
    {
        Firestation firestation = new Firestation("3", "a1");

        request = MockMvcRequestBuilders.put("/firestation")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(firestation))
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        // CLEAN
        service.updateFirestation(new Firestation("1", "a1"));
    }

    @Test
    void deleteFirestationThatDoesNotExist() throws Exception
    {
        Firestation firestation = new Firestation("9", "a9");

        request = MockMvcRequestBuilders.delete("/firestation")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(firestation))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    void deleteFirestation() throws Exception
    {
        Firestation firestation = new Firestation("1", "a1");

        request = MockMvcRequestBuilders.delete("/firestation")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(firestation))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        // CLEAN
        service.addFirestation(firestation);
    }
}