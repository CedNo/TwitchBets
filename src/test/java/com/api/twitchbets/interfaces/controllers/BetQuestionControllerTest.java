package com.api.twitchbets.interfaces.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.twitchbets.application.BetService;
import com.api.twitchbets.interfaces.dto.requests.AddBetQuestionRequest;
import com.api.twitchbets.interfaces.mappers.responses.BetQuestionResponseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BetQuestionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BetService betService;
    @MockitoBean
    private BetQuestionResponseMapper betQuestionResponseMapper;

    @Test
    void whenCreateBetQuestion_thenReturnCreatedStatus() throws Exception {
        String question = "Will @xQc stream today? (02/16/2025)";
        List<String> options = new ArrayList<>(Arrays.asList("Yes", "No"));
        AddBetQuestionRequest addBetQuestionRequest = new AddBetQuestionRequest(question, options);

        mvc.perform(MockMvcRequestBuilders.post("/bets/questions")
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

        mvc.perform(MockMvcRequestBuilders.post("/bets/questions")
            .content(asJsonString(addBetQuestionRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        verify(betService).createBetQuestion(any(), any());
    }

    @Test
    void givenId_whenGetBetQuestion_thenBetServiceFetchBetQuestionAndMapperAssemblesResponse() throws Exception {
        when(betService.getBetQuestion(any())).thenReturn(mock());

        mvc.perform(MockMvcRequestBuilders.get("/bets/questions/{id}", UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isFound());

        verify(betService).getBetQuestion(any());
        verify(betQuestionResponseMapper).toResponse(any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}