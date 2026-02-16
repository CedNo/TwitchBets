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

import com.api.twitchbets.application.PlayerService;
import com.api.twitchbets.domain.user.Player;
import com.api.twitchbets.interfaces.dto.responses.PlayerResponse;
import com.api.twitchbets.interfaces.mappers.responses.PlayerResponseMapper;

@RestController
@RequestMapping("/users")
public class PlayerController {

    private final Logger logger = LoggerFactory.getLogger(BetController.class);
    private final PlayerService playerService;
    private final PlayerResponseMapper playerResponseMapper;

    public PlayerController(
        PlayerService playerService,
        PlayerResponseMapper playerResponseMapper
    ) {
        this.playerService = playerService;
        this.playerResponseMapper = playerResponseMapper;
    }

    @PostMapping("/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(@PathVariable String username) {
        logger.info("Creating user : {}", username);

        playerService.createPlayer(username);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlayerResponse getUser(@PathVariable String username) {
        logger.info("Getting user : {}", username);

        Player player = playerService.getPlayer(username);
        PlayerResponse response = playerResponseMapper.toResponse(player);

        return response;
    }
}
