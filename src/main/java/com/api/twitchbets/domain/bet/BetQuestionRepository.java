package com.api.twitchbets.domain.bet;

import java.util.List;
import java.util.UUID;

public interface BetQuestionRepository {

    BetQuestion getBetQuestion(UUID id);

    List<BetQuestion> getBetQuestions();

    void addBetQuestion(BetQuestion betQuestion);
}
