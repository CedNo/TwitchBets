package com.api.twitchbets.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.twitchbets.domain.BetQuestion;
import com.api.twitchbets.domain.BetQuestionRepository;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

@Service
public class BetService {

    private final BetQuestionRepository betQuestionRepository;
    private final BetQuestionFactory betQuestionFactory;

    @Autowired
    public BetService(
        BetQuestionRepository betQuestionRepository,
        BetQuestionFactory betQuestionFactory
    ) {
        this.betQuestionRepository = betQuestionRepository;
        this.betQuestionFactory = betQuestionFactory;
    }

    public void createBetQuestion() {
        BetQuestion newQuestion = betQuestionFactory.createBetQuestion();

        betQuestionRepository.addBetQuestion(newQuestion);
    }
}
