package com.api.twitchbets.domain.factories;

import java.util.List;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.BetOption;
import com.api.twitchbets.domain.BetQuestion;

@Component
public class BetQuestionFactory {

    public BetQuestion createBetQuestion(String question, List<BetOption> options) {
        return new BetQuestion(question, options);
    }
}
