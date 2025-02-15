package com.api.twitchbets.infrastructure.persistence.memory;

import java.util.ArrayList;
import java.util.List;

import com.api.twitchbets.domain.BetQuestion;
import com.api.twitchbets.domain.BetQuestionRepository;

public class InMemoryBetQuestionRepository implements BetQuestionRepository {

    private static List<BetQuestion> betQuestions;

    public InMemoryBetQuestionRepository() {
        betQuestions = new ArrayList<>();
    }

    @Override
    public List<BetQuestion> getBetQuestions() {
        return betQuestions;
    }

    @Override
    public void addBetQuestion(BetQuestion betQuestion) {
        betQuestions.add(betQuestion);
    }
}
