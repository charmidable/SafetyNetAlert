package com.openclassrooms.safetynet.controller;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestGetPersons() throws Exception
    {
        mockMvc .perform(get("/persons"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Shawna")));
    }

    @Test
    public void TestGetPerson()
    {

    }
}