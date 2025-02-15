package com.api.twitchbets.interfaces.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.twitchbets.application.BetService;

@RestController
@RequestMapping("/bet")
public class BetQuestionController {

    private final BetService betService;

    public BetQuestionController(BetService betService) {
        this.betService = betService;
    }

    private final Logger logger = LoggerFactory.getLogger(BetQuestionController.class);

    @PostMapping("/question/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBetQuestion() {
        logger.info("Creating new question");

        betService.createBetQuestion();
    }
}