package com.api.twitchbets.interfaces.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bets")
public class BetController {

    private final Logger logger = LoggerFactory.getLogger(BetController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBet() {
        logger.info("Placing bet");

    }
}