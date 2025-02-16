package com.api.twitchbets.interfaces.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.twitchbets.application.BetService;

@SpringBootTest
@AutoConfigureMockMvc
public class BetQuestionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BetService betService;

    @Test
    void whenCreateBetQuestion_thenReturnCreatedStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/bet/question/create").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void whenCreateBetQuestion_thenBetServiceCreatesBetQuestion() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/bet/question/create").accept(MediaType.APPLICATION_JSON));

        verify(betService).createBetQuestion(any(), any());
    }
}