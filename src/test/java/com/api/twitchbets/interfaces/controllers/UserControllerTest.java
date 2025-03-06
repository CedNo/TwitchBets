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

import com.api.twitchbets.application.UserService;
import com.api.twitchbets.interfaces.mappers.responses.UserResponseMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final String VALID_USERNAME = "username";

    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private UserResponseMapper userResponseMapper;

    @Test
    void whenCreateUser_thenReturnCreatedStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users/" + VALID_USERNAME).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(userService).createUser(VALID_USERNAME);
    }

    @Test
    void whenGetUser_thenReturnUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/" + VALID_USERNAME).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(userService).getUser(VALID_USERNAME);
        verify(userResponseMapper).toResponse(any());
    }

    @Test
    void givenId_whenGetUser_thenUserServiceFetchUserAndMapperAssemblesResponse() throws Exception {
        when(userService.getUser(any())).thenReturn(mock());

        mvc.perform(MockMvcRequestBuilders.get("/users/{username}", VALID_USERNAME)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(userService).getUser(any());
        verify(userResponseMapper).toResponse(any());
    }
}