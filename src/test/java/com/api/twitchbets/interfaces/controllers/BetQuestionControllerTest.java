package com.api.twitchbets.interfaces.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
    void whenCreateBetQuestion_thenBetServiceCreatesBetQuestionAndHasCreatedStatus() throws Exception {
        String question = "Will @xQc stream today? (02/16/2025)";
        List<String> options = new ArrayList<>(Arrays.asList("Yes", "No"));
        LocalDateTime endTime = LocalDateTime.now();
        AddBetQuestionRequest addBetQuestionRequest = new AddBetQuestionRequest(question, options, endTime);
        UUID id = UUID.randomUUID();
        when(betService.createBetQuestion(question, options, endTime)).thenReturn(id);

        mvc.perform(MockMvcRequestBuilders.post("/bets/questions")
            .content(asJsonString(addBetQuestionRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .header("location", id)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(betService).createBetQuestion(any(), any(), any());
    }

    @Test
    void givenId_whenGetBetQuestion_thenBetServiceFetchBetQuestionAndMapperAssemblesResponse() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/bets/questions/{id}", UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(betService).getBetQuestion(any());
        verify(betQuestionResponseMapper).toResponse(any());
    }

    public static String asJsonString(final Object obj) {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}