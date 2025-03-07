package com.api.twitchbets.infrastructure.persistence.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.bet.BetQuestionRepository;
import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;

@Repository
public class InMemoryBetQuestionRepository implements BetQuestionRepository {

    private final List<BetQuestion> betQuestions;

    public InMemoryBetQuestionRepository() {
        betQuestions = new ArrayList<>();
    }

    @Override
    public BetQuestion getBetQuestion(UUID id) throws BetQuestionNotFoundException {
        for (BetQuestion betQuestion : betQuestions) {
            if (betQuestion.getId().equals(id)) {
                return betQuestion;
            }
        }

        throw new BetQuestionNotFoundException(id);
    }

    @Override
    public List<BetQuestion> getBetQuestions() {
        return betQuestions;
    }

    @Override
    public void addBetQuestion(BetQuestion betQuestion) {
        betQuestions.add(betQuestion);
    }

    @Override
    public void updateBetQuestion(BetQuestion betQuestion) {
        for (BetQuestion question : betQuestions) {
            if (question.getId().equals(betQuestion.getId())) {
                betQuestions.remove(question);
                betQuestions.add(betQuestion);
                return;
            }
        }
    }
}
