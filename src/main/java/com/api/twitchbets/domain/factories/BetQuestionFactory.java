package com.api.twitchbets.domain.factories;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.BetQuestion;

@Component
public class BetQuestionFactory {

    public BetQuestion createBetQuestion() {
        return new BetQuestion();
    }
}
