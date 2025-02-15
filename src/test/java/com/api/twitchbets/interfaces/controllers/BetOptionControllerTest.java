package com.api.twitchbets.interfaces.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class BetOptionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void whenCreateBetOption_thenReturnOkStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/bet/option/create").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}