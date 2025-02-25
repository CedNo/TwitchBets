package com.api.twitchbets.application;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.bet.BetQuestionRepository;
import com.api.twitchbets.domain.factories.BetOptionFactory;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

@Service
public class BetService {

    private final BetQuestionRepository betQuestionRepository;
    private final BetQuestionFactory betQuestionFactory;
    private final BetOptionFactory betOptionFactory;

    @Autowired
    public BetService(
        BetQuestionRepository betQuestionRepository,
        BetQuestionFactory betQuestionFactory,
        BetOptionFactory betOptionFactory
    ) {
        this.betQuestionRepository = betQuestionRepository;
        this.betQuestionFactory = betQuestionFactory;
        this.betOptionFactory = betOptionFactory;
    }

    public UUID createBetQuestion(String question, List<String> options) {
        List<BetOption> betOptions = betOptionFactory.createBetOptions(options);
        BetQuestion newQuestion = betQuestionFactory.createBetQuestion(question, betOptions);

        betQuestionRepository.addBetQuestion(newQuestion);

        return newQuestion.getId();
    }

    public BetQuestion getBetQuestion(UUID id) {
        return betQuestionRepository.getBetQuestion(id);
    }
}
