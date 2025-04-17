package com.api.twitchbets.domain.factories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;

@Component
public class BetQuestionFactory {

    public BetQuestion createBetQuestion(String question, List<BetOption> options, LocalDateTime endTime) {
        return new BetQuestion(UUID.randomUUID(), question, options, endTime);
    }
}
