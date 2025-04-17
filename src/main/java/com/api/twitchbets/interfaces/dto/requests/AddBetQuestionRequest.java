package com.api.twitchbets.interfaces.dto.requests;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AddBetQuestionRequest(
    @JsonProperty
    String question,

    @JsonProperty
    List<String> options,

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime
) {

}
