package com.api.twitchbets.interfaces.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.api.twitchbets.interfaces.dto.requests.AddBetQuestionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BetQuestionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BetService betService;

    @Test
    void whenCreateBetQuestion_thenReturnCreatedStatus() throws Exception {
        String question = "Will @xQc stream today? (02/16/2025)";
        List<String> options = new ArrayList<>(Arrays.asList("Yes", "No"));
        AddBetQuestionRequest addBetQuestionRequest = new AddBetQuestionRequest(question, options);

        mvc.perform(MockMvcRequestBuilders.post("/bet/question/create")
                .content(asJsonString(addBetQuestionRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void whenCreateBetQuestion_thenBetServiceCreatesBetQuestion() throws Exception {
        String question = "Will @xQc stream today? (02/16/2025)";
        List<String> options = new ArrayList<>(Arrays.asList("Yes", "No"));
        AddBetQuestionRequest addBetQuestionRequest = new AddBetQuestionRequest(question, options);

        mvc.perform(MockMvcRequestBuilders.post("/bet/question/create")
            .content(asJsonString(addBetQuestionRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        verify(betService).createBetQuestion(any(), any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}