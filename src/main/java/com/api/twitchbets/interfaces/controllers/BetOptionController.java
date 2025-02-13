package com.api.twitchbets.interfaces.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bet")
public class BetOptionController {

    private final Logger logger = LoggerFactory.getLogger(BetQuestionController.class);

    @PostMapping("/option/create")
    @ResponseStatus(HttpStatus.OK)
    public void createBetOption() {
        logger.info("Creating new option for question");

    }
}