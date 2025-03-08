package com.api.twitchbets.interfaces.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.twitchbets.application.BetService;
import com.api.twitchbets.application.UserService;
import com.api.twitchbets.interfaces.dto.requests.AddBetRequest;

@RestController
@RequestMapping("/bets")
public class BetController {

    private final Logger logger = LoggerFactory.getLogger(BetController.class);
    private final BetService betService;
    private final UserService userService;

    public BetController(BetService betService, UserService userService) {
        this.betService = betService;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBet(@RequestBody AddBetRequest request) {
        logger.info("Placing bet");

        try {
            userService.checkIfCanPlaceBet(request.username(), request.amount());
            betService.checkIfCanPlaceBet(request.betQuestionId(), request.betOptionId());

            userService.chargeUser(request.username(), request.amount());
            betService.createBet(request.username(), request.amount(), request.betQuestionId(), request.betOptionId());
        } catch (Exception e) {
            logger.error("Error placing bet");
            throw e;
        }

        //todo:    ** test **
    }
}