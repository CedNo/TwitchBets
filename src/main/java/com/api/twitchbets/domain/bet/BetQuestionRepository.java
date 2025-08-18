package com.api.twitchbets.domain.bet;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

public interface BetQuestionRepository {

    BetQuestion getBetQuestion(UUID id);

    List<BetQuestion> getBetQuestions();

    void addBetQuestion(BetQuestion betQuestion);

    void updateBetQuestion(BetQuestion betQuestion);

    List<BetQuestion> getEndingBetQuestions(int amount, Clock clock);

    List<Bet> getBetsByUsername(String username);
}
