package com.api.twitchbets.interfaces.dto.requests;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddBetQuestionRequest(
    @JsonProperty String question,
    @JsonProperty List<String> options,
    @JsonProperty LocalDateTime endTime
) {

}
