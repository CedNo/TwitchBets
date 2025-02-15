package com.api.twitchbets.domain;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface BetQuestionRepository {

    List<BetQuestion> getBetQuestions();

    void addBetQuestion(BetQuestion betQuestion);
}
