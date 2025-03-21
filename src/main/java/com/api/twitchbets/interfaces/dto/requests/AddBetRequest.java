package com.api.twitchbets.interfaces.dto.requests;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddBetRequest (
    @JsonProperty
    String username,
    @JsonProperty
    float amount,
    @JsonProperty
    UUID betQuestionId,
    @JsonProperty
    UUID betOptionId
) {

}
