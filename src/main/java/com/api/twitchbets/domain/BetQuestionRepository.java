package com.api.twitchbets.domain;

import java.util.List;
import java.util.UUID;

public interface BetQuestionRepository {

    BetQuestion getBetQuestion(UUID id);

    List<BetQuestion> getBetQuestions();

    void addBetQuestion(BetQuestion betQuestion);
}
