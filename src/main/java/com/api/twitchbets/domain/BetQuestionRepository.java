package com.api.twitchbets.domain;

import java.util.List;

public interface BetQuestionRepository {

    List<BetQuestion> getBetQuestions();

    void addBetQuestion(BetQuestion betQuestion);
}
