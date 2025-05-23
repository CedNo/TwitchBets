package com.api.twitchbets.interfaces.controllers;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
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
import com.api.twitchbets.application.UserService;
import com.api.twitchbets.domain.bet.BetQuestionRepository;
import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;
import com.api.twitchbets.domain.exceptions.UserNotFoundException;
import com.api.twitchbets.domain.user.UserRepository;
import com.api.twitchbets.interfaces.dto.requests.AddBetRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BetControllerTest {

    @MockitoBean
    private UserService userService;
    @MockitoBean
    private BetService betService;
    @MockitoBean
    private BetQuestionRepository betQuestionRepository;
    @MockitoBean
    private UserRepository userRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    void givenValidParameters_whenCreateBet_thenCreateBetAndReturnCreatedStatus() throws Exception {
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        UUID VALID_BET_QUESTION_ID = UUID.randomUUID();
        String VALID_USERNAME = "username";
        float VALID_AMOUNT = 10;
        AddBetRequest addBetRequest = new AddBetRequest(VALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);

        mvc.perform(MockMvcRequestBuilders.post("/bets")
                .content(asJsonString(addBetRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(userService).checkIfCanPlaceBet(VALID_USERNAME, VALID_AMOUNT);
        verify(betService).checkIfCanPlaceBet(VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        verify(userService).chargeUser(VALID_USERNAME, VALID_AMOUNT);
        verify(betService).createBet(VALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
    }

    @Test
    void givenInvalidUsername_whenCreateBet_thenReturnNotFound() throws Exception {
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        UUID VALID_BET_QUESTION_ID = UUID.randomUUID();
        String INVALID_USERNAME = "invalid_username";
        float VALID_AMOUNT = 10;
        AddBetRequest addBetRequest = new AddBetRequest(INVALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        doThrow(new UserNotFoundException(INVALID_USERNAME)).when(userService).checkIfCanPlaceBet(INVALID_USERNAME, VALID_AMOUNT);

        mvc.perform(MockMvcRequestBuilders.post("/bets")
                .content(asJsonString(addBetRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(userService).checkIfCanPlaceBet(INVALID_USERNAME, VALID_AMOUNT);
        verify(betService, never()).checkIfCanPlaceBet(VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        verify(userService, never()).chargeUser(INVALID_USERNAME, VALID_AMOUNT);
        verify(betService, never()).createBet(INVALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
    }

    @Test
    void givenInvalidBetQuestionId_whenCreateBet_thenReturnNotFound() throws Exception {
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        UUID INVALID_BET_QUESTION_ID = UUID.randomUUID();
        String VALID_USERNAME = "username";
        float VALID_AMOUNT = 10;
        AddBetRequest addBetRequest = new AddBetRequest(VALID_USERNAME, VALID_AMOUNT, INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        doThrow(new BetQuestionNotFoundException(INVALID_BET_QUESTION_ID)).when(betService).checkIfCanPlaceBet(INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);

        mvc.perform(MockMvcRequestBuilders.post("/bets")
                .content(asJsonString(addBetRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(userService).checkIfCanPlaceBet(VALID_USERNAME, VALID_AMOUNT);
        verify(betService).checkIfCanPlaceBet(INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        verify(userService, never()).chargeUser(VALID_USERNAME, VALID_AMOUNT);
        verify(betService, never()).createBet(VALID_USERNAME, VALID_AMOUNT, INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}