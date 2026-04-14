package com.api.twitchbets.interfaces.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.twitchbets.application.services.PlayerService;
import com.api.twitchbets.interfaces.dto.requests.AddPlayerRequest;
import com.api.twitchbets.interfaces.dto.requests.PlayerLoginRequest;
import com.api.twitchbets.interfaces.mappers.responses.PlayerResponseMapper;

import static com.testutils.TestUtilities.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    private static final String VALID_USERNAME = "username";
    private static final String VALID_PASSWORD = "password1";

    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private PlayerService playerService;
    @MockitoBean
    private PlayerResponseMapper playerResponseMapper;

    @Test
    void whenCreatePlayerWithValidBody_thenReturnCreatedStatus() throws Exception {
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(VALID_USERNAME, VALID_PASSWORD, VALID_PASSWORD);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
            .content(asJsonString(addPlayerRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(playerService).createPlayer(VALID_USERNAME, VALID_PASSWORD);
    }

    @Test
    void whenCreatePlayerWithInvalidCharactersInUsername_thenReturnBadRequestStatus() throws Exception {
        String invalidCharacterUsername = "$invalidusername";
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(invalidCharacterUsername, VALID_PASSWORD, VALID_PASSWORD);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
                .content(asJsonString(addPlayerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreatePlayerWithEmptyUsername_thenReturnBadRequestStatus() throws Exception {
        String emptyUsername = "";
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(emptyUsername, VALID_PASSWORD, VALID_PASSWORD);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
                .content(asJsonString(addPlayerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreatePlayerWithTooLongUsername_thenReturnBadRequestStatus() throws Exception {
        String tooLongUsername = "aaaaaaaaaaaaaaaaaaaaa";
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(tooLongUsername, VALID_PASSWORD, VALID_PASSWORD);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
                .content(asJsonString(addPlayerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreatePlayerWithNonMatchingPasswords_thenReturnBadRequestStatus() throws Exception {
        String password = "password";
        String confirmPassword = "nonmatchingpassword";
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(VALID_USERNAME, password, confirmPassword);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
                .content(asJsonString(addPlayerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreatePlayerWithBlankPassword_thenReturnBadRequestStatus() throws Exception {
        String password = "";
        String confirmPassword = "";
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(VALID_USERNAME, password, confirmPassword);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
                .content(asJsonString(addPlayerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreatePlayerWithTooShortPassword_thenReturnBadRequestStatus() throws Exception {
        String password = "1234567";
        String confirmPassword = "1234567";
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(VALID_USERNAME, password, confirmPassword);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
                .content(asJsonString(addPlayerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreatePlayerWithTooLongPassword_thenReturnBadRequestStatus() throws Exception {
        String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String confirmPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(VALID_USERNAME, password, confirmPassword);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
                .content(asJsonString(addPlayerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPlayer_thenReturnPlayer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/players/" + VALID_USERNAME)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(playerService).getPlayer(VALID_USERNAME);
        verify(playerResponseMapper).toResponse(any());
    }

    @Test
    void givenId_whenGetPlayer_thenPlayerServiceFetchPlayerAndMapperAssemblesResponse() throws Exception {
        when(playerService.getPlayer(any())).thenReturn(mock());

        mvc.perform(MockMvcRequestBuilders.get("/players/{username}", VALID_USERNAME)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(playerService).getPlayer(any());
        verify(playerResponseMapper).toResponse(any());
    }

    @Test
    void givenUsernameAndPassword_whenLoginPlayer_thenPlayerServiceLoginPlayer() throws Exception {
        PlayerLoginRequest playerLoginRequest = new PlayerLoginRequest(VALID_USERNAME, VALID_PASSWORD);

         mvc.perform(MockMvcRequestBuilders.post("/players/login")
                .content(asJsonString(playerLoginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(playerService).loginPlayer(eq(VALID_USERNAME), eq(VALID_PASSWORD), any(), any());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenCreatePlayer_thenReturnCreated() throws Exception {
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(VALID_USERNAME, VALID_PASSWORD, VALID_PASSWORD);

        mvc.perform(MockMvcRequestBuilders.post("/players/create")
                .content(asJsonString(addPlayerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenGetPlayer_thenReturnOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/players/" + VALID_USERNAME)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenLoginPlayer_thenReturnOk() throws Exception {
        PlayerLoginRequest playerLoginRequest = new PlayerLoginRequest(VALID_USERNAME, VALID_PASSWORD);

        mvc.perform(MockMvcRequestBuilders.post("/players/login")
                .content(asJsonString(playerLoginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}