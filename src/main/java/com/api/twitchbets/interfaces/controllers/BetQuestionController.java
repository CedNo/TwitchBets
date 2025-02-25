package com.api.twitchbets.interfaces.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.twitchbets.application.BetService;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.interfaces.dto.requests.AddBetQuestionRequest;
import com.api.twitchbets.interfaces.dto.responses.BetQuestionResponse;
import com.api.twitchbets.interfaces.mappers.responses.BetQuestionResponseMapper;

@RestController
@RequestMapping("/bet")
public class BetQuestionController {

    private final BetService betService;
    private final BetQuestionResponseMapper betQuestionResponseMapper;

    public BetQuestionController(
        BetService betService,
        BetQuestionResponseMapper betQuestionResponseMapper
    ) {
        this.betService = betService;
        this.betQuestionResponseMapper = betQuestionResponseMapper;
    }

    private final Logger logger = LoggerFactory.getLogger(BetQuestionController.class);

    @PostMapping("/question/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UUID createBetQuestion(@RequestBody AddBetQuestionRequest request) {
        logger.info("Creating new question: {}", request.question());

        UUID id = betService.createBetQuestion(request.question(), request.options());

        return id;
    }

    @GetMapping ("/question/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public BetQuestionResponse createBetQuestion(@PathVariable UUID id) {
        logger.info("Getting bet question: {}", id);

        BetQuestion betQuestion = betService.getBetQuestion(id);
        BetQuestionResponse response = betQuestionResponseMapper.toResponse(betQuestion);

        return response;
    }
}