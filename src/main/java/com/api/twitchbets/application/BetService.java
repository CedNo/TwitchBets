package com.api.twitchbets.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.bet.BetQuestionRepository;
import com.api.twitchbets.domain.exceptions.BetOptionNotFoundException;
import com.api.twitchbets.domain.factories.BetFactory;
import com.api.twitchbets.domain.factories.BetOptionFactory;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

@Service
public class BetService {

    private final BetQuestionRepository betQuestionRepository;
    private final BetQuestionFactory betQuestionFactory;
    private final BetOptionFactory betOptionFactory;
    private final BetFactory betFactory;

    @Autowired
    public BetService(
        BetQuestionRepository betQuestionRepository,
        BetQuestionFactory betQuestionFactory,
        BetOptionFactory betOptionFactory,
        BetFactory betFactory
    ) {
        this.betQuestionRepository = betQuestionRepository;
        this.betQuestionFactory = betQuestionFactory;
        this.betOptionFactory = betOptionFactory;
        this.betFactory = betFactory;
    }

    public UUID createBetQuestion(String question, List<String> options, LocalDateTime endTime) {
        List<BetOption> betOptions = betOptionFactory.createBetOptions(options);
        BetQuestion newQuestion = betQuestionFactory.createBetQuestion(question, betOptions, endTime);

        betQuestionRepository.addBetQuestion(newQuestion);

        return newQuestion.getId();
    }

    public BetQuestion getBetQuestion(UUID id) {
        return betQuestionRepository.getBetQuestion(id);
    }

    public void checkIfCanPlaceBet(UUID betQuestionId, UUID betOptionId) {
        BetQuestion betQuestion = betQuestionRepository.getBetQuestion(betQuestionId);
        boolean betQuestionHasOption = betQuestion.hasOption(betOptionId);

        if(!betQuestionHasOption) {
            throw new BetOptionNotFoundException(betOptionId);
        }
    }

    public void createBet(String username, float amount, UUID betQuestionId, UUID betOptionId) {
        Bet bet = betFactory.createBet(username, amount);
        BetQuestion betQuestion = betQuestionRepository.getBetQuestion(betQuestionId);

        betQuestion.placeBet(betOptionId, bet);

        betQuestionRepository.updateBetQuestion(betQuestion);
    }
}
