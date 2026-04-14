package com.api.twitchbets.interfaces.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.testutils.TestUtilities.asJsonString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.twitchbets.application.services.BetService;
import com.api.twitchbets.application.services.PlayerService;
import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;
import com.api.twitchbets.domain.exceptions.PlayerNotFoundException;
import com.api.twitchbets.interfaces.dto.requests.AddBetRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class BetControllerTest {

    @MockitoBean
    private PlayerService playerService;
    @MockitoBean
    private BetService betService;
    @Autowired
    private MockMvc mvc;

    private static final String VALID_USERNAME = "username";

    @Test
    @WithMockUser
    void givenValidParameters_whenCreateBet_thenCreateBetAndReturnCreatedStatus() throws Exception {
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        UUID VALID_BET_QUESTION_ID = UUID.randomUUID();
        float VALID_AMOUNT = 10;
        AddBetRequest addBetRequest = new AddBetRequest(VALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);

        mvc.perform(MockMvcRequestBuilders.post("/bets")
                .content(asJsonString(addBetRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(playerService).checkIfCanPlaceBet(VALID_USERNAME, VALID_AMOUNT);
        verify(betService).checkIfCanPlaceBet(VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        verify(playerService).chargePlayer(VALID_USERNAME, VALID_AMOUNT);
        verify(betService).createBet(VALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
    }

    @Test
    @WithMockUser
    void givenInvalidUsername_whenCreateBet_thenReturnNotFound() throws Exception {
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        UUID VALID_BET_QUESTION_ID = UUID.randomUUID();
        String INVALID_USERNAME = "invalid_username";
        float VALID_AMOUNT = 10;
        AddBetRequest addBetRequest = new AddBetRequest(INVALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        doThrow(new PlayerNotFoundException(INVALID_USERNAME)).when(playerService).checkIfCanPlaceBet(INVALID_USERNAME, VALID_AMOUNT);

        mvc.perform(MockMvcRequestBuilders.post("/bets")
                .content(asJsonString(addBetRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(playerService).checkIfCanPlaceBet(INVALID_USERNAME, VALID_AMOUNT);
        verify(betService, never()).checkIfCanPlaceBet(VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        verify(playerService, never()).chargePlayer(INVALID_USERNAME, VALID_AMOUNT);
        verify(betService, never()).createBet(INVALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
    }

    @Test
    @WithMockUser
    void givenInvalidBetQuestionId_whenCreateBet_thenReturnNotFound() throws Exception {
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        UUID INVALID_BET_QUESTION_ID = UUID.randomUUID();
        float VALID_AMOUNT = 10;
        AddBetRequest addBetRequest = new AddBetRequest(VALID_USERNAME, VALID_AMOUNT, INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        doThrow(new BetQuestionNotFoundException(INVALID_BET_QUESTION_ID)).when(betService).checkIfCanPlaceBet(INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);

        mvc.perform(MockMvcRequestBuilders.post("/bets")
                .content(asJsonString(addBetRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(playerService).checkIfCanPlaceBet(VALID_USERNAME, VALID_AMOUNT);
        verify(betService).checkIfCanPlaceBet(INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
        verify(playerService, never()).chargePlayer(VALID_USERNAME, VALID_AMOUNT);
        verify(betService, never()).createBet(VALID_USERNAME, VALID_AMOUNT, INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenCreateBet_thenReturnUnauthorized() throws Exception {
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        UUID VALID_BET_QUESTION_ID = UUID.randomUUID();
        float VALID_AMOUNT = 10;
        AddBetRequest addBetRequest = new AddBetRequest(VALID_USERNAME, VALID_AMOUNT, VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);

        mvc.perform(MockMvcRequestBuilders.post("/bets")
                .content(asJsonString(addBetRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void givenInvalidUsername_whenGetUserBetHistory_thenReturnNotFound() throws Exception {
        String INVALID_USERNAME = "invalid_username";
        doThrow(new PlayerNotFoundException(INVALID_USERNAME)).when(playerService).getPlayer(INVALID_USERNAME);

        mvc.perform(MockMvcRequestBuilders.get("/bets/" + INVALID_USERNAME + "/history")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(playerService).getPlayer(INVALID_USERNAME);
    }

    @Test
    @WithMockUser
    void givenValidUsername_whenGetUserBetHistory_thenReturnOk() throws Exception {
        Bet bet = new Bet(UUID.randomUUID(), VALID_USERNAME, 10.0f, LocalDateTime.now(), 0);
        List<Bet> betList = List.of(bet);

        when(betService.getBetsByUsername(VALID_USERNAME)).thenReturn(betList);
        when(playerService.getPlayer(VALID_USERNAME)).thenReturn(mock());

        mvc.perform(MockMvcRequestBuilders.get("/bets/" + VALID_USERNAME + "/history")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(playerService).getPlayer(VALID_USERNAME);
        verify(betService).getBetsByUsername(VALID_USERNAME);
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenGetUserBetHistory_thenReturnOk() throws Exception {
        Bet bet = new Bet(UUID.randomUUID(), VALID_USERNAME, 10.0f, LocalDateTime.now(), 0);
        List<Bet> betList = List.of(bet);

        when(betService.getBetsByUsername(VALID_USERNAME)).thenReturn(betList);
        when(playerService.getPlayer(VALID_USERNAME)).thenReturn(mock());

        mvc.perform(MockMvcRequestBuilders.get("/bets/" + VALID_USERNAME + "/history")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUserAndUsername_whenGetLatestUserBets_thenReturnOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/bets/" + VALID_USERNAME + "/history")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}