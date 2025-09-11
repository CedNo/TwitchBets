package com.api.twitchbets.interfaces.dto.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.api.twitchbets.domain.bet.BetQuestion;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BetQuestionResponse {
    @JsonProperty
    public final UUID id;
    @JsonProperty
    public final String question;
    @JsonProperty
    public final List<BetOptionResponse> options;
    @JsonProperty
    public final LocalDateTime endTime;

    public BetQuestionResponse(BetQuestion betQuestion) {
        this.id = betQuestion.getId();
        this.question = betQuestion.getQuestion();
        this.options = betQuestion.getOptions().stream().map(BetOptionResponse::new).toList();
        this.endTime = betQuestion.getEndTime();
    }
}
