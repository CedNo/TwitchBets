package com.api.twitchbets.interfaces.dto.responses;

import com.api.twitchbets.domain.bet.BetQuestion;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BetQuestionResponse {
    @JsonProperty
    public BetQuestion betQuestion;

    public BetQuestionResponse(BetQuestion betQuestion) {
        this.betQuestion = betQuestion;
    }
}
