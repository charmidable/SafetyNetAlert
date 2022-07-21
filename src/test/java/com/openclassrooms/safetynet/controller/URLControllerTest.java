package com.openclassrooms.safetynet.controller;

import          org.springframework.test.context.TestPropertySource;
import          org.springframework.beans.factory.annotation.Autowired;
import          org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import          org.springframework.boot.test.context.SpringBootTest;

import          org.springframework.test.web.servlet.MockMvc;
import static   org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static   org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import          org.junit.jupiter.api.Test;




@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"jsonpath=src/test/resources/data.json"})
class URLControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFirestation() throws Exception
    {
        mockMvc.perform(get("/firestation?stationNumber=1")).andExpect(status().isOk());

        mockMvc.perform(get("/firestation?stationNumber=9")).andExpect(status().isNotFound());
    }


    @Test
    void testChildAlert() throws Exception
    {
        mockMvc.perform(get("/childAlert?address=a33")).andExpect(status().isOk());

        mockMvc.perform(get("/childAlert?address=a9")).andExpect(status().isNotFound());
    }


    @Test
    void testFire() throws Exception
    {
        mockMvc.perform(get("/fire?address=a33")).andExpect(status().isOk());

        mockMvc.perform(get("/fire?address=a9")).andExpect(status().isNotFound());
    }


    @Test
    void testFlood() throws Exception
    {
        mockMvc.perform(get("/flood?aListOfStationNumber=1&aListOfStationNumber=3")).andExpect(status().isOk());

        mockMvc.perform(get("/flood?aListOfStationNumber=1&aListOfStationNumber=9")).andExpect(status().isNotFound());
    }


    @Test
    void testPersonInfo() throws Exception
    {
        mockMvc.perform(get("/personInfo?firstName=f1&lastName=l1")).andExpect(status().isOk());

        mockMvc.perform(get("/personInfo?firstName=f9&lastName=l9")).andExpect(status().isNotFound());
    }


    @Test
    void testPhoneAlert() throws Exception
    {
        mockMvc.perform(get("/phoneAlert?stationNumber=1")).andExpect(status().isOk());

        mockMvc.perform(get("/phoneAlert?stationNumber=9")).andExpect(status().isNotFound());
    }


    @Test
    void testCommunityEmail() throws Exception
    {
        mockMvc.perform(get("/communityEmail?city=c1")).andExpect(status().isOk());

        mockMvc.perform(get("/communityEmail?city=c9")).andExpect(status().isNotFound());
    }
}