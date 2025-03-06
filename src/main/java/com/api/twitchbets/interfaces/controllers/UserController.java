package com.api.twitchbets.interfaces.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.twitchbets.application.UserService;
import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.interfaces.dto.responses.UserResponse;
import com.api.twitchbets.interfaces.mappers.responses.UserResponseMapper;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(BetController.class);
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;

    public UserController (
        UserService userService,
        UserResponseMapper userResponseMapper
    ) {
        this.userService = userService;
        this.userResponseMapper = userResponseMapper;
    }

    @PostMapping("/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@PathVariable String username) {
        logger.info("Creating user : {}", username);

        userService.createUser(username);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserResponse getUser(@PathVariable String username) {
        logger.info("Getting user : {}", username);

        User user = userService.getUser(username);
        UserResponse response = userResponseMapper.toResponse(user);

        return response;
    }
}
