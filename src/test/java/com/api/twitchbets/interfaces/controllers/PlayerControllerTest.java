package com.api.twitchbets.interfaces.controllers;

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

import com.api.twitchbets.application.PlayerService;
import com.api.twitchbets.interfaces.mappers.responses.PlayerResponseMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    private static final String VALID_USERNAME = "username";

    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private PlayerService playerService;
    @MockitoBean
    private PlayerResponseMapper playerResponseMapper;

    @Test
    void whenCreatePlayer_thenReturnCreatedStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/players/" + VALID_USERNAME).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(playerService).createPlayer(VALID_USERNAME);
    }

    @Test
    void whenGetPlayer_thenReturnPlayer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/players/" + VALID_USERNAME).accept(MediaType.APPLICATION_JSON))
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
}