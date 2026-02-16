package com.api.twitchbets.interfaces.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.twitchbets.application.BetService;
import com.api.twitchbets.application.PlayerService;
import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.Wager;
import com.api.twitchbets.interfaces.dto.requests.AddBetRequest;
import com.api.twitchbets.interfaces.dto.responses.BetResponse;
import com.api.twitchbets.interfaces.dto.responses.WagerResponse;
import com.api.twitchbets.interfaces.mappers.responses.BetResponseMapper;
import com.api.twitchbets.interfaces.mappers.responses.WagerResponseMapper;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/bets")
public class BetController {

    private final Logger logger = LoggerFactory.getLogger(BetController.class);
    private final BetService betService;
    private final PlayerService playerService;
    private final BetResponseMapper betResponseMapper;
    private final WagerResponseMapper wagerResponseMapper;

    public BetController(
        BetService betService,
        PlayerService playerService,
        BetResponseMapper betResponseMapper,
        WagerResponseMapper wagerResponseMapper
    ) {
        this.betService = betService;
        this.playerService = playerService;
        this.betResponseMapper = betResponseMapper;
        this.wagerResponseMapper = wagerResponseMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBet(@RequestBody AddBetRequest request) {
        logger.info("Placing bet");

        try {
            playerService.checkIfCanPlaceBet(request.username(), request.amount());
            betService.checkIfCanPlaceBet(request.betQuestionId(), request.betOptionId());

            playerService.chargePlayer(request.username(), request.amount());
            betService.createBet(request.username(), request.amount(), request.betQuestionId(), request.betOptionId());
        } catch (Exception e) {
            logger.error("Error placing bet");
            throw e;
        }
    }

    @GetMapping("/{username}/history")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BetResponse> getUserBetHistory(@PathVariable String username) {
        logger.info("Getting bet history for user: {}", username);

        playerService.getPlayer(username);

        List<Bet> betHistory = betService.getBetsByUsername(username);

        List<BetResponse> response = betResponseMapper.toResponseList(betHistory);

        return response;
    }

    @GetMapping("/{username}/latest")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<WagerResponse> getLatestUserBets(
        @PathVariable String username,
        @RequestParam(defaultValue = "10") @Min(value = 0, message = "Limit must be greater than zero.") int limit
    ) {
        logger.info("Getting latest bets for user: {}", username);

        playerService.getPlayer(username);

        List<Wager> latestBets = betService.getLatestWagersByUsername(username, limit);

        List<WagerResponse> response = wagerResponseMapper.toResponseList(latestBets);

        return response;
    }
}