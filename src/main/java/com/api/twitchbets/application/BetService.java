package com.api.twitchbets.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.twitchbets.domain.BetQuestion;
import com.api.twitchbets.domain.BetQuestionRepository;

@Service
public class BetService {

    private final BetQuestionRepository betQuestionRepository;

    @Autowired
    public BetService(final BetQuestionRepository betQuestionRepository) {
        this.betQuestionRepository = betQuestionRepository;
    }

    //TODO: Validate that BetQuestion's id is not already taken.
    public void createBetQuestion() {
        betQuestionRepository.addBetQuestion(new BetQuestion()); //TODO: Convert to factory
    }
}
