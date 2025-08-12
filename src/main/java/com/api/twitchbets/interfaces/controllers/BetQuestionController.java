package com.api.twitchbets.interfaces.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.twitchbets.application.BetService;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.interfaces.dto.requests.AddBetQuestionRequest;
import com.api.twitchbets.interfaces.dto.responses.BetQuestionResponse;
import com.api.twitchbets.interfaces.mappers.responses.BetQuestionResponseMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/bets/questions")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> createBetQuestion(@Valid @RequestBody AddBetQuestionRequest request) {
        logger.info("Creating new question: {}", request.question());

        UUID id = betService.createBetQuestion(request.question(), request.options(), request.endTime());

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BetQuestionResponse getBetQuestion(@PathVariable UUID id) {
        logger.info("Getting bet question: {}", id);

        BetQuestion betQuestion = betService.getBetQuestion(id);
        BetQuestionResponse response = betQuestionResponseMapper.toResponse(betQuestion);

        return response;
    }

    @GetMapping ("/ending")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BetQuestionResponse> getEndingBetQuestions(@Min(value = 1, message = "Must fetch at least 1 ending bet question.") @RequestParam("amount") int amount) {
        logger.info("Getting {} ending bet questions...", amount);

        List<BetQuestion> endingBetQuestions = betService.getEndingBetQuestions(amount);
        List<BetQuestionResponse> response = betQuestionResponseMapper.toResponseList(endingBetQuestions);

        return response;
    }
}