package com.api.twitchbets.interfaces.dto.requests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddBetQuestionRequest(
    @JsonProperty String question,
    @JsonProperty List<String> options
) {

}
